package com.m2dl.cracotte.cracottechallenge.game.domain.cave;

import android.graphics.Color;

public class Stalagmite extends CaveElement {
    private final static float MIN_SIZE = 100.0f;
    private final static float MAX_SIZE = 350.0f;

    public Stalagmite() {
        super(Color.rgb(158, 158, 158));
    }

    @Override
    public void randomizeSize() {
        this.randomizeSize((int)MIN_SIZE, (int)MAX_SIZE);
    }
}
