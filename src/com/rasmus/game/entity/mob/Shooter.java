package com.rasmus.game.entity.mob;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.graphics.AnimatedSprite;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.graphics.SpriteSheet;
import com.rasmus.game.util.Debug;
import com.rasmus.game.util.Vector2i;

import java.util.List;

public class Shooter extends Mob {

    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
    private AnimatedSprite side = new AnimatedSprite(SpriteSheet.dummy_side, 32, 32, 3);

    private AnimatedSprite animSprite = down;

    private int time = 0;
    private double xa = 1, ya = 0;

    private int fireRate = 0;
    private Entity rand = null;

    public Shooter(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
        sprite = Sprite.dummy;
        fireRate = 60;
    }

    public void update() {
        if(fireRate > 0) fireRate--;

        time++;
        if(time % (random.nextInt(50) + 30) == 0) {
            xa = random.nextInt(3) - 1;
            ya = random.nextInt(3) - 1;
            if(random.nextInt(5) == 0) {
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
        shootRandom();
    }

    private void shootRandom() {
        if(time % (40 + random.nextInt(61)) == 0) {
            List<Entity> entities = level.getEntities(this, 500);
            entities.add(level.getClientPlayer());

            int index = random.nextInt(entities.size());
            rand = entities.get(index);
        }

        if(rand != null && fireRate <= 0) {
            double dx = rand.getX() - x;
            double dy = rand.getY() - y;
            double direction = Math.atan2(dy, dx);
            shoot(x, y, direction);
            fireRate = 60;
        }
    }


    private void shootClosest() {
        List<Entity> entities = level.getEntities(this, 500);
        entities.add(level.getClientPlayer());

        double min = 0;
        Entity closest = null;

        for(int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            double distance = Vector2i.getDistance(new Vector2i(getX(), getY()), new Vector2i(entity.getX(), entity.getY()));
            if(i == 0 || distance < min) {
                min = distance;
                closest = entity;
            }
        }

        if(closest != null && fireRate <= 0) {
            double dx = closest.getX() - x;
            double dy = closest.getY() - y;
            double direction = Math.atan2(dy, dx);
            shoot(x, y, direction);
            fireRate = 60;
        }
    }

    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        Debug.drawRect(screen, 20 * 16, 40 * 16, 40, 40, true);
        screen.renderMob((int) x - 16, (int) y - 16, this, dir == Direction.LEFT, false);
    }
}
