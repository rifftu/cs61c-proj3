package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

abstract class Creature {
    abstract WorldFrame world();
    abstract int getX();
    abstract int getY();
    abstract Direction facing();
    abstract int getW();
    abstract int getH();
    abstract String name();
    abstract void move(int Dist, Direction dir);
    abstract boolean alive();
    abstract void kill();
    abstract boolean killer();
    abstract boolean blocking();
    static boolean blocked (Creature cr) {
        TETile[][] grid = cr.world().tiles();
        Object[][] map = cr.world().animals();
        int x = cr.getX();
        int y = cr.getY();
        switch (cr.facing()) {
            case UP: y++;
            case RIGHT: x++;
            case DOWN: y--;
            case LEFT: x--;
        }
        return (grid[x][y] == Tileset.WALL || !(map[x][y].blo));
    }
}
