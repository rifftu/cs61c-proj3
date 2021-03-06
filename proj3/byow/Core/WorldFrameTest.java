package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
//import byow.TileEngine.Tileset;
//import edu.princeton.cs.introcs.StdDraw;

//import java.awt.desktop.SystemEventListener;
import java.util.Random;


public class WorldFrameTest {

    static  Engine engine = new Engine();
    public static void main(String[] args) {

        int w = 80;
        int h = 30;
        long seed = new Random().nextInt(1000000000);
        //seed = 71610048;
        //System.out.println(seed);
        //int seed = 5912;
        TERenderer ter = new TERenderer();
        WorldFrame frame;
        TETile[][] testTiles = new TETile[w][h];
        System.out.println(seed);

        frame = new WorldFrame(w, h, seed, "P1", "P2");

        //for (int i = 0; i < args.length; i++) {
        //    engine.interactWithInputString(args[i]);
        //}
        //System.out.println(engine.toString());
        ter.initialize(w, h);
        for (int x = 0; x < w; x += 1) {
            for (int y = 0; y < h; y += 1) {
                testTiles[x][y] = Tileset.NOTHING;
            }
        }
        testTiles[w / 2][h - 10] = Tileset.DUMBO;
        //testTiles = frame.tiles();

        ter.renderFrame(testTiles);
        ter.showOnly();

        KeyboardInputSource key = new KeyboardInputSource();

        /*while (true) {
            char c = key.getNextKey();
            if (c == 'q') {
                break;
            } else {
                frame.keyCatcher(c);
                ter.renderFrame(testTiles);
                ter.showOnly();
            }
        }*/




    }


}
