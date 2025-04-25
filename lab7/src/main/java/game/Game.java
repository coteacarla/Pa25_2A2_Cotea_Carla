package game;

import bag.Bag;
import board.Board;
import dictionary.FileDictionary;
import dictionary.MockDictionary;
import player.Player;
import dictionary.Dictionary;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Bag bag = new Bag();
    private final Board board = new Board();
    private FileDictionary dictionary;
    private final List<Player> players = new ArrayList<>();
    private final Object lock = new Object();
    private int currentPlayerIndex = 0;
    private volatile boolean running = true;

    public Game(String wordFile) throws IOException {
        this.dictionary = new FileDictionary(wordFile);
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }
    public Bag getBag() {
        return bag;
    }
    public Board getBoard() {
        return board;
    }
    public Dictionary getDictionary() {
        return dictionary;
    }
    public Object getLock() {
        return lock;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public boolean isRunning() {
        return running;
    }

    public void stopGame() {
        running = false;
    }

    public void play() {
        for (Player player : players) {
            new Thread(player).start();
        }
        Thread timeKeeper = new Thread(() -> {
            long start = System.currentTimeMillis();
            int timeLimitSeconds = 10;

            while(isRunning()) {
                long elapsed = (System.currentTimeMillis() - start) / 1000;
                System.out.println("Time elapsed: " + elapsed + " seconds");
                if(elapsed >= timeLimitSeconds) {
                    System.out.println("Time limit reached! Ending game...");
                    stopGame();
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                    break;
                }
                try{
                    Thread.sleep(1000);
                }
                catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        timeKeeper.setDaemon(true);
        timeKeeper.start();
    }
    public static void main(String args[]) throws IOException {
        String wordListFile = "C:/Users/Carla/Desktop/words.txt";
        Game game = new Game(wordListFile);
        game.addPlayer(new Player("Player 1", 0));
        game.addPlayer(new Player("Player 2", 1));
        game.addPlayer(new Player("Player 3", 2));
        game.play();
    }
}
