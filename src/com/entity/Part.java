package com.entity;

import com.graphics.Renderer;

import java.awt.*;

public class Part {

    int x, y, diameter;
    Color color;

    public Part(int x, int y, int scaleDivider, Color color) {
        this.setXY(x, y);
        this.diameter = (Renderer.MY_SCALE / scaleDivider) * 2;
        this.color = color;
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
