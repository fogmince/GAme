package com.rasmus.game;

import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.graphics.Font;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.graphics.ui.UIManager;
import com.rasmus.game.input.Keyboard;
import com.rasmus.game.input.Mouse;
import com.rasmus.game.level.Level;
import com.rasmus.game.level.TileCoordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {

    private static final int WIDTH = 400 - 110;
    private static final int HEIGHT = 225;    //225
    private static final int SCALE = 3;
    public static final String TITLE = "Rain";

    private Thread thread;
    private JFrame frame;
    private boolean running = false;

    private Screen screen;
    private Keyboard key;
    private Level level;
    private Player player;
    private Font font;
    private static UIManager uiManager;

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
        font = new Font();
        TileCoordinate playerSpawn = new TileCoordinate(22, 70);
        player = new Player("Cherno", playerSpawn.x(), playerSpawn.y(), key);
        level.add(player);

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
        level.update();
        uiManager.update();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();
        double xScroll = player.getX() - screen.width / 2;
        double yScroll = player.getY() - screen.height / 2;
        level.render((int) xScroll, (int) yScroll, screen);

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();


        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
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
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setTitle(TITLE);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }

}