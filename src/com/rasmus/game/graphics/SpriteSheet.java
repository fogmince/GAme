package com.rasmus.game.graphics;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    private String path;
    public final int SIZE;
    public int[] pixels;
    public final int SPRITE_WIDTH, SPRITE_HEIGHT;
    private int width, height;

    public static SpriteSheet spawnLevel = new SpriteSheet("/textures/spawnlevel.png", 48);
    public static SpriteSheet projectile_laser = new SpriteSheet("/textures/projectiles/laserProjectile.png", 48);

    public static SpriteSheet mobUI = new SpriteSheet("/ui/mobUI.png", 48);

    // Dummy
    public static SpriteSheet dummy = new SpriteSheet("/textures/test.png", 128);
    public static SpriteSheet dummy_down = new SpriteSheet(dummy, 2, 0, 1, 3, 32);
    public static SpriteSheet dummy_up = new SpriteSheet(dummy, 0, 0, 1, 3, 32);
    public static SpriteSheet dummy_side = new SpriteSheet(dummy, 1, 0, 1, 3, 32);

    //Player
    public static SpriteSheet player = new SpriteSheet("/textures/playersheet.png", 128, 96);
    public static SpriteSheet player_down = new SpriteSheet(player, 0, 0, 1, 3, 32);
    public static SpriteSheet player_up = new SpriteSheet(player, 1, 0, 1, 3, 32);
    public static SpriteSheet player_left = new SpriteSheet(player, 2, 0, 1, 3, 32);
    public static SpriteSheet player_right = new SpriteSheet(player, 3, 0, 1, 3, 32);

    private Sprite[] sprites;

    public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
        int xx = x * spriteSize;
        int yy = y * spriteSize;
        int w = width * spriteSize;
        int h = height * spriteSize;
        SPRITE_WIDTH = w;
        SPRITE_HEIGHT = h;
        if(width == height) SIZE = width;
        else SIZE = -1;
        pixels = new int[w * h];

        for(int y0 = 0; y0 < h; y0++) {
            int yp = yy + y0;
            for(int x0 = 0; x0 < w; x0++) {
                int xp = xx + x0;
                pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
            }
        }

        int frame = 0;
        sprites = new Sprite[width * height];
        for(int ya = 0; ya < height; ya++) {
            for(int xa = 0; xa < width; xa++) {
                int[] spritePixels = new int[spriteSize * spriteSize];
                for(int y0 = 0; y0 < spriteSize; y0++) {
                     for(int x0 = 0; x0 < spriteSize; x0++) {
                         spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
                sprites[frame++] = sprite;
            }
        }

    }

    public SpriteSheet(String path, int size) {
        this.path = path;
        SIZE = size;
        SPRITE_WIDTH = size;
        SPRITE_HEIGHT = size;
        pixels = new int[SIZE * SIZE];
        load();
    }

    public SpriteSheet(String path, int width, int height) {
        this.path = path;
        SIZE = -1;
        SPRITE_WIDTH = width;
        SPRITE_HEIGHT = height;
        pixels = new int[width * height];
        load();
    }


    private void load() {
        try {
            System.out.print("Trying to load: " + path + "...");
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
            System.out.println(" Succeeded!");
            width = image.getWidth();
            height = image.getHeight();

            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);

        } catch(IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            System.err.println(" Failed");
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[] getPixels() {
        return pixels;
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    public String getPath() {
        return path;
    }

}
