package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
//import java.lang.reflect.WildcardType;
//import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 38;

    private WorldFrame w;
    private WorldFrame previousW;
    private static long seed = -1;
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
                        w = new WorldFrame(WIDTH, HEIGHT - 3, seed, name1, name2);
                        newGameStart = true;
                        break;
                    case 'Q':
                        System.exit(0);
                        break;
                    case 'L':
                        w = loadGame();
                        if (w.getSeed() == -1) {
                            System.exit(0);
                        }
                        newGameStart = true;
                        break;
                    case 'C':
                        mainM.choosePlayer();
                        setName(mainM);
                        break;
                    case 'R':
                        previousW = loadGame();
                        w = new WorldFrame(WIDTH, HEIGHT - 3, previousW.getSeed(),
                                previousW.getName1(), previousW.getName2());
                        newGameStart = true;
                        playGameWithInitial(w, previousW.getAction());
                        break;
                    default:
                }
            }
        }
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(w.tiles());
        playGame(w, newGameStart);
        if (w.getGameover()) {
            mainM.drawFrameGameover();
        }
        if (w.getThereIsWinner()) {
            mainM.drawFrameWin(w.getWinner);
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
                            w = new WorldFrame(WIDTH, HEIGHT - 3, seed, name1, name2);
                            newW = false;
                        }
                    } else {
                        w.keyCatcher('s');
                    }
                    break;
                case 'L':
                    w = loadGame();
                    if (w.getSeed() == -1) {
                        System.exit(0);
                    }
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
        //ter.initialize(WIDTH, HEIGHT);
        //ter.renderFrame(w.tiles());
        //ter.showOnly();
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
        return new WorldFrame(WIDTH, HEIGHT - 3, seed, name1, name2);
    }
    /**
     * function to display mouse pointer on the game
     *
     */
    private void hudDisplay(boolean isPaused) {
        ter.renderFrame(w.tiles());
        StdDraw.setPenColor(Color.white);
        StdDraw.line(0, w.getH() - 1, w.getW(), w.getH() - 1);
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if (x < w.getW() && y < w.getH()) {
            String des = w.tiles()[x][y].description();
            if (des.equals("W") || des.equals("A")
                    || des.equals("S") || des.equals("D")) {
                StdDraw.text(5, HEIGHT - 1.5, "Player 1");
            } else if (des.equals("J") || des.equals("K")
                    || des.equals("I") || des.equals("L")) {
                StdDraw.text(5, HEIGHT - 1.5, "Player 2");
            } else {
                StdDraw.text(5, HEIGHT - 1.5, des);
            }
        } else {
            StdDraw.text(5, HEIGHT - 1.5, "nothing");
        }
        StdDraw.text(WIDTH / 2 - 3, HEIGHT - 1.5, "Player 1: " + w.getName1());
        StdDraw.text(WIDTH - 10, HEIGHT - 1.5, "Player 2: " + w.getName2());
        //StdDraw.filledCircle(x, y, 1);
        StdDraw.show();
        if (isPaused) {
            StdDraw.pause(500);
        }

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

    /**
     * function to play game with input from keyboard
     * @param  world: the world the player is in
     * @param isGameStarted: boolean if the game start or not
     */
    void playGame(WorldFrame world, boolean isGameStarted) {
        Character c = '[';
        while (isGameStarted) {
            hudDisplay(false);
            if (StdDraw.hasNextKeyTyped()) {
                Character pre = c;
                c =  StdDraw.nextKeyTyped();
                //String in = Character.toString(c);
                if (c == 'q' && pre == ':') {
                    callSave(w);
                    //System.exit(0);
                    interactWithKeyboard();
                } else if (c == 'q' && pre != ':') {
                    //do nothing
                    newGameStart = false;
                    interactWithKeyboard();
                } else {
                    //System.out.println("action " + w.getAction());
                    w.setAction(w.getAction() + c);
                    w.keyCatcher(c);
                }
            }
            isGameStarted = world.getGameover() || world.getThereIsWinner();
        }

    }

    /**
     * function to start the game with inital input string
     */
    void playGameWithInitial(WorldFrame world, String act) {
        if (w.getSeed() != -1) {
            ter.initialize(WIDTH, HEIGHT);
            ter.renderFrame(world.tiles());
            //ter.showOnly();
            hudDisplay(false);
            StdDraw.pause(500);
            if (!act.equals("")) { //if there is saved version before
                for (int i = 0; i < act.length(); i++) {
                    world.keyCatcher(act.charAt(i));
                    ter.renderFrame(world.tiles());
                    hudDisplay(true);
                }
            }
            //Font font = new Font("Arial", Font.BOLD, 20);
            //StdDraw.setFont(font);
            StdDraw.text(WIDTH / 2, HEIGHT - 3.2, "FINISH REPLAY. YOU CAN PLAY NOW!");
            StdDraw.show();
            StdDraw.pause(5000);
            playGame(world, true);
        } else {
            System.exit(0);
        }
    }
}
