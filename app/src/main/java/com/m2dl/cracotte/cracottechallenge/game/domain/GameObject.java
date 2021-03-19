package com.m2dl.cracotte.cracottechallenge.game.domain;

import android.graphics.Canvas;

import com.m2dl.cracotte.cracottechallenge.utils.shapes.Coordinates;

public abstract class GameObject {
    private Coordinates position;
    private float height;
    private float width;
    private float speedX;
    private float speedY;
    private float accelerationX;
    private float accelerationY;
    private int opacity;

    public GameObject() {
        this.position = new Coordinates();
        this.height = 0.0f;
        this.width = 0.0f;
        this.speedX = 0.0f;
        this.speedY = 0.0f;
        this.accelerationX = 0.0f;
        this.accelerationY = 0.0f;
        this.opacity = 100;
    }

    public abstract void draw(Canvas canvas);

    public void move(float movementInX, float movementInY) {
        position.increaseX(movementInX);
        position.increaseY(movementInY);
    }

    public boolean collision(GameObject gameObject) {
        return hitbox().isOverlapping(gameObject.hitbox());
    }

    private Hitbox hitbox() {
        return new Hitbox(position, height, width);
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public void update(){
        this.position.setX(this.position.getX() + this.speedX);
        this.position.setY(this.position.getY() + this.speedY);
        this.speedX += this.accelerationX;
        this.speedY += this.accelerationY;
    }

    public float getPositionX() {
        return position.getX();
    }

    public void setPositionX(float positionX) {
        position.setX(positionX);
    }

    public float getPositionY() {
        return position.getY();
    }

    public void setPositionY(float positionY) {
        position.setY(positionY);
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
