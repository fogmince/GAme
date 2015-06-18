package com.rasmus.game.graphics.ui;

import com.rasmus.game.util.Vector2i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UISprite extends UIComponent {

    private BufferedImage image;
    private String path;

    public UISprite(Vector2i position, String path) {
        super(position);
        this.path = path;
        loadImage();
    }

    public void render(Graphics g) {
        g.drawImage(image, position.x + offset.x, position.y + offset.y, 48, 48, null);
    }

    private void loadImage() {
        try {
            System.out.print("Trying to load: " + path + "...");
            image = ImageIO.read(this.getClass().getResourceAsStream(path));
            System.out.println(" Succeeded!");
        }catch(IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            System.err.println(" Failed");
        }
    }


}
