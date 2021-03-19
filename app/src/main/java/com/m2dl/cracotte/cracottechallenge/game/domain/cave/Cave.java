package com.m2dl.cracotte.cracottechallenge.game.domain.cave;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cave {
    public final static int NB_ELEMENTS_PER_SCREEN = 10;
    private final float top;
    private final float bottom;
    private final float left;
    private final float right;
    private final float width;
    private final float height;

    private CaveBlock caveBlock;

    public Cave(float top, float bottom, float left, float right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.width = this.right - this.left;
        this.height = this.bottom - this.top;
        this.caveBlock = new CaveBlock(this);
        this.caveBlock.generate();
    }

    public void move(float movementInX) {
        this.caveBlock.move(movementInX);
    }

    public void draw(Canvas canvas) {
        this.caveBlock.draw(canvas);
    }

    public float getTop() {
        return top;
    }

    public float getBottom() {
        return bottom;
    }

    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
