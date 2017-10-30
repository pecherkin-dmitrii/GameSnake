package com;

import com.entity.Frog;
import com.entity.Snake;
import com.graphics.Renderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Controller extends MouseAdapter implements ActionListener {

    public static final int SNAKE_HEAD_DIVIDER = 2;
    public static final int SNAKE_TAIL_DIVIDER = 4;
    public static final int SNAKE_BODY_AND_FROG_DIVIDER = 3;
    public static final Color DEFAULT_COLOR = Color.yellow;
    public static final Color FROG_COLOR = Color.green;
    public static final int RIGHT = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int UP = 3;

    private int startSnakeX = 15;
    private int startSnakeY = 15;
    private int startDirection = RIGHT;

    private Renderer renderer;
    private Snake snake;
    private ArrayList<Thread> unitThreads;
    private volatile ArrayList<Frog> frogs;

    //options from command line
    private static int startSnakeLength;
    private static int snakeSleepTime; //millis, min 120
    private static int myWidth; //min 7
    private static int myHeight;
    private static int frogsQuantity;


    public static void main(String[] args) {
        myWidth = (args.length == 0) || (!args[0].matches("\\d+")) || (Integer.parseInt(args[0]) < 10) ? 10 : Integer.parseInt(args[0]);
        myHeight = (args.length == 0) || (!args[1].matches("\\d+")) || (Integer.parseInt(args[1]) < 10) ? 10 : Integer.parseInt(args[1]);
        startSnakeLength = (args.length == 0) || (!args[2].matches("\\d+")) || (Integer.parseInt(args[2]) < 3) ? 3 : Integer.parseInt(args[2]);
        frogsQuantity = (args.length == 0) || (!args[3].matches("\\d+")) || (Integer.parseInt(args[3]) < 1) ? 1 : Integer.parseInt(args[3]);
        snakeSleepTime = (args.length == 0) || (!args[4].matches("\\d+")) || (Integer.parseInt(args[4]) < 120) ? 120 : Integer.parseInt(args[4]);

        new Controller(myWidth, myHeight);

    }

    public Controller(int width, int height) {
        renderer = new Renderer(this, width, height);
    }

    public void go() {
        snake = new Snake(-startSnakeX, startSnakeY, startSnakeLength, snakeSleepTime, startDirection, myWidth, myHeight, this);
        makeFrogs(myWidth, myHeight, frogsQuantity, this);
        for (Frog frog : frogs)
            frog.setFrogs(frogs);

        renderer.getScore().setText("0");
        renderer.getCanvas().setSnake(snake);
        renderer.getCanvas().setFrogs(frogs);

        unitThreads = new ArrayList<>();
        unitThreads.add(new Thread(snake));
        for (Frog frog : frogs)
            unitThreads.add(new Thread(frog));

        for (Thread thread : unitThreads)
            thread.start();
        renderer.setStartPressed(true);
    }

    public synchronized void stop() {
        snake.setGameOver(true);
        for (Frog frog : frogs)
            frog.setEaten(true);

        renderer.getCanvas().setSnake(null);
        unitThreads.clear();
        frogs.clear();
        renderer.getCanvas().repaint();
        renderer.setStartPressed(false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (renderer.isStartPressed()) {
            super.mousePressed(e);
            snake.setDirection(e.getButton());
        }
    }

    private void makeFrogs(int width, int height, int frogsQuantity, Controller controller) {
        frogs = new ArrayList<>();
        for (int i = 0; i < frogsQuantity; i++) {
            frogs.add(new Frog(width, height, controller));
        }
    }

    public int getStartSnakeX() {
        return startSnakeX;
    }

    public int getStartSnakeY() {
        return startSnakeY;
    }

    public ArrayList<Frog> getFrogs() {
        return frogs;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void replaceFrog(Frog currentFrog, Thread frogThread) {
        Frog newFrog = new Frog(myWidth, myHeight, this);
        for (int i = 0; i < frogs.size(); i++) {
            if (frogs.get(i) == currentFrog) {
                frogs.set(i, newFrog);
                break;
            }
        }
        renderer.getCanvas().setFrogs(frogs);
        for (Frog frog : frogs)
            frog.setFrogs(frogs);

        for (int i = 0; i < unitThreads.size(); i++) {
            if (unitThreads.get(i) == frogThread) {
                unitThreads.set(i, new Thread(newFrog));
                unitThreads.get(i).start();
            }
        }
    }

    public void increaseScore() {
        int currentScore = Integer.parseInt(renderer.getScore().getText());
        renderer.getScore().setText(Integer.toString(++currentScore));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("START")){
            if (!renderer.isStartPressed()) {
                go();
            }
        }

        if(e.getActionCommand().equals("STOP")){
            if (renderer.isStartPressed()) {
                stop();
            }
        }
    }
}
