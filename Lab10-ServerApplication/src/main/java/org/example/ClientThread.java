package org.example;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                if (line.equalsIgnoreCase("stop")) {
                    out.println("Server stopped");
                    GameServer.stopServer();
                    break;
                } else {
                    String response = GameServer.gameManager.processCommand(line, socket);
                    out.println(response);
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        }
    }
}
