/*
 * Copyright (c) 2020. This code, and all contained source files, are under copyright law protection under linktlh.
 */

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameBoard implements isDebug {

    private final int height, width;

    private final Block[][] bricks;

    private final Paddle paddle;

    private final GameBall gameBall;

    private final BufferedImage board;

    public final Font UIFont;

    private int lives;

    private int DIFFICULTY;

    public GameBoard(){
        this(500,1000);
        if (__debug__)
        System.out.println("Info[GameBoard] : No Width and Height Given, defaulting to 500x1000");
    }

    public GameBoard(int width, int height) {
        this(width, height, -1);
        if (__debug__)
        System.out.println("Info[GameBoard] : No Difficulty Given [0-4] defaulting to difficulty by screensize");
    }

    public GameBoard(int width, int height, int difficulty){
        this.lives = 3;
        this.UIFont = new Font("BRITANNIC", Font.BOLD, 25);
        this.width = width;
        this.height = height;
        this.DIFFICULTY = difficulty;
        //Weird UI Scale?
        //Sure I'll handle it.
        if (width>height) {
            int tmp = height;
            height = width;
            width = tmp;
        }

        // 20% of the total width
        int paddleWidth = (int)Math.round(this.width*.20);
        // 2% of the total width
        int ballSize = (int)Math.round(width*height*.00003);
        // 8% of the total width
        int blockWidth = (int)Math.round(width*.08);
        // 4% of the total height
        int blockHeight = (int)Math.round(height*.02);
        if (__debug__)
        System.out.println("Info[GameBoard] : PaddleWidth: " + paddleWidth + " BallSize: " + ballSize +
                " BlockWidth: " + blockWidth + " BlockHeight: " + blockHeight);

        board = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        paddle = new Paddle(new Point(this.width/2, this.height-40), paddleWidth);
        gameBall = new GameBall(new Point(this.width/2, this.height-(30+ballSize+paddleWidth/2)), ballSize);

        int bCols = (this.width-20)/blockWidth;
        //Default if invalid or no given difficulty
        int bRows = (this.height/2)/blockHeight;
        switch (DIFFICULTY){
            case -1: break;
            case 0: bRows = 3; break;
            case 1: bRows = 5; break;
            case 2: bRows = 8; break;
            case 3: bRows = 12; break;
            case 4: bRows = 15; break;
            default:
                System.out.println("Info[GameBoard] : Invalid difficulty ["+DIFFICULTY+"]");
        }
        bricks = new Block[bCols][bRows];
        for(int i =0; i < bRows; i++){
            Color rowColor = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
            for (int k = 0; k < bCols; k++){
                bricks[k][i] = new Block(new Point((this.width-bCols*blockWidth)/2+blockWidth/2+k*blockWidth,150 + blockHeight/2+i*blockHeight),
                        blockWidth, blockHeight, rowColor);
            }
        }

        drawBoardImage();
        System.out.println("Info[GameBoard] : GameBoard Initialized");
    }

    public void movePaddle(boolean LEFT){
        if (lives >= 0) // Player not out of lives
            if(!(paddle.center.x - paddle.width/2 <= 0 && LEFT))
                if(!(paddle.center.x+paddle.width/2 >= width && !LEFT))
                    paddle.movePaddle(LEFT);

    }

    public void updateGameObjs(){
        if (lives >= 0){
            /*boolean ballHit = false;
            for (Block[] bA : bricks)
                for (Block b : bA)
                    ballHit = b.isHit(gameBall) || ballHit;
            ballHit = paddle.isHit(gameBall) || ballHit;*/

            gameBall.update(bricks, paddle);
            //Check if Paddle is Hit
            if(gameBall.getLocation().x <= 0){
                gameBall.setXspeed(-gameBall.getXspeed());
            }
            if(gameBall.getLocation().x >= width || gameBall.getLocation().x+gameBall.getSize() >= width){
                gameBall.setXspeed(-gameBall.getXspeed());
            }

            if(gameBall.getLocation().y <= 0){
                gameBall.setYspeed(-gameBall.getYspeed());
            }
            // Game Over Or Continue
            if(gameBall.getLocation().y >= height || gameBall.getLocation().y+gameBall.getSize() >= height) {
                gameBall.setXspeed(0);
                gameBall.getLocation().setLocation(paddle.center.getX(), this.height-(40+gameBall.getSize()+paddle.width/2.0));
                --this.lives;
                start_game();
            }
        }
    }

    public void drawBoardImage(){
        Graphics g = board.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, width, height);
        for (Block[] bA : bricks)
            for (Block b : bA)
                b.draw(g);

        paddle.draw(g);
        gameBall.draw(g);
        g.setColor(new Color(155, 219, 103));
        g.setFont(UIFont);
        g.drawString("Lives : " + lives, 20, 30);
        g.drawString("Blocks Left : " + getBlocksLeft(), 20, 60);
        if (lives < 0) {
            g.setColor(Color.BLACK);
            g.fillRect((int)((width / 2)-width/3f),height/2-(int)(width/3f)/3+5,(int)(width/3f)*2+5,(int)(width/3f)/3);
            g.setColor(Color.RED);
            g.setFont(UIFont.deriveFont(width/9f));
            g.drawString("GAME OVER", (int)((width / 2)-width/3f), (int)(height / 2));
        }
    }

    public void start_game(){
        //Reasonable speed for how far bricks are away [.005% of total distance]
        int calcSpeed = (int) (-(height - bricks[bricks.length-1][bricks[bricks.length-1].length-1].center.y)*.010);
        if (calcSpeed >= -1)
            calcSpeed=-2;
        calcSpeed-=DIFFICULTY;
        gameBall.setYspeed(calcSpeed);
        if (__debug__)
        System.out.println("Info[GameBoard] : GameBall Yspeed set to "+gameBall.getYspeed());
        gameBall.setXspeed((int)(Math.random()*2) == 1 ? -1 : 1);
    }

    private int getBlocksLeft(){
        int blocksLeft=0;
        for (Block[] bs : bricks)
            for (Block b : bs)
                if (!b.isDestroyed())
                    blocksLeft++;
        return blocksLeft;
    }

    public BufferedImage getBoardImage(){
        return board;
    }
}
