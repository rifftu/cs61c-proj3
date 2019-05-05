package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {

    public static final TETile W = new TETile('W', Color.white, Color.black, "W");
    public static final TETile A = new TETile('A', Color.white, Color.black, "A");
    public static final TETile S = new TETile('S', Color.white, Color.black, "S");
    public static final TETile D = new TETile('D', Color.white, Color.black, "D");
    public static final TETile I = new TETile('I', Color.white, Color.black, "I");
    public static final TETile J = new TETile('J', Color.white, Color.black, "J");
    public static final TETile K = new TETile('K', Color.white, Color.black, "K");
    public static final TETile L = new TETile('L', Color.white, Color.black, "L");
    public static final TETile PATH = new TETile('*', Color.red, Color.black, "L");
    public static final TETile Ws = new TETile('W', Color.cyan, Color.black, "W");
    public static final TETile As = new TETile('A', Color.cyan, Color.black, "A");
    public static final TETile Ss = new TETile('S', Color.cyan, Color.black, "S");
    public static final TETile Ds = new TETile('D', Color.cyan, Color.black, "D");
    public static final TETile Is = new TETile('I', Color.cyan, Color.black, "I");
    public static final TETile Js = new TETile('J', Color.cyan, Color.black, "J");
    public static final TETile Ks = new TETile('K', Color.cyan, Color.black, "K");
    public static final TETile Ls = new TETile('L', Color.cyan, Color.black, "L");

    public static final TETile DUMBO = new TETile('☠', Color.white, Color.black, "dumb");
    public static final TETile EATING = new TETile('❌', Color.red, Color.black, "eating");
    public static final TETile SMART = new TETile('☠', Color.red, Color.black, "smartBoi");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 150), Color.black,
            "floor");
    public static final TETile XRAY =  new TETile('·', Color.white, Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
}


