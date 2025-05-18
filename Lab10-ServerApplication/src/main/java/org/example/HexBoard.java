package org.example;
import java.util.HashSet;
import java.util.Set;

public class HexBoard {
    private char[][] board;
    private int size;

    public HexBoard(int size) {
        this.size = size;
        board = new char[size][size];
    }

    public boolean placeMove(int x, int y, char symbol) {
        if (x < 0 || y < 0 || x >= size || y >= size || board[x][y] != 0)
            return false;
        board[x][y] = symbol;
        return true;
    }

    public boolean checkWin(char symbol) {
        Set<String> visited = new HashSet<>();

        if (symbol == 'X') {
            for (int row = 0; row < size; row++) {
                if (board[row][0] == 'X' && dfs(row, 0, symbol, visited)) {
                    return true;
                }
            }
        } else if (symbol == 'O') {
            for (int col = 0; col < size; col++) {
                if (board[0][col] == 'O' && dfs(0, col, symbol, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(int x, int y, char symbol, Set<String> visited) {
        if (symbol == 'X' && y == size - 1) return true;
        if (symbol == 'O' && x == size - 1) return true;

        String key = x + "," + y;
        if (visited.contains(key)) return false;
        visited.add(key);

        int[][] directions = {
                {-1, 0}, {-1, 1}, {0, -1},
                {0, 1}, {1, -1}, {1, 0}
        };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (isValid(newX, newY) && board[newX][newY] == symbol) {
                if (dfs(newX, newY, symbol, visited)) {
                    return true;
                }
            }
        }

        return false;
    }


    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < size && y < size;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nCurrent board:\n");
        for (int i = 0; i < size; i++) {
            sb.append(" ".repeat(i));
            for (int j = 0; j < size; j++) {
                char c = board[i][j];
                sb.append((c == 0 ? '.' : c)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean isCellEmpty(int i, int j) {
            return board[i][j] == 0;
    }

    public int getSize() {
        return size;
    }

    public char get(int x, int y) {
        return board[x][y];
    }

}

