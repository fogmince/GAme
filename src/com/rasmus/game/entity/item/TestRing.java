package com.rasmus.game.entity.item;

import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class TestRing extends ItemRing {

    public TestRing(double x, double y, Sprite sprite, boolean isInInventory) {
        super(x, y, sprite, isInInventory);
        stackSize = 1;
    }

    public TestRing(Sprite sprite) {
        super(sprite);
        stackSize = 1;
    }

    @Override
    public void onInteract(double x, double y, Player player) {
        player.addItem(new TestRing(Sprite.floor_wood), 1);
        remove();
    }

    public void render(Screen screen) {
        if(!isInInventory) screen.renderItem((int) x << 4, (int) y << 4, this);
    }
}
