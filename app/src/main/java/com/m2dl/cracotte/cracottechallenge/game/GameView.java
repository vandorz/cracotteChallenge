package com.m2dl.cracotte.cracottechallenge.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.m2dl.cracotte.cracottechallenge.R;
import com.m2dl.cracotte.cracottechallenge.game.domain.Bat;
import com.m2dl.cracotte.cracottechallenge.game.domain.GameObject;
import com.m2dl.cracotte.cracottechallenge.scores.ScoresActivity;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int MENU_HEIGHT = 150;
    public static final float GRAVITY = 0.4f;

    private GameThread thread;

    private float screenHeight;
    private float screenWidth;

    private int backgroundColor;
    private int menuColor;
    private int menuTextColor;

    private long score;

    private float lightMeasurement;

    private List<GameObject> gameObjectList;
    private Bat bat;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        initThread();
        initGame();
    }

    private void initThread() {
        thread = new GameThread(getHolder(), this);
    }

    private void initGame() {
        initGameArea();
        initBat();
        initGameObjets();
        initScore();
    }

    private void initGameArea() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    private void initBat(){
        float defineBatWidth = screenWidth/10;
        float defineBatHeight = screenWidth/10;
        bat = new Bat(getContext(), defineBatWidth, defineBatHeight);
        bat.setPositionX(screenWidth/5);
        bat.setPositionY(screenHeight/2);
        bat.setAccelerationY(GRAVITY);
    }

    private void initGameObjets(){
        gameObjectList = new ArrayList<>();
    }

    private void initScore() {
        score = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            drawBackground(canvas);
            drawScoreMenu(canvas);
            drawBat(canvas);
            drawAllObjects(canvas);
        }
    }

    public void drawBackground(Canvas canvas) {
        canvas.drawColor(backgroundColor);
    }

    public void drawScoreMenu(Canvas canvas) {
        String textScore = getResources().getString(R.string.game_textView_score) + " : " + score;

        float textSize = 40;
        float textTop = (float)MENU_HEIGHT / 2 + textSize / 2;

        Paint menuBackgroundPaint = new Paint();
        menuBackgroundPaint.setColor(menuColor);
        Paint menuTextPaint = new Paint();
        menuTextPaint.setColor(menuTextColor);
        menuTextPaint.setTextSize(textSize);
        menuTextPaint.setTextAlign(Paint.Align.CENTER);

        canvas.drawRect(0, 0, screenWidth, MENU_HEIGHT, menuBackgroundPaint);
        canvas.drawText(textScore, screenWidth / 2, textTop, menuTextPaint);
    }

    private void drawBat(Canvas canvas){
        bat.draw(canvas);
    }

    private void drawAllObjects(Canvas canvas){
        for (GameObject gameObject : gameObjectList){
            gameObject.draw(canvas);
        }
    }

    public void update() {
        updateAllObjects();
        updateColors();
    }

    private void updateAllObjects(){
        bat.update();
        for (GameObject gameObject : gameObjectList){
            gameObject.update();
        }
    }

    private void updateColors() {
        updateBackgroundColor();
        updateScoreMenuColor();
    }

    private void updateBackgroundColor() {
        backgroundColor = Color.WHITE;
    }

    private void updateScoreMenuColor() {
        menuColor = Color.rgb(0, 51, 102);
        menuTextColor = Color.WHITE;
    }

    public void performAccelerometerEvent(){
        bat.forward();
        //TODO reprendre l'esprit du forward pour faire scroll le décors plutôt que faire avancer la chauve souris
    }

    private void endTheGame() {
        thread.setRunning(false);
        navigateToScoresActivity();
    }

    public void navigateToScoresActivity() {
        Context context = getContext();
        Activity gameActivity = (Activity) context;
        Intent scoresIntent = new Intent().setClass(getContext(), ScoresActivity.class);
        getContext().startActivity(scoresIntent);
        gameActivity.finish();
    }

    public void touchedScreenEvent(float xPosition, float yPosition) {
        bat.fly();
    }

    public void updateLightMeasurement(float lightMeasurement) {
        this.lightMeasurement = lightMeasurement;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public float getScreenWidth() {
        return screenWidth;
    }
}
