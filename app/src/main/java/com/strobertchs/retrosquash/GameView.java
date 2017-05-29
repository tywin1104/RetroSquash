package com.strobertchs.retrosquash;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by eric on 2017-05-08.
 */


class GameView extends SurfaceView implements Runnable{

    Canvas canvas;
    Thread ourThread = null;
    SurfaceHolder ourHolder;
    volatile boolean playingSquash;
    Paint paint;
    Ball ball;
//    Racket racket;
    RacketWithImage racket;
    Brick brick;
    ArrayList<Brick> bricks;

    long lastFrameTime;
    int fps;
    int score;
    int lives;
    public int screenWidth;
    public int screenHeight;

    //Sound variables
    private SoundPool soundPool;
    int sample1 = -1;
    int sample2 = -1;
    int sample3 = -1;
    int sample4 = -1;




    public GameView(Context context, int sScreenWidth, int sScreenHeight) {

        super(context);
        bricks = new ArrayList<>();
        for(int i=0;i<3;i++) {
            bricks.add(new Brick(context, sScreenWidth));
        }

        screenWidth = sScreenWidth;
        screenHeight = sScreenHeight;
        ourHolder = getHolder();
        paint = new Paint();

        ball = new Ball(context, screenWidth);
        ball.moveDown();  // initialize ball to move downwards;
//        racket = new Racket(sScreenWidth, sScreenHeight);
        racket = new RacketWithImage(context, sScreenWidth, sScreenHeight);


        brick = new Brick(context,screenWidth);
        lives = 3;
        score = 0;

        //Sound code
        soundPool = new SoundPool(10,
                AudioManager.STREAM_MUSIC, 0);
        try {
            //Create objects of the 2 required classes
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            //create our three fx in memory ready for use
            descriptor =
                    assetManager.openFd("sample1.ogg");
            sample1 = soundPool.load(descriptor, 0);
            descriptor =
                    assetManager.openFd("sample2.ogg");
            sample2 = soundPool.load(descriptor, 0);
            descriptor =
                    assetManager.openFd("sample3.ogg");
            sample3 = soundPool.load(descriptor, 0);
            descriptor =
                    assetManager.openFd("sample4.ogg");
            sample4 = soundPool.load(descriptor, 0);
        } catch (IOException e) {
            //catch exceptions here
        }


        //Send the ball in the random horizontal direction
        Random randomNumber = new Random();
        int ballDirection = randomNumber.nextInt(3);
        switch(ballDirection){
            case 0:
                ball.moveLeft();
                break;

            case 1:
                ball.moveRight();
                break;

            case 2:
                ball.moveStraight();
                break;
        }
    }

    @Override
    public void run() {
        while (playingSquash) {
            updateCourt();
            drawCourt();
            controlFPS();
        }
    }

