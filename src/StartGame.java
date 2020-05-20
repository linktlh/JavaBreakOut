/*
 * Copyright (c) 2020. frame code, and all contained source files, are under copyright law protection under linktlh.
 */

import javax.swing.*;
import java.awt.*;

public class StartGame {
    public static void main(String[] args){
        Config config = new Config(args);
        JFrame frame = new JFrame("Breakout Java - linktlh");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FrameListener frameListener = new FrameListener(frame);
        frame.setUndecorated(true);
        frame.add(new Breakout(config.window, config.difficulty));
        frame.addMouseListener(frameListener);
        frame.addMouseMotionListener(frameListener);
        frame.pack();
        frame.setLayout(null);
        frame.setResizable(false);
        Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int)(sz.getWidth()/2-frame.getSize().getWidth()/2),   //Center Frame
                (int)(sz.getHeight()/2-frame.getSize().getHeight()/2));

        frame.setVisible(true);
    }

    private static class Config{
        public Dimension window;
        public int difficulty;
        public Config(String[] args){
            int windoww = -500;
            int windowh = -1000;
            int difficulty = -1;
            for (String cs : args){
                if (cs.contains("help")){
                    System.out.println("--difficulty : sets difficulty [--difficulty=[0-4]\n" +
                            "--window-width : sets window width [--window-width=500]\n" +
                            "--window-height : Sets window height [--window-height=1000]");
                    System.exit(0);
                }
                if(cs.contains("--window-width"))
                    try{
                        windoww = Integer.parseInt(cs.split("=")[1]);
                    } catch (Exception ex){
                        // ignored
                    }
                if(cs.contains("--window-height"))
                    try{
                        windowh = Integer.parseInt(cs.split("=")[1]);
                    } catch (Exception ex){
                        // ignored
                    }
                if(cs.contains("difficulty"))
                    try{
                        difficulty = Integer.parseInt(cs.split("=")[1]);
                    } catch (Exception ex){
                        // ignored
                    }
            }
            if (windowh <= 0){
                System.out.println("Invalid, or no window height given. Defaulting to 1000");
            }
            if (windoww <= 0){
                System.out.println("Invalid, or no window width given. Defaulting to 500");
            }
            if (difficulty <=0){
                System.out.println("Invalid, or no difficulty given. Defaulting to 1");
            }
            window = new Dimension(Math.abs(windoww), Math.abs(windowh));
            this.difficulty = Math.abs(difficulty);
        }
    }
}
