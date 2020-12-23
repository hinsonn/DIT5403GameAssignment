package com.example.group7assignment;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;

import static android.graphics.Paint.Style.STROKE;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    //for logcat
    private static final String TAG = MainGamePanel.class.getSimpleName();
    private MainThread thread;
    Canvas canvas;
    Ball ball;
    Grid grid;

    //for accelerate the ball
//    private Sensor mSensorAccelerometer;

    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);
//        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ball=new Ball((SensorManager)context.getSystemService(Context.SENSOR_SERVICE), BitmapFactory.decodeResource(getResources(), R.drawable.cross_ball_1),539,820);

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "Surface is being destroyed");
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(true);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // delegating event handling to the Grid
            if(grid.handleActionDown((int)event.getX(), (int)event.getY()))
                Toast.makeText(((Activity)getContext()), "the ball rolled into hole 3", Toast.LENGTH_SHORT).show();

//            if(event.getY() > getHeight() - 50){
//                thread.setRunning(false);
//                ((Activity)getContext()).finish();
//            }
            Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
        }

        //leave the code for further use
/*
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            if(saucer.isTouched()){
                saucer.setX((int) event.getX());
                saucer.setY((int) event.getY());
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP){
            if(saucer.isTouched()){
                saucer.setTouched(false);
            }
        }
*/
        return true;
    }
    
    public void render(Canvas canvas) {
        //this.canvas = canvas;
        grid = new Grid(getHeight(),getWidth());
        grid.draw(canvas);

        Paint testPaint = new Paint();
        testPaint.setStrokeWidth(15);
        testPaint.setARGB(255, 255, 255, 255);
        testPaint.setStyle(Paint.Style.STROKE);
        for (int gridNum = 0; gridNum < 9; gridNum++)
            canvas.drawCircle(grid.calCentreX(gridNum), grid.calCentreY(gridNum), 1, testPaint);

        ball.draw(canvas);
    }

    public void update() {
        ball.update();
//        if (mSensorAccelerometer != null) {
//            mSensorManager.registerListener(this, mSensorAccelerometer,
//                    SensorManager.SENSOR_DELAY_GAME);
//        }
    }

    public void stop(){
    }


}