    public void updateCourt() {

        // move racket only if it is not at the edge of the screen

        if (racket.isMovingRight()) {
            if(racket.getPositionX() + racket.getWidth()/2 < screenWidth){ //move right only if you haven't hit the right edge
                racket.updatePosition();
            }
        }

        if (racket.isMovingLeft()) {
            if (racket.getPositionX() - racket.getWidth()/2 > 0){  //move left only if you haven't hit the left edge
                racket.updatePosition();
            }
        }


        //hit right of screen
        if (ball.getPositionX() + ball.getWidth() > screenWidth) {
            ball.moveLeft();
            soundPool.play(sample1, 1, 1, 0, 0, 1);
        }


        //hit left of screen
        if (ball.getPositionX() < 0) {
            ball.moveRight();
            soundPool.play(sample1, 1, 1, 0, 0, 1);
        }


        //Edge of ball has hit bottom of screen
        if (ball.getPositionY() > screenHeight - ball.getWidth()) {
            lives = lives - 1;
            if (lives == 0) {
                lives = 3;
                score = 0;
                soundPool.play(sample4, 1, 1, 0, 0, 1);
            }

            //reset the ball to the top of the screen
            ball.setPositionY(1 + ball.getWidth());


            //what horizontal direction should we use
            //for the next falling ball
            Random randomNumber = new Random();
            int startX = randomNumber.nextInt(screenWidth - ball.getWidth()) + 1;
            ball.setPositionX(startX + ball.getWidth());

            int ballDirection = randomNumber.nextInt(3);
            switch (ballDirection) {

                case 0:
                    ball.moveLeft();
                    break;
                case 1:
                    ball.moveRight();
                    break;
                case 2:
                    ball.moveStraight();
                    break;
            }
        }


        //we hit the top of the screen
        if (ball.getPositionY() <= 0) {
            ball.moveDown();
            ball.setPositionY(1);
            soundPool.play(sample2, 1, 1, 0, 0, 1);
        }



        //Has ball hit racket
        if (ball.getPositionY() + ball.getWidth() >= (racket.getPositionY() - racket.getHeight()/ 2)) {

            int halfRacket = racket.getWidth()/2;


            if (ball.getPositionX() + ball.getWidth() > (racket.getPositionX() - halfRacket) && //the right side of the ball hits the left edge of the racket
                    ball.getPositionX() - ball.getWidth() < (racket.getPositionX() + halfRacket)) {  //the left side of the ball hits the right edge of the racket

                //rebound the ball vertically and play a sound
                soundPool.play(sample3, 1, 1, 0, 0, 1);
                score++;
                ball.moveUp();

                //now decide how to rebound the ball horizontally
                if (ball.getPositionX() > racket.getPositionX()) {
                    ball.moveRight();
                } else {
                    ball.moveLeft();
                }
            }
        }
        //Has the ball hit brick
//        if (ball.getPositionY() + ball.getWidth() >= brick.getPositionY() && ball.getPositionY() + ball.getWidth() <= brick.getPositionY() + brick.getHeight() / 2 && ball.getPositionX() + ball.getWidth() >= brick.getPositionX() && ball.getPositionX() <= brick.getPositionX() + brick.getWidth())
//        {   soundPool.play(sample1, 1, 1, 0, 0, 1);
//            ball.moveUp();
//            if (ball.getPositionX() > brick.getPositionX() + brick.getWidth() / 2) {
//                ball.moveRight();
//            } else {
//                ball.moveLeft();
//            }
//        }
//        //from bottom
//        if(ball.getPositionY() <= brick.getPositionY()+ brick.getHeight() &&ball.getPositionY() >=brick.getPositionY()+ brick.getHeight()/2&& ball.getPositionX() + ball.getWidth() > brick.getPositionX() && ball.getPositionX() < brick.getPositionX()+brick.getWidth()) {    //make sound
//            soundPool.play(sample1, 1, 1, 0, 0, 1);
//            ball.moveDown();
//            if (ball.getPositionX() > brick.getPositionX() + brick.getWidth() / 2) {
//                ball.moveRight();
//            } else {
//                ball.moveLeft();
//            }
//        }
//        //from left
//        if(ball.getPositionX() + ball.getWidth() >= brick.getPositionX() && ball.getPositionX()+ball.getWidth() <= brick.getPositionX()+ brick.getWidth()/2&& ball.getPositionY()+ ball.getHeight()>= brick.getPositionY() && ball.getPositionY() <= brick.getPositionY()+brick.getHeight()) {
//            ball.moveLeft();
//            soundPool.play(sample1, 1, 1, 0, 0, 1);
//            if(ball.getPositionY() < brick.getPositionY() + brick.getHeight()/2) {
//                ball.moveDown();
//            } else {
//                ball.moveUp();
//            }
//        }
//        // from right
//        if(ball.getPositionX() <= brick.getPositionX()+ brick.getWidth() && ball.getPositionX() > brick.getPositionX()+ brick.getWidth() && ball.getPositionY()+ ball.getHeight()>= brick.getPositionY() && ball.getPositionY() <= brick.getPositionY()+brick.getHeight()) {
//            ball.moveRight();
//            soundPool.play(sample1, 1, 1, 0, 0, 1);
//            if(ball.getPositionY() < brick.getPositionY() + brick.getHeight()/2) {
//                ball.moveDown();
//            } else {
//                ball.moveUp();
//            }
//        }
//        //update position of ball based on current direction
//        ball.updatePosition();
        for(int i=0;i<bricks.size();i++) {
            collision(bricks.get(i));
        }
    }

