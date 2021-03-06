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

    public static final TETile W = new TETile('∧', Color.white, Color.black, "Player 1");
    public static final TETile A = new TETile('<', Color.white, Color.black, "Player 1");
    public static final TETile S = new TETile('∨', Color.white, Color.black, "Player 1");
    public static final TETile D = new TETile('>', Color.white, Color.black, "Player 1");
    public static final TETile I = new TETile('\u23F6', Color.white, Color.black, "Player 2");
    public static final TETile J = new TETile('\u23F4', Color.white, Color.black, "Player 2");
    public static final TETile K = new TETile('\u23F7', Color.white, Color.black, "Player 2");
    public static final TETile L = new TETile('\u23F5', Color.white, Color.black, "Player 2");
    public static final TETile PATH = new TETile('*', Color.red, Color.black, "Path");
    public static final TETile Ws = new TETile('∧', Color.cyan, Color.black, "Player 1");
    public static final TETile As = new TETile('<', Color.cyan, Color.black, "Player 1");
    public static final TETile Ss = new TETile('∨', Color.cyan, Color.black, "Player 1");
    public static final TETile Ds = new TETile('>', Color.cyan, Color.black, "Player 1");
    public static final TETile Is = new TETile('\u23F6', Color.cyan, Color.black, "Player 2");
    public static final TETile Js = new TETile('\u23F4', Color.cyan, Color.black, "Player 2");
    public static final TETile Ks = new TETile('\u23F7', Color.cyan, Color.black, "Player 2");
    public static final TETile Ls = new TETile('\u23F5', Color.cyan, Color.black, "Player 2");

    public static final TETile DUMBO = new TETile('☠', Color.white, Color.black, "dumbBoi");
    public static final TETile EATING = new TETile('❌', Color.red, Color.black, "DeadBoi");
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
    public static final TETile BLUBOI = new TETile('❀', Color.cyan, Color.black, "powerup!");
    public static final TETile CROWN = new TETile('♛', new Color(255, 215, 0), Color.black, "Win!");

    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
}


