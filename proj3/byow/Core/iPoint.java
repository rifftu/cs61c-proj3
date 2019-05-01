package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class iPoint implements Serializable {
    int x;
    int y;
    private TETile[][] tiles;
    private iPoint[][] nodes;

    iPoint(int x, int y, TETile[][] tiles, iPoint[][] nodes) {
        this.x = x;
        this.y = y;
        this.tiles = tiles;
        this.nodes = nodes;
    }

    List<iPoint> neighbors() {
        List<iPoint> result = new ArrayList<>();
        if (tiles[x + 1][y] != Tileset.WALL) {
            result.add(nodes[x + 1][y]);
        }
        if (tiles[x - 1][y] != Tileset.WALL) {
            result.add(nodes[x - 1][y]);
        }
        if (tiles[x][y + 1] != Tileset.WALL) {
            result.add(nodes[x][y + 1]);
        }
        if (tiles[x][y - 1] != Tileset.WALL) {
            result.add(nodes[x][y - 1]);
        }
        return result;
    }
}
