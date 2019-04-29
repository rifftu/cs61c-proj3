package byow.Core;

class Player extends Creature {

    private WorldFrame world;

    @Override
    WorldFrame world() {
        return this.world;
    }

    @Override
    int getX() {
        return 0;
    }

    @Override
    int getY() {
        return 0;
    }

    @Override
    Direction facing() {
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
    String name() {
        return null;
    }

    @Override
    void move(int Dist, Direction dir) {

    }

    @Override
    boolean alive() {
        return false;
    }

    @Override
    void kill() {

    }
}
