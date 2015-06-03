package com.rasmus.game.util;

public class Vector2i {

    private int x, y;

    public Vector2i() {
        set(0, 0);
    }

    public Vector2i(int x, int y) {
        set(x, y);
    }

    public Vector2i(Vector2i vector) {
        set(vector.getX(), vector.getY());
    }

    public Vector2i add(Vector2i vector) {
        this.x += vector.getX();
        this.y += vector.getY();
        return this;
    }

    public Vector2i subtract(Vector2i vector) {
        this.x -= vector.getX();
        this.y -= vector.getY();
        return this;
    }

    public static double getDistance(Vector2i v0, Vector2i v1) {
        double x = v0.getX() - v1.getX();
        double y = v0.getY() - v1.getX();
        return Math.sqrt(x * x * y * y);
    }

    public boolean equals(Object object) {
        if(!(object instanceof Vector2i)) return false;
        Vector2i vec = (Vector2i) object;
        if(vec.getX() == this.getX() && vec.getY() == this.getY()) return true;
        return false;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i setX(int x) {
        this.x = x;
        return this;
    }

    public Vector2i setY(int y) {
        this.y = y;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
