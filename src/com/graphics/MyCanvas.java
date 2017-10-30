package com.graphics;

import com.entity.Frog;
import com.entity.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyCanvas extends JPanel {

    private int scale, width, height;
    private Snake snake;
    private ArrayList<Frog> frogs;


    public MyCanvas(int scale, int width, int height) {
        super();
        this.scale = scale;
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        makeGrid(g);
        if (snake != null) {
            snake.paint(g);
        }
        if (frogs != null) {
            for (Frog frog : frogs)
                frog.paint(g);
        }
    }

    private void makeGrid(Graphics g) {
        g.setColor(Color.white);
        for (int i = scale; i < width * scale; i += scale)
            g.drawLine(i,0, i, height * scale);
        for (int n = scale; n < height * scale; n += scale)
            g.drawLine(0, n, width * scale, n);
    }

    @Override
    public synchronized void repaint() {
        super.repaint();
    }

    public void setFrogs(ArrayList<Frog> frogs) {
        this.frogs = frogs;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }
}
