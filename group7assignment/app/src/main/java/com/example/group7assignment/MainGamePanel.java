package com.example.group7assignment;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;

import static android.graphics.Paint.Style.STROKE;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
    //testing
    //for logcat
    private static final String TAG = MainGamePanel.class.getSimpleName();
    private MainThread thread;
    Canvas canvas;
    private Ball ball;
    private Ball oppoBall;
    Grid grid;

    //for accelerate the ball
//    private Sensor mSensorAccelerometer;

    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        if(this.ball == null){
            this.ball = new Ball(context, BitmapFactory.decodeResource(getResources(), R.drawable.dot_ball_1), 539, 820);
        }

        //find screen height & width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        grid = new Grid(displayMetrics.heightPixels, displayMetrics.widthPixels,
                BitmapFactory.decodeResource(getResources(), R.drawable.cross_ball_1),
                BitmapFactory.decodeResource(getResources(), R.drawable.dot_ball_1), ball,
                BitmapFactory.decodeResource(getResources(), R.drawable.won_screen_1),
                BitmapFactory.decodeResource(getResources(), R.drawable.lost_screen_1),
                BitmapFactory.decodeResource(getResources(), R.drawable.draw_screen_1));

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();

    }


    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {


    }

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
            grid.detectBall();
//            if(grid.handleActionDown((int)event.getX(), (int)event.getY())) {
//            for (int i=0;i<9;i++){
//                ball.goInHole(grid.calCentreY(i), grid.calCentreY(i));
//                Toast.makeText(((Activity)getContext()), "the ball rolled into a hole",
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            }
//
//            if(event.getY() > getHeight() - 50){
//                thread.setRunning(false);
//                ((Activity)getContext()).finish();
//            }
//            Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
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

    @Override
    protected void onDraw(Canvas canvas) {


    }

    public void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        this.canvas = canvas;
        this.ball.draw(canvas);
        grid.draw(canvas);

//        Paint testPaint = new Paint();
//        testPaint.setStrokeWidth(15);
//        testPaint.setARGB(255, 255, 255, 255);
//        testPaint.setStyle(Paint.Style.STROKE);
//        for (int gridNum = 0; gridNum < 9; gridNum++)
//            canvas.drawCircle(grid.calCentreX(gridNum), grid.calCentreY(gridNum), 1, testPaint);


    }

    public void update() {

        this.ball.update();
        if(this.ball.isInHole()){
            this.ball.remove();
            this.grid.opponentBallDetect();
        }
    }

    public void stop() {
    }


}
