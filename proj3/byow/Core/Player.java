package byow.Core;

class Player extends Creature {

    private WorldFrame world;
    private Direction facing;

    private int x;
    private int y;

    @Override
    WorldFrame world() {
        return this.world;
    }

    @Override
    int getX() {
        return x;
    }

    @Override
    int getY() {
        return y;
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
        this.facing = dir;
        if (Creature.blocked(this)) {
            int x = this.x;
            int y = this.y;
            world.animals()[x][y] = null;
            switch (dir) {
                case LEFT: x--;
                case RIGHT: x++;
                case UP: y++;
                case DOWN: y--;
            }
            this.x = x;
            this.y = y;
            world.animals()[x][y] = this;
        }
    }

    @Override
    boolean alive() {
        return false;
    }

    @Override
    void kill() {

    }
}
