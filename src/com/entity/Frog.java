package com.entity;

import com.Controller;

import java.awt.*;

public class Frog extends Unit {

    Controller controller;
    Part part;
    private int width, height;

    public Frog(int width, int height, Controller controller) {
        this.controller = controller;
        this.width = width;
        this.height = height;
        //part = new Part()
    }

    @Override
    public void run() {
        while (true) {
            move();
            controller.repaint();
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void move() {

    }

    @Override
    public void paint(Graphics g) {

    }
}
