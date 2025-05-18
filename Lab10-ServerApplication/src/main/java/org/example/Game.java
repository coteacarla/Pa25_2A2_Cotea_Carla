package org.example;

import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private String id;
    private Player player1;
    private Player player2;
    private HexBoard board = new HexBoard(11);
    private Player currentTurn;
    private long timePerPlayer = 300000;
    private Timer timer1 = new Timer();
    private Timer timer2 = new Timer();

    public Game(String id) {
        this.id = id;
    }

    public void setPlayer1(Player p) { this.player1 = p; this.currentTurn = p; }
    public void setPlayer2(Player p) { this.player2 = p; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }

    public boolean containsPlayer(Player p) {
        return p.equals(player1) || p.equals(player2);
    }

    public String makeMove(Player p, int x, int y) {
        if (p != currentTurn) return "Not your turn.";
        if (!board.placeMove(x, y, p.getSymbol())) return "Invalid move.";


        if (board.checkWin(p.getSymbol())) return "Player " + p.getSymbol() + " wins!";
        currentTurn = (currentTurn == player1) ? player2 : player1;
        return "Move accepted.";
    }

    public void startTimers() {
        startTimer(player1, timer1);
        startTimer(player2, timer2);
    }

    private void startTimer(Player p, Timer timer) {
        timer.schedule(new TimerTask() {
            long timeLeft = timePerPlayer;

            @Override
            public void run() {
                timeLeft -= 1000;
                if (timeLeft <= 0) {
                    timer.cancel();
                    System.out.println("Player " + p.getSymbol() + " time expired. They lose.");
                }
            }
        }, 0, 1000);
    }

    public String getBoardString() {
        return board.toString();
    }
}
