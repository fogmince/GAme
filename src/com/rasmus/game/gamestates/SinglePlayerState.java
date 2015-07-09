package com.rasmus.game.gamestates;

import com.rasmus.game.Game;
import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.level.Level;
import com.rasmus.game.level.TileCoordinate;

import java.awt.*;

public class SinglePlayerState extends GamState {

    private Level level;
    private Player player;

    private boolean hasPLayer = false;

    private boolean paused = false;

    private int selected;
    private String[] options = {"Resume", "Options", "Menu"};
    private int mKey = 0;

    public SinglePlayerState(GameStateManager gsm, Level level, Keyboard key) {
        super(gsm, key);
        this.level = level;
        this.key = key;

        if(gsm.getState() == GameStateManager.SINGLEPLAYER_STATE) {
            TileCoordinate playerSpawn = new TileCoordinate(29, 70);
            player = new Player(Game.PLAYER_NAME, playerSpawn.x(), playerSpawn.y(), key);
            level.add(player);
        }
    }

    @Override
    public void update() {
        level.update();
        if(paused) player.canMove = false;

        if(key.esc) {
            if(!paused) paused = true;
            else paused = false;
        }

        if(paused) {
            if(mKey != -2) mKey = Mouse.getButton();


            if(mKey == 1) {
                if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 97 && Mouse.getY() > 90 * 3 - 30 && Mouse.getY() <= 90 * 3) {
                    paused = false;
                } else if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 95 && Mouse.getY() > 105 * 3 - 30 && Mouse.getY() <= 105 * 3) {
                    gsm.setState(GameStateManager.OPTIONS_STATE);
                } else if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 60 && Mouse.getY() > 120 * 3 - 30 && Mouse.getY() <= 120 * 3) {
                    gsm.setState(GameStateManager.MENU_STATE);
                } else {
                    selected = 0;
                }
            } else {
                if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 97 && Mouse.getY() > 90 * 3 - 30 && Mouse.getY() <= 90 * 3) {
                    selected = 1;
                } else if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 95 && Mouse.getY() > 105 * 3 - 30 && Mouse.getY() <= 105 * 3) {
                    selected = 2;
                } else if(Mouse.getX() >= Game.getWindowWidth() / 2 - 12 && Mouse.getX() <= Game.getWindowWidth() / 2 + 60 && Mouse.getY() > 120 * 3 - 30 && Mouse.getY() <= 120 * 3) {
                    selected = 3;
                } else {
                    selected = 0;
                }
            }
        }

        if(Mouse.getButton() == -1) mKey = 0;
    }

    @Override
    public void init() {
        paused = false;
    }

    @Override
    public void render(Screen screen) {
        if(gsm.getState() == GameStateManager.SINGLEPLAYER_STATE && !hasPLayer) {
            player = new Player("'Name'", 29, 70, key);
            level.add(player);
            hasPLayer = true;
        }

        double xScroll = player.getX() - screen.width / 2;
        double yScroll = player.getY() - screen.height / 2;
        level.render((int) xScroll, (int) yScroll, screen);
    }

    @Override
    public void render(Graphics g) {
        if(paused) {
            Color c = new Color(0xA17F7F7F, true);
            g.setColor(c);
            g.fillRect(0, 0, Game.getWindowWidth(), Game.getWindowHeight());

            g.setColor(Color.red);
            g.setFont(new Font("Verdana", Font.PLAIN, 28));

            for(int i = 0; i < options.length; i++) {
                if(i + 1 == selected) g.setColor(Color.WHITE);
                else g.setColor(Color.RED);

                g.drawString(options[i], Game.getWindowWidth() / 2 - 15, (90 + 15 * i )* 3);
            }
        }


    }
}
