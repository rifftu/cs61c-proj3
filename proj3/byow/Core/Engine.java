package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.SaveDemo.Editor;
import byow.TileEngine.TETile;

import java.io.*;
import java.lang.reflect.WildcardType;

public class Engine {
    TERenderer ter = new TERenderer();
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
        String newWorld = "";
        long seed = 0;
        int startIndexSeed = 0;
        boolean newW = false;
        TETile[][] finalWorldFrame = null;
        WorldFrame w = loadGame();
        while (inputType.possibleNextInput()) {
            totalCharacters += 1;
            char c = Character.toUpperCase(inputType.getNextKey());
            switch (c) {
                case 'N':
                    startIndexSeed = totalCharacters;
                    newW = true;
                    break;
                case 'S':
                    if ((startIndexSeed != totalCharacters - 1) && newW) {
                        newWorld = input.substring(startIndexSeed, totalCharacters - 1);
                        seed = Long.parseLong(newWorld);
                        w = new WorldFrame(WIDTH, HEIGHT, seed);
                        finalWorldFrame = w.tiles();
                    }
                    break;
                case 'L':
                    //System.out.println("test L");
                    w.drawRooms();
                    finalWorldFrame = w.tiles();
                    break;
                case ':':
                    c = Character.toUpperCase(inputType.getNextKey());
                    if (c == 'Q') {
                        saveGame(w);
                        System.exit(0);
                    }
                    break;
                default:
                    //System.out.println("default");
                    break;
                    // keyCatcher(c);// something to do with invalid input
            }
        }

        //if (seed > 0) {
        ter.initialize(WIDTH, HEIGHT);
            //System.out.println("seed "+ seed);
            //WorldFrame frame = new WorldFrame(WIDTH, HEIGHT, seed, newInput);

        ter.renderFrame(finalWorldFrame);

        //}
        //System.out.println("Processed " + totalCharacters + " characters.");

        return finalWorldFrame;
    }

    /**
     * this is function to save the game
     * source: save demo by Professor Hug
     */
    private static void saveGame(WorldFrame w) {
        File f = new File("./save_game");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(w);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    /**
     * this is function to load the game
     * source: save Demo by Professor Hug
     */
    private static WorldFrame loadGame() {
        File f = new File("./save_game");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (WorldFrame) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        //In the case no Editor has been saved yet, we return a new one.
        return new WorldFrame(WIDTH, HEIGHT, 1);
    }

}
