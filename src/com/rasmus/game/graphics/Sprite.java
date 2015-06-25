package com.rasmus.game.graphics;

public class Sprite {

    public final int SIZE;
    private int x, y;
    private int width, height;
    public int[] pixels;
    protected SpriteSheet sheet;
    public String path;

    public static Sprite voidSprite = new Sprite(16, 0x1B87E0);

    //SpawnLevel Sprites
    public static Sprite wall_stone = new Sprite(16, 1, 0, SpriteSheet.spawnLevel);
    public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.spawnLevel);
    public static Sprite flower = new Sprite(16, 0, 1, SpriteSheet.spawnLevel);
    public static Sprite floor_wood = new Sprite(16, 1, 1, SpriteSheet.spawnLevel);

    //projectiles
    public static Sprite projectile_laser = new Sprite(16, 0, 0, SpriteSheet.projectile_laser);
    public static Sprite projectile_arrow = new Sprite(16, 1, 0, SpriteSheet.projectile_laser);


    //Particles
    public static Sprite particle_Default = new Sprite(3, 0xAAAAAA);
    public static Sprite particle_Red = new Sprite(3, 0xFF0000);

    //Items
    public static Sprite sword = new Sprite(16, 0, 0, SpriteSheet.sword);
    public static Sprite potion = new Sprite(16, 0, 0, SpriteSheet.potion);
    public static Sprite ring = new Sprite(16, 0, 0, SpriteSheet.ring);

    //Item Icons
    public static Sprite sword_icon = new Sprite(16, 0, 0, SpriteSheet.sword_icon);
    public static Sprite potion_icon = new Sprite(16, 0, 0, SpriteSheet.potion_icon);
    public static Sprite ring_icon = new Sprite(16, 0, 0, SpriteSheet.ring_icon);

    //Entities
    public static Sprite dummy = new Sprite(32, 0, 0, SpriteSheet.dummy);

    protected Sprite(SpriteSheet sheet, int width, int height) {
        SIZE = width == height ? width : -1;
        this.width = width;
        this.height = height;
        this.sheet = sheet;
    }

    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        SIZE = size;
        this.x = x * SIZE;
        this.y = y * SIZE;
        this.width = size;
        this.height = size;
        this.sheet = sheet;
        pixels = new int[SIZE * SIZE];
        path = sheet.getPath();
        load();
    }

    public Sprite(int width, int height, int color) {
        SIZE = -1;
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        setColor(color);
    }

    public Sprite(int size, int color) {
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        this.width = size;
        this.height = size;
        setColor(color);
    }

    public Sprite(int[] pixels, int width, int height) {
        SIZE = width == height ? width : -1;
        this.width = width;
        this.height = height;
        this.pixels = new int[pixels.length];
        for(int i = 0; i < pixels.length; i++) {
            this.pixels[i] = pixels[i];
        }
    }

    public static Sprite rotate(Sprite sprite, double angle) {
        return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
    }

    private static int[] rotate(int[] pixels, int width, int height, double angle) {
        int[] result = new int[width * height];

        double nx_x = rot_x(-angle, 1.0, 0.0);
        double nx_y = rot_y(-angle, 1.0, 0.0);
        double ny_x = rot_x(-angle, 0.0, 1.0);
        double ny_y = rot_y(-angle, 0.0, 1.0);

        double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
        double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

        for(int y = 0; y < height; y++) {
            double x1 = x0;
            double y1 = y0;
            for(int x = 0; x < width; x++) {
                int xx = (int) x1;
                int yy = (int) y1;
                int col = 0;
                if(xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xFFFF00FF;
                else col = pixels[xx + yy * width];
                result[x + y * width] = col;
                x1 += nx_x;
                y1 += nx_y;
            }
            x0 += ny_x;
            y0 += ny_y;
        }

        return result;
    }

    private static double rot_x(double angle, double x, double y) {
        double cos = Math.cos(angle - Math.PI / 2);
        double sin = Math.sin(angle - Math.PI / 2);

        return x * cos + y * -sin;
    }

    private static double rot_y(double angle, double x, double y) {
        double cos = Math.cos(angle - Math.PI / 2);
        double sin = Math.sin(angle - Math.PI / 2);

        return x * sin + y * cos;
    }

    public static Sprite scale(int[] pixels, int w1, int h1, int w2, int h2) {

        int[] new_pixels = new int[w2 * h2];

        int xr = (int) ((w1 << 16) / w2) + 1;
        int yr = (int) ((h1 << 16) / h2) + 1;

        int x2, y2;

        for (int i = 0; i < h2; i++) {
            for (int j = 0; j < w2; j++) {
                x2 = ((j * xr) >> 16);
                y2 = ((i * yr) >> 16);
                new_pixels[(i * w2) + j] = pixels[(y2 * w1) + x2];
            }
        }
        // Create a new sprite from your new pixels, new width, and new height
        Sprite new_sprite = new Sprite(new_pixels, w2, h2);

        return new_sprite;
    }

    public static Sprite[] split(SpriteSheet sheet) {
        int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
        Sprite[] sprites = new Sprite[amount];
        int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
        int current = 0;

        for(int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
            for(int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {

                for(int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
                    for(int x = 0; x < sheet.SPRITE_WIDTH; x++) {
                        int xo = x + xp * sheet.SPRITE_WIDTH;
                        int yo = y + yp * sheet.SPRITE_HEIGHT;
                        pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
                    }
                }
                sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
            }
        }

        return sprites;
    }

    private void setColor(int color) {
        for(int i = 0; i < width * height; i++) {
            pixels[i] = color;
        }
    }

    private void load() {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
