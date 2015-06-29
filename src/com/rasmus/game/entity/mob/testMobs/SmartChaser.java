package com.rasmus.game.entity.mob.testMobs;

import com.rasmus.game.entity.mob.Mob;
import com.rasmus.game.graphics.AnimatedSprite;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.SpriteSheet;
import com.rasmus.game.level.Node;
import com.rasmus.game.util.Vector2i;

import java.util.List;

public class SmartChaser extends Mob {

    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
    private AnimatedSprite side = new AnimatedSprite(SpriteSheet.dummy_side, 32, 32, 3);

    private AnimatedSprite animSprite = down;

    private double xa = 0, ya = 0;
    private int time = 0;

    private List<Node> path = null;

    public SmartChaser(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        sprite = down.getSprite();
        momentSpeed = 1;
    }

    public void update() {
        time++;
        move();
        if(walking) animSprite.update();
        else animSprite.setFrame(0);

        if(xa < 0) {
            animSprite = side;
            dir = Mob.Direction.LEFT;
        } else if(xa > 0) {
            animSprite = side;
            dir = Mob.Direction.RIGHT;
        }

        if(ya < 0) {
            animSprite = up;
            dir = Mob.Direction.UP;
        } else if(ya > 0) {
            animSprite = down;
            dir = Mob.Direction.DOWN;
        }
    }

    private void move() {
        xa = 0;
        ya = 0;

        int px = level.getClientPlayer().getX();
        int py = level.getClientPlayer().getY();

        Vector2i start = new Vector2i(getX() >> 4, getY() >> 4);
        Vector2i destination = new Vector2i(px >> 4, py >> 4);

        int dx = Math.abs(px - getX());
        int dy = Math.abs(py - getY());
        double distance = Math.sqrt((dx * dx) + (dy * dy));

        if(distance <= 100)
            if(time % 3 == 0) path = level.findPath(start, destination);

        if(path != null) {
            if(path.size() > 0) {
                Vector2i vec = path.get(path.size() - 1).tile;
                if(x < vec.getX() << 4) xa += momentSpeed / 10;
                if(x > vec.getX() << 4) xa -= momentSpeed / 10;
                if(y < vec.getY() << 4) ya += momentSpeed / 10;
                if(y > vec.getY() << 4) ya -= momentSpeed / 10;
            }
        }

        if(xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }
    }


    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int) (x - 16), (int) (y - 16), this, dir == Direction.LEFT, false);
    }
}
