package com.rasmus.game.entity.mob.testMobs;

import com.rasmus.game.entity.mob.Mob;
import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.AnimatedSprite;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.SpriteSheet;

import java.util.List;

public class Chaser extends Mob {

    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3, 7);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3, 7);
    private AnimatedSprite side = new AnimatedSprite(SpriteSheet.dummy_side, 32, 32, 3, 7);

    private AnimatedSprite animSprite = down;

    private double xa = 0, ya = 0;

    public Chaser(int x, int y) {
        super(x, y);
        sprite = down.getSprite();
        momentSpeed = 0.8;
        health = 100;
    }

    private void move() {
        xa = 0;
        ya = 0;
        List<Player> players = level.getPlayers(this, 100);
        if(players.size() > 0) {
            Player player = players.get(0);
            if(x < player.getX()) xa += momentSpeed / 10;
            if(x > player.getX()) xa -= momentSpeed / 10;
            if(y < player.getY()) ya += momentSpeed / 10;
            if(y > player.getY()) ya -= momentSpeed / 10;

            if (Math.floor(x) == Math.floor(player.getX())) xa = 0;
            if (Math.floor(y) == Math.floor(player.getY())) ya = 0;
        }

        if(xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }
    }

    public void update() {
        move();
        if(walking) animSprite.update();
        else animSprite.setFrame(0);

        if(xa < 0) {
            animSprite = side;
            dir = Direction.LEFT;
        } else if(xa > 0) {
            animSprite = side;
            dir = Direction.RIGHT;
        }

        if(ya < 0) {
            animSprite = up;
            dir = Direction.UP;
        } else if(ya > 0) {
            animSprite = down;
            dir = Direction.DOWN;
        }
    }


    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int) x - 16, (int) y - 16, this, dir == Direction.LEFT, false);
    }
}
