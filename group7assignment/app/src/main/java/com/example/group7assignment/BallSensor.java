package com.example.group7assignment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BallSensor extends View implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mSensorAccelerometer;
    private float xAccel = 0;
    private float yAccel = 0;

    public BallSensor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (mSensorAccelerometer != null) {
            sensorManager.registerListener(this, mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public float getxAccel(){
        return this.xAccel;
    }

    public float getyAccel(){
        return this.yAccel;
    }

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
//                DecimalFormat precision = new DecimalFormat("0.00");
//                Log.d("Sensor Test", "xAccel = " + precision.format(sensorEvent.values[0])
//                        + " yAccel = " + precision.format(sensorEvent.values[1])
//                        + " zAccel = " + precision.format(sensorEvent.values[2]));
            default:
//                Log.d("SENSOR:", "not light or proximity");
                break;
        }

//        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
