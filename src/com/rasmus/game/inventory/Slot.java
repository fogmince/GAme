package com.rasmus.game.inventory;

import com.rasmus.game.entity.item.Item;
import com.rasmus.game.graphics.ui.UIPanel;
import com.rasmus.game.graphics.ui.UISprite;
import com.rasmus.game.graphics.ui.UISquare;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.util.Vector2i;

import java.awt.*;


public class Slot {

    public double x, y;
    public double width, height;

    public boolean isMousedOver;
    public boolean isClicked;

    public UIPanel panel;
    public UISquare square;
    private UISprite itemSprite;
    public Item item;

    public boolean hasSquare = false;
    public boolean hasItem = false;
    private boolean hasRendered = false;

    public Slot(double x, double y, double width, double height, UIPanel panel) {
        this.x = x + 870;
        this.y = y;
        this.width = width;
        this.height = height;
        this.panel = panel;

        square = new UISquare(new Vector2i((int) x + 3, (int) y + 3), new Vector2i(42, 42));
        square.setColor(new Color(0x7FB8B2B2, true));
    }

    public void update() {
        square.update();
        if(Mouse.getButton() == 1) {
            if(Mouse.getX() > x && Mouse.getX() < x + width - 16 && Mouse.getY() > y && Mouse.getY() < y + height - 16) {
                isClicked = true;
            } else {
                isClicked = false;
            }
        } else {
            isClicked = false;
        }

        if(Mouse.getX() > x && Mouse.getX() < x + width - 16 && Mouse.getY() > y && Mouse.getY() < y + height - 16){
            isMousedOver = true;
        } else {
            isMousedOver = false;
            isClicked = false;
        }

        if(isMousedOver || isClicked) {
            if(!hasSquare) {
                panel.addComponent(square);
                hasSquare = true;
            }
        } else {
            panel.removeComponent(square);
            hasSquare = false;
        }

        if(hasItem && !hasRendered) {
            itemSprite = new UISprite(new Vector2i((int) x - 870, (int) y), item.getSprite().path);
            panel.addComponent(itemSprite);
            hasRendered = true;
        }

        if(!hasItem) {
            panel.removeComponent(itemSprite);
            hasRendered = false;
        }

    }

    public void addItem(Item item) {
        this.item = item;
        hasItem = true;
    }

    public UISprite removeItem() {
        hasItem = false;
        return itemSprite;
    }
}
