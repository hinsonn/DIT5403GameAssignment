package com.example.group7assignment;

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
    private final int[][] gridCoords = new int[9][4];


    public Grid(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void draw(Canvas canvas) {
        calCoord();

        Paint gridPaint = new Paint();
        gridPaint.setStrokeWidth(15);
        gridPaint.setARGB(255, 255, 255, 255);
        gridPaint.setStyle(Paint.Style.STROKE);
        for (int gridNum = 0; gridNum < 9; gridNum++) {
            canvas.drawRect(gridCoords[gridNum][topLeftX], gridCoords[gridNum][topLeftY],
                    gridCoords[gridNum][bottomRightX], gridCoords[gridNum][bottomRightY], gridPaint);
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

    public boolean handleActionDown(int touchX, int touchY) {
        int radius = calWidth() / 2;
        int distDiffX = touchX - calCentreX(4);    //distance from touch point to center by X
        int distDiffY = touchY - calCentreY(4);    //distance from touch point to center by Y


        Log.d(TAG, "Coords: touchx=" + touchX + ",touchy=" + touchY + " centreX=" + calCentreX(3) + " centreY=" + calCentreY(3));

        if (Math.ceil(touchX) == Math.ceil(calCentreX(4))) {
            if (Math.ceil(touchY) == Math.ceil(calCentreY(4))) {
                return true;
            }
        }
        return false;


//        Paint testPaint = new Paint();
//        testPaint.setStrokeWidth(15);
//        testPaint.setARGB(255, 255, 255, 255);
//        testPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(calCentreX(3), calCentreY(3), radius, testPaint);

//        The formula is just interpretation of schools geometry for determining if dot is inside circle area or not.
//        return Math.sqrt(distDiffX)+Math.sqrt(distDiffY)<= radius * radius;

    }
}
