package com.m2dl.cracotte.cracottechallenge.game.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.m2dl.cracotte.cracottechallenge.game.GameView;

public class LightSensorListener implements SensorEventListener {
    private GameView gameView;

    public LightSensorListener(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensor = event.sensor.getType();
        float[] values = event.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_LIGHT) {
                float lightMeasurement = values[0];
                updateGameViewLightMeasurement(lightMeasurement);
            }
        }
    }

    private void updateGameViewLightMeasurement(float lightMeasurement) {
        gameView.updateLightMeasurement(lightMeasurement);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
