package com.rasmus.game.graphics;

public class Font {

    private static SpriteSheet font = new SpriteSheet("/fonts/arial.png", 16);
    private static Sprite[] caracters = Sprite.split(font);

    public Font() {

    }

}
