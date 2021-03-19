package com.m2dl.cracotte.cracottechallenge.game.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.m2dl.cracotte.cracottechallenge.R;

public class ApacheHelicopter extends Sprite {
    private final float SPEED_VALUE = 1;

    public ApacheHelicopter(Context context, float width, float height, float initialPositionX, float initialPositionY) {
        super();
        setWidth(width);
        setHeight(height);
        setPositionY(initialPositionY);
        setPositionX(initialPositionX);
        this.setImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_helicopter), (int)this.getWidth(), (int)this.getHeight(), true));
    }

    public void update(){
        super.update();
        setSpeedX(-SPEED_VALUE);
    }
}
