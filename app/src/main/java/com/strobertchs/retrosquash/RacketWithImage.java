package com.strobertchs.retrosquash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by admin on 2017/5/29.
 */

public class RacketWithImage extends Racket {
    private Bitmap bitmap;
    public RacketWithImage(Context context, int screen_width, int screen_height) {
        super(screen_width,screen_height);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.racketphoto);
    }
    //override
    public void draw(Canvas source_canvas){
//        source_canvas.drawRect(getPositionX() - (getWidth() / 2),
//                getPositionY() - (getHeight() / 2),
//                getPositionX() + (getWidth() / 2),
//                getPositionY() + getHeight(),
//                paint);
        Rect destRect = new Rect(getPositionX() - (getWidth() / 2),
                getPositionY() - (getHeight() / 2),
                getPositionX() + (getWidth() / 2),
                getPositionY() + getHeight());
        source_canvas.drawBitmap(bitmap, null, destRect, null);
    }
}
