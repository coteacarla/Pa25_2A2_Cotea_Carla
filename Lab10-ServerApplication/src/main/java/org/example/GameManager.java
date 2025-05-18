package org.example;

import java.net.Socket;
import java.util.*;

public class GameManager {
    private Map<String, Game> games = new HashMap<>();
    private Map<Socket, Player> playerSockets = new HashMap<>();
    private int gameCounter = 0;

    public synchronized String processCommand(String command, Socket socket) {
        String[] parts = command.trim().split("\\s+");
        if (parts.length == 0) return "Empty command.";

        String cmd = parts[0].toLowerCase();

        try {
            switch (cmd) {
                case "create":
                        if (parts.length == 3 && parts[1].equalsIgnoreCase("vs") && parts[2].equalsIgnoreCase("ai")) {
                            return createGameVsAI(socket);
                        }
                        return createGame(socket);
                case "join":
                    if (parts.length < 2) return "Usage: join <gameId>";
                    return joinGame(parts[1], socket);
                case "move":
                    if (parts.length < 4) return "Usage: move <gameId> <x> <y>";
                    return submitMove(parts[1], parts[2], parts[3], socket);
                default:
                    return "Unknown command.";
            }
        } catch (Exception e) {
            return "Error processing command: " + e.getMessage();
        }
    }


    private String createGame(Socket socket) {
        String gameId = "game" + (++gameCounter);
        Game game = new Game(gameId);
        Player player = new Player(socket, 'X');
        game.setPlayer1(player);
        games.put(gameId, game);
        playerSockets.put(socket, player);
        return "Game created with ID: " + gameId;
    }

    private String joinGame(String gameId, Socket socket) {
        Game game = games.get(gameId);
        if (game == null) return "Game not found.";
        if (game.getPlayer2() != null) return "Game already has two players.";

        Player player = new Player(socket, 'O');
        game.setPlayer2(player);
        playerSockets.put(socket, player);
        game.startTimers();
        return "Joined game " + gameId;
    }



    private String submitMove(String gameId, String xStr, String yStr, Socket socket) {
        Game game = games.get(gameId);
        if (game == null) {
            return "Game not found: " + gameId;
        }

        Player player = playerSockets.get(socket);
        if (player == null || !game.containsPlayer(player)) {
            return "You are not part of this game.";
        }

        int x, y;
        try {
            x = Integer.parseInt(xStr);
            y = Integer.parseInt(yStr);
        } catch (NumberFormatException e) {
            return "Invalid coordinates. Use integers for x and y.";
        }

        String result = game.makeMove(player, x, y);
        String boardView = game.getBoardString();
        System.out.println(boardView);

        return result;
    }

    private String createGameVsAI(Socket socket) {
        String gameId = "game" + (++gameCounter);
        Game game = new Game(gameId);

        Player human = new Player(socket, 'X');
        AIPlayer ai = new AIPlayer('O');

        game.setPlayer1(human);
        game.setPlayer2(ai);
        games.put(gameId, game);
        playerSockets.put(socket, human);

        return "Game vs AI created with ID: " + gameId;
    }

}
