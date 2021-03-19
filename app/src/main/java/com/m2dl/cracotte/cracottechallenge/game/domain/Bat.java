package com.m2dl.cracotte.cracottechallenge.game.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.m2dl.cracotte.cracottechallenge.R;

public class Bat extends Sprite {

    public Bat(Context context) {
        super();
        this.setImage(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_bat), (int)this.getWidth(), (int)this.getHeight(), true));
    }
}
