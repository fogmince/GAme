package com.rasmus.game.level;

import com.rasmus.game.entity.mob.testMobs.Dummy;
import com.rasmus.game.entity.mob.testMobs.Shooter;
import com.rasmus.game.entity.mob.testMobs.Star;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.item.Test2Item;
import com.rasmus.game.item.TestItem;
import com.rasmus.game.item.TestRing;

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
            add(new Dummy(20 + i * 4, 70 - i * 3));
            addItem(new TestRing(27, 80 / (i + 1), Sprite.ring));
        }
        add(new Star(30, 50));
        add(new Shooter(30, 60));

        addItem(new TestItem(30, 60, Sprite.sword));
        addItem(new TestItem(30, 65, Sprite.sword));
        addItem(new Test2Item(27, 60, Sprite.potion));
}

    protected void generateLevel() {

    }
}
