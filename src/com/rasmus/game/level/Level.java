package com.rasmus.game.level;

import com.rasmus.game.entity.Entity;
import com.rasmus.game.entity.item.Item;
import com.rasmus.game.entity.mob.Mob;
import com.rasmus.game.entity.mob.Player;
import com.rasmus.game.entity.particle.Particle;
import com.rasmus.game.entity.projectlile.Projectile;
import com.rasmus.game.graphics.Screen;
import com.rasmus.game.level.tile.Tile;
import com.rasmus.game.util.Vector2i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Level {

    public static Level spawn = new SpawnLevel("/levels/spawnlevel.png");

    protected int width, height;
    protected int[] tilesInt;
    protected int[] tiles;

    private List<Entity> entities = new ArrayList<Entity>();
    private List<Projectile> projectiles = new ArrayList<Projectile>();
    private List<Particle> particles = new ArrayList<Particle>();
    private List<Mob> mobs = new ArrayList<Mob>();

    private List<Player> players = new ArrayList<Player>();

    private List<Item> items = new ArrayList<Item>();

    private Comparator<Node> nodeSorter = new Comparator<Node>() {
        public int compare(Node n0, Node n1) {
            if(n1.fCost < n0.fCost) return 1;
            if(n1.fCost > n0.fCost) return -1;

            return 0;
        }
    };

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        tilesInt = new int[width * height];
        generateLevel();
    }

    public Level(String path) {
        loadLevel(path);
        generateLevel();
    }

    protected void generateLevel() {

    }

    protected void loadLevel(String path) {

    }

    public void update() {
        for(int  i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }

        for(int  i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }

        for(int  i = 0; i < particles.size(); i++) {
            particles.get(i).update();
        }

        for(int i = 0; i < players.size(); i++) {
            players.get(i).update();
        }

        for(int i = 0; i < mobs.size(); i++) {
            mobs.get(i).update();
        }

        for(int i = 0; i < items.size(); i++) {
            items.get(i).update();
        }

        remove();
    }

    private void remove() {
        for(int  i = 0; i < entities.size(); i++) {
            if(entities.get(i).isRemoved()) entities.remove(i);
        }

        for(int  i = 0; i < projectiles.size(); i++) {
            if(projectiles.get(i).isRemoved()) projectiles.remove(i);
        }

        for(int  i = 0; i < particles.size(); i++) {
            if(particles.get(i).isRemoved()) particles.remove(i);
        }

        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).isRemoved()) players.remove(i);
        }

        for(int i = 0; i < mobs.size(); i++) {
            mobs.get(i).remove();
        }

        for(int i = 0; i < items.size(); i++) {
            items.get(i).remove();
        }
    }

    private void time() {

    }

    public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
        boolean solid = false;
        for(int c = 0; c < 4; c++) {
            int xt = (x + c % 2 * size - xOffset) >> 4;
            int yt = (y + c / 2 * size - yOffset) >> 4;

            if(getTile(xt, yt).solid()) solid = true;
        }

        return solid;
    }

    public void render(int xScroll, int yScroll, Screen screen) {
        screen.setOffset(xScroll, yScroll);

        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.width + 16) >> 4;
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.height + 16) >> 4;

        for(int y = y0; y < y1; y++) {
            for(int x = x0; x < x1; x++) {
                getTile(x, y).render(x, y, screen);
            }
        }

        for(int  i = 0; i < entities.size(); i++) {
            entities.get(i).render(screen);
        }

        for(int  i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(screen);
        }

        for(int  i = 0; i < particles.size(); i++) {
            particles.get(i).render(screen);
        }

        for(int i = 0; i < players.size(); i++) {
            players.get(i).render(screen);
        }

        for(int i = 0; i < mobs.size(); i++) {
            mobs.get(i).render(screen);
        }

        for(int i = 0; i < items.size(); i++) {
            items.get(i).render(screen);
        }
    }

    public Tile getTile(int x, int y) {
        if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
        if(tiles[x + y * width] == Tile.col_grass) return Tile.grass;
        if(tiles[x + y * width] == Tile.col_flower) return Tile.flower;
        if(tiles[x + y * width] == Tile.col_wallStone) return Tile.wallStone;
        if(tiles[x + y * width] == Tile.col_floorWood) return Tile.floorWood;
        return Tile.voidTile;
    }

    public void add(Entity entity) {
        entity.init(this);
        if(entity instanceof Particle) {
            particles.add((Particle) entity);
        } else if(entity instanceof Projectile) {
            projectiles.add((Projectile) entity);
        } else if(entity instanceof Player) {
            players.add((Player) entity);
        } else if(entity instanceof Mob) {
            mobs.add((Mob) entity);
        } else if(entities instanceof Item) {
            items.add((Item) entity);
        } else {
            entities.add(entity);
        }
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public List<Player> getPlayers() {
       return players;
    }

    public Player getPlayerAt(int index) {
        return players.get(index);
    }

    public Player getClientPlayer() {
        return players.get(0);
    }

    public List<Node> findPath(Vector2i start, Vector2i goal) {
        List<Node> openList = new ArrayList<Node>();
        List<Node> closedList = new ArrayList<Node>();
        Node current = new Node(start, null, 0, getDistance(start, goal));
        openList.add(current);

        while(openList.size() > 0) {
            Collections.sort(openList, nodeSorter);
            current = openList.get(0);

            if(current.tile.equals(goal)) {
                List<Node> path = new ArrayList<Node>();
                while(current.parent != null) {
                    path.add(current);
                    current = current.parent;
                }

                openList.clear();
                closedList.clear();

                return path;
            }

            openList.remove(current);
            closedList.add(current);

            for(int i = 0; i < 9; i++) {
                if(i == 4) continue;
                int x = current.tile.getX();
                int y = current.tile.getY();
                int xi = (i % 3) - 1;
                int yi = (i / 3) - 1;
                Tile at = getTile(x + xi, y + yi);

                if(at == null) continue;
                if(at.solid()) continue;

                Vector2i a = new Vector2i(x + xi, y + yi);
                double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.95);
                double hCost = getDistance(a, goal);
                Node node = new Node(a, current, gCost, hCost);

                if(vecInList(closedList, a) && gCost >= node.gCost) continue;
                if(!vecInList(openList, a) || gCost < node.gCost) openList.add(node);
            }
        }
        closedList.clear();
        return null;
    }

    private boolean vecInList(List<Node> list, Vector2i vector) {
        for(Node n : list) {
            if(n.tile.equals(vector)) return true;
        }

        return false;
    }

    private double getDistance(Vector2i tile, Vector2i goal) {
        double dx = tile.getX() - goal.getX();
        double dy = tile.getY() - goal.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public List<Entity> getEntities(Entity entity, int radius) {
        List<Entity> result = new ArrayList<Entity>();
        int ex = entity.getX();
        int ey = entity.getY();
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if(e.equals(entity)) continue;
            int x = e.getX();
            int y = e.getY();

            int dx = Math.abs(x - ex);
            int dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx * dx) + (dy * dy));

            if(distance <= radius) result.add(e);
        }
        return result;
    }

    public List<Player> getPlayers(Entity entity, int radius) {
        List<Player> result = new ArrayList<Player>();
        int ex = entity.getX();
        int ey = entity.getY();
        for(int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int x = player.getX();
            int y = player.getY();

            int dx = Math.abs(x - ex);
            int dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx * dx) + (dy * dy));

            if(distance <= radius) result.add(player);
        }
        return result;
    }

}