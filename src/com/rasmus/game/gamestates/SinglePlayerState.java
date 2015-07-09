package com.rasmus.game.gamestates;

import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.level.Level;
import com.rasmus.game.level.TileCoordinate;

public class SinglePlayerState extends GamState {

    private Level level;
    private Player player;

    private boolean hasPLayer = false;

    public SinglePlayerState(GameStateManager gsm, Level level, Keyboard key) {
        super(gsm, key);
        this.level = level;
        this.key = key;

        if(gsm.currentState == GameStateManager.SINGLEPLAYER_STATE) {
            TileCoordinate playerSpawn = new TileCoordinate(29, 70);
            player = new Player("Name", playerSpawn.x(), playerSpawn.y(), key);
            level.add(player);
        }
    }

    @Override
    public void update() {
        level.update();
    }

    @Override
    public void render(Screen screen) {
        if(gsm.currentState == GameStateManager.SINGLEPLAYER_STATE && !hasPLayer) {
            player = new Player("Name", 29, 70, key);
            level.add(player);
            hasPLayer = true;
        }

        double xScroll = player.getX() - screen.width / 2;
        double yScroll = player.getY() - screen.height / 2;
        level.render((int) xScroll, (int) yScroll, screen);
    }
}
