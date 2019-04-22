package byow.Core;

import byow.SaveDemo.World;

import java.util.Random;

public class Hallway extends Room {
    private Direction dir;
    private int length;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private final int width = 1;
    Hallway(int x, int y, Direction d, int l) {
        dir = d;
        length = l;
        startX = x;
        startY = y;
        switch (d) {
            case UP:
                endY = y + l;
                endX = x;
            case DOWN:
                endY = y - l;
                endX = x;
            case LEFT:
                endX = x - l;
                endY = y;
            default:
                endX = x + l;
                endY = y;
        }
    }
    //Built vertical hallway from lower room to other room
    static void builtVertical(Room one, Room two, WorldFrame world) {
        Random ran = new Random();
        int length, y;
        int smallestRWallX = Math.min(one.RWall(), two.RWall());
        int largestX = Math.max(one.getX(), two.getX());
        Direction dir;
        if (one.TWall() < two.getY()) {
            dir = Direction.UP;
            length = two.getY() - one.TWall();
            y = one.TWall();
        } else { // two.TWall() < one.getY()
            dir = Direction.UP;
            length = one.getY() - two.TWall();
            y = two.RWall();
        }
        int x = RandomUtils.uniform(ran, largestX, smallestRWallX + 1);
        world.hallwaysSet.add(new Hallway(x, y, dir, length));
    }
    //Built horizontal hallway from most left room to other room
    static void builtHorizontal(Room one, Room two, WorldFrame world) {
        Random ran = new Random();
        int length, x;
        int smallestTWallY = Math.min(one.TWall(), two.TWall());
        int largestY = Math.max(one.getY(), two.getY());
        Direction dir;
        if (one.RWall() < two.getX()) {
            dir = Direction.RIGHT;
            length = two.getX() - one.RWall();
            x = one.RWall();
        } else {// two.RWall() < one.getX()
            dir = Direction.RIGHT;
            length = one.getX() - two.RWall();
            x = two.getX();
        }
        int y = RandomUtils.uniform(ran, largestY, smallestTWallY + 1);
        world.hallwaysSet.add(new Hallway(x, y, dir, length));
    }

    //Built forward L hallway from higher left room to lower right room
    // L shape hallway have vertical and horizontal part
    // room one is higher room, roo two is lower room
    static void forwardL(Room one, Room two, WorldFrame world) {
        //built horizontal hallway part
        Random ran = new Random();
        int yH = RandomUtils.uniform(ran, two.getY(), two.TWall() + 1);
        int xH = RandomUtils.uniform(ran, one.getX(), one.RWall() + 1);
        int lH = two.getX() - xH;
        world.hallwaysSet.add(new Hallway(xH, yH, Direction.RIGHT, lH));
        // built vertical part first
        int xV = xH;
        int yV = yH;
        int lV = one.getY() - yV;
        world.hallwaysSet.add(new Hallway(xV, yV, Direction.UP, lV));
    }

    //Built backward L hallway from higher right room to lower left room
    static void backwardL(Room one, Room two, WorldFrame world) {
        Random ran = new Random();
        int yH = RandomUtils.uniform(ran, two.getY(), two.TWall());
        int xH = two.getX();
        int lH = RandomUtils.uniform(ran, one.getX(), one.RWall() + 1);
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

}
