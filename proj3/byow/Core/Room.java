package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.TileEngine.TERenderer;

import java.util.Queue;
import java.util.Set;

public class Room {
    private int x;
    private int y;
    private int w;
    private int h;
    private WorldFrame world;

    Room() {}

    Room(int x, int y, int w, int h, WorldFrame world) {
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
    int LWall() {
        return x - 1;
    }

    int RWall() {
        return x + w;
    }

    int TWall() {
        return y + h;
    }

    int BWall() {
        return y - 1;
    }

    int REdge() {
        return RWall() - 1;
    }

    int TEdge() {
        return TWall() - 1;
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
        if /*(ibti(one.getX(), two.getX(), two.REdge()) || ibti(one.REdge(), two.getX(), two.REdge())
                || ibti(two.getX(), one.getX(), one.REdge()) || ibti(two.REdge(), one.getX(), one.REdge()))*/
        (Math.min(one.REdge(), two.REdge()) >= Math.max(one.getX(), two.getX())){

            Hallway.builtVertical(one, two, world);

        } else if /*(ibti(one.getY(), two.getY(), two.TEdge()) || ibti(one.TEdge(), two.getY(), two.TEdge())
                || ibti(two.getY(), one.getY(), one.TEdge()) || ibti(two.TEdge(), one.getY(), one.TEdge()))*/
        (Math.min(one.TEdge(), two.TEdge()) >= Math.max(one.getY(), two.getY())) {

            Hallway.builtHorizontal(one, two, world);

        } else if (one.getX() > two.RWall()) {
            if (one.TEdge() < two.getY()) {
                Hallway.forwardL(two, one, world);
            }
            if (two.TEdge() < one.getY()){
                Hallway.backwardL(one, two, world);
            }
        } else if(one.REdge() < two.getX()) {
            if (one.TEdge() < two.getY()) {
                Hallway.backwardL(two, one, world);
            }
            if (two.TEdge() < one.getY()){
                Hallway.forwardL(one, two, world);
            }
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
        if (ibti(two.TWall(), one.BWall(), one.TWall())
                || ibt(two.BWall(), one.BWall(), one.TWall())) {
            hMeet = true;
        }
        if (ibt(one.TWall(), two.BWall(), two.TWall())
                || ibt(one.BWall(), two.BWall(), two.TWall())) {
            hMeet = true;
        }

        if (ibt(two.LWall(), one.LWall(), one.RWall())
                || ibt(two.RWall(), one.LWall(), one.RWall())) {
            vMeet = true;
        }
        if (ibt(one.LWall(), two.LWall(), two.RWall())
                || ibt(one.RWall(), two.LWall(), two.RWall())) {
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
        //TODO: draw left and right wall
        int y = getY();
        int x = getX();
        int h = getH();
        int w = getW();
        for (int j = y - 1; j <= y + h; j++) {
            if (tiles[x - 1][j] == Tileset.NOTHING) {
                tiles[x - 1][j] = Tileset.WALL;
            }
            if (tiles[x + w][j] == Tileset.NOTHING) {
                tiles[x + w][j] = Tileset.WALL;
            }
        }
        //TODO: draw the middle part
        for (int i = x; i < x + w; i++) {
            if (tiles[i][y - 1] == Tileset.NOTHING) {
                tiles[i][y - 1] = Tileset.WALL;
            }
            for (int j = y; j < y + h; j++) {
                tiles[i][j] = Tileset.FLOOR;
            }
            if (tiles[i][y + h] == Tileset.NOTHING) {
                tiles[i][y + h] = Tileset.WALL;
            }
        }
    }

    boolean connected(Set<Hallway> halls) {
        for (Hallway hall : halls) {
            if (Room.intersect(this, hall)) {
                return true;
            }
        }
        return false;
    }
}

