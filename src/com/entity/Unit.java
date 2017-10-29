package com.entity;

import java.awt.*;

public abstract class Unit implements Runnable {

    @Override
    public abstract void run();

    public abstract void move();

    public abstract void paint(Graphics g);


}
