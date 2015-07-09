package com.rasmus.game.level.tile;

import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class GrassTile extends Tile {

    public GrassTile(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, this);
    }

    public int getColor() {
        return 0xFF00FF00;
    }
}
