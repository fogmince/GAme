package com.rasmus.game.entity.item;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class Item extends Entity {

    protected boolean isInInventory = false;
    public int stackSize = 64;

    public Item(double x, double y, Sprite sprite, boolean isInInventory) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.isInInventory = isInInventory;
    }

    public boolean interaction(double x, double y, Player player) {
        boolean interact = false;

        double px = player.getX() / 16;
        double py = player.getY() / 16;

        if(Math.abs(x - px) == 0 && Math.abs(y - py) == 0) {
            interact = true;
        }

        return interact;
    }

    public void update() {}
    public void render(Screen screen) {}
}
