package player;

import game.Game;
import tile.Tile;
import dictionary.MockDictionary;

import java.util.List;

public class Player implements Runnable {
    private String name;
    private Game game;
    private boolean running;
    private int score = 0;
    public Player(String name) { this.name = name; }

    private boolean submitWord() {
        List<Tile> extracted = game.getBag().extractTiles(7);
        if (extracted.isEmpty()) {
            return false;
        }
        StringBuilder wordBuilder = new StringBuilder();
        int wordScore = 0;
        for (Tile tile : extracted) {
            wordBuilder.append(tile.getLetter());
            wordScore += tile.getPoints();
        }
        String word = wordBuilder.toString();
        if(game.getDictionary().isWord(word)){
            game.getBoard().addWord(this, word);
            score += wordScore;
        }
        try{
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return true;
    }

    public String getName() { return name; }
    public void setGame(Game game) {
        this.game = game;
    }


    @Override
    public void run() {
        while(!game.getBag().isEmpty()){
            boolean submitWord = submitWord();
            if(!submitWord){
                break;
            }
        }
        System.out.println(name + " finished with score: " + score);
    }

}