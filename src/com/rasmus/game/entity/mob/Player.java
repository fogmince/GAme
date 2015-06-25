package com.rasmus.game.entity.mob;

import com.rasmus.game.Game;
import com.rasmus.game.entity.item.Item;
import com.rasmus.game.entity.projectlile.LaserProjectile;
import com.rasmus.game.entity.projectlile.Projectile;
import com.rasmus.game.graphics.AnimatedSprite;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.graphics.SpriteSheet;
import com.rasmus.game.graphics.ui.*;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.inventory.PlayerInventory;
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
    private int playerLevel = 1;

    private int fireRate = 0;

    private UIManager ui;
    private UIProgressBar uiHealthBar;
    private UILabel uiNameLabel;
    private UIProgressBar uiEnergyBar;
    private UIProgressBar uiLevelBar;
    private UILabel uiHpLabel;
    private UILabel uiENLabel;
    private UILabel uiLevelLabel;
    private UISprite uiPlayerClassIcon;
    private UILabel amountOfHealth;
    private UILabel amountOfExp;
    private UILabel amountOfEnergy;

    private UIPanel panel;

    private PlayerInventory inventory;

    private boolean canAddHealthAmount = true;
    private boolean canAddEnergyAmount = true;
    private boolean canAddExpAmount = true;

    //Player stats
    private int energy;
    private int exp = 0;
    private double maxHealth;
    private double maxExp;

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

        //Default player stats
        health = 100;
        maxHealth = 100;
        maxExp = 100;
        energy = 100;

        //UI Stuff
        ui = Game.getUiManager();
        panel = (UIPanel) new UIPanel(new Vector2i((400 - 110) * 3, 0), new Vector2i(110  * 3, 225 * 3)).setColor(new Color(0x4F4F4F));
        ui.addPanel(panel);

        uiNameLabel = new UILabel(new Vector2i(55, 250), name).setColor(0xBBBBBB);
        uiNameLabel.setFont(new Font("Verdana", Font.PLAIN, 28));
        uiNameLabel.dropShadow = true;
        panel.addComponent(uiNameLabel);

        uiLevelBar = new UIProgressBar(new Vector2i(20, 265), new Vector2i(110 * 3 - 45, 20));
        uiLevelBar.setColor(0x6A6A6A);
        uiLevelBar.setForegroundColor(0x3AAF00);
        uiLevelBar.setProgress(0.8);
        panel.addComponent(uiLevelBar);

        uiLevelLabel = new UILabel(new Vector2i(uiLevelBar.position).add(new Vector2i(1, 16)), "Level 1");
        uiLevelLabel.setColor(0xFFFFFF);
        uiLevelLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        panel.addComponent(uiLevelLabel);

        uiHealthBar = new UIProgressBar(new Vector2i(20, 290), new Vector2i(110 * 3 - 45, 20));
        uiHealthBar.setColor(0x6A6A6A);
        uiHealthBar.setForegroundColor(0xCC1A15);
        panel.addComponent(uiHealthBar);

        uiHpLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(1, 16)), "HP");
        uiHpLabel.setColor(0xFFFFFF);
        uiHpLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        panel.addComponent(uiHpLabel);

        uiPlayerClassIcon = new UISprite(new Vector2i(10, 215), "/ui/icons/classIcon.png");
        panel.addComponent(uiPlayerClassIcon);

        uiEnergyBar = new UIProgressBar(new Vector2i(20, 315), new Vector2i(110 * 3 - 45, 20));
        uiEnergyBar.setColor(0x6A6A6A);
        uiEnergyBar.setForegroundColor(0xCCD611);
        panel.addComponent(uiEnergyBar);

        uiENLabel = new UILabel(new Vector2i(uiEnergyBar.position).add(new Vector2i(1, 16)), "EN");
        uiENLabel.setColor(0xFFFFFF);
        uiENLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        panel.addComponent(uiENLabel);

        amountOfHealth = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(142, 16)), health + "/100");
        amountOfHealth.setColor(0xFFFFFF);
        amountOfHealth.setFont(new Font("Verdana", Font.BOLD, 18));

        amountOfExp = new UILabel(new Vector2i(uiLevelBar.position).add(new Vector2i(142, 16)), exp + "/100");
        amountOfExp.setColor(0xFFFFFF);
        amountOfExp.setFont(new Font("Verdana", Font.BOLD, 18));

        amountOfEnergy = new UILabel(new Vector2i(uiEnergyBar.position).add(new Vector2i(142, 16)), energy + "/100");
        amountOfEnergy.setColor(0xFFFFFF);
        amountOfEnergy.setFont(new Font("Verdana", Font.BOLD, 18));

        inventory = new PlayerInventory(this, panel);
    }

    int timer = 0;
    public void update() {
        timer++;
        if(timer > 10000) timer = 0;
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

        if(energy < 100 && timer % 18 == 0) {
            energy += random.nextInt(4) + 1;
        }

        if(Mouse.getButton() == 3) energy -= 20;

        if(exp >= maxExp) {
            levelUp();
        }

        if(health > maxHealth) health = (int) maxHealth;
        if(energy > 100) energy = 100;
        if(energy < 0) energy = 0;
        if(health < 0) health = 0;

        amountOfHealth.setText(health + "/" + (int) maxHealth);
        amountOfExp.setText(exp + "/" + (int) maxExp);
        amountOfEnergy.setText(energy + "/100");

        if(Mouse.getX() > uiHealthBar.position.x + 870 && Mouse.getX() < uiHealthBar.position.x + uiHealthBar.size.x + 870 && Mouse.getY() > uiHealthBar.position.y && Mouse.getY() < uiHealthBar.position.y + uiHealthBar.size.y) {
            if(canAddHealthAmount) {
                panel.addComponent(amountOfHealth);
                canAddHealthAmount = false;
            }
        } else {
            panel.removeComponent(amountOfHealth);
            canAddHealthAmount = true;
        }

        if(Mouse.getX() > uiLevelBar.position.x + 870 && Mouse.getX() < uiLevelBar.position.x + uiLevelBar.size.x + 870 && Mouse.getY() > uiLevelBar.position.y && Mouse.getY() < uiLevelBar.position.y + uiLevelBar.size.y) {
            if(canAddExpAmount) {
                panel.addComponent(amountOfExp);
                canAddExpAmount = false;
            }
        } else {
            panel.removeComponent(amountOfExp);
            canAddExpAmount = true;
        }

        if(Mouse.getX() > uiEnergyBar.position.x + 870 && Mouse.getX() < uiEnergyBar.position.x + uiEnergyBar.size.x + 870 && Mouse.getY() > uiEnergyBar.position.y && Mouse.getY() < uiEnergyBar.position.y + uiEnergyBar.size.y) {
            if(canAddEnergyAmount) {
                panel.addComponent(amountOfEnergy);
                canAddEnergyAmount = false;
            }
        } else {
            panel.removeComponent(amountOfEnergy);
            canAddEnergyAmount = true;
        }

        uiHealthBar.setProgress(health / maxHealth);
        uiEnergyBar.setProgress(energy / 100.0);
        uiLevelBar.setProgress(exp / maxExp);

        uiLevelLabel.setText("Level " + String.valueOf(playerLevel));

        inventory.update();
    }

    public void addExp(int exp) {
        this.exp += exp;
    }

    public void addHealth(int health) {
        this.health += health;
    }

    private void levelUp() {
        maxHealth += random.nextInt(5) + 20;
        maxExp += 15;
        health = (int) maxHealth;
        exp -= maxExp;
        if(exp < 0) exp = 0;
        playerLevel++;
        System.out.println(exp);
        if(exp >= maxExp) levelUp();
    }

    private void clear() {
        for(int i = 0; i < level.getProjectiles().size(); i++) {
            Projectile p = level.getProjectiles().get(i);
            if(p.isRemoved()) level.getProjectiles().remove(i);
        }
    }

    private void updateShoot() {
        if(Mouse.getButton() == 1 && Mouse.getX() < 875 && fireRate <= 0 && !inventory.holdingItem) {
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

    public void addItem(int x, int y, Item item, int amount) {
        inventory.addItem(x, y, item, amount);
    }

    public void addItem(Item item, int amount) {
        inventory.addItem(item, amount);
    }
}
