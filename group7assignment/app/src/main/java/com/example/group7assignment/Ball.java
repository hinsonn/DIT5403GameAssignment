package com.example.group7assignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.text.DecimalFormat;

public class Ball  {
    private Bitmap bitmap;
    private float xVel = 0;
    private float yVel = 0;
    private float xMax = 0;
    private float yMax = 0;
    private float x;
    private float y;
    private boolean inHole;
    private BallSensor ballSensor;

    public Ball(Context context, Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        ballSensor = new BallSensor(context, null);
    }

    public void update() {
//        Log.d("Update Ball: ", "x = "+x+" y = "+y);

        float frameTime = 0.66f;
        if (!inHole) {
            //update by xAccel and yAccel Sensor control
            xVel += (frameTime * ballSensor.getxAccel());
            yVel += (frameTime * ballSensor.getyAccel());

            x -= xVel * frameTime;
            y -= yVel * frameTime;

            if(xMax != 0)
            if (x > xMax) {
                x = xMax;
            } else if (x < 0) {
                x = 0;
            }

            if(yMax != 0)
            if (y > yMax) {
                y = yMax;
            } else if (y < 0) {
                y = 0;
            }
        }
    }

    public void draw(Canvas canvas) {
//        Log.d("Draw Ball: ", "x = "+x+" y = "+y);
        this.xMax = canvas.getWidth() - bitmap.getWidth() / 2;
        this.yMax = canvas.getHeight() - bitmap.getHeight() / 2;
        canvas.drawBitmap(this.bitmap, x - (this.bitmap.getWidth() / 2),
                y - (this.bitmap.getHeight() / 2), null);
    }

    public boolean goInHole(int targetX, int targetY) {
        double radius;
        double centreX = x;
        double centreY = y;
        double distDiffX;   //distance from target to the ball centre by X
        double distDiffY;   //distance from target to the ball centre by X

        distDiffX = targetX - centreX;
        distDiffY = targetY - centreY;

        radius = bitmap.getWidth() / 2;

        if (Math.pow(distDiffX, 2) + Math.pow(distDiffY, 2) <= Math.pow(radius, 2)) {
//            setInHole(true);
            return true;
        } else {
            return false;
        }
    }

    private void setInHole(boolean inHole) {
        this.inHole = inHole;
    }

    public boolean isInHole() {
        return inHole;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    ;

    public float getY() {
        return y;
    }

    ;

    public float getX() {
        return x;
    }

    ;

    public void setXVel(float xVel) {
        this.xVel = xVel;
    }

    ;

    public void setYVel(float yVel) {
        this.yVel = yVel;
    }

    ;

    public float getxVel() {
        return this.xVel;
    }

    ;

    public float getyVel() {
        return this.yVel;
    }

    ;

    public void setX(int x) {
        this.x = x;
    }

    ;

    public void setY(int y) {
        this.y = y;
    }

    ;

    public float getxMax() {
        return this.xMax;
    }

    ;

    public float getyMax() {
        return this.yMax;
    }

    ;

    public void setXMax(int xMax) {
        this.xMax = xMax;
    }

    ;

    public void setYMax(int yMax) {
        this.yMax = yMax;
    }

    ;

}
