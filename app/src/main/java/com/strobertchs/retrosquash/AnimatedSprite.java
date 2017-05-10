package com.strobertchs.retrosquash;

/**
 * Created by eric on 2017-05-09.
 * Abstract class outlining the attributes & behaviours of moving game pieces
 */
public abstract class AnimatedSprite extends Sprite {
    private boolean movingLeft;     // left flag
    private boolean movingRight;    // right flag
    private boolean movingUp;       // up flag
    private boolean movingDown;     // down flag
    private int horizontal_amount;  // how much the ball moves along the horizontal axis
    private int up_amount;          // how much the ball moves downward
    private int down_amount;        // how much the ball moves upward

    /**
     * Constructor - just calls the the parent constructor
     */
    public AnimatedSprite(){
        super();
    }

    /**
     * movingLeft getter
     * @return true if intended direction is left
     */
    public boolean isMovingLeft() {
        return movingLeft;
    }

    /**
     * movingRight getter
     * @return true if intended direction is right
     */
    public boolean isMovingRight() {
        return movingRight;
    }

    /**
     * movingRight setter
     * @param movingRight true to set the intended direction right
     */
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    /**
     * movingLeft setter
     * @param movingLeft true to set the intended direction left
     */
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    /**
     * movingUp getter
     * @return true when intended vertical direction is up
     */
    public boolean isMovingUp() {
        return movingUp;
    }

    /**
     * movingDown getter
     * @return true when intended vertical direction is down
     */
    public boolean isMovingDown() {
        return movingDown;
    }


    /**
     * Current value of horizontal movement (number of pixels to move horizontally)
     * @return int of current horizontal movement amount
     */
    public int getHorizontal_amount() {
        return horizontal_amount;
    }

    /**
     * set horizontal movement
     * @param horizontal_amount int number of pixels to move horizontally
     */
    public void setHorizontal_amount(int horizontal_amount) {
        this.horizontal_amount = horizontal_amount;
    }


    /**
     * getter for up_amount, the number of pixels moving upwards
     * @return
     */
    public int getUp_amount() {
        return up_amount;
    }


    /**
     * setter for up_amount, the number of pixels moving upwards
     * @param up_amount
     */
    public void setUp_amount(int up_amount) {
        this.up_amount = up_amount;
    }


    /**
     * getter for down_amount, the number of pixels downwards
     * @return
     */
    public int getDown_amount() {
        return down_amount;
    }


    /**
     * setter for down_amount, the number of pixels downwards
     * @param down_amount
     */
    public void setDown_amount(int down_amount) {
        this.down_amount = down_amount;
    }


    /**
     * set the intended direction to the right
     */
    public void moveRight(){
        movingRight = true;
        movingLeft = false;
    }


    /**
     * set the intended direction to the left
     */
    public void moveLeft(){
        movingRight = false;
        movingLeft = true;
    }


    /**
     * moveUp: indicate the ball to move up
     */
    public void moveUp(){
        movingUp = true;
        movingDown = false;
    }


    /**
     * moveDown: indicate the ball to move down
     */
    public void moveDown(){
        movingUp = false;
        movingDown = true;
    }


    /**
     * updatePosition - apply the horizontal & vertical amounts based on current direction flags
     */
    public abstract void updatePosition();

}
