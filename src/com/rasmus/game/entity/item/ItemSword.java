package com.rasmus.game.entity.item;

import com.rasmus.game.graphics.Sprite;

public class ItemSword extends Item {

    public ItemSword(double x, double y, Sprite sprite, boolean isInInventory) {
        super(x, y, sprite, isInInventory);
    }

    public ItemSword(Sprite sprite) {
        super(sprite);
    }
}
