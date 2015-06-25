package com.rasmus.game.entity.item;

import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class TestItem extends ItemSword {

    public TestItem(double x, double y, Sprite sprite) {
        super(x, y, sprite);
    }

    public TestItem(Sprite sprite) {
        super(sprite);
        stackSize = 1;
    }

    @Override
    public void onInteract(double x, double y, Player player) {
        player.addItem(new TestItem(Sprite.sword), 1);
        remove();
    }

    public void render(Screen screen) {
        if(!isInInventory) screen.renderItem((int) x << 4, (int) y << 4, this);
    }
}
