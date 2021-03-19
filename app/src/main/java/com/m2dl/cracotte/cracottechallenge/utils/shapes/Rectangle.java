package com.m2dl.cracotte.cracottechallenge.utils.shapes;

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
        float minX = getPositionX();
        float maxX = getPositionX() + getWidth();
        float minY = getPositionY();
        float maxY = getPositionY() + getHeight();
        float otherMinX = rectangle.getPositionX();
        float otherMaxX = rectangle.getPositionX() + rectangle.getWidth();
        float otherMinY = rectangle.getPositionY();
        float otherMaxY = rectangle.getPositionY() + rectangle.getHeight();

        if (minX > otherMaxX || maxX < otherMinX) {
            return false;
        }
        if(minY > otherMaxY || maxY < otherMinY) {
            return false;
        }
        return true;
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
}
