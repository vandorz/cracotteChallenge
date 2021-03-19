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
import com.m2dl.cracotte.cracottechallenge.game.domain.cave.Cave;
import com.m2dl.cracotte.cracottechallenge.game.domain.cave.Rock;
import com.m2dl.cracotte.cracottechallenge.game.domain.cave.Stalactite;
import com.m2dl.cracotte.cracottechallenge.game.domain.cave.Stalagmite;
import com.m2dl.cracotte.cracottechallenge.scores.ScoresActivity;
import com.m2dl.cracotte.cracottechallenge.utils.shapes.Coordinates;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int MENU_HEIGHT = 150;

    private GameThread thread;

    private float screenHeight;
    private float screenWidth;

    private int backgroundColor;
    private int menuColor;
    private int menuTextColor;

    private long score;

    private Cave cave;

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
        initScore();
        initCave();
        backgroundColor = Color.WHITE;
    }

    private void initGameArea() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    private void initScore() {
        score = 0;
    }

    private void initCave() {
        cave = new Cave(MENU_HEIGHT, screenHeight, 0, screenWidth);
        cave.generate();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            drawBackground(canvas);
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

    public void update() {
        updateColors();
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
        // TODO
    }

    public void accelerometerEvent() {
        // TODO
    }

    public void lightSensorEvent() {
        // TODO
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
