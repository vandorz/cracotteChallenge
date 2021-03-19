package com.m2dl.cracotte.cracottechallenge.game.domain;

import android.graphics.Canvas;

public abstract class GameObject {
    private float positionX;
    private float positionY;
    private float height;
    private float width;
    private float speedX;
    private float speedY;
    private float accelerationX;
    private float accelerationY;
    private int opacity;

    public GameObject() {
        this.positionX = 0.0f;
        this.positionY = 0.0f;
        this.height = 0.0f;
        this.width = 0.0f;
        this.speedX = 0.0f;
        this.speedY = 0.0f;
        this.accelerationX = 0.0f;
        this.accelerationY = 0.0f;
        this.opacity = 100;
    }

    public abstract void draw(Canvas canvas);

    public void move(float movementInX, float movemntInY) {
        this.positionY += movemntInY;
        this.positionX += movementInX;
    }

    public void update(){
        this.positionX += this.speedX;
        this.positionY += speedY;
        this.speedX += this.accelerationX;
        this.speedY += this.accelerationY;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getAccelerationX() {
        return accelerationX;
    }

    public void setAccelerationX(float accelerationX) {
        this.accelerationX = accelerationX;
    }

    public float getAccelerationY() {
        return accelerationY;
    }

    public void setAccelerationY(float accelerationY) {
        this.accelerationY = accelerationY;
    }

    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }
}
