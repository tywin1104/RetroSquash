package com.strobertchs.retrosquash;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by admin on 2017/5/22.
 */

public class Brick extends Sprite {
    public Brick(int screen_width){
        super();
        setPositionX((int)(screen_width/(Math.random()*8+1)));
        setPositionX((int)(screen_width/(Math.random()*8+1)));
        setWidth((int)(screen_width/(Math.random()*8+1)));
        setHeight((int)(screen_width/(Math.random()*8+1)));
    }
    @Override
    public void draw(Canvas canvas) {
            Rect destRect = new Rect(getPositionX(),
                    getPositionY(),
                    getPositionX() + getWidth(),
                    getPositionY() + getWidth());
    }
}
