package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ConfigPanel extends JPanel {
    final MainFrame frame;
    JLabel label;
    JSpinner spinner;
    JButton newGameButton;
    JComboBox<String> player1Type;
    JComboBox<String> player2Type;
    JComboBox<String> player1Difficulty;
    JComboBox<String> player2Difficulty;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }
    private void init() {
        label = new JLabel("Number of dots:");
        spinner = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        newGameButton = new JButton("New Game");

        newGameButton.addActionListener(this::startNewGame);
        player1Type = new JComboBox<>(new String[]{"Human"});
        player2Type = new JComboBox<>(new String[]{"Human", "AI"});
        player1Difficulty = new JComboBox<>(new String[]{"Easy", "Hard"});
        player2Difficulty = new JComboBox<>(new String[]{"Easy", "Hard"});

        add(new JLabel("Player 1:"));
        add(player1Type);
        add(new JLabel("Difficulty:"));
        add(player1Difficulty);

        add(new JLabel("Player 2:"));
        add(player2Type);
        add(new JLabel("Difficulty:"));
        add(player2Difficulty);

        add(label);
        add(spinner);
        add(newGameButton);
    }
    public void startNewGame(ActionEvent e) {
        int numDots = (Integer) spinner.getValue();
        frame.canvas.generateDots(numDots);
        frame.canvas.clearLines();
        frame.canvas.repaint();
        frame.canvas.player2IsAI = player2Type.getSelectedItem().equals("AI");
        frame.canvas.player1Difficulty = player1Difficulty.getSelectedIndex() + 1;
        frame.canvas.player2Difficulty = player2Difficulty.getSelectedIndex() + 1;

    }

}