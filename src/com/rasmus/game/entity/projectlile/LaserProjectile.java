package com.rasmus.game.entity.projectlile;

import com.rasmus.game.entity.Spawner.ParticleSpawner;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class LaserProjectile extends Projectile {

    public static final int FIRE_RATE = 15;

    public LaserProjectile(double x, double y, double dir) {
        super(x, y, dir);
        range = 150;
        damage = 2;
        speed = 3;
        sprite = Sprite.rotate(Sprite.projectile_laser, angle);
        size = 7;

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
    }

    public void update() {
        if(level.tileCollision((int) (x + nx),(int) (y + ny), size, 3, 3)) {
            level.add(new ParticleSpawner((int) x, (int) y, 44, 20, level, Sprite.particle_Red));
            remove();
        }
        move();
    }


    public void render(Screen screen) {
        screen.renderProjectile((int) x - 9,(int) y + 1, this);
    }
}
