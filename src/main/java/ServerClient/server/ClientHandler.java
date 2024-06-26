package ServerClient.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private Socket socket;
    private Server server;
    private Scanner input;
    private String username;
    private int nelectrode;


    public ClientHandler(Socket socket, Server server){
        try {
            this.socket = socket;
            this.server = server;
            input = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            boolean connected = isConnected();

            if (!connected) {
                return;
            }
            join();
            comunicate();
            disconnect();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isConnected() throws IOException {
        if (input.hasNextLine()) {
            username = input.nextLine();
            return true;
        }
        return false;
    }

    private void join() {
        server.addClient(username, this);
    }


    private void comunicate() throws IOException {
        String clientMessage;
        boolean connected = true;

        while (connected) {
            if (input.hasNextLine()) {
                clientMessage = input.nextLine();
                connected = parseClientMessage(clientMessage);
            } else {
                connected = false;
            }
        }
    }

    public boolean parseClientMessage(String clientMessage) {
        if(!clientMessage.equalsIgnoreCase("BYE")) {
            server.process(clientMessage, username);
            return true;
        }
        return false;
    }

    private void disconnect() throws IOException {
        server.removeClient(username);
        socket.close();
    }

    public int getAndIncrease(){
        int electrode = this.nelectrode;
        nelectrode++;
        return electrode;
    }

}
