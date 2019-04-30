package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.io.Serializable;


class Player extends Creature implements Serializable{

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

<<<<<<< HEAD
=======
    @Override
    TETile tile() {
        if (this.name().equals("P1")) {
            switch (this.facing) {
                case UP: return Tileset.W;
                case LEFT: return Tileset.A;
                case DOWN: return Tileset.S;
                default: return Tileset.D;
            }
        } else {
            switch (this.facing) {
                case UP: return Tileset.I;
                case LEFT: return Tileset.J;
                case DOWN: return Tileset.K;
                default: return Tileset.L;
            }
        }
    }

>>>>>>> e95361a5e908f3605e856e9b60ae75bf67aed0b5
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
<<<<<<< HEAD
            world.animals()[x][y] = this;
            world.tiles()[x][y] = Tileset.AVATAR;
=======

            if (map[x][y] != null && map[x][y].killer()) {
                this.alive = false;
                map[x][y].kill();
            } else {
                map[x][y] = this;
                world.flip(nextX(), nextY());
            }
>>>>>>> e95361a5e908f3605e856e9b60ae75bf67aed0b5
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
