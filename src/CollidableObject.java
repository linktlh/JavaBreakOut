/*
 * Copyright (c) 2020. This code, and all contained source files, are under copyright law protection under linktlh.
 */

import java.awt.*;

public abstract class CollidableObject extends ColoredObject {
    protected Point center;
    protected int width, height;

    public CollidableObject(Point center, int width, int height) {
        super();
        this.center = center;
        this.width = width;
        this.height = height;
    }

    public CollidableObject(Point center, int width, int height, Color color) {
        this(center, width, height);
        this.setColor(color);
    }

}
