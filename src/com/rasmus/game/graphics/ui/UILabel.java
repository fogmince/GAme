package com.rasmus.game.graphics.ui;

import com.rasmus.game.util.Vector2i;

import java.awt.*;

public class UILabel extends UIComponent {

    public String text;
    private Font font;
    public boolean dropShadow = false;
    public int dropShadowOffset = 2;

    public UILabel(Vector2i position, String text) {
        super(position);
        this.text = text;
        font = new Font("Helvetica", Font.PLAIN, 32);
        color = new Color(0xFF00FF);
    }

    public void render(Graphics g) {
        if(dropShadow) {
            g.setFont(font);
            g.setColor(Color.BLACK);
            g.drawString(text, position.x + offset.x + dropShadowOffset, position.y + offset.y + dropShadowOffset);
        }
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, position.x + offset.x, position.y + offset.y);
    }

    public UILabel setFont(Font font) {
        this.font = font;
        return this;
    }

    @Override
    public UILabel setColor(int color) {
        this.color = new Color(color);
        return this;
    }

    @Override
    public UILabel setColor(Color color) {
        this.color = color;
        return this;
    }
}
