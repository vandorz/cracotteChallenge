package com.m2dl.cracotte.cracottechallenge.game.domain;

import com.m2dl.cracotte.cracottechallenge.utils.shapes.Coordinates;
import com.m2dl.cracotte.cracottechallenge.utils.shapes.Rectangle;

public class Hitbox extends Rectangle {
    public Hitbox(Coordinates position, float height, float width) {
        super(position, height, width);
    }
}
