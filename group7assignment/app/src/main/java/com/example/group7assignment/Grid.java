package com.example.group7assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Grid {
    //for logcat
    private static final String TAG = MainGamePanel.class.getSimpleName();

    private int height;
    private int width;
    private boolean won;
    private int[][] gridCoords = new int[9][4];
    private int[][] gridContents = new int[3][3];

    private final int topLeftX = 0;
    private final int topLeftY = 1;
    private final int bottomRightX = 2;
    private final int bottomRightY = 3;

    private final int empty = 0;
    private final int dotBall = 1;
    private final int crossBall = 2;

    public final int ROWS = 3, COLS = 3;


    private Bitmap crossBallImg;
    private Bitmap dotBallImg;
    private final Ball ball;


    public Grid(int height, int width, Bitmap crossBallImg, Bitmap dotBallImg, Ball ball) {
        this.height = height;
        this.width = width;
        this.crossBallImg = crossBallImg;
        this.dotBallImg = dotBallImg;
        this.ball = ball;
    }

    public void draw(Canvas canvas) {
        int ballRadius = dotBallImg.getWidth() / 2;
        int gridNum = 0;
        calCoord();

        Paint gridPaint = new Paint();
        gridPaint.setStrokeWidth(15);
        gridPaint.setColor(Color.WHITE);
//        gridPaint.setARGB(255, 255, 255, 255);


        if (won) {
            gridPaint.setStyle(Paint.Style.FILL);
            gridPaint.setTextSize(20);
            canvas.drawText("W O N", gridCoords[4][topLeftX], gridCoords[4][topLeftY], gridPaint);

        } else {
            //draw 9x9 grid
            gridPaint.setStyle(Paint.Style.STROKE);
            for (gridNum = 0; gridNum < 9; gridNum++) {
                canvas.drawRect(gridCoords[gridNum][topLeftX], gridCoords[gridNum][topLeftY],
                        gridCoords[gridNum][bottomRightX], gridCoords[gridNum][bottomRightY], gridPaint);
            }

            //draw ball in grid if any
            gridNum = 0;    //initialize gridNum
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (gridContents[row][col] == dotBall) {  //this grid has a player's ball
                        canvas.drawBitmap(dotBallImg, calCentreX(gridNum) - ballRadius,
                                calCentreY(gridNum) - ballRadius, null);
                    } else if (gridContents[row][col] == crossBall) {    //this grid has a opponent's ball
                        canvas.drawBitmap(crossBallImg, calCentreX(gridNum) - ballRadius,
                                calCentreY(gridNum) - ballRadius, null);
                    }
                    gridNum++;
                }
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

        gridWidth = calWidth();

        //center the whole grid
        gridStartX = (width - gridWidth * 3) / 2;

        gridStartY = (height - gridWidth * 3) / 2;


        //assign the coordinates of all grids
        bottomGridStartY = gridStartY + gridWidth;

        for (int gridNum = 0; gridNum < 9; gridNum++) {
            gridCoords[gridNum][topLeftX] = gridStartX + gridWidth * movedLeft;
            gridCoords[gridNum][bottomRightX] = gridStartX + gridWidth * (movedLeft + 1);
            gridCoords[gridNum][topLeftY] = gridStartY;
            gridCoords[gridNum][bottomRightY] = bottomGridStartY;

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
                if (gridNum == 0) {
                    gridContents[0][0] = dotBall;
                    hasWon();
                } else if (gridNum == 1) {
                    gridContents[0][1] = dotBall;
                    hasWon();
                } else if (gridNum == 2) {
                    gridContents[0][2] = dotBall;
                    hasWon();
                } else if (gridNum == 3) {
                    gridContents[1][0] = dotBall;
                    hasWon();
                } else if (gridNum == 4) {
                    gridContents[1][1] = dotBall;
                    hasWon();
                } else if (gridNum == 5) {
                    gridContents[1][2] = dotBall;
                    hasWon();
                } else if (gridNum == 6) {
                    gridContents[2][0] = dotBall;
                    hasWon();
                } else if (gridNum == 7) {
                    gridContents[2][1] = dotBall;
                    hasWon();
                } else if (gridNum == 8) {
                    gridContents[2][2] = dotBall;
                    hasWon();
                }

//
            }
        }
    }

    public void hasWon() {
        int currentRow = 0;
        int currentCol = 0;


//        for (int gridNum = 0; gridNum < 9; gridNum = +3) {
            //3 in the row
//            if (gridCoords[0][hasBall] == 1 && gridCoords[1][hasBall] == 1 &&
//                    gridCoords[2][hasBall] == 1) {
//                Log.d(TAG, "Game is finished. Player won.");
//                setWon(true);
//            }
//        }
        setWon(false);
    }

    private void setWon(boolean won) {
        Log.d(TAG, "Game is finished. Player won.");
        this.won = won;
    }
}
