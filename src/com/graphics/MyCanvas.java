package com.graphics;

import com.entity.Snake;

import javax.swing.*;
import java.awt.*;

public class MyCanvas extends JPanel {

    private int scale, width, height;
    Snake snake;

    public MyCanvas(int scale, int width, int height, Snake snake) {
        super();
        this.scale = scale;
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        makeGrid(g);
        snake.paint(g);
    }

    private void makeGrid(Graphics g) {
        g.setColor(Color.white);
        for (int i = scale; i < width * scale; i += scale)
            g.drawLine(i,0, i, height * scale);
        for (int n = scale; n < height * scale; n += scale)
            g.drawLine(0, n, width * scale, n);
    }
}
