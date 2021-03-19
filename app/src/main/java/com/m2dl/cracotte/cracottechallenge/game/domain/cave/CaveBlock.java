package com.m2dl.cracotte.cracottechallenge.game.domain.cave;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaveBlock {
    private Cave cave;
    private final List<CaveElement> topElements;
    private final List<CaveElement> bottomElements;

    public CaveBlock(Cave cave) {
        this.cave = cave;
        this.topElements = new ArrayList<>();
        this.bottomElements = new ArrayList<>();
    }

    public void generate() {
        topElements.clear();
        bottomElements.clear();
        for (int i = 0; i < Cave.NB_ELEMENTS_PER_SCREEN; i++) {
            float positionX = cave.getWidth() / Cave.NB_ELEMENTS_PER_SCREEN * i + cave.getLeft();
            topElements.add(createTopElement(positionX));
            bottomElements.add(createBottomElement(positionX));
        }
    }

    private CaveElement createTopElement(float positionX) {
        CaveElement element = (new Random().nextInt(10) < 2) ? new Stalactite() : new Rock();
        element.randomizeSize();
        element.setWidth(cave.getWidth() / Cave.NB_ELEMENTS_PER_SCREEN);
        element.setPositionX(positionX);
        element.setPositionY(cave.getTop());
        return element;
    }

    private CaveElement createBottomElement(float positionX) {
        CaveElement element = (new Random().nextInt(10) < 3) ? new Stalagmite() : new Rock();
        element.randomizeSize();
        element.setWidth(cave.getWidth() / cave.NB_ELEMENTS_PER_SCREEN);
        element.setPositionX(positionX);
        element.setPositionY(cave.getBottom() - element.getHeight());
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
