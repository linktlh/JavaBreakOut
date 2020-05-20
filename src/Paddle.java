/*
 * Copyright (c) 2020. This code, and all contained source files, are under copyright law protection under linktlh.
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Paddle extends CollidableObject implements isDebug {

    private int speed = 10;

    public Paddle(Point center) {
        this(center, 20);
        if (__debug__)
        System.out.println("Info[Paddle] : No Paddle With Given!! Defaulting to 20");
    }

    public Paddle(Point center, int width){
        super(center, width, 20);
    }

    public Paddle(Point center, int width, Color color) {
        super(center, width, 20, color);

    }


    public void draw(Graphics g){
        g.setColor(color);
        g.fill3DRect(center.x-width/2,center.y-height/2, width, height,true);
    }

    public void movePaddle(boolean LEFT){
        if (LEFT)
            center.x -= speed;
        else
            center.x += speed;
    }

    public void speedVarient(GameBall gameBall) {
        gameBall.setYspeed(-gameBall.getYspeed());
        double gbX = gameBall.getLocation().x - (center.x - width/2.0);
        if (__debug__ && vlevel >= 2)
        System.out.println("Info[Paddle] : Ball hit " +gbX+ " from left edge");
        if (gbX < width / 2.0-10) {
            gameBall.setXspeed((int) (-gameBall.getXspeed() - (8 - 8 * (gbX / width * .5))));
            if (__debug__ && vlevel >= 2)
            System.out.println("Info[Paddle] : Looking at Decrease of " + (int)(10 - 10*(gbX / width * .5)) + " notches");
        }
        if (gbX > width / 2.0+10) {
            gbX -= width / 2.0;
            gameBall.setXspeed((int) (-gameBall.getXspeed() + (8 - 8 * (gbX / width * .5))));
            if (__debug__ && vlevel >= 2)
            System.out.println("Info[Paddle] : Looking at Increase of " + (int)(10 - 10*(gbX / width * .5)) + " notches");
        }
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
