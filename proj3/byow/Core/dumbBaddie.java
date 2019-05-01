package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class dumbBaddie extends Creature {

    dumbBaddie() {
        digest = 0;
    }

    @Override
    TETile tile() {
        if (digest > 0) {
            return Tileset.EATING;
        } else {
            return Tileset.DUMBO;
        }
    }

    @Override
    int getW() {
        return 1;
    }

    @Override
    int getH() {
        return 1;
    }

    @Override
    void move(int dist, Direction dir) {

    }

    @Override
    void kill() {
        this.digest = 50;
    }

    @Override
    boolean killer() {
        return (this.digest == 0);
    }
}
