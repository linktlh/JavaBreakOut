/*
 * Copyright (c) 2020. This code, and all contained source files, are under copyright law protection under linktlh.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class FrameListener extends MouseAdapter implements isDebug {

    private final JFrame frame;
    private Point initialMouseClickPoint;

    public FrameListener(JFrame frame){
        this.frame = frame;

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!e.getPoint().equals(initialMouseClickPoint)) {
            if (__debug__ && vlevel >= 3)
            System.out.println("Info[FrameListener] : Mouse Dragged " + e.getPoint() + " initial pt: " + initialMouseClickPoint);
            // setLocation is based on Top Left Corner MUST add the points
            Point newLocation = new Point(e.getLocationOnScreen().x-initialMouseClickPoint.x,
                    e.getLocationOnScreen().y - initialMouseClickPoint.y);
            frame.setLocation(newLocation);
            frame.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(__debug__ && vlevel >= 2)
        System.out.println("Info[FrameListener] : Mouse Pressed: " + e.getPoint());
        initialMouseClickPoint = e.getPoint();
        if (e.getY() < 50){
            if (e.getX() >= frame.getWidth()-20 && e.getX()<= frame.getWidth())
                //User Requested Exit
                System.exit(0);
            if (e.getX() >= frame.getWidth()-52 && e.getX() <= frame.getWidth()-25)
                //User Requested Minimize
                frame.setState(JFrame.ICONIFIED);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(__debug__ && vlevel >= 3)
        System.out.println("Info[FrameListener] : Mouse Clicked: " + e.getPoint());
    }

}
