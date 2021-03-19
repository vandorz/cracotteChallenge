package com.m2dl.cracotte.cracottechallenge.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.m2dl.cracotte.cracottechallenge.R;
import com.m2dl.cracotte.cracottechallenge.game.domain.cave.Cave;
import com.m2dl.cracotte.cracottechallenge.game.domain.cave.Rock;
import com.m2dl.cracotte.cracottechallenge.game.domain.cave.Stalactite;
import com.m2dl.cracotte.cracottechallenge.game.domain.cave.Stalagmite;
import com.m2dl.cracotte.cracottechallenge.game.domain.ApacheHelicopter;
import com.m2dl.cracotte.cracottechallenge.game.domain.Bat;
import com.m2dl.cracotte.cracottechallenge.game.domain.GameObject;
import com.m2dl.cracotte.cracottechallenge.game.domain.Ultrasound;
import com.m2dl.cracotte.cracottechallenge.scores.ScoresActivity;
import com.m2dl.cracotte.cracottechallenge.utils.shapes.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int MENU_HEIGHT = 150;
    public static final float GRAVITY = 0.4f;

    private final Handler gameViewHandler;

    private GameThread thread;

    private float screenHeight;
    private float screenWidth;

    private int backgroundColor;
    private int menuColor;
    private int menuTextColor;

    private long score;

    private Cave cave;

    private float lightMeasurement;

    private List<GameObject> gameObjectList;
    private Bat bat;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        initThread();
        initGame();
        gameViewHandler = new Handler();
    }

    private void initThread() {
        thread = new GameThread(getHolder(), this);
    }

    private void initGame() {
        initGameArea();
        initBat();
        initGameObjets();
        initScore();
        initCave();
        backgroundColor = Color.WHITE;
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

    private void initHelicopter() {
        float defineHelicopterWidth = screenWidth/10;
        float defineHelicopterHeight = screenWidth/10;
        float initialPositionX = screenWidth + (defineHelicopterWidth / 2);
        float initialPositionY = randomNumber(150, (int)screenHeight);
        ApacheHelicopter apacheHelicopter = new ApacheHelicopter(getContext(), defineHelicopterWidth, defineHelicopterHeight, initialPositionX, initialPositionY);
        gameObjectList.add(apacheHelicopter);
    }

    private void initGameObjets(){
        gameObjectList = new ArrayList<>();
    }

    private void initScore() {
        score = 0;
    }

    private void initCave() {
        cave = new Cave(MENU_HEIGHT, screenHeight, 0, screenWidth);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            drawBackground(canvas);
            drawBat(canvas);
            drawAllObjects(canvas);
            drawScoreMenu(canvas);
            drawCave(canvas);
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

    public void drawCave(Canvas canvas) {
        cave.draw(canvas);
    }

    private void drawBat(Canvas canvas) {
        bat.draw(canvas);
    }

    private void drawAllObjects(Canvas canvas) {
        for (GameObject gameObject : gameObjectList) {
            gameObject.draw(canvas);
        }
    }

    public void update() {
        randomizeHelicopterCreation();
        updateAllObjects();
        updateColors();
        updateCave();
        removeUnusedObjects();
        verifyCollisions();
    }

    private void verifyCollisions() {
        boolean lost = false;
        for (GameObject gameObject : gameObjectList) {
            if (bat.collision(gameObject)) {
                lost = true;
            }
        }
        if(cave.collision(bat)) {
            lost = true;
        }
        if (lost) {
            endTheGame();
        }
    }

    private void updateAllObjects(){
        bat.update();
        ArrayList<GameObject> toRemove = new ArrayList<>();
        for (GameObject gameObject : gameObjectList){
            gameObject.update();
            if(gameObject.getPositionX() < 0 - gameObject.getWidth()/2) {
                toRemove.add(gameObject);
            }
        }
        for (GameObject gameObject : toRemove) {
            gameObjectList.remove(gameObject);
        }
    }

    private void updateColors() {
        updateBackgroundColor();
        updateScoreMenuColor();
    }

    private void removeUnusedObjects(){
        List<GameObject> gameObjectToRemoveList = new ArrayList<>();
        for (GameObject gameObject : gameObjectList){
            if (!gameObject.isActive()) {
                gameObjectToRemoveList.add(gameObject);
            }
        }
        for (GameObject gameObject : gameObjectToRemoveList){
            gameObjectList.remove(gameObject);
        }
    }

    private void updateBackgroundColor() {
        backgroundColor = Color.WHITE;
    }

    private void updateScoreMenuColor() {
        menuColor = Color.rgb(0, 51, 102);
        menuTextColor = Color.WHITE;
    }

    private void updateCave() {
        cave.update();
    }

    private void randomizeHelicopterCreation() {
        int randomNumber = (int) randomNumber(1,50);
        if (randomNumber == 1){
            initHelicopter();
        }
    }

    public void performAccelerometerEvent() {

        cave.forward();
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

    private void launchUltrasound(){
        Ultrasound ultrasound = new Ultrasound(bat);
        gameObjectList.add(ultrasound);
        revealObjects();
    }

    private void revealObjects(){
        //TODO
        gameViewHandler.postDelayed(this::hideObjects, 3000);
    }

    private void hideObjects(){
        //TODO
    }

    public void accelerometerEvent() {
        // TODO
    }

    public void lightSensorEvent() {
        launchUltrasound();
    }

    private float randomNumber(int min, int max){
        return (float)(Math.random()*(max-min+1)+min);
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
