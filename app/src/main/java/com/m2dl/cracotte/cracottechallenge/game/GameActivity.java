package com.m2dl.cracotte.cracottechallenge.game;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.m2dl.cracotte.cracottechallenge.game.listeners.AccelerometerSensorListener;
import com.m2dl.cracotte.cracottechallenge.game.listeners.LightSensorListener;

public class GameActivity extends Activity {
    private GameView gameView;
    private SensorManager sensorManager;

    private AccelerometerSensorListener accelerometerSensorListener;
    private LightSensorListener lightSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        initListeners();
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        destroyListeners();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyListeners();
    }

    private void initListeners() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        initAccelerometerListener();
        initLightSensorListener();
    }

    private void initAccelerometerListener() {
        accelerometerSensorListener = new AccelerometerSensorListener(gameView);
    }

    private void initLightSensorListener() {
        lightSensorListener = new LightSensorListener(gameView);
    }

    private void registerListeners() {
        registerAccelerometer();
        registerLightSensor();
        registerTouchScreen();
    }

    private void registerAccelerometer() {
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(accelerometerSensorListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    private void registerLightSensor() {
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private void destroyListeners() {
        destroyAccelerometer();
        destroyLightSensor();
    }

    private void destroyAccelerometer() {
        sensorManager.unregisterListener(accelerometerSensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
    }

    private void destroyLightSensor() {
        sensorManager.unregisterListener(lightSensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void registerTouchScreen() {
        gameView.setOnTouchListener((v, event) -> {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                gameView.touchedScreenEvent(event.getX(), event.getY());
            }
            return true;
        });
        gameView.setOnClickListener(listener -> gameView.touchedScreenEvent(listener.getPivotX(), listener.getPivotY()));
    }
}
