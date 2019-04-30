package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;

abstract class Creature implements Serializable {


    protected WorldFrame world;
    protected Direction facing;

    protected String name;
    protected boolean alive;

    protected int x;
    protected int y;

    protected boolean eating;

    WorldFrame world() {
        return this.world;
    }
    void randomLocation(){

    }
    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int nextX() {
        switch (this.facing) {
            case RIGHT: return getX() + 1;
            case LEFT: return getX() - 1;
            default: return getX();
        }
    }

    int nextY() {
        switch (this.facing) {
            case UP: return getY() + 1;
            case DOWN: return getY() - 1;
            default: return getY();
        }
    }

    String name() {
        return name;
    }

    Direction facing() {
        return facing;
    }

    abstract TETile tile();

    abstract int getW();

    abstract int getH();

    abstract void move(int Dist, Direction dir);

    boolean alive() {
        return this.alive;
    }

    abstract void kill();

    abstract boolean killer();


    static boolean blocked (Creature cr) {
        TETile[][] grid = cr.world().getFloortiles();
        Creature[][] map = cr.world().animals();
        int x = cr.nextX();
        int y = cr.nextY();
        return (grid[x][y] == Tileset.WALL || !(map[x][y] == null));
    }

}
