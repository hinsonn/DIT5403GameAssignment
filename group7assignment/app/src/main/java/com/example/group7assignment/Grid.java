package com.example.group7assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Grid {
    //for logcat
    private static final String TAG = MainGamePanel.class.getSimpleName();

    private final int height;
    private final int width;
    private final int topLeftX = 0;
    private final int topLeftY = 1;
    private final int bottomRightX = 2;
    private final int bottomRightY = 3;
    private final int hasBall = 4;  //0 = no ball, 1 = has player's' ball, 2 = has opponent's ball
    private final int[][] gridCoords = new int[9][5];

    private Bitmap crossBall;
    private Bitmap dotBall;
    private final Ball ball;


    public Grid(int height, int width, Bitmap crossBall, Bitmap dotBall, Ball ball) {
        this.height = height;
        this.width = width;
        this.crossBall = crossBall;
        this.dotBall = dotBall;
        this.ball = ball;
    }

    public void draw(Canvas canvas) {
        int ballRadius = dotBall.getWidth() / 2;
        calCoord();

        Paint gridPaint = new Paint();
        gridPaint.setStrokeWidth(15);
        gridPaint.setARGB(255, 255, 255, 255);
        gridPaint.setStyle(Paint.Style.STROKE);
        for (int gridNum = 0; gridNum < 9; gridNum++) {
            canvas.drawRect(gridCoords[gridNum][topLeftX], gridCoords[gridNum][topLeftY],
                    gridCoords[gridNum][bottomRightX], gridCoords[gridNum][bottomRightY], gridPaint);



//            Log.d(TAG, "grid " + gridNum + " has " + gridCoords[gridNum][hasBall] + " ball");
            //draw ball in grid if any
            if (gridCoords[gridNum][hasBall] == 1) {  //this grid has a player's ball
                canvas.drawBitmap(dotBall, calCentreX(gridNum) - ballRadius,
                        calCentreY(gridNum) - ballRadius, null);

                Log.d(TAG, "ball drawn in grid " + (gridNum + 1));
            } else if (gridCoords[gridNum][hasBall] == 2) {    //this grid has a opponent's ball
                canvas.drawBitmap(crossBall, gridCoords[gridNum][topLeftX] + calWidth(),
                        gridCoords[gridNum][topLeftY] + calWidth(), null);
            }
        }
    }

    public int calWidth() {
        //find the grid width, all sides of a grid shares the same width
        if (width / 3 > height / 5)
            return height / 5;
        else
            return width / 3;
    }

    public void calCoord() {
        int gridStartX = 0;
        int gridStartY = 0;
        int bottomGridStartY = 0;
        int gridWidth = 0;
        int movedLeft = 0;

        gridWidth = (int) (calWidth() * 0.99);    //space for the border thickness

        //center the whole grid
        gridStartX = (width - gridWidth * 3) / 2;
        ;
        gridStartY = (height - gridWidth * 3) / 2;


        //assign the coordinates of all grids
        bottomGridStartY = gridStartY + gridWidth;

        for (int gridNum = 0; gridNum < 9; gridNum++) {
            gridCoords[gridNum][topLeftX] = gridStartX + gridWidth * movedLeft;
            gridCoords[gridNum][bottomRightX] = gridStartX + gridWidth * (movedLeft + 1);
            gridCoords[gridNum][topLeftY] = gridStartY;
            gridCoords[gridNum][bottomRightY] = bottomGridStartY;
            gridCoords[gridNum][hasBall] = gridCoords[gridNum][hasBall];

            //go to next line after moved left twice
            if (movedLeft % 2 == 0 && movedLeft > 0) {
                gridStartY += gridWidth;
                bottomGridStartY += gridWidth;
                movedLeft = 0;
            } else
                movedLeft++;
        }
    }

    public int calCentreX(int gridNum) {
        return (gridCoords[gridNum][topLeftX] + gridCoords[gridNum][bottomRightX]) / 2;
    }

    public int calCentreY(int gridNum) {
        return (gridCoords[gridNum][topLeftY] + gridCoords[gridNum][bottomRightY]) / 2;
    }

    public void detectBall() {
        for (int gridNum = 0; gridNum < 9; gridNum++) {
            if (ball.goInHole(calCentreX(gridNum), calCentreY(gridNum))) {
                gridCoords[gridNum][hasBall] = 1;   //player rolled a ball inside this hole
//                Log.d(TAG, "ball in grid " + gridNum);
//                Log.d(TAG, "grid " + gridNum + " has " + gridCoords[gridNum][hasBall] + " ball");


            }
        }


    }

    public void update() {

    }
}
//testing for local.properties