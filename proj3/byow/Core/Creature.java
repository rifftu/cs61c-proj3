package byow.Core;

import byow.TileEngine.TETile;
//import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.List;

abstract class Creature implements Serializable {


    protected WorldFrame world;
    protected Direction facing;

    protected String name;
    protected boolean alive;

    protected int x;
    protected int y;

    protected boolean eating;

    int digest;

    Direction guide;

    List<IPoint> path;

    WorldFrame world() {
        return this.world;
    }
    void randomLocation() {

    }
    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setX(int newX) {
        x = newX;
    }

    void setY(int newY) {
        y = newY;
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

    abstract void move(int dist, Direction dir);

    boolean alive() {
        return this.alive;
    }

    abstract void kill();

    abstract boolean killer();

    static boolean blocked(Creature cr) {
        TETile[][] grid = cr.world().getFloortiles();
        Creature[][] map = cr.world().animals();
        int x = cr.nextX();
        int y = cr.nextY();
        //System.out.println(grid[x][y].description().equals("wall"));
        //return (grid[x][y] == Tileset.WALL || !(map[x][y] == null));
        return (grid[x][y].description().equals("wall")
                || !(map[x][y] == null || map[x][y].killer()));
    }

    List<IPoint> path(int goalX, int goalY) {
        IPoint[][] nodes = world.nodes();
        IPoint goal = nodes[goalX][goalY];
        IPoint start = nodes[this.getX()][this.getY()];
        AStarSolver<IPoint> solver = new AStarSolver<>(world.graph(), start, goal, 10);
        path = solver.solution();
        if (path.size() < 2) {
            guide = null;
        }
        IPoint next = path.get(1);
        if (next.x > this.x) {
            guide = Direction.RIGHT;
        } else if (next.x < this.x) {
            guide = Direction.LEFT;
        } else {
            //up or down
            if (next.y > this.y) {
                guide = Direction.UP;
            } else {
                guide = Direction.DOWN;
            }
        }
        return path;
    }

    void die() {
        this.alive = false;
        if (this.killer()) {
            world.animals()[x][y].die();
        } else {
            world.animals()[x][y].kill();
        }
        world.animalSet().remove(this);
    }

}
