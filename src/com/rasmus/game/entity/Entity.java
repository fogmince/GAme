package com.rasmus.game.entity;

import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.level.Level;

import java.util.Random;

public class Entity {

    protected double x, y;
    private boolean removed = false;
    protected Level level;
    protected final Random random = new Random();
    protected Sprite sprite;

    public Entity() {

    }

    public Entity(int x, int y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void update() {}

    public void render(Screen screen) {
        if(sprite != null) screen.renderSprite((int) x, (int) y, sprite, true);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void init(Level level) {
        this.level = level;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

}
