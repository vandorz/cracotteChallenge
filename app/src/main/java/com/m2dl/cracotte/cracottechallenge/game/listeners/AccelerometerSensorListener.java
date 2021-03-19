package com.m2dl.cracotte.cracottechallenge.game.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.m2dl.cracotte.cracottechallenge.game.GameView;

import java.util.Date;

public class AccelerometerSensorListener implements SensorEventListener {
    private static final int ACCELERATION_THRESHOLD = 20;

    private final GameView gameView;

    public AccelerometerSensorListener(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensor = sensorEvent.sensor.getType();
        float[] values = sensorEvent.values;
        float[] gravity = new float[3];

        synchronized (this) {
            if (sensor == Sensor.TYPE_ACCELEROMETER) {
                final float alpha = (float) 0.8;
                gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2];

                float accelerationOnX = values[0] - gravity[0];
                float accelerationOnY = values[1] - gravity[1];
                float accelerationOnZ = values[2] - gravity[2];
                if (
                        Math.abs(accelerationOnX) > ACCELERATION_THRESHOLD ||
                                Math.abs(accelerationOnY) > ACCELERATION_THRESHOLD ||
                                Math.abs(accelerationOnZ) > ACCELERATION_THRESHOLD
                ) {
                    gameView.accelerometerEvent();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
