package com.m2dl.cracotte.cracottechallenge.utils.shapes;

import android.graphics.Rect;

public class Rectangle {
    private Coordinates position;
    private float height;
    private float width;

    public Rectangle(Coordinates position, float height, float width) {
        this.position = position;
        this.height = height;
        this.width = width;
    }

    public boolean isOverlapping(Rectangle rectangle) {
        Rect self = new Rect((int)position.getX(), (int)position.getY(), (int)position.getX() + (int)width, (int)position.getY() + (int)height);
        Rect other = new Rect((int)rectangle.getPositionX(), (int)rectangle.getPositionY(), (int)rectangle.getPositionX() + (int)rectangle.getWidth(), (int)rectangle.getPositionY() + (int)rectangle.getHeight());
        return self.intersect(other);
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

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
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

    @Override
    public String toString() {
        return "Rectangle{" +
                "position=" + position +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
