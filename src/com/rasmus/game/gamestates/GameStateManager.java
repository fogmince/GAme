package com.rasmus.game.gamestates;

import com.rasmus.game.graphics.Screen;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.level.Level;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateManager {

    private List<GamState> gamStates = new ArrayList<GamState>();

    public static int MENU_STATE = 0;
    public static int SINGLEPLAYER_STATE = 1;
    public static int OPTIONS_STATE = 2;
    public static int HELP_STATE = 3;

    private int currentState = MENU_STATE;
    private int lastState = MENU_STATE;

    public GameStateManager(Level level, Keyboard key) {
        addState(new MenuState(this, key));
        addState(new SinglePlayerState(this, level, key));
        addState(new OptionState(this, key));
        addState(new HelpState(this, key));
    }

    public void update() {
        gamStates.get(currentState).update();
    }

    public void render(Screen screen) {
        gamStates.get(currentState).render(screen);
    }

    public void render(Graphics g) {
        gamStates.get(currentState).render(g);
    }

    public void addState(GamState gamState) {
        gamStates.add(gamState);
    }

    public void setState(int state) {
        lastState = currentState;
        currentState = state;
        gamStates.get(currentState).init();
    }

    public int getState() {
        return currentState;
    }

    public int getLastState() {
        return lastState;
    }
}
