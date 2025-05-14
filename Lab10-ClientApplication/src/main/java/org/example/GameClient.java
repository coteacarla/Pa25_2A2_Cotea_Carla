package org.example;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter command: ");
                String command = scanner.nextLine();

                if ("exit".equalsIgnoreCase(command)) {
                    System.out.println("Client exited.");
                    break;
                }

                try (
                        Socket socket = new Socket(host, port);
                        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ) {
                    output.println(command);
                    String response = input.readLine();
                    System.out.println("Response: " + response);
                } catch (IOException e) {
                    System.err.println("Server not available: " + e.getMessage());
                }
            }
        }
    }
}
