package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

abstract class Creature {

    protected WorldFrame world;
    protected Direction facing;

    protected String name;
    protected boolean alive;

    protected int x;
    protected int y;

    WorldFrame world() {
        return this.world;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    String name() {
        return name;
    }

    Direction facing() {
        return facing;
    }

    abstract int getW();

    abstract int getH();

    abstract void move(int Dist, Direction dir);

    boolean alive() {
        return this.alive;
    }

    abstract void kill();

    abstract boolean killer();

    abstract boolean blocking();

    static boolean blocked (Creature cr) {
        TETile[][] grid = cr.world().tiles();
        Creature[][] map = cr.world().animals();
        int x = cr.getX();
        int y = cr.getY();
        switch (cr.facing()) {
            case UP: y++;
            case RIGHT: x++;
            case DOWN: y--;
            case LEFT: x--;
        }
        return (grid[x][y] == Tileset.WALL || !(map[x][y].blocking()));
    }
}
