package com.rasmus.game.level.tile;

import com.rasmus.game.graphics.AnimatedSprite;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.graphics.SpriteSheet;

public class Tile {

    public Sprite sprite;

    public static Tile voidTile = new VoidTile(Sprite.voidSprite);

    //MMCOLORS
    public static final int col_grass = 0xFF00FF00;
    public static final int col_flower = 0xFFFFFF00;
    public static final int col_wallStone = 0xFF404040;
    public static final int col_floorWood = 0xFFB77D37;


    //Animated Tile Sprites
    private static final AnimatedSprite flower_anim = new AnimatedSprite(SpriteSheet.flower_anim, 3, 1, 3, 16);

    //Animated Tiles
    public static AnimatedTile flower_anim_tile = new AnimatedTile(flower_anim, false, col_grass);


    //SpawnLevel Tiles:
    public static Tile grass = new GrassTile(Sprite.grass);
    public static Tile wallStone = new WallTile(Sprite.wall_stone);
    public static Tile floorWood = new FloorTile(Sprite.floor_wood);



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
