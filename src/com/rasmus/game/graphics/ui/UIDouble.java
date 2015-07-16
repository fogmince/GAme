package com.rasmus.game.graphics.ui;

import com.rasmus.game.util.Vector2i;

import java.awt.*;

public class UIDouble extends UIComponent {

    private double number;
    private Font font;

    public UIDouble(Vector2i position, double number) {
        super(position);
        this.number = number;
    }

    public void render(Graphics g) {
        g.setFont(font);
        g.setColor(color);
        number = Math.floor(number * 10) / 10;

        g.drawString(Double.toString(number), position.x + offset.x, position.y + offset.y);
    }

    public UIDouble setFont(Font font) {
        this.font = font;
        return this;
    }

    public UIDouble setColor(int color) {
        this.color = new Color(color);
        return this;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public UIDouble setColor(Color color) {
        this.color = color;
        return this;
    }
}
