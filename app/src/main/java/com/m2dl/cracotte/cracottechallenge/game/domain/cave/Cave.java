package com.m2dl.cracotte.cracottechallenge.game.domain.cave;

import android.graphics.Canvas;

import com.m2dl.cracotte.cracottechallenge.game.domain.Bat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cave {
    public final static int NB_ELEMENTS_PER_SCREEN = 10;
    private final static int NB_CAVE_BLOCKS = 3;
    private float FORWARD_SPEED_MODIFIER = 25;

    private final float top;
    private final float bottom;
    private final float left;
    private final float right;
    private final float width;
    private final float height;

    private float speedX;
    private List<CaveBlock> caveBlockList;

    public Cave(float top, float bottom, float left, float right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.width = this.right - this.left;
        this.height = this.bottom - this.top;
        
        this.caveBlockList = new ArrayList<>();
        for (int i = 0; i < NB_CAVE_BLOCKS; i++) {
            CaveBlock caveBlock = new CaveBlock(this, i * width + left);
            caveBlock.generate();
            this.caveBlockList.add(caveBlock);
        }
    }

    public void move() {
        for (CaveBlock caveBlock : caveBlockList) {
            caveBlock.move(-speedX);
            if(caveBlock.getPositionX() + width < left) {
                caveBlock.setPositionX((NB_CAVE_BLOCKS - 1) * width + left - speedX);
                caveBlock.generate();
            }
        }
    }

    public void update(){
        setSpeedX(getSpeedX()*0.9f);
        if (getSpeedX() < 1){
            setSpeedX(0);
        }else{
            move();
        }
    }

    public void draw(Canvas canvas) {
        for (CaveBlock caveBlock : caveBlockList) {
            caveBlock.draw(canvas);
        }
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

    public boolean collision(Bat bat) {
        for (CaveBlock caveBlock : caveBlockList)
            if (caveBlock.collision(bat))
                return true;
        return false;
    }

    public void forward(){
        this.setSpeedX(FORWARD_SPEED_MODIFIER);
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }
}
