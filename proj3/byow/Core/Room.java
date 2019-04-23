package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
//import byow.TileEngine.TERenderer;

//import java.util.Queue;
import java.util.Set;

public class Room {
    private int x;
    private int y;
    private int w;
    private int h;
    private WorldFrame world;

    //DO
    public boolean tracked;

    Room() { }

    Room(int x, int y, int w, int h, WorldFrame world) {
        tracked = false;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.world = world;

    }

    void addPoints(DynamicKD pset) {
        Point ur = new Point(x + w - 1, y + h - 1, this);
        Point ul = new Point(x, y + h - 1, this);
        Point ll = new Point(x, y, this);
        Point lr = new Point(x + w - 1, y, this);
        pset.put(ur);
        pset.put(ul);
        pset.put(ll);
        pset.put(lr);
    }

    /*
    void expand(WorldFrame world) {

    }

    private int hasSpace(Direction d) {

        return 0;
    }
    */
    int getW() {
        return w;
    }
    int getH() {
        return h;
    }
    int getY() {
        return y;
    }
    int getX() {
        return x;
    }
    int lWall() {
        return x - 1;
    }

    int rWall() {
        return x + w;
    }

    int tWall() {
        return y + h;
    }

    int bWall() {
        return y - 1;
    }

    int rEdge() {
        return rWall() - 1;
    }

    int tEdge() {
        return tWall() - 1;
    }

    static void connect(Room one, Room two, WorldFrame world) {

        if (intersect(one, two)) {
            return;
        }
        /*
        if (
                (one.TWall() < two.y || two.TWall() < one.y)
                &&
                        (ibt(one.x, two.x, two.RWall())
                || ibt(two.x, one.x, one.RWall()))) {
            Hallway.builtVertical(one, two, world);
        } else if ((two.RWall() < one.x || one.RWall() < two.x)
                && (ibt(one.TWall(), two.y, two.TWall())
                || ibt(two.TWall(), one.y, one.TWall()))) {
            Hallway.builtHorizontal(one, two, world);
        } else if (one.x >= two.RWall()) {
            if (one.TWall() <= two.y) {
                Hallway.forwardL(two, one, world);
            }
            if (two.TWall() <= one.y){
                Hallway.backwardL(one, two, world);
            }
        } else if(one.RWall() <= two.x) {
            if (one.TWall() <= two.y) {
                Hallway.backwardL(two, one, world);
            }
            if (two.TWall() <= one.y){
                Hallway.forwardL(one, two, world);
            }
        }
        */
        /*if (ibti(one.getX(), two.getX(), two.REdge())
                || ibti(one.REdge(), two.getX(), two.REdge())
                || ibti(two.getX(), one.getX(), one.REdge())
                || ibti(two.REdge(), one.getX(), one.REdge()))*/
        if (Math.min(one.rEdge(), two.rEdge()) >= Math.max(one.getX(), two.getX())) {
            if (two.tracked) {
                System.out.println("VVV");
            }
            Hallway.builtVertical(one, two, world);

        /*} else if (ibti(one.getY(), two.getY(), two.TEdge())
                || ibti(one.TEdge(), two.getY(), two.TEdge())
                || ibti(two.getY(), one.getY(), one.TEdge())
                || ibti(two.TEdge(), one.getY(), one.TEdge()))*/
        } else if (Math.min(one.tEdge(), two.tEdge()) >= Math.max(one.getY(), two.getY())) {
            if (two.tracked) {
                System.out.println("HHH");
                throw new RuntimeException("gsafs");
            }
            Hallway.builtHorizontal(one, two, world);

        } else if (one.getX() > two.rEdge()) {
            if (one.tEdge() < two.getY()) {
                if (two.tracked) {
                    System.out.println("LL");

                }
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
        } else {
            System.out.println("tf now");
        }
    }

    static boolean intersect(Room one, Room two) {
        /*
        //r2 UL cut
        if (ibt(two.TWall(), one.BWall(), one.TWall())
                && ibt(two.LWall(), one.LWall(), one.RWall())) {
            return true;
        }
        //r2 UR cut
        if (ibt(two.TWall(), one.BWall(), one.TWall())
                && ibt(two.RWall(), one.LWall(), one.RWall())) {
            return true;
        }
        //r2 BL cut
        if (ibt(two.BWall(), one.BWall(), one.TWall())
                && ibt(two.LWall(), one.LWall(), one.RWall())) {
            return true;
        }
        //r2 BR cut
        if (ibt(two.BWall(), one.BWall(), one.TWall())
                && ibt(two.RWall(), one.LWall(), one.RWall())) {
            return true;
        }
        */
        boolean hMeet = false;
        boolean vMeet = false;
        if (ibt(two.tWall(), one.bWall(), one.tWall())
                || ibt(two.bWall(), one.bWall(), one.tWall())) {
            hMeet = true;
        }
        if (ibt(one.tWall(), two.bWall(), two.tWall())
                || ibt(one.bWall(), two.bWall(), two.tWall())) {
            hMeet = true;
        }

        if (ibt(two.lWall(), one.lWall(), one.rWall())
                || ibt(two.rWall(), one.lWall(), one.rWall())) {
            vMeet = true;
        }
        if (ibt(one.lWall(), two.lWall(), two.rWall())
                || ibt(one.rWall(), two.lWall(), two.rWall())) {
            vMeet = true;
        }


        return hMeet && vMeet;
    }
    static boolean ibt(int you, int one, int two) {
        return (you > one && you < two) || (you < one && you > two);
    }
    static boolean ibti(int you, int one, int two) {
        return (you >= one && you <= two) || (you <= one && you >= two);
    }

    void draw(TETile[][] tiles) {
        //DO: draw left and right wall
        int yValue = getY();
        int xValue = getX();
        int hValue = getH();
        int wValue = getW();
        for (int j = yValue - 1; j <= yValue + hValue; j++) {
            if (tiles[xValue - 1][j] == Tileset.NOTHING) {
                tiles[xValue - 1][j] = Tileset.WALL;
            }
            if (tiles[xValue + wValue][j] == Tileset.NOTHING) {
                tiles[xValue + wValue][j] = Tileset.WALL;
            }
        }
        //DO: draw the middle part
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

    boolean connected(Set<Hallway> halls) {
        for (Hallway hall : halls) {
            if (Room.intersect(this, hall)) {
                if (this.x == 26 && this.y == 2) {
                    System.out.println("hallx" + hall.getX());
                    System.out.println("hally" + hall.getY());
                    System.out.println("hallL" + hall.getL());
                    System.out.println("hallD" + hall.getD());
                }
                return true;
            }
        }
        return false;
    }
}

