package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitBtn = new JButton("Exit");
    JButton saveBtn = new JButton("Save");
    JButton loadBtn = new JButton("Load");
    JButton exportBtn = new JButton("Export");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        setLayout(new GridLayout(1, 4));
        frame.canvas.clearLines();
        frame.canvas.repaint();

        add(exitBtn);
        add(saveBtn);
        add(loadBtn);
        add(exportBtn);

        exitBtn.addActionListener(this::exitGame);
        saveBtn.addActionListener(this::saveGame);
        loadBtn.addActionListener(this::loadGame);
        exportBtn.addActionListener(this::exportGame);
    }
    private void exitGame(ActionEvent e) {
        frame.dispose();
    }
    private void saveGame(ActionEvent e) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game.dat"))){
            out.writeObject(frame.canvas.getDots());
            out.writeObject(frame.canvas.getLines());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    private void loadGame(ActionEvent e) {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("game.dat"))){
            frame.canvas.setDots((java.util.List<Dot>) in.readObject());
            frame.canvas.clearLines();
            frame.canvas.setLines((java.util.Map<Dot, Dot>) in.readObject());
            frame.canvas.repaint();
        }
        catch (IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
    private void exportGame(ActionEvent e) {
        try {
            BufferedImage image = frame.canvas.createImage();
            File outputFile = new File("gameBoard.png");
            ImageIO.write(image, "PNG", outputFile);
            System.out.println("Game board exported to gameBoard.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}