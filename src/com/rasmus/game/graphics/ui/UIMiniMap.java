package com.rasmus.game.graphics.ui;

import com.rasmus.game.level.Level;
import com.rasmus.game.util.Vector2i;

import java.awt.*;

public class UIMiniMap extends UIComponent {

    private Level level;
    private Color[][] colors;

    public UIMiniMap(Vector2i position, Level level) {
        super(position);
        this.level = level;
        colors = new Color[32][32];

        for(int y = 0; y < 32; y++) {
            for(int x = 0; x < 32; x++) {
                colors[x][y] = new Color(level.getTile((int) (level.getClientPlayer().x + x * 16 - 16 * 16) / 16, (int) (level.getClientPlayer().y + y * 16 - 16 * 16) / 16).getColor());
            }
        }

        colors[16][16] = new Color(0xFFFA00);
    }

    @Override
    public void update() {
        for(int y = 0; y < 32; y++) {
            for(int x = 0; x < 32; x++) {
                colors[x][y] = new Color(level.getTile((int) (level.getClientPlayer().x + x * 16 - 16 * 16) / 16, (int) (level.getClientPlayer().y + y * 16 - 16 * 16) / 16).getColor());
            }
        }

        colors[16][16] = new Color(0xFFFA00);
    }

    @Override
    public void render(Graphics g) {
        for(int y = 0; y < 32; y++) {
            for(int x = 0; x < 32; x++) {
                g.setColor(colors[x][y]);
                g.fillRect(position.x + offset.x + x * 8, position.y + offset.y + y * 6, 8, 6);
            }
        }
    }
}
