package com.example.group7assignment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.text.DecimalFormat;

public class Ball implements SensorEventListener {
    private Bitmap bitmap;
    private float xVel = 0;
    private float yVel = 0;
    private float xAccel ;
    private float yAccel ;
    private float xMax;
    private float yMax;
    private float x;
    private float y;
    private boolean inHole;
    private SensorManager sensorManager;
    private Sensor mSensorAccelerometer;

    public Ball(SensorManager sensorManager, Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.sensorManager = sensorManager;

    }

    public void update(){
        mSensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                if (mSensorAccelerometer != null) {
                    sensorManager.registerListener(this, mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // The sensor type (as defined in the Sensor class).
        int sensorType = sensorEvent.sensor.getType();
        // element in the values array.
        float currentValue = sensorEvent.values[0];
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                xAccel = sensorEvent.values[0];
                yAccel = -sensorEvent.values[1];
                DecimalFormat precision = new DecimalFormat("0.00");
                Log.d("Sensor Test","xAccel = " + precision.format(sensorEvent.values[0])
                        + " yAccel = " + precision.format(sensorEvent.values[1])
                        + " zAccel = " + precision.format(sensorEvent.values[2]));
            default:
//                Log.d("SENSOR:", "not light or proximity");
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
