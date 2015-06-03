package com.rasmus.game.level.tile;

import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class FloorTile extends Tile {
    public FloorTile(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }
}
