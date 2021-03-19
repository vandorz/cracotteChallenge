package com.m2dl.cracotte.cracottechallenge.game.domain.cave;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cave {
    private final static int NB_ELEMENTS_PER_SCREEN = 10;
    private final List<CaveElement> topElements;
    private final List<CaveElement> bottomElements;
    private final float top;
    private final float bottom;
    private final float left;
    private final float right;
    private final float width;
    private final float height;

    public Cave(float top, float bottom, float left, float right) {
        this.topElements = new ArrayList<>();
        this.bottomElements = new ArrayList<>();
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.width = this.right - this.left;
        this.height = this.bottom - this.top;
    }

    public void generate() {
        topElements.clear();
        bottomElements.clear();
        for (int i = 0; i < NB_ELEMENTS_PER_SCREEN; i++) {
            float positionX = width / NB_ELEMENTS_PER_SCREEN * i + left;
            topElements.add(createTopElement(positionX));
            bottomElements.add(createBottomElement(positionX));
        }
    }

    private CaveElement createTopElement(float positionX) {
        CaveElement element = (new Random().nextInt(10) < 2) ? new Stalactite() : new Rock();
        element.randomizeSize();
        element.setWidth(width / NB_ELEMENTS_PER_SCREEN);
        element.setPositionX(positionX);
        element.setPositionY(top);
        return element;
    }

    private CaveElement createBottomElement(float positionX) {
        CaveElement element = (new Random().nextInt(10) < 3) ? new Stalagmite() : new Rock();
        element.randomizeSize();
        element.setWidth(width / NB_ELEMENTS_PER_SCREEN);
        element.setPositionX(positionX);
        element.setPositionY(bottom - element.getHeight());
        return element;
    }

    public void move(float movementInX) {
        for (CaveElement caveElement : topElements) {
            caveElement.move(movementInX, 0);
        }
        for (CaveElement caveElement : bottomElements) {
            caveElement.move(movementInX, 0);
        }
    }

    public void draw(Canvas canvas) {
        for (CaveElement caveElement : topElements) {
            caveElement.draw(canvas);
        }
        for (CaveElement caveElement : bottomElements) {
            caveElement.draw(canvas);
        }
    }
}
