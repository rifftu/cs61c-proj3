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

    static void connect(Room one, Room two) {}
    static boolean intersect(Room one, Room two) {
        return false;
    }
}
