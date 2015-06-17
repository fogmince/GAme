package com.rasmus.game.graphics.ui;

import com.rasmus.game.util.Vector2i;

import java.awt.*;

public class UIComponent {

    public int backgroundColor;
    public Vector2i position, offset;
    public Color color;


    public UIComponent(Vector2i position) {
        this.position = position;
        offset = new Vector2i();
    }

    public void update() {

    }

    public void render(Graphics g) {
        
    }

    void setOffset(Vector2i offset) {
        this.offset = offset;
    }

    public UIComponent setColor(int color) {
        this.color = new Color(color);
        return this;
    }

    public UIComponent setColor(Color color) {
        this.color = color;
        return this;
    }

}
