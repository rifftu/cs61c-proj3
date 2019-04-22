package byow.Core;

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
    //Built vertical hallway from first room to second room
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
            dir = Direction.DOWN;
            length = one.getY() - two.TWall();
            y = one.getY();
        }
        int x = largestX + RandomUtils.uniform(ran, smallestRWallX - largestX);
        world.hallwaysSet.add(new Hallway(x, y, dir, length));
    }
    //Built horizontal hallway from one to two
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
            dir = Direction.LEFT;
            length = one.getX() - two.RWall();
            x = one.getX();
        }
        int y = largestY + RandomUtils.uniform(ran, smallestTWallY - largestY);
        world.hallwaysSet.add(new Hallway(x, y, dir, length))
    }

    //Built forward L hallway from higher left room to lower right room
    // L shape hallway have vertical and horizontal part
    // room one is higher room, roo two is lower room
    static void forwardL(Room one, Room two, WorldFrame world) {
        // built vertical part first
        Random ran = new Random();
        int xV = one.getX() + RandomUtils.uniform(ran, one.RWall());
        int yV = one.getY();
        int lV = one.getY() - two.TWall() + RandomUtils.uniform(ran, two.getH());
        world.hallwaysSet.add(new Hallway(xV, yV, Direction.DOWN, lV));
        //built horizontal part
        int xH = xV;
        int yH = yV + lV;
        int lH = one.getX() - xV + 1;
        world.hallwaysSet.add(new Hallway(xH, yH, Direction.RIGHT, lH));
    }

    //Built backward L hallway from higher right room to lower left room
    static Hallway backwardL(Room one, Room two) {
        // built vertical part first
        Random ran = new Random();
        int xV = one.getX() + RandomUtils.uniform(ran, one.RWall());
        int yV = one.getY();
        int lV = one.getY() - two.TWall() + RandomUtils.uniform(ran, two.getH());
        Hallway ver = new Hallway(xV, yV, Direction.DOWN, lV);
        //built horizontal
        int xH = xV;
        int yH = yV + lV;
        int lH = one.getX() - xV + 1;
        Hallway hor = new Hallway(xH, yH, Direction.LEFT, lH);
        return ver;
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
