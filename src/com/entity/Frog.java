package com.entity;

import com.Controller;
import com.graphics.Renderer;

import java.awt.*;
import java.util.ArrayList;

public class Frog extends Unit {

    Controller controller;
    Part part;
    private int width, height, direction;
    private boolean isEaten;
    private Thread thread;
    private ArrayList<Frog> frogs;

    public Frog(int width, int height, Controller controller) {
        setDirection();
        this.controller = controller;
        this.width = width;
        this.height = height;
        int x = (int) Math.round((width - 1) * Math.random()) * Renderer.MY_SCALE + Renderer.MY_SCALE / 2;
        int y = (int) Math.round((height - 1) * Math.random()) * Renderer.MY_SCALE + Renderer.MY_SCALE / 2;
        part = new Part(x, y, Controller.SNAKE_BODY_AND_FROG_DIVIDER, Controller.FROG_COLOR );
    }

    @Override
    public void run() {
        thread = Thread.currentThread();
        while (!isEaten) {
            move();
            setDirection();
            controller.getRenderer().getCanvas().repaint();
            try {
                Thread.sleep(3000 + Math.round(3000 * Math.random()));
            } catch (InterruptedException e) {}
        }
    }

    @Override
    public void move() {
        int x, y;
        boolean anotherFrogIsHere;
        do {
            anotherFrogIsHere = false;
            x = part.getX();
            y = part.getY();
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
            anotherFrogIsHere = checkAnotherFrogIsHere();
        } while (anotherFrogIsHere);
        part = new Part(x, y, Controller.SNAKE_BODY_AND_FROG_DIVIDER, Controller.FROG_COLOR );
    }

    @Override
    public void paint(Graphics g) {
        part.paint(g);
    }

    public Part getPart() {
        return part;
    }

    private void setDirection() {
        direction = (int) Math.round(3 * Math.random());
    }

    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    public Thread getThread() {
        return thread;
    }

    public void setFrogs(ArrayList<Frog> frogs) {
        this.frogs = frogs;
    }

    private boolean checkAnotherFrogIsHere() {
        for (Frog frog : frogs) {
            if (frog != this) {
                if (frog.getPart().getX() == part.getX() && frog.getPart().getY() == part.getY())
                    return true;
            }
        }
        return false;
    }
}
