package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class GameServer {
    private static boolean running = true;
    public static final int PORT = 12345;
    public static GameManager gameManager = new GameManager();

    public static void main(String[] args) {
        System.out.println("Hex Game Server starting on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (running) {
                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopServer() {
        running = false;
    }
}
