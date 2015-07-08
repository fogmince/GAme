package com.rasmus.game.gamestates;

import com.rasmus.game.Game;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.input.Mouse;

import java.awt.*;

public class MenuState extends GamState {

    private int selected;
    private String[] options = {"Start", "Options", "Help", "Exit"};
    private int mKey = 0;

    public MenuState(GameStateManager gsm, Keyboard key) {
        super(gsm, key);
    }

    @Override
    public void update() {
        if(mKey != -2) mKey = Mouse.getButton();


        if(mKey == 1) {
            if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 55 && Mouse.getY() > 135 * 3 - 30 && Mouse.getY() <= 135 * 3) {
                gsm.currentState = GameStateManager.SINGLEPLAYER_STATE;
                selected = 1;
            } else if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 95 && Mouse.getY() > 150 * 3 - 30 && Mouse.getY() <= 150 * 3) {
                gsm.currentState = GameStateManager.OPTIONS_STATE;
                selected = 2;
            } else if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 50 && Mouse.getY() > 165 * 3 - 30 && Mouse.getY() <= 165 * 3) {
                gsm.currentState = GameStateManager.HELP_STATE;
                selected = 3;
            } else if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 47 && Mouse.getY() > 180 * 3 - 30 && Mouse.getY() <= 180 * 3) {
                System.exit(1);
                selected = 4;
            } else {
                selected = 0;
            }
        } else {
            if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 55 && Mouse.getY() > 135 * 3 - 30 && Mouse.getY() <= 135 * 3) {
                selected = 1;
            } else if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 95 && Mouse.getY() > 150 * 3 - 30 && Mouse.getY() <= 150 * 3) {
                selected = 2;
            } else if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 50 && Mouse.getY() > 165 * 3 - 30 && Mouse.getY() <= 165 * 3) {
                selected = 3;
            } else if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 47 && Mouse.getY() > 180 * 3 - 30 && Mouse.getY() <= 180 * 3) {
                selected = 4;
            } else {
                selected = 0;
            }
        }

        if(Mouse.getButton() == -1) mKey = 0;
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(0, 0, Sprite.menu, false);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Verdana", Font.PLAIN, 28));

        for(int i = 0; i < options.length; i++) {
            if(i + 1 == selected) g.setColor(Color.WHITE);
            else g.setColor(Color.RED);

            g.drawString(options[i], Game.getWindowWidth() / 2 - 15, (135 + 15 * i) * 3);
        }

    }
}
