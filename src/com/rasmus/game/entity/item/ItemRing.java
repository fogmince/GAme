package com.rasmus.game.entity.item;

import com.rasmus.game.graphics.Sprite;

public class ItemRing extends Item {

    public ItemRing(double x, double y, Sprite sprite, boolean isInInventory) {
        super(x, y, sprite, isInInventory);
    }

    public ItemRing(Sprite sprite) {
        super(sprite);
    }
}
