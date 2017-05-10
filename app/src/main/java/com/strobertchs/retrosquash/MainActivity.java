package com.strobertchs.retrosquash;


import android.os.Bundle;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.KeyEvent;


public class MainActivity extends Activity {


    GameView squashCourtGame;

    //For getting display details like the number of pixels
    Display display;
    Point size;
    int screenWidth;
    int screenHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the screen size in pixels
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        squashCourtGame = new GameView(this, screenWidth, screenHeight);
        setContentView(squashCourtGame);
    }


    @Override
    protected void onStop() {
        super.onStop();
        while (true) {
            squashCourtGame.pause();
            break;
        }
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        squashCourtGame.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        squashCourtGame.resume();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            squashCourtGame.pause();
            finish();
            return true;
        }
        return false;
    }

}
