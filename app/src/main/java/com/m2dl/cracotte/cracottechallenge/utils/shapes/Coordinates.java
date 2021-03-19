package com.m2dl.cracotte.cracottechallenge.utils.shapes;

public class Coordinates {
    private float x;
    private float y;

    public Coordinates() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void increaseX(float increment) {
        x += increment;
    }

    public void increaseY(float increment) {
        y += increment;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
