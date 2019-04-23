package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TERenderer.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


public class WorldFrameTest {
    static  Engine engine = new Engine();
    public static void main(String[] args) {

        int w = 80;
        int h = 30;
        int seed = 543;

        TERenderer ter = new TERenderer();
        ter.initialize(w, h);

        WorldFrame frame = new WorldFrame(w, h, seed);


        //for (int i = 0; i < args.length; i++) {
        //    engine.interactWithInputString(args[i]);
        //}
        //System.out.println(engine.toString());
        //TETile[][] testTiles = frame.tiles();

        ter.renderFrame(frame.tiles);

    }

}
