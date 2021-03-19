package com.m2dl.cracotte.cracottechallenge.game.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.m2dl.cracotte.cracottechallenge.game.GameView;

public class AccelerometerSensorListener implements SensorEventListener {
    private final GameView gameView;

    public AccelerometerSensorListener(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensor = event.sensor.getType();
        float[] values = event.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_ACCELEROMETER) {
                // TODO
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
