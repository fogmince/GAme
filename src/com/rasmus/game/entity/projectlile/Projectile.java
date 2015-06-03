package com.rasmus.game.entity.projectlile;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.graphics.Sprite;

import java.util.Random;

public abstract class Projectile extends Entity {

    protected final double xOrigin, yOrigin;
    protected double x, y;
    protected double angle;
    protected Sprite sprite;
    protected double nx, ny;
    protected double speed, range, damage;
    protected int size;

    protected final Random random = new Random();

    public Projectile(double x, double y, double dir) {
        xOrigin = x;
        yOrigin = y;
        angle = dir;
        this.x = x;
        this.y = y;
    }

    protected void move() {
        x += nx;
        y += ny;


        if(distance() > range) remove();
    }

    private double distance() {
        return Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getSpriteSize() {
        return sprite.SIZE;
    }

}
