package com.m2dl.cracotte.cracottechallenge.game.domain.cave;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.m2dl.cracotte.cracottechallenge.game.domain.Obstacle;

import java.util.Random;

public abstract class CaveElement extends Obstacle {
    private int color;

    public CaveElement(int color) {
        super();
        this.color = color;
    }

    public abstract void randomizeSize();

    protected void randomizeSize(int minSize, int maxSize) {
        this.setHeight((float) (new Random().nextInt(maxSize - minSize) + minSize));
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getColor());
        canvas.drawRect(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight(), paint);
    }

    public int getColor() {
        return color;
    }

    protected void setColor(int color) {
        this.color = color;
    }
}
