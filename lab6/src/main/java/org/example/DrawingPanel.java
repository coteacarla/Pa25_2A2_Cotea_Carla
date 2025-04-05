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

public class DrawingPanel extends JPanel {
    final MainFrame frame;
    private final int canvasWidth = 400, canvasHeight = 400;
    private final int dotSize = 10;
    private BufferedImage image;
    private Graphics2D offscreen;
    private final List<Dot> dots = new ArrayList<>();
    private Dot firstSelectedDot = null;
    private Dot secondSelectedDot = null;
    private final Map<Dot,Dot> lines= new HashMap<Dot,Dot>();
    private Player player1=new Player();
    private Player player2=new Player();
    private int turn=1;

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
                for (Dot dot : dots) {
                    if (dot.contains(e.getX(), e.getY(), dotSize)) {
                        System.out.println("Clicked at: " + e.getX() + ", " + e.getY() + ", " + dot.x + ", " + dot.y);
                        if (firstSelectedDot == null) {
                            firstSelectedDot = dot;
                        } else {
                            secondSelectedDot = dot;
                            lines.put(firstSelectedDot,secondSelectedDot);
                            if(turn==1)
                                player1.addScore(firstSelectedDot,secondSelectedDot);
                            else
                                player2.addScore(firstSelectedDot,secondSelectedDot);
                            turn=3-turn;
                            repaint();
                            firstSelectedDot = null;
                            secondSelectedDot = null;
                        }
                        return;
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
            for (Map.Entry<Dot, Dot> entry : lines.entrySet()) {
                Dot start = entry.getKey();
                Dot end = entry.getValue();
                g2d.drawLine(start.x, start.y, end.x, end.y);
            }
            repaint();
    }

    public List<Dot> getDots() {
        return dots;
    }

    public Map<Dot,Dot> getLines() {
        return lines;
    }

    public void setDots(List<Dot> newDots) {
        dots.clear();
        dots.addAll(newDots);
        repaint();
    }

    public void setLines(Map<Dot,Dot> newLines) {
        clearLines();
        lines.putAll(newLines);
        repaint();
    }
    public void clearLines() {
        lines.clear();
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


}
