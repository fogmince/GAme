package com.rasmus.game.graphics;

public class Sprite {

    public final int SIZE;
    private int x, y;
    private int width, height;
    public int[] pixels;
    protected SpriteSheet sheet;

    public static Sprite voidSprite = new Sprite(16, 0x1B87E0);

    //SpawnLevel Sprites
    public static Sprite wall_stone = new Sprite(16, 1, 0, SpriteSheet.spawnLevel);
    public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.spawnLevel);
    public static Sprite flower = new Sprite(16, 0, 1, SpriteSheet.spawnLevel);
    public static Sprite floor_wood = new Sprite(16, 1, 1, SpriteSheet.spawnLevel);

    //projectiles
    public static Sprite projectile_laser = new Sprite(16, 0, 0, SpriteSheet.projectile_laser);


    //Particles
    public static Sprite particle_Default = new Sprite(3, 0xAAAAAA);
    public static Sprite particle_Red = new Sprite(3, 0xFF0000);


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
