package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
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
        TETile[][] testTiles;
        System.out.println(seed);

        frame = new WorldFrame(w, h, seed);

        //for (int i = 0; i < args.length; i++) {
        //    engine.interactWithInputString(args[i]);
        //}
        //System.out.println(engine.toString());
        ter.initialize(w, h);

        testTiles = frame.tiles();

        ter.renderFrame(testTiles);
        ter.showOnly();

        KeyboardInputSource key = new KeyboardInputSource();

        while (true) {
            char c = key.getNextKey();
            if (c == 'q') {
                break;
            } else {
                System.out.println("BOOP");
                frame.keyCatcher(c);
                ter.renderFrame(testTiles);
                ter.showOnly();
            }
        }




    }


}
