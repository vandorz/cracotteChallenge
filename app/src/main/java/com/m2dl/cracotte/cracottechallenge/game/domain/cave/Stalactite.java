package com.m2dl.cracotte.cracottechallenge.game.domain.cave;

import android.graphics.Color;

public class Stalactite extends CaveElement {
    private final static float MIN_SIZE = 150.0f;
    private final static float MAX_SIZE = 350.0f;

    public Stalactite() {
        super(Color.rgb(206, 206, 206));
    }

    @Override
    public void randomizeSize() {
        this.randomizeSize((int)MIN_SIZE, (int)MAX_SIZE);
    }
}
