package com.rasmus.game.graphics;

import com.rasmus.game.entity.mob.Mob;
import com.rasmus.game.entity.mob.testMobs.Chaser;
import com.rasmus.game.entity.mob.testMobs.Shooter;
import com.rasmus.game.entity.mob.testMobs.Star;
import com.rasmus.game.entity.projectlile.Projectile;
import com.rasmus.game.item.Item;
import com.rasmus.game.level.tile.Tile;

import java.util.Random;

public class Screen {

    public int width, height;
    public int[] pixels;

    public int xOffset, yOffset;
    private final int ALPHA_COL = 0xFFFF00FF;

    private Random random = new Random();

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
        if(fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }

        for(int y = 0; y < sprite.getHeight(); y++) {
            int ya = y + yp;
            for(int x = 0; x < sprite.getWidth(); x++) {
                int xa = x + xp;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
                int col = sprite.pixels[x + y * sprite.getWidth()];
                if(col != ALPHA_COL && col != 0xFF7F007F) pixels[xa + ya * width] = col;
            }
        }
    }

    public void renderTextCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed) {
        if(fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }
        for(int y = 0; y < sprite.getHeight(); y++) {
            int ya = y + yp;
            for(int x = 0; x < sprite.getWidth(); x++) {
                int xa = x + xp;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
                int col = sprite.pixels[x + y * sprite.getWidth()];
                if(col != ALPHA_COL && col != 0xFF7F007F) pixels[xa + ya * width] = color;
            }
        }
    }

    public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
        if(fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }
        for(int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
            int ya = y + yp;
            for(int x = 0; x < sheet.SPRITE_WIDTH; x++) {
                int xa = x + xp;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
                pixels[xa + ya * width] = sheet.pixels[x + y * sheet.SPRITE_WIDTH];
            }
        }
    }

    public void renderTile(int xp, int yp, Tile tile) {
        xp -= xOffset;
        yp -= yOffset;
        for(int y = 0; y < tile.sprite.SIZE; y++) {
            int ya = y + yp;
            for(int x = 0; x < tile.sprite.SIZE; x++) {
                int xa = x + xp;
                if(xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
                if(xa < 0) xa = 0;
                pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
            }
        }
    }

    public void renderProjectile(int xp, int yp, Projectile p){
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < p.getSpriteSize(); y++){
            int ya = y + yp;
            for (int x = 0; x < p.getSpriteSize(); x++){
                int xa = x + xp;
                if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height ) break;
                if (xa < 0) xa = 0;
                int col = p.getSprite().pixels[x + y * p.getSpriteSize()];
                if(col != ALPHA_COL) pixels[xa + ya * width] = col;
            }
        }
    }

    public void renderItem(int xp, int yp, Item item) {
        xp -= xOffset;
        yp -= yOffset;
        for(int y = 0; y < item.getSprite().SIZE; y++) {
            int ya = y + yp;
            for(int x = 0; x < item.getSprite().SIZE; x++) {
                int xa = x + xp;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height) break;
                int col = item.getSprite().pixels[x + y * item.getSprite().SIZE];
                if(col != ALPHA_COL) pixels[xa + ya * width] = col;
            }
        }
    }

    public void renderMob(int xp, int yp, Mob mob, boolean xFlip, boolean yFlip) {
        xp -= xOffset;
        yp -= yOffset;
        for(int y = 0; y < 32; y++) {
            int ys = y;
            if(yFlip) ys = 31 - y;
            int ya = y + yp;
            for(int x = 0; x < 32; x++) {
                int xa = x + xp;
                int xs = x;
                if(xFlip) xs = 31 - x;
                if(xa < -32 || xa >= width || ya < 0 || ya >= height) break;
                if(xa < 0) xa = 0;
                int col = mob.getSprite().pixels[xs + ys * 32];
                if(mob instanceof Chaser && col == 0xFF472BBF) col = 0xFFBA0015;
                if(mob instanceof Star && col == 0xFF472BBF) col = 0xFFED9344;
                if(mob instanceof Shooter && col == 0xFF472BBF) col = 0xFF00FF62;
                if(col != ALPHA_COL) pixels[xa + ya * width] = col;
            }
        }
    }

    public void renderMob(int xp, int yp, Sprite sprite, boolean xFlip, boolean yFlip) {
        xp -= xOffset;
        yp -= yOffset;
        for(int y = 0; y < 32; y++) {
            int ys = y;
            if(yFlip) ys = 31 - y;
            int ya = y + yp;
            for(int x = 0; x < 32; x++) {
                int xa = x + xp;
                int xs = x;
                if(xFlip) xs = 31 - x;
                if(xa < -32 || xa >= width || ya < 0 || ya >= height) break;
                if(xa < 0) xa = 0;
                int col = sprite.pixels[xs + ys * 32];
                if(col != ALPHA_COL) pixels[xa + ya * width] = col;
            }
        }
    }

    public void drawRect(int xp, int yp, int width, int height, int color, boolean fixed) {
        if(fixed) {
            xp -= xOffset;
            yp -= yOffset;
        }

        for(int x = xp; x < xp + width; x++) {
            if(x < 0 || x >= this.width || yp >= this.height) continue;
            if(yp > 0) pixels[x + yp * this.width] = color;
            if(yp + height >= this.height) continue;
            if(yp + height > 0) pixels[x + (yp + height) * this.width] = color;
        }

        for(int y = yp; y <= yp + height; y++) {
            if(xp >= this.width || y < 0 || y >= this.height) continue;
            if(xp > 0) pixels[xp + y * this.width] = color;
            if(xp + width >= this.width) continue;
            if(xp + width > 0) pixels[(xp + width) + y * this.width] = color;
        }

    }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }



    public void clear() {
        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }

}