package com.graphics;

import com.Controller;
import com.entity.Snake;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JFrame {

    private Controller controller;
    private MyCanvas canvas;
    private String title = "My cool snake";
    private int additionalHeight = 54;
    private int additionalWidth = 6;
    public static final int MY_SCALE = 30;

    private int myWidth; //min 7
    private int myHeight; //min 1


    public Renderer(Controller controller, int width, int height, Snake snake) {
        this.controller = controller;
        this.myWidth = width;
        this.myHeight = height;

        canvas = new MyCanvas(MY_SCALE, width, height, snake);
        canvas.setSize(myWidth * MY_SCALE, myHeight * MY_SCALE);
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
        //startButton.addActionListener(controller);
        JButton stopButton = new JButton("STOP");
        JLabel score = new JLabel("0");

        controlPanel.add(score);
        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    public int getMyWidth() {
        return myWidth;
    }

    public int getMyHeight() {
        return myHeight;
    }

    public MyCanvas getCanvas() {
        return canvas;
    }

}
