package com.strobertchs.retrosquash;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Created by eric on 2017-05-08.
 */
public class Racket extends AnimatedSprite {



    public Racket(int screen_width, int screen_height){

        super(); //Constructor from Sprite

        setPositionX(screen_width / 2);
        setPositionY(screen_height - 20);
        setHorizontal_amount(10);

        setWidth(screen_width / 8);
        setHeight(10);


    }

    /**
     * stop horizontal movement
     */
    public void stop(){
        setMovingLeft(false);
        setMovingRight(false);
    }


    /**
     * update the position of the racket based on intended direction
     */
    public void updatePosition(){
        if(isMovingRight()){
            setPositionX(getPositionX() + getHorizontal_amount());
        }

        if(isMovingLeft()){
            setPositionX(getPositionX() - getHorizontal_amount());
        }
    }


    public void draw(Canvas source_canvas){
        source_canvas.drawRect(getPositionX() - (getWidth() / 2),
                getPositionY() - (getHeight() / 2),
                getPositionX() + (getWidth() / 2),
                getPositionY() + getHeight(),
                paint);
    }

}
