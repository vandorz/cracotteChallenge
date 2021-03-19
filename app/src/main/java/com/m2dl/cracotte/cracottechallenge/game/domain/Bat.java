package com.m2dl.cracotte.cracottechallenge.game.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.m2dl.cracotte.cracottechallenge.R;

public class Bat extends Sprite {
    private float UP_SPEED_MODIFIER = 12;


    public Bat(Context context, float width, float height) {
        super();
        setWidth(width);
        setHeight(height);
        this.setImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_bat), (int)this.getWidth(), (int)this.getHeight(), true));
    }



    public void fly(){
        this.setSpeedY(- UP_SPEED_MODIFIER);
    }

}
