package com.rasmus.game.level.tile;

import com.rasmus.game.graphics.AnimatedSprite;

public class AnimatedTile extends Tile {

    private AnimatedSprite anim_sprite;
    private boolean solid;
    private int MMColor;

    public AnimatedTile(AnimatedSprite sprite, boolean solid, int MMColor) {
        super(sprite);
        this.solid = solid;
        this.MMColor = MMColor;
        anim_sprite = sprite;
    }

    public void update() {
        anim_sprite.update();
        sprite = anim_sprite.getSprite();
    }

    public boolean solid() {
        return solid;
    }

    public int getColor() {
        return MMColor;
    }
}
