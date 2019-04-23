package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class Engine {
    //TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    //int previousSeed = 0;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     * @source: inputDemo by Professor Hug
     */
    public TETile[][] interactWithInputString(String input) {
        // DO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        //TETile[][] finalWorldFrame = new WorldFrame(input).tiles();
        InputSource inputType = new StringInputDevice(input);
        int totalCharacters = 0;
        //String previousWorld = input,
        String newWorld = input;
        //String newInput = input;
        long seed = 0;
        int startIndexSeed = 0;
        boolean newW = false;
        while (inputType.possibleNextInput()) {
            totalCharacters += 1;
            char c = Character.toUpperCase(inputType.getNextKey());
            if (c == 'N') {
                startIndexSeed = totalCharacters;
                newW = true;
                //if (0 < inputType.getNextKey() || inputType.getNextKey() > 9) {
                //    System.out.println("wrong format to build new world");
                //}
            }
            if (c == 'S') {
                //System.out.println("moo");
                if ((startIndexSeed != totalCharacters - 1) && newW) {
                    newWorld = input.substring(startIndexSeed, totalCharacters - 1);
                    seed = Long.parseLong(newWorld);
                    System.out.println(seed);
                    //newInput = input.substring(totalCharacters, input.length());
                    break;
                }

            }
            //for phase 2 might be
            /*if (c == 'L') {
                newWorld = previousWorld;
                seed = previousSeed;
                break;
            }
            if (c == 'Q') {
                System.out.println("done.");
                break;
            }
            if (c == ':') {
                previousWorld = newWorld;
            }*/
        }
        TETile[][] finalWorldFrame = null;
        if (seed > 0 && seed < Math.pow(2, 63)) {
            ter.initialize(WIDTH, HEIGHT);
            //System.out.println("seed "+ seed);
            //WorldFrame frame = new WorldFrame(WIDTH, HEIGHT, seed, newInput);
            WorldFrame frame = new WorldFrame(WIDTH, HEIGHT, seed);
            finalWorldFrame = frame.tiles;
            //ter.renderFrame(frame.tiles);
        }

        //System.out.println("Processed " + totalCharacters + " characters.");

        return finalWorldFrame;
    }



}
