package byow.Core;



import java.util.Random;

class Hallway extends Room {
    private Direction dir;
    private int length;
    private int startX;
    private int startY;

    private Hallway(int x, int y, Direction d, int l, WorldFrame world) {

        dir = d;
        length = l;
        startX = x;
        startY = y;
        Point farEnd;
        if (d == Direction.UP) {
            farEnd = new Point(x, y + l - 1, this);
        } else {
            farEnd = new Point(x + l - 1, y, this);
        }
        Point closeEnd = new Point(x, y, this);

        world.pSet.put(closeEnd);
        world.pSet.put(farEnd);


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


        if (len > 0) {
            world.hallwaysSet.add(new Hallway(x, y, Direction.UP, len, world));
        }
    }

    static void builtHorizontal(Room one, Room two, WorldFrame world) {
        Random ran = world.rand;
        int len;
        int x;
        int smallestTWallY = Math.min(one.tEdge(), two.tEdge());
        int largestY = Math.max(one.getY(), two.getY());

        if (one.rWall() < two.getX()) {
            len = two.getX() - one.rWall();
            x = one.rWall();
        } else if (two.rWall() < one.getX()) {
            len = one.getX() - two.rWall();
            x = two.rWall();
        } else {

            return;

        }
        int y = RandomUtils.uniform(ran, largestY, smallestTWallY + 1);


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
        int xH = RandomUtils.uniform(ran, one.getX(), one.rEdge() + 1);
        int lH = two.getX() - xH;

        if (lH > 0) {
            world.hallwaysSet.add(new Hallway(xH, yH, Direction.RIGHT, lH, world));
        }
        // built vertical part first


        int lV = one.getY() - yH;


        if (lV > 0) {
            world.hallwaysSet.add(new Hallway(xH, yH, Direction.UP, lV, world));
        }
    }

    //Built backward L hallway from higher right room to lower left room
    static void backwardL(Room one, Room two, WorldFrame world) {
        Random ran = world.rand;
        int yH = RandomUtils.uniform(ran, two.getY(), two.tWall());
        int xH = two.rWall();
        int lH = RandomUtils.uniform(ran, one.getX() - two.rWall(), one.rWall() - two.rWall());

        if (lH > 0) {


            world.hallwaysSet.add(new Hallway(xH, yH, Direction.RIGHT, lH, world));
        }
        // built vertical part first
        int xV = xH + lH;
        int lV = one.getY() - yH;


        if (lV > 0) {
            world.hallwaysSet.add(new Hallway(xV, yH, Direction.UP, lV, world));
        }
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



}
