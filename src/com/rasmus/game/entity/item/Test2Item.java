package com.rasmus.game.entity.item;

import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class Test2Item extends Item {

    public Test2Item(double x, double y, Sprite sprite, boolean isInInventory) {
        super(x, y, sprite, isInInventory);
    }

    @Override
    public void render(Screen screen) {
        screen.renderItem((int) x, (int) y, this);
    }
}