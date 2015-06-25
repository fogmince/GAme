package com.rasmus.game.graphics.ui;

import com.rasmus.game.util.Vector2i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UISprite extends UIComponent {

    private BufferedImage image;
    private BufferedImage imageA;
    private String path;

    public UISprite(Vector2i position, String path) {
        super(position);
        this.path = path;
        loadImage();
    }

    public void render(Graphics g) {
        g.drawImage(imageA, position.x + offset.x, position.y + offset.y, 48, 48, null);
    }

    private void loadImage() {
        try {
            System.out.print("Trying to load: " + path + "...");
            image = ImageIO.read(this.getClass().getResourceAsStream(path));
            imageA = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            for(int i = 0; i < pixels.length; i++) {
                if(pixels[i] == 0xFFFF00FF) {
                    pixels[i] = 0x00FF00FF;
                }
            }

            imageA.setRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

            System.out.println(" Succeeded!");
        }catch(IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            System.err.println(" Failed");
        }
    }


}
