package com.m2dl.cracotte.cracottechallenge.game.domain;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Ultrasound extends GameObject {
    private static final int ULTRASOUND_SPEED = 20;
    private static final int MAXIMUM_ULTRASOUND_DISTANCE = 500;
    Bat bat;

    public Ultrasound(Bat bat){
        super();
        setPositionX(bat.getPositionX());
        setPositionY(bat.getPositionY());
        setSpeedX(ULTRASOUND_SPEED);
        this.bat = bat;
        this.setActive(true);
    }

    public void draw(Canvas canvas){
        RectF rectF = new RectF();
        rectF.set(getPositionX()-200,getPositionY()-300, getPositionX()+200,getPositionY()+300);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(69,65,99));
        canvas.drawArc(rectF,-45,90, false, paint);
    }

    @Override
    public void update(){
        super.update();
        if (getPositionX() > bat.getPositionX() + MAXIMUM_ULTRASOUND_DISTANCE){
            setActive(false);
        }
    }

}
