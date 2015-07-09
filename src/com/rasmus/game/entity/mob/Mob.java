package com.rasmus.game.entity.mob;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.entity.Spawner.ParticleSpawner;
import com.rasmus.game.entity.projectlile.PlayerProjectile;
import com.rasmus.game.entity.projectlile.Projectile;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.util.Vector2i;

import java.util.List;


public abstract class Mob extends Entity {

    protected boolean walking = false;

    public double momentSpeed;
    public int attackDamage;
    public int magicDamage;
    public int damageResistance;

    public int health;
    private int tempHealth;
    protected double maxHealth;
    private int healthTimer;

    protected boolean attacking = false;

    private boolean displayHealth = false;

    protected enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected Direction dir;

    public Mob(int x, int y) {
        this.x = x << 4;
        this.y = y << 4;
    }

    public void move(double xa, double ya) {
        if(xa != 0 && ya != 0) {
            move(xa, 0);
            move(0, ya);
            return;
        }

        if(xa > 0) dir = Direction.RIGHT;
        if(xa < 0) dir = Direction.LEFT;
        if(ya > 0) dir = Direction.DOWN;
        if(ya < 0) dir = Direction.UP;

        while(xa != 0) {
            if(Math.abs(xa) > 1) {
                if(!collision(abs(xa), ya)) {
                    this.x += abs(xa);
                }
                xa -= abs(xa);
            } else {
                if(!collision(abs(xa), ya)) {
                    this.x += xa;
                }
                xa = 0;
            }
        }

        while(ya != 0) {
            if(Math.abs(ya) > 1) {
                if(!collision(xa, abs(ya))) {
                    this.y += abs(ya);
                }
                ya -= abs(ya);
            } else {
                if(!collision(xa, abs(ya))) {
                    this.y += ya;
                }
                ya = 0;
            }
        }
    }

    private int abs(double value) {
        if(value < 0) return -1;
        if(value > 0) return 1;
        return 0;
    }

    public void update() {
        if(health > maxHealth) health = (int) maxHealth;

        //If you want to have the health bar to disappear after a while
        if(health != tempHealth) {
            healthTimer = 0;
            displayHealth = true;
            tempHealth = health;
        }

        if(healthTimer >= 175) {
            displayHealth = false;
            healthTimer = 0;
        }

        if(displayHealth && healthTimer < 175) {
            healthTimer++;
        }

        if(isDead()) {
            remove();
            level.add(new ParticleSpawner((int) x, (int) y, 44, 20, 0.4, level, Sprite.particle_Red));
        }
    }

    public void render(Screen screen) {
        if(displayHealth) {
            screen.renderSprite((int) x - 16, (int) y - 27, Sprite.mobHealthBar, true);

            double h = health / maxHealth * 16;
            for(int i = 0; i < h; i++) {
                screen.renderSprite((int) x - 16 + 2 * i, (int) y - 27, Sprite.mobHealth, true);
            }
        }
    }

    protected void init() {
        maxHealth = health;
        tempHealth = health;
    }

    protected boolean isDead() {
        return health <= 0 ? true : false;
    }

    public void dealDamage(int dmg) {
        health -= dmg;
    }

    protected void shoot(double x, double y, double dir) {
        Projectile p = new PlayerProjectile(x, y, dir);
        level.add(p);
    }

    protected void shootRandom(int time, int radius, int fireRate) {
        Entity rand = null;
        if(time % (10 + random.nextInt(fireRate)) == 0) {
            List<Entity> entities = level.getEntities(this, radius);
            entities.add(level.getClientPlayer());

            int index = random.nextInt(entities.size());
            rand = entities.get(index);
        }

        if(rand != null) {
            double dx = rand.getX() - x;
            double dy = rand.getY() - y;
            double direction = Math.atan2(dy, dx);
            shoot(x, y, direction);
        }
    }


    protected void shootClosest(int fireRate, int radius) {
        List<Entity> entities = level.getEntities(this, radius);
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
        }
    }

    protected void shootClosestPlayer(int radius) {
        List<Player> players = level.getPlayers(this, radius);

        double min = 0;
        Entity closest = null;

        for(int i = 0; i < players.size(); i++) {
            Entity entity = players.get(i);
            double distance = Vector2i.getDistance(new Vector2i(getX(), getY()), new Vector2i(entity.getX(), entity.getY()));
            if(i == 0 || distance < min) {
                min = distance;
                closest = entity;
            }
        }

        if(closest != null) {
            double dx = closest.getX() - x;
            double dy = closest.getY() - y;
            double direction = Math.atan2(dy, dx);
            shoot(x, y, direction);
        }

    }

    protected void shootRandomPlayer(int time, int radius, int fireRate) {
        Player player = null;
        if(time % (10 + random.nextInt(fireRate)) == 0) {
            List<Player> players = level.getPlayers(this, radius);
            players.add(level.getClientPlayer());

            int index = random.nextInt(players.size());
            player = players.get(index);
        }

        if(player != null) {
            double dx = player.getX() - x;
            double dy = player.getY() - y;
            double direction = Math.atan2(dy, dx);
            shoot(x, y, direction);
        }
    }

    private boolean collision(double xa, double ya) {
        boolean solid = false;
        for(int c = 0; c < 4; c++) {
            double xt = ((x + xa) - c % 2 * 27 + 5) / 16;
            double yt = ((y + ya) - c / 2 * 30 + 15) / 16;

            int ix = (int) Math.ceil(xt);
            int iy = (int) Math.ceil(yt);

            if(c % 2 == 0) ix = (int) Math.floor(xt);
            if(c / 2 == 0) iy = (int) Math.floor(yt);

            if(level.getTile(ix, iy).solid()) solid = true;
        }

        return solid;
    }

    public void addAttackDamage(int attackDamage) {
        this.attackDamage += attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void subAttackDamage(int attackDamage) {
        this.attackDamage -= attackDamage;
    }

    public void addMagicDamage(int magicDamage) {
        this.magicDamage += magicDamage;
    }

    public void setMagicDamage(int magicDamage) {
        this.magicDamage = magicDamage;
    }

    public void subMagicDamage(int magicDamage) {
        this.magicDamage -= magicDamage;
    }

    public void addMovmentSpeed(int momentSpeed) {
        this.momentSpeed += momentSpeed;
    }

    public void setMomentSpeed(int momentSpeed) {
        this.momentSpeed = momentSpeed;
    }

    public void subMovmentSpeed(int momentSpeed) {
        this.momentSpeed -= momentSpeed;
    }

    public void addDamageResistance(int damageResistance) {
        this.damageResistance += damageResistance;
    }

    public void setDamageResistance(int damageResistance) {
        this.damageResistance = damageResistance;
    }

    public void subRESISTANCE(int damageResistance) {
        this.damageResistance -= damageResistance;
    }
}
