package com.rasmus.game.entity.item;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class Item extends Entity {

    protected boolean isInInventory = false;
    public int stackSize = 64;

    private boolean playerMoved = true;

    private double tempPX = 0, tempPY = 0;

    public Item(double x, double y, Sprite sprite, boolean isInInventory) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.isInInventory = isInInventory;
    }

    public Item(Sprite sprite) {
        this.sprite = sprite;
        this.isInInventory = true;
    }

    public void update() {
        Player player = level.getClientPlayer();
        double px = player.getX() / 16;
        double py = player.getY() / 16;
        double intX = Math.abs(x - px);
        double intY = Math.abs(y - py);

        if(tempPX == 0 && tempPY == 0) {
            tempPX = px;
            tempPY = py;
        }

        if(intX == 0 && intY == 0) {
            interaction(x, y, player);
        }

        if(intX == 0 && intY == 0 && playerMoved) {
            onInteract(x, y, player);
            playerMoved = false;
        }

        if(tempPX != px || tempPY != py) {
            playerMoved = true;
            tempPX = px;
            tempPY = py;
        } else {
            playerMoved = false;
        }
    }

    public void render(Screen screen) {}


    //Calls when a player is standing in the item
    public void interacting(double x, double y, Player player) {
    }

    //Calls when a player steps on the item
    public void onInteract(double x, double y, Player player) {
    }

    private void interaction(double x, double y, Player player) {

    }
}
