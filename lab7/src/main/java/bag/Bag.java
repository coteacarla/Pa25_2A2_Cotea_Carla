package bag;

import tile.Tile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Bag {
    private final List<Tile> tiles = new ArrayList<>();

    public Bag() {
        Random rand = new Random();
        for (char c = 'a'; c <= 'z'; c++) {
            int score = rand.nextInt(10) + 1;
            for (int i = 0; i < 10; i++) {
                tiles.add(new Tile(c, score));
            }
        }
    }
    public synchronized List<Tile> extractTiles(int howMany) {
        List<Tile> extracted = new ArrayList<>();
        for (int i = 0; i < howMany && !tiles.isEmpty(); i++) {
            extracted.add(tiles.remove(0));
        }
        return extracted;
    }

    public boolean isEmpty() {
        return tiles.isEmpty();
    }
}
