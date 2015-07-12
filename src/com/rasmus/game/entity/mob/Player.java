package com.rasmus.game.entity.mob;

import com.rasmus.game.Game;
import com.rasmus.game.entity.projectlile.PlayerProjectile;
import com.rasmus.game.entity.projectlile.Projectile;
import com.rasmus.game.graphics.AnimatedSprite;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.graphics.SpriteSheet;
import com.rasmus.game.graphics.ui.*;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.inventory.PlayerInventory;
import com.rasmus.game.item.Item;
import com.rasmus.game.util.Vector2i;

import java.awt.*;


public class Player extends Mob {

    private Keyboard input;
    private Sprite sprite;
    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);


    private AnimatedSprite attackDown = new AnimatedSprite(SpriteSheet.player_attack_down, 32, 32, 3);
    private AnimatedSprite attackUp = new AnimatedSprite(SpriteSheet.player_attack_up, 32, 32, 3);
    private AnimatedSprite attackLeft = new AnimatedSprite(SpriteSheet.player_attack_left, 32, 32, 3);
    private AnimatedSprite attackRight = new AnimatedSprite(SpriteSheet.player_attack_right, 32, 32, 3);
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

    private UIMiniMap miniMap;
    private UISquare mimiMapSquare;

    //Stats
    private UILabel ATT;
    private UILabel ATTDAMAGE;

    private UILabel MAG;
    private UILabel MAGICDAMAGE;

    private UILabel RESITANCE;
    private UILabel DEF;

    private UILabel SPD;
    private UILabel MOVMENTSPEED;

    public UIPanel panel;

    private PlayerInventory inventory;

    private boolean canAddHealthAmount = true;
    private boolean canAddEnergyAmount = true;
    private boolean canAddExpAmount = true;
    private boolean canAddMiniMap = true;
    public boolean canMove = true;

    //Player stats
    private int energy;
    private int exp;
    private double maxExp;

    public Player(String name, Keyboard input) {
        super(0, 0);
        this.name = name;
        this.input = input;
        sprite = up;
        animSprite = down;
    }

    public Player(String name, int x, int y, Keyboard input) {
        super(x, y);
        this.name = name;
        this.input = input;
        sprite = down.getSprite();
        fireRate = PlayerProjectile.FIRE_RATE;
        dir = Direction.DOWN;

        //Default player stats
        health = 100;
        maxHealth = 100;
        maxExp = 100;
        energy = 100;
        exp = 0;
        attackDamage = 20;
        magicDamage = 0;
        damageResistance = 10;
        momentSpeed = 15;
        attackSpeed = 40;

        //UI Stuff
        ui = Game.getUiManager();
        panel = (UIPanel) new UIPanel(new Vector2i((400 - 110) * 3, 0), new Vector2i(110 * 3, 225 * 3)).setColor(new Color(0x4F4F4F));
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

        mimiMapSquare = new UISquare(new Vector2i(34, 6), new Vector2i(268, 200));
        mimiMapSquare.setColor(new Color(0x262626));
        panel.addComponent(mimiMapSquare);

        ATT = new UILabel(new Vector2i(50, 380), "ATT - ");
        ATT.dropShadow = true;
        ATT.setColor(0xCCCCCC);
        ATT.setFont(new Font("Verdana", Font.BOLD, 14));
        panel.addComponent(ATT);

        ATTDAMAGE = new UILabel(new Vector2i(100, 380), attackDamage, true);
        ATTDAMAGE.dropShadow = true;
        ATTDAMAGE.setColor(0xF7F7F7);
        ATTDAMAGE.setFont(new Font("Verdana", Font.BOLD, 12));
        panel.addComponent(ATTDAMAGE);

        MAG = new UILabel(new Vector2i(50, 405), "MAG - ");
        MAG.dropShadow = true;
        MAG.setColor(0xCCCCCC);
        MAG.setFont(new Font("Verdana", Font.BOLD, 14));
        panel.addComponent(MAG);

        MAGICDAMAGE = new UILabel(new Vector2i(103, 405), magicDamage, true);
        MAGICDAMAGE.dropShadow = true;
        MAGICDAMAGE.setColor(0xF7F7F7);
        MAGICDAMAGE.setFont(new Font("Verdana", Font.BOLD, 12));
        panel.addComponent(MAGICDAMAGE);

        DEF = new UILabel(new Vector2i(195, 380), "DEF - ");
        DEF.dropShadow = true;
        DEF.setColor(0xCCCCCC);
        DEF.setFont(new Font("Verdana", Font.BOLD, 14));
        panel.addComponent(DEF);

        RESITANCE = new UILabel(new Vector2i(245, 380), damageResistance, true);
        RESITANCE.dropShadow = true;
        RESITANCE.setColor(0xF7F7F7);
        RESITANCE.setFont(new Font("Verdana", Font.BOLD, 12));
        panel.addComponent(RESITANCE);

        SPD = new UILabel(new Vector2i(195, 405), "SPD - ");
        SPD.dropShadow = true;
        SPD.setColor(0xCCCCCC);
        SPD.setFont(new Font("Verdana", Font.BOLD, 14));
        panel.addComponent(SPD);

        MOVMENTSPEED = new UILabel(new Vector2i(245, 405), (int) momentSpeed, true);
        MOVMENTSPEED.dropShadow = true;
        MOVMENTSPEED.setColor(0xF7F7F7);
        MOVMENTSPEED.setFont(new Font("Verdana", Font.BOLD, 12));
        panel.addComponent(MOVMENTSPEED);

        inventory = new PlayerInventory(this, panel, input);
    }

    int timer = 0;
    public void update() {
        timer++;
        if(timer > 10000) timer = 0;
        if(walking || attacking) animSprite.update();
        else animSprite.setFrame(0);
        if(fireRate > 0) fireRate--;
        if(attackSpeed > 0) attackSpeed--;

        double xa = 0, ya = 0;

        if(canMove) {
            if(input.left) {
                animSprite = left;
                xa -= momentSpeed / 10;
            } else if(input.right) {
                animSprite = right;
                xa += momentSpeed / 10;
            }

            if(input.up) {
                animSprite = up;
                ya -= momentSpeed / 10;
            } else if(input.down) {
                animSprite = down;
                ya += momentSpeed / 10;
            }

            if(xa != 0 || ya != 0) {
                move(xa, ya);
                walking = true;
            } else {
                walking = false;
            }
        }

        if(dir == Direction.RIGHT) animSprite = right;
        if(dir == Direction.DOWN) animSprite = down;
        if(dir == Direction.UP) animSprite = up;
        if(dir == Direction.LEFT) animSprite = left;

        if(dir == Direction.DOWN && attacking) animSprite = attackDown;
        if(dir == Direction.UP && attacking) animSprite = attackUp;
        if(dir == Direction.LEFT && attacking) animSprite = attackLeft;
        if(dir == Direction.RIGHT && attacking) animSprite = attackRight;

        if(Mouse.getButton() == 1 && attackSpeed <= 0) {
            double dx = Mouse.getX() - Game.getWindowWidth() / 2;
            double dy = Mouse.getY() - Game.getWindowHeight() / 2;
            double dir = Math.atan2(dy, dx);
            attackSpeed = 40;
            attack(dir);
        }

        if(attackSpeed <= 20) attacking = false;

        clear();
        updateShoot();
        updateStats();

        if(energy < 100 && timer % 18 == 0) {
            energy += random.nextInt(4) + 1;
        }

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
        if(canAddMiniMap) addMiniMap();
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
        if(exp >= maxExp) levelUp();
    }

    private void clear() {
        for(int i = 0; i < level.getProjectiles().size(); i++) {
            Projectile p = level.getProjectiles().get(i);
            if(p.isRemoved()) level.getProjectiles().remove(i);
        }
    }

    private void updateShoot() {
        if(Mouse.getButton() == 3 && Mouse.getX() < 875 && fireRate <= 0 && !inventory.holdingItem) {
            double dx = Mouse.getX() - Game.getWindowWidth() / 2;
            double dy = Mouse.getY() - Game.getWindowHeight() / 2;
            double dir = Math.atan2(dy, dx);
            shoot(x, y, dir);
            fireRate = PlayerProjectile.FIRE_RATE;
        }
    }

    private void updateStats() {
        if(magicDamage > 0) {
            MAGICDAMAGE.setColor(Color.GREEN);
            MAGICDAMAGE.setText("(+" + magicDamage + ")");
        } else {
            MAGICDAMAGE.setText(null);
            MAGICDAMAGE.setNumber(magicDamage);
            MAGICDAMAGE.setColor(0xF7F7F7);
        }

        if(attackDamage > 20) {
            ATTDAMAGE.setColor(Color.GREEN);
            ATTDAMAGE.setText("(+" + (attackDamage - 20) + ")");
        } else {
            ATTDAMAGE.setText(null);
            ATTDAMAGE.setNumber(magicDamage);
            ATTDAMAGE.setColor(0xF7F7F7);
        }

        if(damageResistance > 10) {
            RESITANCE.setColor(Color.GREEN);
            RESITANCE.setText("(+" + (damageResistance - 10) + ")");
        } else {
            RESITANCE.setText(null);
            RESITANCE.setNumber(magicDamage);
            RESITANCE.setColor(0xF7F7F7);
        }

        if(momentSpeed > 20) {
            MOVMENTSPEED.setColor(Color.GREEN);
            MOVMENTSPEED.setText("(+" + ((int) momentSpeed - 20) + ")");
        } else {
            MOVMENTSPEED.setText(null);
            MOVMENTSPEED.setNumber(magicDamage);
            MOVMENTSPEED.setColor(0xF7F7F7);
        }

        MAGICDAMAGE.setNumber(magicDamage);
        ATTDAMAGE.setNumber(attackDamage);
        RESITANCE.setNumber(damageResistance);
        MOVMENTSPEED.setNumber((int) momentSpeed);
    }

    private void addMiniMap() {
        miniMap = new UIMiniMap(new Vector2i(40, 10), level);
        panel.addComponent(miniMap);
        canAddMiniMap = false;
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
