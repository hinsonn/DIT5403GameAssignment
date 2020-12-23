package com.example.group7assignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class Ball {
    private Bitmap bitmap;
    private float xVel = 0;
    private float yVel = 0;
    private float xMax;
    private float yMax;
    private float x;
    private float y;
    private boolean inHole;


    public Ball(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;

    }

    public void update(float xAccel, float yAccel){
        float frameTime = 1f;
        if(!inHole) {
            //update by xAccel and yAccel Sensor control
            xVel += (frameTime * xAccel);
            yVel += (frameTime * yAccel);

            x -= xVel*frameTime;
            y -= yVel*frameTime;

            if(x > xMax){
                x = xMax;
            }else if(x < 0){
                x = 0;
            }

            if(y > yMax){
                y = yMax;
            }else if(y < 0){
                y = 0;
            }
        }
    }

    public void draw(Canvas canvas) {
        this.xMax = canvas.getWidth() - 100;
        this.yMax = canvas.getHeight() - 100;
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2),
                y - (bitmap.getHeight() / 2), null);
    }

    public void goInHole () {

    }

    public boolean isInHole(){
        return this.inHole;
    }


    public Bitmap getBitmap() { return bitmap; }
    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; };
    public float getY() { return y;};
    public float getX(){ return x;};
    public void setXVel(float xVel) { this.xVel = xVel;};
    public void setYVel(float yVel) { this.yVel = yVel;};
    public float getxVel() { return this.xVel;};
    public float getyVel() { return this.yVel;};
    public void setX(int x) { this.x = x;};
    public void setY(int y) { this.y = y;};
    public float getxMax() { return this.xMax;};
    public float getyMax() { return this.yMax;};
    public void setXMax(int xMax) { this.xMax = xMax;};
    public void setYMax(int yMax) { this.yMax = yMax;};
}
