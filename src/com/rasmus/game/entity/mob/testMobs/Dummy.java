package com.rasmus.game.entity.mob.testMobs;

import com.rasmus.game.entity.mob.Mob;
import com.rasmus.game.graphics.AnimatedSprite;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.SpriteSheet;

public class Dummy extends Mob {

    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
    private AnimatedSprite side = new AnimatedSprite(SpriteSheet.dummy_side, 32, 32, 3);

    private AnimatedSprite animSprite = down;

    private int time = 0;
    private double xa = 1, ya = 0;

    public Dummy(int x, int y) {
        super(x, y);
        sprite = down.getSprite();
        health = 100;
    }

    public void update() {
        super.update();
        time++;
        if(time % (random.nextInt(50) + 30) == 0) {
            xa = random.nextInt(3) - 1;
            ya = random.nextInt(3) - 1;
            if(random.nextInt(4) == 0) {
                xa = 0;
                ya = 0;
            }
        }


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

        if(xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }
    }

    public void render(Screen screen) {
        super.render(screen);
        sprite = animSprite.getSprite();
        screen.renderMob((int) (x - 16), (int) (y - 16), this, dir == Direction.LEFT, false);
    }
}
