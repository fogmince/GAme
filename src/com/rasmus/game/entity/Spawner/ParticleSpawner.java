package com.rasmus.game.entity.Spawner;

import com.rasmus.game.entity.particle.Particle;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.level.Level;

public class ParticleSpawner extends Spawner {

    private int life;

    public ParticleSpawner(int x, int y, int life, int amount, Level level, Sprite sprite) {
        super(x, y, Type.PARTICLE, amount, level);
        this.life = life;

        for(int i = 0; i < amount; i++) {
            level.add(new Particle(x, y, life, sprite));
        }

        remove();
    }
}
