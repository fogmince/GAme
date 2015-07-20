package com.rasmus.game.level.tile;

import com.rasmus.game.graphics.Sprite;

public class FloorTile extends Tile {
    public FloorTile(Sprite sprite) {
        super(sprite);
    }

    public int getColor() {
        return 0xFFB77D37;
    }
}
