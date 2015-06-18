package com.rasmus.game.entity.item;

import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class TestItem extends Item {

    public TestItem(double x, double y, Sprite sprite, boolean isInInventory) {
        super(x, y, sprite, isInInventory);
    }

    public void update() {
        if(interaction(x, y, level.getClientPlayer())) remove();
    }

    public void render(Screen screen) {
        if(!isInInventory) screen.renderItem((int) x << 4, (int) y << 4, this);
    }
}
