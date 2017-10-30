package com.graphics;

import com.Controller;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JFrame {

    private Controller controller;
    private MyCanvas canvas;
    private JLabel score;
    private String title = "My cool snake";
    private int additionalHeight = 54;
    private int additionalWidth = 6;
    public static final int MY_SCALE = 30;

    private boolean StartPressed;


    public Renderer(Controller controller, int width, int height) {
        this.controller = controller;

        canvas = new MyCanvas(MY_SCALE, width, height);
        canvas.setSize(width * MY_SCALE, height * MY_SCALE);
        canvas.setBackground(Color.black);

        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(canvas.getWidth() + additionalWidth, canvas.getHeight() + additionalHeight);
        setResizable(false);
        setLocationRelativeTo(null);

        getContentPane().add(canvas, BorderLayout.CENTER);

        addControlElements();

        addMouseListener(controller);

        setVisible(true);

    }

    private void addControlElements() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout());

        JButton startButton = new JButton("START");
        startButton.addActionListener(controller);
        JButton stopButton = new JButton("STOP");
        stopButton.addActionListener(controller);
        score = new JLabel();

        controlPanel.add(score);
        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    public MyCanvas getCanvas() {
        return canvas;
    }

    public JLabel getScore() {
        return score;
    }

    public boolean isStartPressed() {
        return StartPressed;
    }

    public void setStartPressed(boolean startPressed) {
        StartPressed = startPressed;
    }
}
