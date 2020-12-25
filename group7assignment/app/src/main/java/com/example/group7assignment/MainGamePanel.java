package com.example.group7assignment;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {
    //for logcat
    private static final String TAG = MainGamePanel.class.getSimpleName();
    private MainThread thread;
    Canvas canvas;
    private Ball ball;
    Grid grid;

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
        }
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
    }

    public void update() {
        this.ball.update();
        if(this.ball.isInHole()){
            this.ball.remove();
            this.grid.opponentBallDetect();
        }
    }
}
