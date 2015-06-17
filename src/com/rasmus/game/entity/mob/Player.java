package com.rasmus.game.entity.mob;

import com.rasmus.game.Game;
import com.rasmus.game.entity.projectlile.LaserProjectile;
import com.rasmus.game.entity.projectlile.Projectile;
import com.rasmus.game.graphics.AnimatedSprite;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.graphics.SpriteSheet;
import com.rasmus.game.graphics.ui.UILabel;
import com.rasmus.game.graphics.ui.UIManager;
import com.rasmus.game.graphics.ui.UIPanel;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.util.Vector2i;

import java.awt.*;


public class Player extends Mob {

    private Keyboard input;
    private Sprite sprite;
    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
    private AnimatedSprite animSprite = down;

    private String name;

    private int fireRate = 0;

    private int anim = 0;

    private UIManager ui;

    public Player(String name, Keyboard input) {
        this.name = name;
        this.input = input;
        sprite = up;
        animSprite = down;
    }

    public Player(String name, int x, int y, Keyboard input) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.input = input;
        sprite = down.getSprite();
        speed = 2;
        fireRate = LaserProjectile.FIRE_RATE;

        ui = Game.getUiManager();
        UIPanel panel = (UIPanel) new UIPanel(new Vector2i((400 - 110) * 3, 0), new Vector2i(110  * 3, 225 * 3)).setColor(new Color(0x4F4F4F));
        ui.addPanel(panel);
        UILabel nameLabel = new UILabel(new Vector2i(40, 250), name).setColor(0xBBBBBB);
        nameLabel.setFont(new Font("Courier New", Font.BOLD, 25));
        nameLabel.dropShadow = true;
        panel.addComponent(nameLabel);
    }

    public void update() {
        if(walking) animSprite.update();
        else animSprite.setFrame(0);
        if(fireRate > 0) fireRate--;

        double xa = 0, ya = 0;
        double speed = 1.4;

        if(input.left) {
            animSprite = left;
            xa -= speed;
        } else if(input.right) {
            animSprite = right;
            xa += speed;
        }

        if(input.up) {
            animSprite = up;
            ya -= speed;
        } else if(input.down) {
            animSprite = down;
            ya += speed;
        }

        if(xa != 0 || ya != 0) {
            move(xa, ya);
            walking = true;
        } else {
            walking = false;
        }

        clear();
        updateShoot();
    }

    private void clear() {
        for(int i = 0; i < level.getProjectiles().size(); i++) {
            Projectile p = level.getProjectiles().get(i);
            if(p.isRemoved()) level.getProjectiles().remove(i);
        }
    }

    private void updateShoot() {
        if(Mouse.getButton() == 1 && fireRate <= 0) {
            double dx = Mouse.getX() - Game.getWindowWidth() / 2;
            double dy = Mouse.getY() - Game.getWindowHeight() / 2;
            double dir = Math.atan2(dy, dx);
            shoot(x, y, dir);
            fireRate = LaserProjectile.FIRE_RATE;
        }
    }

    public void render(Screen screen) {
        sprite = animSprite.getSprite();

        screen.renderMob((int) (x - 16), (int) (y - 16), sprite, false, false);
    }

    public String getName() {
        return name;
    }

}
