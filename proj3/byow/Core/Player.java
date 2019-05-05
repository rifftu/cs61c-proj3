package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.io.Serializable;


class Player extends Creature implements Serializable {
    protected String setName;
    protected boolean winner;
    int stealth;
    Player(String name, String setName, WorldFrame world) {
        this.name = name;
        this.alive = true;
        this.facing = Direction.UP;
        this.eating = false;
        this.setName = setName;
        this.world = world;
        this.winner = false;
        this.stealth = 0;
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
        if (this.stealth == 0) {
            if (this.name().equals("P1")) {
                switch (this.facing) {
                    case UP:
                        return Tileset.W;
                    case LEFT:
                        return Tileset.A;
                    case DOWN:
                        return Tileset.S;
                    default:
                        return Tileset.D;
                }
            } else {
                switch (this.facing) {
                    case UP:
                        return Tileset.I;
                    case LEFT:
                        return Tileset.J;
                    case DOWN:
                        return Tileset.K;
                    default:
                        return Tileset.L;
                }
            }
        } else {
            if (this.name().equals("P1")) {
                switch (this.facing) {
                    case UP:
                        return Tileset.Ws;
                    case LEFT:
                        return Tileset.As;
                    case DOWN:
                        return Tileset.Ss;
                    default:
                        return Tileset.Ds;
                }
            } else {
                switch (this.facing) {
                    case UP:
                        return Tileset.Is;
                    case LEFT:
                        return Tileset.Js;
                    case DOWN:
                        return Tileset.Ks;
                    default:
                        return Tileset.Ls;
                }
            }
        }

    }

    void move(int dist, Direction dir) {
        if (!this.alive()) {
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

            if (map[x][y] != null && map[x][y].killer()) {
                die();
            } else {
                map[x][y] = this;
                if (stealth <= 0) {
                    world.flip(nextX(), nextY());
                } else {
                    stealth--;
                }
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

    boolean isWinner() {
        return this.winner;
    }
}
