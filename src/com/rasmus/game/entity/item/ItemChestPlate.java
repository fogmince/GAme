package com.rasmus.game.entity.item;

import com.rasmus.game.graphics.Sprite;

public class ItemChestPlate extends Item {
    public ItemChestPlate(double x, double y, Sprite sprite, boolean isInInventory) {
        super(x, y, sprite, isInInventory);
    }

    public ItemChestPlate(Sprite sprite) {
        super(sprite);
    }
}

