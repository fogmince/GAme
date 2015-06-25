package com.rasmus.game.entity.item;

import com.rasmus.game.graphics.Sprite;

public class ItemSword extends Item {

    public ItemSword(double x, double y, Sprite sprite) {
        super(x, y, sprite);
    }

    public ItemSword(Sprite sprite) {
        super(sprite);
    }
}
