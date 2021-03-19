package com.m2dl.cracotte.cracottechallenge.game.domain;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Sprite extends GameObject {

    private Bitmap image;

    public Sprite() {
        super();
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.image != null) {
            Paint paint = new Paint();
            paint.setAlpha(this.getOpacity());
            canvas.drawBitmap(image, this.getPositionX(), this.getPositionY(), paint);
        } else {
            Log.e("Sprite", "Cannot draw sprite because image is null");
        }
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
