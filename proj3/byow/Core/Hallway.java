package byow.Core;

//import byow.SaveDemo.World;
//import byow.TileEngine.TETile;
//import byow.TileEngine.Tileset;

import java.util.Random;

class Hallway extends Room {
    private Direction dir;
    private int length;
    private int startX;
    private int startY;
    //private int endX;
    //private int endY;
    Hallway(int x, int y, Direction d, int l, WorldFrame world) {

        dir = d;
        length = l;
        startX = x;
        startY = y;
        Point farend;
        if (d == Direction.UP) {
            farend = new Point(x, y + l - 1, this);
        } else {
            farend = new Point(x + l - 1, y, this);
        }
        Point closeend = new Point(x, y, this);

        world.pSet.put(closeend);
        world.pSet.put(farend);

        //switch (d) {
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
        //}
    }
    int getStartX() {
        return startX;
    }
    int getStartY() {
        return startY;
    }
    Direction getD() {
        return dir;
    }
    int getL() {
        return length;
    }

    @Override
    int getX() {
        return startX;
    }

    int getY() {
        return startY;
    }

    int getH() {
        if (dir == Direction.UP) {
            return length;
        } else {
            return 1;
        }
    }

    int getW() {
        if (dir == Direction.UP) {
            return 1;
        } else {
            return length;
        }
    }
    //Built vertical hallway from lower room to other room
    static void builtVertical(Room one, Room two, WorldFrame world) {
        Random ran = world.rand;
        int len;
        int y;
        int smallestRWallX = Math.min(one.rEdge(), two.rEdge());
        int largestX = Math.max(one.getX(), two.getX());
        if (one.tWall() < two.getY()) {
            len = two.getY() - one.tWall();
            y = one.tWall();
        } else if (two.tWall() < one.getY()) { // two.TWall() < one.getY()
            len = one.getY() - two.tWall();
            y = two.tWall();
        } else {
            return;

        }
        int x = RandomUtils.uniform(ran, largestX, smallestRWallX + 1);
        if (y + len > world.height) {
            throw new RuntimeException("vertical problem");
        }
        if (len > 0) {
            world.hallwaysSet.add(new Hallway(x, y, Direction.UP, len, world));
        }
    }
    //Built horizontal hallway from most left room to other room
    static void builtHorizontal(Room one, Room two, WorldFrame world) {
        Random ran = world.rand;
        int len;
        int x;
        int smallestTWallY = Math.min(one.tEdge(), two.tEdge());
        int largestY = Math.max(one.getY(), two.getY());

        if (one.rWall() < two.getX()) {
            len = two.getX() - one.rWall();
            x = one.rWall();
        } else if (two.rWall() < one.getX()) {// two.RWall() < one.getX()
            len = one.getX() - two.rWall();
            x = two.rWall();
        } else {

            return;

        }
        int y = RandomUtils.uniform(ran, largestY, smallestTWallY + 1);
        if (x + len > world.width) {
            throw new RuntimeException("horizontal problem");
        }
        if (len > 0) {
            world.hallwaysSet.add(new Hallway(x, y, Direction.RIGHT, len, world));
        }
    }

    //Built forward L hallway from higher left room to lower right room
    // L shape hallway have vertical and horizontal part
    // room one is higher room, roo two is lower room
    static void forwardL(Room one, Room two, WorldFrame world) {
        //built horizontal hallway part
        Random ran = world.rand;

        int yH = RandomUtils.uniform(ran, two.getY(), two.tEdge() + 1);
        if (one.rEdge() + 1 <= one.getX()) {
            System.out.println(((Hallway) one).getW());
            System.out.println(((Hallway) one).getD());
            throw new RuntimeException("wyd");

        }
        int xH = RandomUtils.uniform(ran, one.getX(), one.rEdge() + 1);
        int lH = two.getX() - xH;
        if (xH + lH > world.width) {
            throw new RuntimeException("forward L problem");
        }
        if (lH > 0) {
            world.hallwaysSet.add(new Hallway(xH, yH, Direction.RIGHT, lH, world));
        }
        // built vertical part first
        int xV = xH;
        int yV = yH;
        int lV = one.getY() - yV;
        if (yV + lV > world.height) {
            throw new RuntimeException("forward L problem");
        }
        if (lV > 0) {
            world.hallwaysSet.add(new Hallway(xV, yV, Direction.UP, lV, world));
        }
    }

    //Built backward L hallway from higher right room to lower left room
    static void backwardL(Room one, Room two, WorldFrame world) {
        Random ran = world.rand;
        int yH = RandomUtils.uniform(ran, two.getY(), two.tWall());
        int xH = two.rWall();
        int lH = RandomUtils.uniform(ran, one.getX() - two.rWall(), one.rWall() - two.rWall());
        if (xH + lH > world.width) {
            throw new RuntimeException("backward L problem");
        }
        if (lH > 0) {


            world.hallwaysSet.add(new Hallway(xH, yH, Direction.RIGHT, lH, world));
        }
        // built vertical part first
        int xV = xH + lH;
        int yV = yH;
        int lV = one.getY() - yV;
        if (yV + lV > world.height) {
            throw new RuntimeException("backward L problem");
        }
        if (lV > 0) {
            world.hallwaysSet.add(new Hallway(xV, yV, Direction.UP, lV, world));
        }
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

    @Override
    int lWall() {
        return startX - 1;
    }

    @Override
    int rWall() {
        if (dir == Direction.UP) {
            return startX + 1;
        } else {
            return startX + length;
        }
    }

    @Override
    int tWall() {
        if (dir == Direction.UP) {
            return startY + length;
        } else {
            return startY + 1;
        }
    }

    @Override
    int bWall() {
        return startY - 1;
    }
    /*
    void draw(TETile[][] tiles) {


        if (this.dir == Direction.UP) {
            drawV(tiles, startX, startY , startY + length);
        } else {
            drawH(tiles, startY, startX, startX + length);
        }

    }
    private void drawH(TETile[][] tiles, int y, int x1, int x2) {
        //draw the cap
        for (int i = x1; i < x2 ; i++) {
            if (tiles[i][y - 1] == Tileset.NOTHING) {
                tiles[i][y - 1] = Tileset.WALL;
            }
            tiles[i][y] = Tileset.FLOWER;
            if (tiles[i][y + 1] == Tileset.NOTHING) {
                tiles[i][y + 1] = Tileset.WALL;
            }
        }

        tiles[x2][y - 1] = Tileset.WALL;
        tiles[x2 + 1][y - 1] = Tileset.WALL;
    }
    private void drawV(TETile[][] tiles, int x, int y1, int y2) {
        for (int j = y1; j < y2; j++) {
            if (tiles[x - 1][j] == Tileset.NOTHING) {
                tiles[x - 1][j] = Tileset.WALL;
            }

            tiles[x][j] = Tileset.FLOWER;

            if (tiles[x + 1][j] == Tileset.NOTHING) {
                tiles[x + 1][j] = Tileset.WALL;
            }
        }
    }
    */


}
