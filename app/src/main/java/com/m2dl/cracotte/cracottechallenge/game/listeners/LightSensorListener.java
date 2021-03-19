package com.m2dl.cracotte.cracottechallenge.game.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.m2dl.cracotte.cracottechallenge.game.GameView;

public class LightSensorListener implements SensorEventListener {
    private static final int LIGHT_CHANGE_THRESHOLD = 300;
    private final GameView gameView;
    private float lastLightMeasurement;

    public LightSensorListener(GameView gameView) {
        this.gameView = gameView;
        lastLightMeasurement = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensor = event.sensor.getType();
        float[] values = event.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_LIGHT) {
                float lightMeasurement = values[0];
                float higherMeasurement = Math.max(lightMeasurement, lastLightMeasurement);
                float lowerMeasurement = Math.min(lightMeasurement, lastLightMeasurement);
                if (lastLightMeasurement != 0 && (higherMeasurement - lowerMeasurement > LIGHT_CHANGE_THRESHOLD)) {
                    gameView.lightSensorEvent();
                }
                lastLightMeasurement = lightMeasurement;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
