package ServerClient.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Client {
    private ServerHandler serverHandler;
    BufferedReader reader;

    public Client() {
        serverHandler = new ServerHandler("localhost", 2135);
    }

    public void sendData(String name, String filepath){
        serverHandler.send(name);
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line;
            while((line = reader.readLine()) != null){
                serverHandler.send(line);
                Thread.sleep(2000);
            }
            serverHandler.send("BYE");
            serverHandler.close();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        Client client = new Client();
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        String filepath = in.nextLine();
        client.sendData(name,filepath);
    }
}
