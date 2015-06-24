package com.rasmus.game.entity.item;

import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class TestItem extends ItemSword {

    public TestItem(double x, double y, Sprite sprite, boolean isInInventory) {
        super(x, y, sprite, isInInventory);
    }

    public TestItem(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void onInteract(double x, double y, Player player) {
        remove();
    }

    public void update() {
        super.update();
    }

    public void render(Screen screen) {
        if(!isInInventory) screen.renderItem((int) x << 4, (int) y << 4, this);
    }
}
