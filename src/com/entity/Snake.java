package com.entity;

import com.Controller;
import com.graphics.Renderer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Snake extends Unit {

    private Controller controller;
    private ArrayList<Part> body;
    private int direction, width, height;
    private boolean foodIsEaten;
    private int sleepTime;
    private boolean gameOver;

    public Snake(int x, int y, int length, int sleepTime, int direction, int width, int height, Controller controller) {
        this.controller = controller;
        this.direction = direction;
        this.sleepTime = sleepTime;
        this.width = width;
        this.height = height;
        body = new ArrayList<>();
        addHead(x, y);
        for (int i = 0; i < length - 2; i++) {
            body.add(new Part(x - body.size() * Renderer.MY_SCALE, y, Controller.SNAKE_BODY_AND_FROG_DIVIDER, Controller.DEFAULT_COLOR));
        }
        body.add(new Part(x - body.size() * Renderer.MY_SCALE, y, Controller.SNAKE_TAIL_DIVIDER, Controller.DEFAULT_COLOR));
    }

    private boolean isBitedItself(int x, int y) {
        for (Part part : body) {
            if ((part.getX() == x) && (part.getY() == y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        while (!gameOver) {
            move();
            controller.getRenderer().getCanvas().repaint();
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {}
        }

    }

    @Override
    public void move() {
        int x = body.get(0).getX();
        int y = body.get(0).getY();
        if (direction == Controller.RIGHT)
            x+=Renderer.MY_SCALE;
        if (direction == Controller.DOWN)
            y+=Renderer.MY_SCALE;
        if (direction == Controller.LEFT)
            x-=Renderer.MY_SCALE;
        if (direction == Controller.UP)
            y-=Renderer.MY_SCALE;
        if (x > width * Renderer.MY_SCALE)
            x = controller.getStartSnakeX();
        if (x < 0)
            x = width * Renderer.MY_SCALE - Renderer.MY_SCALE / 2;
        if (y > height * Renderer.MY_SCALE)
            y = controller.getStartSnakeY();
        if (y < 0)
            y = height * Renderer.MY_SCALE - Renderer.MY_SCALE / 2;
        if (isBitedItself(x, y))
            controller.stop();
        addHead(x, y);
        hasEaten(controller.getFrogs());
        if (foodIsEaten) {
            body.set(1, new Part(body.get(1).getX(), body.get(1).getY(), Controller.SNAKE_BODY_AND_FROG_DIVIDER, Controller.DEFAULT_COLOR));
            foodIsEaten = false;
        } else {
            moveBody();
            body.remove(1);
        }

    }

    @Override
    public void paint(Graphics g) {
        for (Part part : body)
            part.paint(g);
    }

    private void addHead(int x, int y) {
        body.add(0, new Part(x, y, Controller.SNAKE_HEAD_DIVIDER, Controller.DEFAULT_COLOR));
    }

    private void moveBody() {
        int prevX = body.get(1).getX();
        int prevY = body.get(1).getY();
        for (int i = 1; i < body.size() - 1; i++) {
            int curX = prevX;
            int curY = prevY;
            prevX = body.get(i + 1).getX();
            prevY = body.get(i + 1).getY();
            body.get(i + 1).setXY(curX, curY);
        }
    }

    public void setDirection(int key) {
        if (key == MouseEvent.BUTTON3) {
            switch (direction) {
                case Controller.RIGHT:
                    direction = Controller.DOWN;
                    break;
                case Controller.DOWN:
                    direction = Controller.LEFT;
                    break;
                case Controller.LEFT:
                    direction = Controller.UP;
                    break;
                case Controller.UP:
                    direction = Controller.RIGHT;
            }
        }

        if (key == MouseEvent.BUTTON1) {
            switch (direction) {
                case Controller.RIGHT:
                    direction = Controller.UP;
                    break;
                case Controller.DOWN:
                    direction = Controller.RIGHT;
                    break;
                case Controller.LEFT:
                    direction = Controller.DOWN;
                    break;
                case Controller.UP:
                    direction = Controller.LEFT;
            }
        }
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void hasEaten(ArrayList<Frog> frogs) {
        int x = body.get(0).getX();
        int y = body.get(0).getY();
        for (Frog frog : frogs) {
            if ((x == frog.getPart().getX()) && (y == frog.getPart().getY())) {
                foodIsEaten = true;
                frog.setEaten(true);
                controller.replaceFrog(frog, frog.getThread());
                controller.increaseScore();
            }
        }
    }




}
