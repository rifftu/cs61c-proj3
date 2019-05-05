package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class powerUp extends Creature {

    powerUp(WorldFrame world) {
        this.alive = true;
        this.world = world;
    }

    @Override
    TETile tile() {
        return Tileset.BLUBOI;
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

    }

    @Override
    boolean killer() {
        return false;
    }

    @Override
    boolean goodie() {
        return true;
    }
}
