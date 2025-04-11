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
    private MockDictionary dictionary;
    private final List<Player> players = new ArrayList<>();

    public Game(String wordFile) throws IOException {
        this.dictionary = new MockDictionary();
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
    public void play() {
        for (Player player : players) {
            new Thread(player).start();
        }
    }
    public static void main(String args[]) throws IOException {
        String wordListFile = "C:/Users/User/Documents/words.txt";
        Game game = new Game(wordListFile);
        game.addPlayer(new Player("Player 1"));
        game.addPlayer(new Player("Player 2"));
        game.addPlayer(new Player("Player 3"));
        game.play();
    }
}
