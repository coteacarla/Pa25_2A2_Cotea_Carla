package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.SpanningTreeGenerator;

public class DrawingPanel extends JPanel {
    final MainFrame frame;
    private final int canvasWidth = 800, canvasHeight = 400;
    private final int dotSize = 10;
    private BufferedImage image;
    private Graphics2D offscreen;
    private final List<Dot> dots = new ArrayList<>();
    private Dot firstSelectedDot = null;
    private Dot secondSelectedDot = null;
    private final List<Line> lines= new ArrayList<>();
    private Player player1=new Player();
    private Player player2=new Player();
    private int turn=1;
    public boolean player1IsAI = false;
    public boolean player2IsAI = false;
    public int player1Difficulty = 1;
    public int player2Difficulty = 1;
    boolean gameOverCalled = false;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        createOffscreenImage();
        initMouseListener();
    }

    public void createOffscreenImage() {
        image = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);
        offscreen = image.createGraphics();
        offscreen.setColor(Color.white);
        offscreen.fillRect(0, 0, canvasWidth, canvasHeight);
    }


    private void initMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if ((turn == 1 && !player1IsAI) || (turn == 2 && !player2IsAI)) {
                    for (Dot dot : dots) {
                        if (dot.contains(e.getX(), e.getY(), dotSize)) {
                            if (firstSelectedDot == null) {
                                firstSelectedDot = dot;
                            } else {
                                secondSelectedDot = dot;
                                lines.add(new Line(firstSelectedDot, secondSelectedDot));
                                if(turn==1)
                                    player1.addScore(firstSelectedDot,secondSelectedDot);
                                else
                                    player2.addScore(firstSelectedDot,secondSelectedDot);
                                turn=3-turn;
                                repaint();
                                firstSelectedDot = null;
                                secondSelectedDot = null;
                                if(checkGameOver()){
                                    gameOver();
                                }
                                return;
                            }
                        }
                    }
                }
            }
        });
    }

    public void generateDots(int numDots) {
        dots.clear();
        for (int i = 0; i < numDots; i++) {
            int x = (int) (Math.random() * canvasWidth);
            int y = (int) (Math.random() * canvasHeight);
            dots.add(new Dot(x, y));
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        paintDots(g);
        drawLines(g);
        if (checkGameOver() && !gameOverCalled) {
            gameOverCalled = true;
            gameOver();
        }
        if ((turn == 1 && player1IsAI) || (turn == 2 && player2IsAI)) {
            if (turn == 1) {
                aiMove(player1Difficulty);
            } else {
                aiMove(player2Difficulty);
            }

            repaint();
        }
    }

    private void paintDots(Graphics g) {
        g.setColor(Color.pink);
        for (Dot dot : dots) {
            g.fillOval(dot.x - dotSize / 2, dot.y - dotSize / 2, dotSize, dotSize);
        }
    }

    private void drawLines(Graphics g) {
            g.setColor(Color.pink);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            for (Line line : lines) {
                Dot start = line.dot1;
                Dot end = line.dot2;
                g2d.drawLine(start.x, start.y, end.x, end.y);
            }
            repaint();
    }

    public List<Dot> getDots() {
        return dots;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setDots(List<Dot> newDots) {
        dots.clear();
        dots.addAll(newDots);
        repaint();
    }

    public void setLines(List<Line> newLines) {
        clearLines();
        lines.addAll(newLines);
        repaint();
    }
    public void clearLines() {
        lines.clear();
    }
    public void gameOver() {
        int player1Score = player1.getScore();
        int player2Score = player2.getScore();

        String message;
        if (player1Score > player2Score) {
            message = "Player 1 wins! Score: " + player1Score + " vs. " + player2Score;
        } else if (player2Score > player1Score) {
            message = "Player 2 wins! Score: " + player2Score + " vs " + player1Score;
        } else {
            message = "It's a tie! Score: " + player1Score;
        }

        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);

        lines.clear();
        player1 = new Player();
        player2 = new Player();
        turn = 1;
        repaint();
    }

    private boolean checkGameOver(){
        if(dots.size() < 2) return false;

        for(int i = 0; i < dots.size(); i++){
            for(int j = i + 1; j < dots.size(); j++){
                Line line = new Line(dots.get(i), dots.get(j));
                Line reversedLine = new Line(dots.get(j), dots.get(i));
                if(!lines.contains(line) && !lines.contains(reversedLine)){
                    return false;
                }
            }
        }
        return true;
    }
    public BufferedImage createImage() {

        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();


        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        paint(g2d);
        g2d.dispose();

        return image;
    }

    public void aiMove(int difficultyLevel) {
        SpanningTreeGenerator generator = new SpanningTreeGenerator(dots, lines);
        List<Line> mst = generator.generateMinimumSpanningTree(dots.get(0));

        Line chosenLine = null;
        int minCost = Integer.MAX_VALUE;
        int maxCost = Integer.MIN_VALUE;

        for (Line line : mst) {
            Line reversedLine = new Line(line.dot2, line.dot1);
            if (!lines.contains(line) && !lines.contains(reversedLine)) {
                if (difficultyLevel == 1) {
                    if (line.cost < minCost) {
                        minCost = line.cost;
                        chosenLine = line;
                    }
                } else {
                    if (line.cost > maxCost) {
                        maxCost = line.cost;
                        chosenLine = line;
                    }
                }
            }
        }

        if (chosenLine != null) {
            lines.add(chosenLine);
            if (turn == 2) {
                player2.addScore(chosenLine.dot1, chosenLine.dot2);
            }

            turn = 3 - turn;
            repaint();
        }


    }


}
