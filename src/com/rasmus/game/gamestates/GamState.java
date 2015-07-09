package com.rasmus.game.gamestates;

import com.rasmus.game.graphics.Screen;
import com.rasmus.game.input.Keyboard;

import java.awt.*;

public abstract class GamState {

    protected Keyboard key;
    protected GameStateManager gsm;

    public GamState(GameStateManager gsm, Keyboard key) {
        this.key = key;
        this.gsm = gsm;
    }

    public abstract void update();
    public abstract void render(Screen screen);
    public void render(Graphics g) {}

    public abstract void init();
}
