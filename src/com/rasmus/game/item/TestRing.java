package com.rasmus.game.item;

import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class TestRing extends ItemRing {

    public TestRing(double x, double y, Sprite sprite) {
        super(x, y, sprite);
        stackSize = 1;
    }

    public TestRing(Sprite sprite) {
        super(sprite);
        stackSize = 1;
    }

    @Override
    public void onInteract(double x, double y, Player player) {
        player.addExp(100);
        player.addItem(new TestRing(Sprite.ring), 1);
        remove();
    }

    public void putInInventory(Player player) {
        player.addMagicDamage(20);
    }

    public void removedFromInventory(Player player) {
        player.subMagicDamage(20);
    }

    public void render(Screen screen) {
        if(!isInInventory) screen.renderItem((int) x << 4, (int) y << 4, this);
    }
}
