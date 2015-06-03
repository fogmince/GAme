package com.rasmus.game.entity.Spawner;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.level.Level;

public abstract class Spawner extends Entity {

    public enum Type{
        PARTICLE, MOB
    }

    private Type type;

    public Spawner(int x, int y, Type type, int amount, Level level) {
        init(level);
        this.x = x;
        this.y = y;
        this.type = type;
    }

}
