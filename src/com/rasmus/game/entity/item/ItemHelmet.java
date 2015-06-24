package com.rasmus.game.entity.item;

import com.rasmus.game.graphics.Sprite;

public class ItemHelmet extends Item {
    public ItemHelmet(double x, double y, Sprite sprite, boolean isInInventory) {
        super(x, y, sprite, isInInventory);
    }

    public ItemHelmet(Sprite sprite) {
        super(sprite);
    }
}
