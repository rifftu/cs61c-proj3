package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TERenderer.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


public class WorldFrameTest {

    public static void main(String[] args) {
        int seed = 5;

        TERenderer ter = new TERenderer();
        ter.initialize(200, 100);

        TETile[][] testTiles = new WorldFrame(200, 100, seed).tiles();

        ter.renderFrame(testTiles);

    }

}
