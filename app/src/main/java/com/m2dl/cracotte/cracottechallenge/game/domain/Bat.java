package com.m2dl.cracotte.cracottechallenge.game.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.m2dl.cracotte.cracottechallenge.R;

public class Bat extends Sprite {
    private float UP_SPEED_MODIFIER = 12;
    private float FORWARD_SPEED_MODIFIER = 13;

    public Bat(Context context, float width, float height) {
        super();
        setWidth(width);
        setHeight(height);
        this.setImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_bat), (int)this.getWidth(), (int)this.getHeight(), true));
    }

    public void update(){
        super.update();
        setSpeedX(getSpeedX()*0.9f);
        if (getSpeedX() < 1){
            setSpeedX(0);
        }
    }

    public void fly(){
        this.setSpeedY(- UP_SPEED_MODIFIER);
    }

    public void forward(){
        this.setSpeedX(FORWARD_SPEED_MODIFIER);
    }

}
