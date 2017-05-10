package com.strobertchs.retrosquash;

import android.graphics.Canvas;

/**
 * Created by eric on 2017-05-07.
 * Class representing the ball gamepiece.
 *
 */
public class Ball extends AnimatedSprite {


    /**
     * Constructor
     * @param screen_width The width of the devices screen
     */
    public Ball(int screen_width){
        super();
        setWidth(screen_width/35);       // width set to 1/35th of the screen width
        setPositionX(screen_width/2);    // default the ball to be in the horizontal middle
        setPositionY(1 + getWidth());    // default the ball to be at the top

        setHorizontal_amount(12);     // horizontal_amount default to 12 pixels
        setUp_amount(10);             // default up_amount to 10
        setDown_amount(6);            // default down_amount to be 6
    }


    /**
     * draw: draws the ball to the canvas object
     * @param source_canvas the canvas object to draw the ball on
     */
    public void draw(Canvas source_canvas){
        source_canvas.drawRect(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getWidth(), paint);
    }

    public void moveStraight(){
        setMovingLeft(false);
        setMovingRight(false);
    }



    /**
     * updatePosition: based on movement flags, move update the position x and y by the proper movement amount
     */
    public void updatePosition(){

        if(isMovingLeft()){
            setPositionX(getPositionX() - getHorizontal_amount());
        }

        if(isMovingRight()){
            setPositionX(getPositionX() + getHorizontal_amount());
        }

        if(isMovingDown()){
            setPositionY(getPositionY() + getDown_amount());

        }else{
            setPositionY(getPositionY() - getUp_amount());

        }
    }


    /**
     * toString: print current position to the console in the F
     * @return
     */
    public String toString(){
        return "BALL--> x: " + Integer.toString(getPositionX()) + "  y: " + Integer.toString(getPositionY());
    }

}
