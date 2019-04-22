package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TERenderer.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


public class WorldFrameTest {

    public static void main(String[] args) {

        int w = 70;
        int h = 40;
        int seed = 27;

        TERenderer ter = new TERenderer();
        ter.initialize(w, h);

        WorldFrame frame = new WorldFrame(w, h, seed);

        TETile[][] testTiles = frame.tiles();

        ter.renderFrame(testTiles);

    }

}
