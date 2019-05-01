package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
//import java.lang.reflect.WildcardType;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 35;

    private TETile[][] finalWorldFrame;
    private WorldFrame w;
    static Random rand = new Random();
    private static long seed = rand.nextInt(999999999);
    private boolean newGameStart = false;
    private static String name1 = "P1";
    private static String name2 = "P2";

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        MainMenu mainM = new MainMenu();
        mainM.menu();
        //boolean menu = true;
        while (!newGameStart) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                switch (c) {
                    case 'N':
                        //System.out.println("test");
                        seed = mainM.setSeed();
                        w = new WorldFrame(WIDTH, HEIGHT - 2, seed, name1, name2);
                        newGameStart = true;
                        break;
                    case 'Q':
                        System.exit(0);
                        break;
                    case 'L':
                        w = loadGame();
                        newGameStart = true;
                        break;
                    case 'C':
                        mainM.choosePlayer();
                        setName(mainM);
                        break;
                    default:
                }
            }
        }
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(w.tiles());
        Character c = '[';
        while (newGameStart) {
            hudDisplay();
            if (StdDraw.hasNextKeyTyped()) {
                Character pre = c;
                c =  StdDraw.nextKeyTyped();
                //String in = Character.toString(c);
                if (c == 'q' && pre == ':') {
                    callSave(w);
                    interactWithKeyboard();
                } else if (c == ':') {
                    //do nothing
                    c = ':';
                } else {
                    w.keyCatcher(c);
                }
            }
        }

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
        int startIndexSeed = 0;
        boolean newW = false;
        while (inputType.possibleNextInput()) {
            totalCharacters += 1;
            char c = Character.toUpperCase(inputType.getNextKey());
            switch (c) {
                case 'N':
                    //System.out.println("tes N");
                    startIndexSeed = totalCharacters;
                    newW = true;
                    break;
                case 'S':
                    if (newW) {
                        if ((startIndexSeed != totalCharacters - 1)) {
                            newWorld = input.substring(startIndexSeed, totalCharacters - 1);
                            seed = Long.parseLong(newWorld);
                            w = new WorldFrame(WIDTH, HEIGHT - 2, seed, name1, name2);
                            newW = false;
                        }
                    } else {
                        w.keyCatcher('s');
                    }
                    break;
                case 'L':
                    w = loadGame();
                    break;
                case ':':
                    c = Character.toUpperCase(inputType.getNextKey());
                    if (c == 'Q') {
                        callSave(w);
                    }
                    break;
                default:
                    c = Character.toLowerCase(c);
                    if (48 <= c && c <= 57) {
                        break;
                    } else {
                        w.keyCatcher(c); // moving player
                    }
                    break;
            }
        }
        ter.initialize(WIDTH, HEIGHT); //need to comment out when using the interact with keyboard
        ter.renderFrame(w.tiles());
        ter.showOnly();
        return w.tiles();
    }

    /**
     * this is function to save the game
     * source: save demo by Professor Hug
     */
    private static void saveGame(WorldFrame w) {
        File f = new File("./save_game.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(w);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            //System.exit(0);

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
        File f = new File("./save_game.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (WorldFrame) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                //System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                //System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                //System.exit(0);
            }
        }
        //In the case no WorldFrame has been saved yet, return a new one.
        return new WorldFrame(WIDTH, HEIGHT - 2, seed, name1, name2);
    }
    /**
     * function to display mouse pointer on the game
     *
     */
    private void hudDisplay() {
        ter.renderFrame(w.tiles());
        StdDraw.setPenColor(Color.white);
        StdDraw.line(0, w.getH() - 1, w.getW(), w.getH() - 1);
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if (x < w.getW() && y < w.getH()) {
            StdDraw.text(5, HEIGHT - 1.5, w.tiles()[x][y].description());
        } else {
            StdDraw.text(5, HEIGHT - 1.5, "nothing");
        }
        StdDraw.text(WIDTH / 2 - 3, HEIGHT - 1.5, "Player 1: " + w.getName1());
        StdDraw.text(WIDTH - 10, HEIGHT - 1.5, "Player 2: " + w.getName2());
        //StdDraw.filledCircle(x, y, 1);
        StdDraw.show();
        //StdDraw.pause(10);
    }

    /**
     * helper function to call load game
     * @param world
     */
    void callSave(WorldFrame world) {
        saveGame(w);
        newGameStart = false;
    }
    /**
     * set name fucntion
     */
    void setName(MainMenu m) {
        if (m.getName1()) {
            name1 = m.setName();
        } else {
            name2 = m.setName();
        }
    }
}
