package com;

import com.entity.Snake;
import com.graphics.Renderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller extends MouseAdapter {

    public static final int SNAKE_HEAD_DIVIDER = 2;
    public static final int SNAKE_TAIL_DIVIDER = 4;
    public static final int SNAKE_BODY_AND_FROG_DIVIDER = 3;
    public static final Color DEFAULT_COLOR = Color.yellow;
    public static final Color FROG_COLOR = Color.green;
    public static final int RIGHT = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int UP = 3;

    private int startSnakeX = 0;
    private int startSnakeY = 0;
    private int startSnakeLength = 6;
    private int startDirection = RIGHT;

    private Renderer renderer;
    private Snake snake;
    private Thread snakeThread;


    public static void main(String[] args) {
        new Controller();

    }

    public Controller() {
        snake = new Snake(startSnakeX, startSnakeY, startSnakeLength, startDirection, 20, 20, this);
        renderer = new Renderer(this, 20, 20, snake);
        snakeThread = new Thread(snake);
        go();
    }

    public void go() {
        snakeThread.start();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        snake.setDirection(e.getButton());
    }

    public synchronized void repaint() {
        renderer.getCanvas().repaint();
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getActionCommand().equals("START")){
//            go();
//        }
//    }
}
