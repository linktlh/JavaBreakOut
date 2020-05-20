/*
 * Copyright (c) 2020. This code, and all contained source files, are under copyright law protection under linktlh.
 */

import java.awt.*;

public class GameBall extends ColoredObject implements isDebug{

    private final int Max_Speed = 20;

    private final Point location;
    private int xspeed, yspeed;
    private int size;

    public boolean reverseX, reverseY;
    public GameBall(Point startPos) {
        this(startPos,5);
        System.out.println("Info[GameBall] : No Ball Size Given, defaulting to 5");
    }

    public GameBall(Point startPos, int size) {
        this.size = size;
        xspeed=0;
        yspeed=0;
        location = startPos;
        reverseX=reverseY=false;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(location.x, location.y, size, size);
    }

    public void update(Block[][] bricks, Paddle paddle){
        Point newLocation = new Point(location.x + xspeed, location.y + yspeed);
        if(__debug__ && vlevel>=3)
        System.out.println("Info[GameBall] : Beginning Collisions Check");
        if (didCollide(paddle, newLocation)) {
            paddle.speedVarient(this);
            //if (reverseX) setXspeed(-getXspeed());
            reverseY = reverseX = false;
            location.setLocation(newLocation.x, newLocation.y);
            return;
        }
        for (Block[] a : bricks) {
            for (Block b : a) {
                if (b.isDestroyed()) {
                    continue;
                }
                if(didCollide(b, newLocation)) {
                    b.destroy();
                }
            }
        }
        if (reverseX) setXspeed(-getXspeed());
        if (reverseY) setYspeed(-getYspeed());
        reverseX = reverseY = false;
        location.setLocation(newLocation.x, newLocation.y);
    }

    public boolean didCollide(CollidableObject b, Point newLocation){
        Point newLocationPlan = new Point(newLocation);

        if (newLocationPlan.x > b.center.x - b.width / 2 && newLocationPlan.x < b.center.x + b.width / 2 &&
                (newLocationPlan.y >= b.center.y - b.height / 2 && newLocationPlan.y <= b.center.y + b.height / 2)) {
            if (location.x <= b.center.x - b.width / 2) {
                if(__debug__&&vlevel>=2)
                    System.out.println("Info[GameBall] : HIT LEFT BLOCK SIDE <Block>" + b.center);
                newLocation.x = b.center.x - b.width / 2;
                reverseX = true;
            } if (location.x >= b.center.x + b.width / 2) {
                if(__debug__&&vlevel>=2)
                    System.out.println("Info[GameBall] : HIT RIGHT BLOCK SIDE <Block>" + b.center);
                newLocation.x = b.center.x + b.width / 2;
                reverseX = true;
            } if (location.y <= b.center.y - b.height / 2) {
                if(__debug__&&vlevel>=2)
                    System.out.println("Info[GameBall] : HIT TOP BLOCK SIDE <Block>" + b.center);
                newLocation.y = b.center.y - b.height / 2;
                reverseY = true;
            } if (location.y >= b.center.y + b.height / 2) {
                if(__debug__&&vlevel>=2)
                    System.out.println("Info[GameBall] : HIT BOT BLOCK SIDE <Block>" + b.center);
                newLocation.y = b.center.y + b.height / 2;
                reverseY = true;
            }
            return true;
        }
        else if (newLocationPlan.x+size > b.center.x - b.width / 2 && newLocationPlan.x+size < b.center.x + b.width / 2 &&
                (newLocationPlan.y+size >= b.center.y - b.height / 2 && newLocationPlan.y+size <= b.center.y+size + b.height / 2)) {
            if (location.x+size <= b.center.x - b.width / 2) {
                if(__debug__&&vlevel>=2)
                    System.out.println("Info[GameBall](s) : HIT LEFT BLOCK SIDE <Block>" + b.center);
                newLocation.x = b.center.x - (size + b.width / 2);
                reverseX = true;
            } if (location.x+size >= b.center.x + b.width / 2) {
                if(__debug__&&vlevel>=2)
                    System.out.println("Info[GameBall](s) : HIT RIGHT BLOCK SIDE <Block>" + b.center);
                newLocation.x = b.center.x + b.width / 2;
                reverseX = true;
            } if (location.y+size <= b.center.y - b.height / 2) {
                if(__debug__&&vlevel>=2)
                    System.out.println("Info[GameBall](s) : HIT TOP BLOCK SIDE <Block>" + b.center);
                newLocation.y = b.center.y - (size + b.height / 2);
                reverseY = true;
            } if (location.y+size >= b.center.y + b.height / 2) {
                if(__debug__&&vlevel>=2)
                    System.out.println("Info[GameBall](s) : HIT BOT BLOCK SIDE <Block>" + b.center);
                newLocation.y = b.center.y + b.height / 2;
                reverseY = true;
            }
            location.setLocation(location.x+size, location.y+size);
            return true;

        }
        return false;
    }

    public void setXspeed(int xspeed) {
        if (Math.abs(xspeed) > Max_Speed){
            this.xspeed = Max_Speed*(Math.abs(xspeed)/xspeed);
        }
        else this.xspeed = xspeed;
    }

    public void setYspeed(int yspeed) {
        if (Math.abs(yspeed) > Max_Speed){
            this.yspeed = Max_Speed*(Math.abs(yspeed)/yspeed);
        }
        else this.yspeed = yspeed;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getXspeed() {
        return xspeed;
    }

    public int getYspeed() {
        return yspeed;
    }

    public Point getLocation() {
        return location;
    }
}
