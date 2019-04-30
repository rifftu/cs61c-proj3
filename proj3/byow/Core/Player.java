package byow.Core;

import byow.TileEngine.Tileset;

import java.io.Serializable;

class Player extends Creature implements Serializable {



    Player(String name) {
        this.name = name;
        this.alive = true;
        this.facing = Direction.UP;
    }

    @Override
    int getW() {
        return 0;
    }

    @Override
    int getH() {
        return 0;
    }

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
            world.tiles()[x][y] = Tileset.AVATAR;
        }
    }


    @Override
    void kill() {

    }

    @Override
    boolean blocking() {
        return true;
    }

    @Override
    boolean killer() {
        return false;
    }
}
