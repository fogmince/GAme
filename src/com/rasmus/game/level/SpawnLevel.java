package com.rasmus.game.level;

import com.rasmus.game.entity.mob.testMobs.Dummy;
import com.rasmus.game.entity.mob.testMobs.Shooter;
import com.rasmus.game.entity.mob.testMobs.Star;

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

        for(int i = 0; i < 3; i++) {
            add(new Dummy(20 + i * 3, 70 - i * 3));
        }
        //add(new Chaser(21, 60));
        add(new Star(20, 50));
        add(new Shooter(20, 60));
}

    protected void generateLevel() {

    }
}
