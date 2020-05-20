/*
 * Copyright (c) 2020. This code, and all contained source files,
 * are under copyright law protection under linktlh.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;



public class Breakout extends JComponent implements isDebug {

    private final GameBoard board;
    FrameUI frameUI;
    private final Timer[] keyTimer = new Timer[2];

    public Breakout(){
        this(new Dimension(600,1000));
        if (__debug__)
        System.out.println("Info[Breakout] : No Dimension Given, Defaulting to 600x1000");
    }

    public Breakout(Dimension dimension){
        this(dimension, 1);
        if (__debug__)
        System.out.println("Info[Breakout] : No Difficulty Given, Defaulting to 1");
    }

    public Breakout(Dimension dimension, int difficulty) {
        this.setPreferredSize(dimension);
        setFocusable(true);
        board = new GameBoard(this.getPreferredSize().width, this.getPreferredSize().height, difficulty);
        frameUI = new FrameUI(this.getPreferredSize().width);
        //Setup for animations [Used instead of threading, more efficient]
        setKeyBinds();
        Timer timer = new Timer(msp, e -> {
            repaint();
            board.updateGameObjs();
            board.drawBoardImage();
        });
        timer.start();

        board.start_game();


    }

    private void setKeyBinds(){
        //Left/A Press and Release
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "lefton");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "leftoff");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0 , false), "lefton");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0 , true), "leftoff");
        //Right/D Press and Release
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0, false), "righton");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0, true), "rightoff");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D,0, false), "righton");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D,0, true), "rightoff");
        //Assigning Actions to each input
        getActionMap().put("lefton", new Left_Right(true, false));
        getActionMap().put("leftoff", new Left_Right(true, true));
        getActionMap().put("righton", new Left_Right(false, false));
        getActionMap().put("rightoff", new Left_Right(false, true));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(board.getBoardImage(),0,0,null);
        //Draw UI
        g.drawImage(frameUI.returnRender(),0,0, null);
    }

    //Action for the button keybinds
    private class Left_Right extends AbstractAction{
        private final boolean LEFT;
        private final int DIRECTION;
        private final boolean released;

        Left_Right(boolean LEFT, boolean released){
            this.LEFT = LEFT;
            this.released = released;
            DIRECTION = LEFT ? 0 : 1; // if Left, index 0, right index 1
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (released){
                if (keyTimer[DIRECTION] != null) {
                    keyTimer[DIRECTION].stop();
                    keyTimer[DIRECTION] = null;
                }
            }
            else if (keyTimer[DIRECTION] == null){
                keyTimer[DIRECTION] = new Timer(msp, e1 -> {
                    board.movePaddle(LEFT);
                    repaint();
                });
                keyTimer[DIRECTION].start();
            }
        }
    }

    private static class FrameUI {
        protected BufferedImage uiElements;

        public FrameUI (int width){
            uiElements = new BufferedImage(width, 50,  BufferedImage.TYPE_INT_ARGB);
            createImage();
        }
        private void createImage(){
            Graphics g = uiElements.getGraphics();
            g.setFont(new Font("MONOSPACE", Font.PLAIN, 20));
            g.setColor(Color.RED);
            g.drawString("X", uiElements.getWidth()-20,19);
            g.setColor(Color.WHITE);
            g.drawString("―", uiElements.getWidth()-52,16);
        }
        public Image returnRender (){
            return uiElements;
        }
    }
}

