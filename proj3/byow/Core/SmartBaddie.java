package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class SmartBaddie extends Creature {

    SmartBaddie() {
        this.alive = true;
        digest = 0;
    }

    @Override
    TETile tile() {
        if (digest > 0) {
            return Tileset.EATING;
        } else {
            return Tileset.SMART;
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
        if (!this.alive() || this.digest > 0) {
            return;
        }
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

            if (map[x][y] != null) {
                if (map[x][y].killer()) {
                    die();
                } else {
                    kill();
                    map[x][y].die();
                    map[x][y] = this;
                }
            } else {
                map[x][y] = this;
            }
        }
    }

    @Override
    void kill() {
        if (!this.alive) {
            return;
        }
        this.digest = 50;
    }

    @Override
    boolean killer() {
        return false;
    }
}
