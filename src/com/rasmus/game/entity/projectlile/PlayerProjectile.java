package com.rasmus.game.entity.projectlile;

import com.rasmus.game.entity.Spawner.ParticleSpawner;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;

public class PlayerProjectile extends Projectile {

    public static final int FIRE_RATE = 90;

    public PlayerProjectile(double x, double y, double dir) {
        super(x, y, dir);
        range = 150;
        speed = 2;
        sprite = Sprite.rotate(Sprite.projectile_laser, angle);
        size = 7;

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
    }

    public void update() {

        for(int i = 0; i < level.mobs.size(); i++) {
            if(x < level.mobs.get(i).getX() + 17 && x > level.mobs.get(i).getX() - 17 && y <  level.mobs.get(i).getY() + 17 && y >  level.mobs.get(i).getY() - 17) {
                level.mobs.get(i).dealDamage(45 + (level.getClientPlayer().playerLevel * 5) + level.getClientPlayer().magicDamage);
                remove();
            }
        }

        if(level.tileCollision((int) (x + nx),(int) (y + ny), size, 3, 3)) {
            level.add(new ParticleSpawner((int) x, (int) y, 44, 20, 0.4, level, Sprite.particle_Red));
            remove();
        }
        move();
    }


    public void render(Screen screen) {
        screen.renderProjectile((int) x - 9,(int) y + 1, this);
    }
}
