package com.rasmus.game.gamestates;

import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.Sprite;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.input.Mouse;

import java.awt.*;

public class OptionState extends GamState {

    private String back = "Back";
    private boolean selected = false;

    public OptionState(GameStateManager gsm, Keyboard key) {
        super(gsm, key);
    }

    @Override
    public void update() {
        if(Mouse.getButton() == 1) {
            if(Mouse.getX() >= 16 && Mouse.getX() <= 16 + 70 && Mouse.getY() > 30 - 30 && Mouse.getY() <= 30) {
                gsm.setState(gsm.getLastState());
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
        screen.renderSprite(200, 150, Sprite.dummy, false);
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
