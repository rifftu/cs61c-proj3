package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class SmartBaddie extends Creature {

    int birthday;
    final String name = "BOO!";

    SmartBaddie(int count, WorldFrame world) {
        this.alive = true;
        digest = 0;
        this.birthday = count;
        this.world = world;
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
        System.out.println("i'm moving!");
        if (!this.alive() || this.digest > 0) {
            return;
        }
        this.facing = dir;
        //System.out.println(dir);
        boolean blk = Creature.blocked(this);
        Creature[][] map = world.animals();
        if (map[nextX()][nextY()] != null) {
            blk = false;
        }
        if (!blk) {

            int x = this.getX();
            int y = this.getY();
            map[x][y] = null;
            x = this.nextX();
            y = this.nextY();
            this.x = x;
            this.y = y;

            if (map[x][y] != null) {
                if (map[x][y].killer()) {
                    world.killList.add(this);
                } else {
                    kill();
                    world.killList.add(map[x][y]);
                    map[x][y] = this;
                }
            } else {
                map[x][y] = this;
            }
        } else {
            System.out.println("heYYY");
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
