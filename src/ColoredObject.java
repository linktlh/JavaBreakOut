/*
 * Copyright (c) 2020. This code, and all contained source files, are under copyright law protection under linktlh.
 */

import java.awt.*;

public abstract class ColoredObject implements isDebug {
    Color color;

    public ColoredObject(){
        this.color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
