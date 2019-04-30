package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


import java.io.Serializable;
import java.util.Random;

import java.util.Set;

class Room implements Serializable {

    /**
     * Private variables storing room location and size
     * (Different from hallways)
     */
    private int x;
    private int y;
    private int w;
    private int h;

    /**
     * Default constructor (unused)
     */
    Room() { }

    /**
     * Room constructor stores input into private variables
     * @param x X of bottom left corner
     * @param y Y of bottom left corner
     * @param w width of room
     * @param h height of room
     */
    Room(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     * Adds the four corners of the room into the pointSet,
     * which is used for calculating the closest point to a new room
     * @param pSet KDTree storing all the corners (belongs to worldFrame)
     */
    void addPoints(DynamicKD pSet) {

        Point ur = new Point(x + w - 1, y + h - 1, this);
        Point ul = new Point(x, y + h - 1, this);
        Point ll = new Point(x, y, this);
        Point lr = new Point(x + w - 1, y, this);

        pSet.put(ur);
        pSet.put(ul);
        pSet.put(ll);
        pSet.put(lr);

    }

    /**
     * Returns width and height of the room
     */
    int getW() {
        return w;
    }
    int getH() {
        return h;
    }


    /**
     * Returns the locations of the inside walls of the room
     */
    int getY() {
        return y;
    }
    int getX() {
        return x;
    }
    int rEdge() {
        return rWall() - 1;
    }
    int tEdge() {
        return tWall() - 1;
    }

    /**
     * Gets the X and Y of room's midpoint.
     * Useful for finding nearest room.
     */
    double midX() {
        return ((double) (getX() + rEdge())) / 2;
    }
    double midY() {
        return ((double) (getY() + tEdge())) / 2;
    }

    /**
     * Returns the locations of the top and right walls of the room
     * (useful for building hallways)
     */
    int rWall() {
        return x + w;
    }
    int tWall() {
        return y + h;
    }

    /**
     * Builds a hallway between two rooms that are known to be not connected
     * @param one first room
     * @param two second room
     * @param world The worldFrame this occurs in
     */
    static void connect(Room one, Room two, WorldFrame world) {

        if (vMeet(one, two)) {

            Hallway.builtVertical(one, two, world);

        } else if (hMeet(one, two)) {

            Hallway.builtHorizontal(one, two, world);

        } else if (one.getX() > two.rEdge()) {

            if (one.tEdge() < two.getY()) {
                Hallway.forwardL(two, one, world);
            }
            if (two.tEdge() < one.getY()) {
                Hallway.backwardL(one, two, world);
            }

        } else if (one.rEdge() < two.getX()) {

            if (one.tEdge() < two.getY()) {
                Hallway.backwardL(two, one, world);
            }
            if (two.tEdge() < one.getY()) {
                Hallway.forwardL(one, two, world);
            }

        }
    }

    /**
     * Checks to see if the rooms overlap in 2d space.
     * i.e. to say, they both horizontally and vertically overlap
     * @param one first room
     * @param two second room
     * @return true if the rooms overlap
     */
    static boolean intersect(Room one, Room two) {
        return hMeet(one, two) && vMeet(one, two);
    }

    /**
     * Checks to see if the rooms overlap vertically
     * @param one first room
     * @param two second room
     * @return true if there are points along the X axis where both rooms exist
     */
    private static boolean vMeet(Room one, Room two) {
        return Math.min(one.rEdge(), two.rEdge()) >= Math.max(one.getX(), two.getX());
    }

    /**
     * Checks to see if the rooms overlap horizontally
     * @param one first room
     * @param two second room
     * @return true if there are points along the Y axis where both rooms exist
     */
    private static boolean hMeet(Room one, Room two) {
        return Math.min(one.tEdge(), two.tEdge()) >= Math.max(one.getY(), two.getY());
    }

    /**
     * Static function to draw a room or hallway using for loops
     * @param tiles tileset representing the room
     * @param room the room currently being drawn
     */
    static void draw(TETile[][] tiles, Room room) {

        // Gets the necessary info about the room
        int yValue = room.getY();
        int xValue = room.getX();
        int hValue = room.getH();
        int wValue = room.getW();

        // Draws the left and right walls of the room,
        // but only if there isn't already a floor there
        for (int j = yValue - 1; j <= yValue + hValue; j++) {
            if (tiles[xValue - 1][j] == Tileset.NOTHING) {
                tiles[xValue - 1][j] = Tileset.WALL;
            }
            if (tiles[xValue + wValue][j] == Tileset.NOTHING) {
                tiles[xValue + wValue][j] = Tileset.WALL;
            }
        }

        // Goes from left to right, drawing the bottom wall, middle
        // floor, and top wall of the room. Walls are only drawn if there isn't
        // already floor there.
        for (int i = xValue; i < xValue + wValue; i++) {
            if (tiles[i][yValue - 1] == Tileset.NOTHING) {
                tiles[i][yValue - 1] = Tileset.WALL;
            }
            for (int j = yValue; j < yValue + hValue; j++) {
                tiles[i][j] = Tileset.FLOOR;
            }
            if (tiles[i][yValue + hValue] == Tileset.NOTHING) {
                tiles[i][yValue + hValue] = Tileset.WALL;
            }
        }
    }

    /**
     * Checks to see if the new room is already connected to a hallway.
     * If it is, there is no need to connect it again.
     * @param halls Set of all existing hallways in the room.
     * @return T if the room overlaps with a hallway, otherwise F
     */
    boolean connected(Set<Hallway> halls) {
        for (Hallway hall : halls) {
            if (Room.intersect(this, hall)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a creature to the room :)
     * Makes 200 attempts (which is a lot of attempts tbh)
     * @param cr the creature to be added
     * @return true if it has been successfully added
     */
    boolean addCreature(Creature cr, WorldFrame world) {
        Random r = world.rand();
        Creature[][] map = world.animals();
        int cX; int cY;
        for (int i = 0; i < 200; i++) {
            cX = RandomUtils.uniform(r, getX(), rWall());
            cY = RandomUtils.uniform(r, getY(), tWall());
            if (map[cX][cY] == null) {
                map[cX][cY] = cr;
                world.animalSet().add(cr);
                return true;
            }
        }
        return false;
    }
}

