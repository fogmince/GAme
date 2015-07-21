package com.rasmus.game;

import com.rasmus.game.gamestates.GameStateManager;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.ui.UIManager;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {

    private static int WIDTH = 400 - 110;
    private static int HEIGHT = 225;    //225
    private static final int SCALE = 3;
    public static final String TITLE = "Rain";

    public static String PLAYER_NAME;

    private Thread thread;
    private JFrame frame;
    private boolean running = false;
    private static Game game;

    private Screen screen;
    private Keyboard key;
    private Level level;
    private static UIManager uiManager;
    private GameStateManager gsm;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public Game() {
        Dimension size = new Dimension(WIDTH * SCALE + 110 * 3, HEIGHT * SCALE);
        setPreferredSize(size);

        screen = new Screen(WIDTH, HEIGHT);
        uiManager = new UIManager();
        frame = new JFrame();
        key = new Keyboard();
        level = Level.spawn;

        gsm = new GameStateManager(level, key);

        Mouse mouse = new Mouse();

        addKeyListener(key);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;

        requestFocus();

        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
               update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("Updates: " + updates + ", FPS: " + frames);
                frame.setTitle(TITLE + "  |  Updates: " + updates + ", FPS:" + frames);
                updates = 0;
                frames = 0;
            }
         }
    }

    public void update() {
        key.update();
        gsm.update();
        uiManager.update();

        if(key.enter) {
            gsm.setState(GameStateManager.SINGLEPLAYER_STATE);
            System.out.println("af");
        }

        if(gsm.getState() != GameStateManager.SINGLEPLAYER_STATE) {
            WIDTH = 400;
        }
        else WIDTH = 290;
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        screen.clear();

        gsm.render(screen);

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }


        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

        gsm.render(g);
        uiManager.render(g);

        g.dispose();
        bs.show();
    }

    public static UIManager getUiManager() {
        return uiManager;
    }

    public static int getWindowWidth() {
        return WIDTH * SCALE;
    }

    public static int getWindowHeight() {
        return HEIGHT * SCALE;
    }

    public static void main(String[] args) {
        game = new Game();
        game.frame.setResizable(false);
        game.frame.setTitle(TITLE);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }

    public static Game game() {
        return game;
    }

    public Level level() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

}