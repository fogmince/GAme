package com.rasmus.game.entity.item;

import com.rasmus.game.graphics.Sprite;

public class ItemBoots extends Item {
    public ItemBoots(double x, double y, Sprite sprite, boolean isInInventory) {
        super(x, y, sprite, isInInventory);
    }

    public ItemBoots(Sprite sprite) {
        super(sprite);
    }
}
