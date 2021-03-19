package com.m2dl.cracotte.cracottechallenge.utils.shapes;

import android.util.Log;

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
        Coordinates bottomLeftThis = new Coordinates(getPositionX(), getPositionY() + getHeight());
        Coordinates topRightThis = new Coordinates(getPositionX() + getWidth(), getPositionY());
        Coordinates bottomLeftOther = new Coordinates(rectangle.getPositionX(), rectangle.getPositionY() + rectangle.getHeight());
        Coordinates topRightOther = new Coordinates(rectangle.getPositionX() + rectangle.getWidth(), rectangle.getPositionY());

        Log.d("Collisions:", "BLT " + bottomLeftThis + " TRT " + topRightThis + " BLO " + bottomLeftOther + " TRO " + topRightOther);

        if (topRightThis.getY() < bottomLeftOther.getY()
                || bottomLeftThis.getY() > topRightOther.getY()) {
            Log.d("Collisions:", "NON 1");
            return false;
        }
        if (topRightThis.getX() < bottomLeftOther.getX()
                || bottomLeftThis.getX() > topRightOther.getX()) {
            Log.d("Collisions", "NON 2");
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
