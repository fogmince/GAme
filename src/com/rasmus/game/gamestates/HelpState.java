package com.rasmus.game.gamestates;

import com.rasmus.game.graphics.Screen;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.level.tile.Tile;

import java.awt.*;

public class HelpState extends GamState {

    private String back = "Back";
    private boolean selected = false;

    public HelpState(GameStateManager gsm, Keyboard key) {
        super(gsm, key);
    }

    @Override
    public void update() {
        if(Mouse.getButton() == 1) {
            if(Mouse.getX() >= 16 && Mouse.getX() <= 16 + 70 && Mouse.getY() > 30 - 30 && Mouse.getY() <= 30) {
                gsm.setState(GameStateManager.MENU_STATE);
                selected = true;
            } else {
                selected = false;
            }
        } else {
            if(Mouse.getX() >= 16 && Mouse.getX() <= 16 + 70 && Mouse.getY() > 30 - 30 && Mouse.getY() <= 30) {
                selected = true;
            } else {
                selected = false;
            }
        }
    }

    @Override
    public void render(Screen screen) {
        for(int y = 0; y < 20; y++) {
            for(int x = 0; x < 15; x++) {
                screen.renderTile(0 + 16 * x, 0 + 16 * y, Tile.flower);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setFont(new Font("Verdana", Font.PLAIN, 28));

        if(!selected) g.setColor(Color.red);
        else g.setColor(Color.WHITE);

        g.drawString(back, 16, 30);
    }

    @Override
    public void init() {

    }
}
