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

    /*
    void expand(WorldFrame world) {

    }

    private int hasSpace(Direction d) {

        return 0;
    }
    */
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

    static void connect(Room one, Room two) {
        if ((one.TWall() < two.y || two.TWall() < one.y)
                && ((one.x < two.RWall() && two.RWall() <= one.RWall())
                || one.x <= two.x && two.x < one.RWall() )) {
                 Hallway.builtVertical(one, two, world);
        } else if ((two.RWall() < one.x || one.RWall() < two.x)
                && ((one.y <= two.y && two.y < one.TWall())
                || (one.y <two.TWall() && two.TWall() <= one.RWall()))) {
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
    }

    static boolean intersect(Room one, Room two) {
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
        return false;
    }
    static boolean ibt(int you, int one, int two) {
        return (you >= one && you <= two) || (you <= one && you >= two);
    }

    void draw(TETile[][] tiles) {
        //TODO: draw left wall

        //TODO: draw the middle part
        for (int i = x; i < x + w; i++) {
            //TODO: draw the first thingy
            //TODO: draw the middle
            for (int j = y; j < y + h; j++) {
                tiles[i][j] = Tileset.FLOOR;
            }
            //TODO: draw the toppu
        }

        //TODO: draw the right wall


    }


}

