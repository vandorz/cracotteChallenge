package com.m2dl.cracotte.cracottechallenge.game.domain.cave;

import android.graphics.Canvas;

import com.m2dl.cracotte.cracottechallenge.game.domain.Bat;
import com.m2dl.cracotte.cracottechallenge.game.domain.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaveBlock {
    private final Cave cave;
    private float positionX;
    private final List<CaveElement> topElements;
    private final List<CaveElement> bottomElements;

    public CaveBlock(Cave cave, float positionX) {
        this.cave = cave;
        this.positionX = positionX;
        this.topElements = new ArrayList<>();
        this.bottomElements = new ArrayList<>();
    }

    public void generate() {
        topElements.clear();
        bottomElements.clear();
        for (int i = 0; i < Cave.NB_ELEMENTS_PER_SCREEN; i++) {
            float blockPositionInX = cave.getWidth() / Cave.NB_ELEMENTS_PER_SCREEN * i + positionX;
            topElements.add(createTopElement(blockPositionInX));
            bottomElements.add(createBottomElement(blockPositionInX));
        }
    }

    private CaveElement createTopElement(float blockPositionInX) {
        CaveElement element = (new Random().nextInt(10) < 2) ? new Stalactite() : new Rock();
        element.randomizeSize();
        element.setWidth(cave.getWidth() / Cave.NB_ELEMENTS_PER_SCREEN);
        element.setPositionX(blockPositionInX);
        element.setPositionY(cave.getTop());
        return element;
    }

    private CaveElement createBottomElement(float blockPositionInX) {
        CaveElement element = (new Random().nextInt(10) < 3) ? new Stalagmite() : new Rock();
        element.randomizeSize();
        element.setWidth(cave.getWidth() / Cave.NB_ELEMENTS_PER_SCREEN);
        element.setPositionX(blockPositionInX);
        element.setPositionY(cave.getBottom() - element.getHeight());
        return element;
    }

    public void move(float movementInX) {
        positionX += movementInX;
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

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public boolean collision(Bat bat) {
        for (GameObject gameObject : topElements)
            if (bat.collision(gameObject))
                return true;
        for (GameObject gameObject : bottomElements)
            if (bat.collision(gameObject))
                return true;
        return false;
    }
}
