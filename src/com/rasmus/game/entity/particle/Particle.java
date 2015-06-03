package com.rasmus.game.entity.particle;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;


public class Particle extends Entity {

    protected Sprite sprite;
    protected int life;
    private int time = 0;
    protected double xx, yy, zz;
    protected double xa, ya, za;

    public Particle(int x, int y, int life, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.xx = x;
        this.yy = y;
        this.life = life + (random.nextInt(20) - 10);
        this.sprite = sprite;

        this.xa = random.nextGaussian();
        this.ya = random.nextGaussian();
        this.zz = random.nextFloat() + 3.0;
    }

    public void update() {
        time++;
        if(time >= 7400) time = 0;
        if(time > life) remove();
        za -= 0.1;

        if(zz < 0) {
            zz = 0;
            za *= -0.7;
            xa *= 0.4;
            ya *= 0.4;
        }

        move(xx + xa, yy + ya + zz + za);
    }

    private void move(double x, double y) {
        if(collision(x, y)) {
            xa *= -0.5;
            ya *= -0.5;
            za *= -0.5;
        }
        xx += xa;
        yy += ya;
        zz += za;
    }

    public boolean collision(double x, double y) {
        boolean solid = false;
        for(int c = 0; c < 4; c++) {
            double xt = (x - c % 2 * 16) / 16;
            double yt = (y - c / 2 * 16) / 16;

            int ix = (int) Math.ceil(xt);
            int iy = (int) Math.ceil(yt);

            if(c % 2 == 0) ix = (int) Math.floor(xt);
            if(c / 2 == 0) iy = (int) Math.floor(yt);

            if(level.getTile(ix, iy).solid()) solid = true;
        }

        return solid;
    }

    public void render(Screen screen) {
        screen.renderSprite((int) xx - 1, (int) yy - (int) zz - 2, sprite, true);
    }
}
