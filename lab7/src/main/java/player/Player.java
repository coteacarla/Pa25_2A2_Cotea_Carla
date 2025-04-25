package player;

import game.Game;
import tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements Runnable {
    private String name;
    private static Game game;
    private int score = 0;
    private final int playerIndex;

    public Player(String name, int index) {
        this.name = name;
        this.playerIndex = index;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    private boolean submitWord() {
        Random rand = new Random();
        List<Tile> extracted = game.getBag().extractTiles(rand.nextInt(10));
        if (extracted.isEmpty()) {
            return false;
        }

        StringBuilder wordBuilder = new StringBuilder();
        int wordScore = 0;
        for (Tile tile : extracted) {
            wordBuilder.append(tile.getLetter());
            wordScore += tile.getPoints();
        }

        System.out.println(wordBuilder.toString());

        List<String> foundWords = new ArrayList<>();
        permute(extracted, 0, foundWords);

        if (!foundWords.isEmpty()) {
            String validWord = foundWords.get(0);
            game.getBoard().addWord(this, validWord);
            score += wordScore;
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return true;
    }

    @Override
    public void run() {
        while (game.isRunning() && !game.getBag().isEmpty()) {
            synchronized (game.getLock()) {
                while (game.isRunning() && game.getCurrentPlayerIndex() != playerIndex) {
                    try {
                        game.getLock().wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                if (!game.isRunning()) break;

                boolean wordSubmitted = submitWord();
                if (!wordSubmitted) break;

                System.out.println(name + " submitted word. Score: " + score);

                game.nextPlayer();
                game.getLock().notifyAll();
            }
        }

        System.out.println(name + " finished with score: " + score);
    }

    static void permute(List<Tile> extracted, int index, List<String> foundWords) {
        if (index == extracted.size()) {
            StringBuilder wordBuilder = new StringBuilder();
            for (Tile tile : extracted) {
                wordBuilder.append(tile.getLetter());
            }
            String word = wordBuilder.toString().toLowerCase();
            if(game.getDictionary().isWord(word)) {
                foundWords.add(word);
            }
            return;
        }

        for (int i = index; i < extracted.size(); i++) {
            Tile temp = extracted.get(i);
            extracted.set(i, extracted.get(index));
            extracted.set(index, temp);

            permute(extracted, index + 1, foundWords);
//please please please
            temp = extracted.get(i);
            extracted.set(i, extracted.get(index));
            extracted.set(index, temp);
        }
    }
}
