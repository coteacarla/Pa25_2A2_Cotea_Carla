package org.example;

public class AIPlayer extends Player {
    public AIPlayer(char symbol) {
        super(null, symbol); // No socket needed for AI
    }

    public int[] decideMove(HexBoard board) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.get(i, j) == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
