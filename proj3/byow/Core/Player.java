package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

class Player extends Creature {

    Player(String name, WorldFrame world) {
        this.name = name;
        this.alive = true;
        this.facing = Direction.UP;
        this.eating = false;

        this.world = world;
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
    TETile tile() {
        if (this.name().equals("P1")) {
            return Tileset.P1;
        } else {
            return Tileset.P2;
        }
    }

    void move(int Dist, Direction dir) {
        this.facing = dir;
        //System.out.println(dir);
        if (!Creature.blocked(this)) {
            Creature[][] map = world.animals();
            int x = this.getX();
            int y = this.getY();
            map[x][y] = null;
            x = this.nextX();
            y = this.nextY();
            this.x = x;
            this.y = y;

            if (map[x][y] != null && map[x][y].killer()) {
                this.alive = false;
                map[x][y].kill();
            } else {
                map[x][y] = this;
                world.flip(nextX(), nextY());
            }
        }
    }


    @Override
    void kill() {

    }

    @Override
    boolean killer() {
        return false;
    }

}
