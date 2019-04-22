package byow.Core;

import java.util.Queue;
import java.util.Set;

public class Room {
    private int x;
    private int y;
    private int w;
    private int h;


    Room() {}

    Room(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

    }

    /*
    void expand(WorldFrame world) {

    }

    private int hasSpace(Direction d) {

        return 0;
    }
    */

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
        if (((one.x < two.x + two.w) && (two.x + two.w <= one.w + one.x))
                || (one.x <= two.x && (two.x < one.x + one.w))) {
            Hallway.builtVertical(one, two);
        } else if () {

        }
        else if (one.x >= two.x + one.w) {
            if (one.y + one.h <= two.y) {
                Hallway.forwardL(two, one);
            } else {
                Hallway.backwardL(two, one);
            }
        } else if(one.x + one.w <= two.x) {
            if (one.y + one.h <= two.y) {
                Hallway.bacdwardL(one, two);
            } else {
                Hallway.forwardL(one, two);
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
}