    public void collision (Brick brick) {
        if (!brick.isDestroyed()) {
            if (ball.getPositionY() + ball.getWidth() >= brick.getPositionY() && ball.getPositionY() + ball.getWidth() <= brick.getPositionY() + brick.getHeight() / 2 && ball.getPositionX() + ball.getWidth() >= brick.getPositionX() && ball.getPositionX() <= brick.getPositionX() + brick.getWidth()) {
                soundPool.play(sample1, 1, 1, 0, 0, 1);
                ball.moveUp();
                brick.setDuration(brick.getDuration() - 1);
                if (ball.getPositionX() > brick.getPositionX() + brick.getWidth() / 2) {
                    ball.moveRight();
                } else {
                    ball.moveLeft();
                }
            }
            //from bottom
            if (ball.getPositionY() <= brick.getPositionY() + brick.getHeight() && ball.getPositionY() >= brick.getPositionY() + brick.getHeight() / 2 && ball.getPositionX() + ball.getWidth() > brick.getPositionX() && ball.getPositionX() < brick.getPositionX() + brick.getWidth()) {    //make sound
                soundPool.play(sample1, 1, 1, 0, 0, 1);
                brick.setDuration(brick.getDuration() - 1);
                ball.moveDown();
                if (ball.getPositionX() > brick.getPositionX() + brick.getWidth() / 2) {
                    ball.moveRight();
                } else {
                    ball.moveLeft();
                }
            }
            //from left
            if (ball.getPositionX() + ball.getWidth() >= brick.getPositionX() && ball.getPositionX() + ball.getWidth() <= brick.getPositionX() + brick.getWidth() / 2 && ball.getPositionY() + ball.getHeight() >= brick.getPositionY() && ball.getPositionY() <= brick.getPositionY() + brick.getHeight()) {
                ball.moveLeft();
                soundPool.play(sample1, 1, 1, 0, 0, 1);
                brick.setDuration(brick.getDuration() - 1);
                if (ball.getPositionY() < brick.getPositionY() + brick.getHeight() / 2) {
                    ball.moveDown();
                } else {
                    ball.moveUp();
                }
            }
            // from right
            if (ball.getPositionX() <= brick.getPositionX() + brick.getWidth() && ball.getPositionX() > brick.getPositionX() + brick.getWidth() && ball.getPositionY() + ball.getHeight() >= brick.getPositionY() && ball.getPositionY() <= brick.getPositionY() + brick.getHeight()) {
                ball.moveRight();
                soundPool.play(sample1, 1, 1, 0, 0, 1);
                brick.setDuration(brick.getDuration() - 1);
                if (ball.getPositionY() < brick.getPositionY() + brick.getHeight() / 2) {
                    ball.moveDown();
                } else {
                    ball.moveUp();
                }
            }
            //update position of ball based on current direction
            ball.updatePosition();

        }
    }

    public void drawCourt() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();
            //draw background
            Drawable d = getResources().getDrawable(R.drawable.background);
            d.setBounds(0,0,screenWidth,screenHeight);
            d.draw(canvas);

            //Paint paint = new Paint();
            canvas.drawColor(Color.BLACK);//the background
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(45);
            canvas.drawText("Score:" + score + " Lives:" + lives + " fps:" + fps, 20, 40, paint);

            //Draw the racket
            racket.draw(canvas);

            //Draw the ball
            ball.draw(canvas);
            //Draw the brick
            //brick.draw(canvas);
            for(int i=0;i<bricks.size();i++) {
                Brick current = bricks.get(i);
                if(current.getDuration() <= 0) {
                    current.setDestroyed(true);
                    bricks.remove(current);
                }
                if(!current.isDestroyed()) {
                    current.draw(canvas);
                }
            }
            if(bricks.size()<3) {
                bricks.add(new Brick(getContext(), screenWidth));
            }
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }


    public void controlFPS() {
        long  timeThisFrame = (System.currentTimeMillis() - lastFrameTime);
        long timeToSleep = 15 - timeThisFrame;
        if (timeThisFrame > 0) {
            fps = (int) (1000 / timeThisFrame);
        }
        if (timeToSleep > 0) {
            try {
                ourThread.sleep(timeToSleep);
            } catch (InterruptedException e) {
            }
        }
        lastFrameTime = System.currentTimeMillis();
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if (motionEvent.getX() >= screenWidth / 2) {
                    racket.moveRight();
                } else {
                    racket.moveLeft();
                }
                break;

            case MotionEvent.ACTION_UP:
                racket.stop();
                break;
        }
        return true;
    }

    public void pause() {
        playingSquash = false;
        try {
            ourThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playingSquash = true;
        ourThread = new Thread(this);
        ourThread.start();
    }
}

