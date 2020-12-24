package com.example.group7assignment;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
    //for logcat
    private static final String TAG = MainThread.class.getSimpleName();

    // desired fps
    private final static int MAX_FPS = 500;
    // maximum number of frames to be skipped
    private final static int MAX_FRAME_SKIPS = 5;
    // the frame period
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    // Surface holder that can access the physical surface
    private SurfaceHolder surfaceHolder;
    // The actual view that handles inputs
    // and draws to the surface
    private MainGamePanel gamePanel;

    // flag to hold game state
    private boolean running;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void run(){
        Canvas canvas;
        Log.d(TAG, "Starting game loop");

        long beginTime;
        long timeDiff;
        int sleepTime = 0;
        int framesSkipped;


        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;
                    this.gamePanel.update();
                    this.gamePanel.render(canvas);
                    timeDiff = System.currentTimeMillis() - beginTime;

                    sleepTime += FRAME_PERIOD - timeDiff;

                    if (sleepTime > 0) {
                        Log.d(TAG, "sleep now");
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        Log.d(TAG, "skip now");
                        this.gamePanel.update(); //assume update() takes very little time compared to render()
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(canvas!=null){
                    this.gamePanel.stop();
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

    }
}
