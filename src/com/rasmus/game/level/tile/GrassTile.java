package com.rasmus.game.level.tile;

import com.rasmus.game.graphics.Sprite;

public class GrassTile extends Tile {

    public GrassTile(Sprite sprite) {
        super(sprite);
    }
    

    public int getColor() {
        return 0xFF00FF00;
    }
}
