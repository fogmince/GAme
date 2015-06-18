package com.rasmus.game.graphics.ui;

import com.rasmus.game.util.Vector2i;

import java.awt.*;

public class UISquare extends UIComponent {

    private Vector2i size;

    public UISquare(Vector2i position, Vector2i size) {
        super(position);
        this.size = size;
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
    }
}
