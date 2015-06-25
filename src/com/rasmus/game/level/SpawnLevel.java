package com.rasmus.game.level;

import com.rasmus.game.entity.item.Test2Item;
import com.rasmus.game.entity.item.TestItem;
import com.rasmus.game.entity.item.TestRing;
import com.rasmus.game.entity.mob.testMobs.Shooter;
import com.rasmus.game.entity.mob.testMobs.Star;
import com.rasmus.game.graphics.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpawnLevel extends Level {

    public SpawnLevel(String path) {
        super(path);
    }

    protected void loadLevel(String path) {
        try {
            BufferedImage image = ImageIO.read(SpawnLevel.class.getResourceAsStream(path));

            width = image.getWidth();
            height = image.getHeight();
            tiles = new int[width * height];
            image.getRGB(0, 0, width, height, tiles, 0, width);
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Could not load level file");
        }

        for(int i = 0; i < 5; i++) {
            //add(new Dummy(20 + i * 4, 70 - i * 3));
            add(new TestRing(27, 80 / (i + 1), Sprite.ring));
        }
        add(new Star(30, 50));
        add(new Shooter(30, 60));

        add(new TestItem(30, 60, Sprite.sword));
        add(new TestItem(30, 65, Sprite.sword));
        add(new Test2Item(27, 60, Sprite.potion));
}

    protected void generateLevel() {

    }
}
