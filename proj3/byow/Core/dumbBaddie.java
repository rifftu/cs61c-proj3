package byow.Core;

import byow.TileEngine.TETile;

public class dumbBaddie extends Creature {
    @Override
    TETile tile() {
        return null;
    }

    @Override
    int getW() {
        return 0;
    }

    @Override
    int getH() {
        return 0;
    }

    @Override
    void move(int dist, Direction dir) {

    }

    @Override
    void kill() {

    }

    @Override
    boolean killer() {
        return false;
    }
}
