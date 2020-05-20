/*
 * Copyright (c) 2020. This code, and all contained source files, are under copyright law protection under linktlh.
 */

import java.awt.*;

public class Block extends CollidableObject implements isDebug {
    private boolean isDestroyed;

    public Block(Point center){
        this(center, 40, 20);
        if(__debug__)
        System.out.println("Info[Block] : No width and height given! Defaulting to 40x20");
    }

    public Block(Point center, int width, int height) {
        super(center, width, height);
    }

    public Block(Point center, int width, int height, Color color) {
        super(center, width, height, color);
    }

    public void draw(Graphics g){
        if(!isDestroyed)
            g.setColor(this.color);
        else g.setColor(Color.BLACK);
        g.fill3DRect(center.x-width/2,center.y-height/2, width, height,true);
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }

    public void destroy(){
        isDestroyed = true;
    }
}
