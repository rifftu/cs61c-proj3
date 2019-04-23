package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TERenderer.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.Random;

public class WorldFrameTest {
    static  Engine engine = new Engine();
    public static void main(String[] args) {

        for (int i = 0; i < 30; i++) {
        int w = 80;
        int h = 30;
        int seed = new Random().nextInt(2000);
        System.out.println(seed);
        //int seed = 1289;
        TERenderer ter = new TERenderer();
        ter.initialize(w, h);

        WorldFrame frame = new WorldFrame(w, h, seed);

        //for (int i = 0; i < args.length; i++) {
        //    engine.interactWithInputString(args[i]);
        //}
        //System.out.println(engine.toString());
        TETile[][] testTiles = frame.tiles();

        ter.renderFrame(frame.tiles);

    }
    }

}
