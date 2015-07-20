package com.rasmus.game.level.tile;

import com.rasmus.game.graphics.Sprite;

public class VoidTile extends Tile {
    public VoidTile(Sprite sprite) {
        super(sprite);
    }

    public int getColor() {
        return 0x1B87E0;
    }
}
