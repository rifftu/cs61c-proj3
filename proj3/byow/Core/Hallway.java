package byow.Core;

import byow.SaveDemo.World;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Hallway extends Room {
    private Direction dir;
    private int length;
    private int startX;
    private int startY;
    //private int endX;
    //private int endY;
    private final int width = 1;
    Hallway(int x, int y, Direction d, int l) {
        dir = d;
        length = l;
        startX = x;
        startY = y;
        switch (d) {
            /*
            case UP:
                //endY = y + l;
                //endX = x;
                //endY = y - l;
                //endX = x;
                //endX = x - l;
                //endY = y;
            default:
                //endX = x + l;
                //endY = y;
                */
        }
    }
    //Built vertical hallway from lower room to other room
    static void builtVertical(Room one, Room two, WorldFrame world) {
        Random ran = world.rand;
        int length, y;
        int smallestRWallX = Math.min(one.RWall(), two.RWall());
        int largestX = Math.max(one.getX(), two.getX());
        if (one.TWall() < two.getY()) {
            length = two.getY() - one.TWall();
            y = one.TWall();
        } else { // two.TWall() < one.getY()
            length = one.getY() - two.TWall();
            y = two.RWall();
        }
        int x = RandomUtils.uniform(ran, largestX, smallestRWallX + 1);
        world.hallwaysSet.add(new Hallway(x, y, Direction.UP, length));
    }
    //Built horizontal hallway from most left room to other room
    static void builtHorizontal(Room one, Room two, WorldFrame world) {
        Random ran = world.rand;
        int length;
        int x;
        int smallestTWallY = Math.min(one.TWall(), two.TWall());
        int largestY = Math.max(one.getY(), two.getY());
        if (one.RWall() < two.getX()) {
            length = two.getX() - one.RWall();
            x = one.RWall();
        } else if (two.RWall() < one.getX()) {// two.RWall() < one.getX()
            length = one.getX() - two.RWall();
            x = two.getX();
        } else {
            throw new RuntimeException("logic error");
        }
        int y = RandomUtils.uniform(ran, largestY, smallestTWallY + 1);
        if (x + length > 70) {
            throw new RuntimeException("horizontal problem");
        }
        world.hallwaysSet.add(new Hallway(x, y, Direction.RIGHT, length));

    }

    //Built forward L hallway from higher left room to lower right room
    // L shape hallway have vertical and horizontal part
    // room one is higher room, roo two is lower room
    static void forwardL(Room one, Room two, WorldFrame world) {
        //built horizontal hallway part
        Random ran = world.rand;
        int yH = RandomUtils.uniform(ran, two.getY(), two.TWall() + 1);
        int xH = RandomUtils.uniform(ran, one.getX(), one.RWall() + 1);
        int lH = two.getX() - xH;
        if (xH + lH > 70) {
            throw new RuntimeException("forward L problem");
        }
        world.hallwaysSet.add(new Hallway(xH, yH, Direction.RIGHT, lH));
        // built vertical part first
        int xV = xH;
        int yV = yH;
        int lV = one.getY() - yV;
        world.hallwaysSet.add(new Hallway(xV, yV, Direction.UP, lV));
    }

    //Built backward L hallway from higher right room to lower left room
    static void backwardL(Room one, Room two, WorldFrame world) {
        Random ran = world.rand;
        int yH = RandomUtils.uniform(ran, two.getY(), two.TWall());
        int xH = two.RWall();
        int lH = RandomUtils.uniform(ran, one.getX() - two.RWall(), one.RWall() + 1 - two.RWall());
        if (xH + lH > 70) {
            throw new RuntimeException("backward L problem");
        }
        world.hallwaysSet.add(new Hallway(xH, yH, Direction.RIGHT, lH));
        // built vertical part first
        int xV = xH + lH;
        int yV = yH;
        int lV = one.getY() - yV;
        world.hallwaysSet.add(new Hallway(xV, yV, Direction.UP, lV));
    }
    /*
    @Override
    void expand(WorldFrame world) {

    }

    private void newRoom(WorldFrame world) {

    }
    private void newHall(WorldFrame world) {

    }
    */
    void draw(TETile[][] tiles) {

        switch (this.dir) {
            case UP:
                drawV(tiles, startX, startY, startY + length);
            case RIGHT:
                drawH(tiles, startY, startX, startX + length);
            default:

        }

    }
    private void drawH(TETile[][] tiles, int y, int x1, int x2) {

        for (int i = x1; i < x2 ; i++) {
            if (tiles[i][y - 1] == Tileset.NOTHING) {
                tiles[i][y - 1] = Tileset.WALL;
            }
            tiles[i][y] = Tileset.FLOOR;
            if (tiles[i][y + 1] == Tileset.NOTHING) {
                tiles[i][y + 1] = Tileset.WALL;
            }
        }
    }
    private void drawV(TETile[][] tiles, int x, int y1, int y2) {
        for (int j = y1; j < y2; j++) {
            if (tiles[x - 1][j] == Tileset.NOTHING) {
                tiles[x - 1][j] = Tileset.WALL;
            }
            tiles[x][j] = Tileset.FLOOR;
            if (tiles[x - 1][j] == Tileset.NOTHING) {
                tiles[x - 1][j] = Tileset.WALL;
            }
        }
    }

}
