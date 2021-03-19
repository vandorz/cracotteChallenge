package com.m2dl.cracotte.cracottechallenge.game.domain.cave;

import android.graphics.Color;

public class Rock extends CaveElement {
    private final static float MIN_SIZE = 15.0f;
    private final static float MAX_SIZE = 100.0f;

    public Rock() {
        super(Color.rgb(96, 96, 96));
    }

    @Override
    public void randomizeSize() {
        this.randomizeSize((int)MIN_SIZE, (int)MAX_SIZE);
    }
}
