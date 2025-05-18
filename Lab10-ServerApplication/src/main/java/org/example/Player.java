package org.example;
import java.net.Socket;

public class Player {
    private Socket socket;
    private char symbol;

    public Player(Socket socket, char symbol) {
        this.socket = socket;
        this.symbol = symbol;
    }

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
