package com.rasmus.game.level.tile;

import com.rasmus.game.graphics.Sprite;

public class WallTile extends Tile {

    public WallTile(Sprite sprite) {
        super(sprite);
    }


    @Override
    public boolean solid() {
        return true;
    }

    public int getColor() {
        return 0xFF404040;
    }
}
