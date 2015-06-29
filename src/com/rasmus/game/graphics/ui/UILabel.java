package com.rasmus.game.graphics.ui;

import com.rasmus.game.util.Vector2i;

import java.awt.*;

public class UILabel extends UIComponent {

    public String text;
    private int number;
    private Font font;
    public boolean dropShadow = false;
    private boolean allNumbers = false;
    public int dropShadowOffset = 2;

    public UILabel(Vector2i position, String text) {
        super(position);
        this.text = text;
        font = new Font("Helvetica", Font.PLAIN, 32);
        color = new Color(0xFF00FF);
    }

    public UILabel(Vector2i position, int number, boolean allNumbers) {
        super(position);
        font = new Font("Helvetica", Font.PLAIN, 32);
        color = new Color(0xFF00FF);
        this.number = number;
        this.allNumbers = allNumbers;
    }

    public void render(Graphics g) {
        if(dropShadow) {
            g.setFont(font);
            g.setColor(Color.BLACK);

            if(text != null && number == 0) {
                g.drawString(text, position.x + offset.x + dropShadowOffset, position.y + offset.y + dropShadowOffset);
            } else if(text != null && allNumbers && number > 0) {
                g.drawString(Integer.toString(number) +  " " + text, position.x + offset.x + dropShadowOffset, position.y + offset.y + dropShadowOffset);
            } else if(allNumbers) {
                g.drawString(Integer.toString(number), position.x + offset.x + dropShadowOffset, position.y + offset.y + dropShadowOffset);
            } else if(!allNumbers) {
                if(number <= 0 || number == 1) return;
                g.drawString(Integer.toString(number), position.x + offset.x + dropShadowOffset, position.y + offset.y + dropShadowOffset);
            }
        }

        g.setFont(font);
        g.setColor(color);

        if(text != null && number == 0) {
            g.drawString(text, position.x + offset.x, position.y + offset.y);
        } else if(text != null && allNumbers && number > 0) {
            g.drawString(Integer.toString(number) + " " + text, position.x + offset.x, position.y + offset.y);
        } else if(allNumbers) {
            g.drawString(Integer.toString(number), position.x + offset.x, position.y + offset.y);
        } else if(!allNumbers) {
            if(number <= 0 || number == 1) return;
            g.drawString(Integer.toString(number), position.x + offset.x, position.y + offset.y);
        }
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

    public void setNumber(int number) {
        this.number = number;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public UILabel setColor(Color color) {
        this.color = color;
        return this;
    }
}
