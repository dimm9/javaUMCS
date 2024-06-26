package ServerClient.server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private String defaultURL = "jdbc:sqlite:jdbc:sqlite:src/main/resources/usereeg.db";
    private ServerSocket serverSocket;
    private boolean running=false;
    private final Map<String, ClientHandler> clientHandlers = new HashMap<>();

    public void start(int port) {

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            running=true;
            while (running) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                new Thread(clientHandler).start();
            }
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String encodeImageToBase64(BufferedImage image) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void process(String message, String username) {
        int electrode = clientHandlers.get(username).getAndIncrease();
        System.out.println("processing: " + message + " [" + username + "] electrode: " + electrode);
        saveToDataBase(message, username, electrode);
    }

    public void removeClient(String userName) {
        ClientHandler userThread = clientHandlers.remove(userName);
        if (userThread != null) {
            System.out.println("The user " + userName + " finished");
        }
    }

    public void addClient(String userName, ClientHandler clientHandler) {
        clientHandlers.put(userName, clientHandler);
    }

    public void saveToDataBase(String message, String username, int electrode) {
        String chart = drawChart(message);
        String insertSQL = "INSERT INTO user_eeg(username, electrode_number, image) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(defaultURL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, electrode);
            pstmt.setString(3, chart);
            pstmt.executeUpdate();
            System.out.println("Data saved.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String drawChart(String message) {
        int height = 100;
        int width = 200;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        graphics.setColor(Color.RED);
        String[] datas = message.split(",");
        double[] data = Arrays.stream(datas).mapToDouble(Double::parseDouble).toArray();
        for (int i = 0; i < data.length; i++) {
            int y = height / 2 - (int) ((data[i]));
            graphics.drawRect(i, y, 2, 2);
        }
        return encodeImageToBase64(image);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(2135);
    }
}
