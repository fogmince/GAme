package com.rasmus.game.entity.item;

import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class Test2Item extends Item {

    public Test2Item(double x, double y, Sprite sprite) {
        super(x, y, sprite);
        isUsable = true;
    }

    public Test2Item(Sprite sprite) {
        super(sprite);
        isUsable = true;
    }

    @Override
    public void onInteract(double x, double y, Player player) {
        player.addItem(new Test2Item(Sprite.potion), 16);
        remove();
    }

    @Override
    public void onUse(Player player) {
        player.addExp(100);
    }

    @Override
    public void render(Screen screen) {
        if(!isInInventory) screen.renderItem((int) x << 4, (int) y << 4, this);
    }
}