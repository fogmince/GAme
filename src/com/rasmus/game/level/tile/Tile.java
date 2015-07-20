package com.rasmus.game.level.tile;

import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class Tile {

    public Sprite sprite;

    public static Tile voidTile = new VoidTile(Sprite.voidSprite);

    //SpawnLevel Tiles:
    public static Tile grass = new GrassTile(Sprite.grass);
    public static Tile flower = new GrassTile(Sprite.flower);
    public static Tile wallStone = new WallTile(Sprite.wall_stone);
    public static Tile floorWood = new FloorTile(Sprite.floor_wood);

    public static final int col_grass = 0xFF00FF00;
    public static final int col_flower = 0xFFFFFF00;
    public static final int col_wallStone = 0xFF404040;
    public static final int col_floorWood = 0xFFB77D37;

    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x << 4, y << 4, sprite);
    }

    public boolean solid() {
        return false;
    }

    public int getColor() {
        return 0xFF00FF;
    }

}
