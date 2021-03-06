package byow.Core;

import byow.TileEngine.TERenderer;
import edu.princeton.cs.algs4.TrieSET;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * class for main menu of the game
 * source: Editor by Professor Hug
 */
public class MainMenu implements Serializable {
    private int width = 80;
    private int height = 38;
    private int nameLength = 20;
    private boolean name1 = false;
    private boolean name2 = false;

    /**
     * initial the background of main menu
     */
    MainMenu() {
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }
    /**
     * return name1 which is set to true if choose player method is called
     * and user chose player 1
     */
    public boolean getName1() {
        return name1;
    }
    /**
     * return name1 which is set to true if choose player method is called
     * and user chose player 1
     */
    public boolean getName2() {
        return name2;
    }
    /**
     * display the main menu with all options
     */

    public void menu() {
        int midWidth = width / 2;
        int midHeight = height / 2;

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(midWidth, height - 5, "CS61B Game");
        StdDraw.text(midWidth, height - 8, "Authors: Jerry Lai && Thanh Nguyen");
        StdDraw.text(midWidth, midHeight + 2, "New Game (N)");
        StdDraw.text(midWidth, midHeight, "Load Game (L)");
        StdDraw.text(midWidth, midHeight - 2, "Quit Game (Q)");
        StdDraw.text(midWidth, midHeight - 4, "Set name of player(s) (C)");
        StdDraw.text(midWidth, midHeight - 6, "Replay (R)");
        StdDraw.text(midWidth, midHeight - 8, "Instruction (I)");
        StdDraw.show();
    }

    /**
     * function to draw frame to ask user to input a seed
     * @param s
     */
    public void drawFrameSeed(String s) {
        //Take the string and display it in the center of the screen
        StdDraw.clear(Color.black);
        Font font = new Font("Arial", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width / 2, height / 2 + 10,
                "Please enter a POSITIVE number for seed then press S");
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.show();
    }


    /**
     * funtionc to draw screen while user input a number for seed
     */
    public long setSeed() {
        // Read n letters of player input
        drawFrameSeed("");
        String result = "";
        boolean finish = false;
        while (!finish) {
            if (StdDraw.hasNextKeyTyped()) {
                StdDraw.clear(Color.black);
                char addedChar = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (addedChar != 'S' && 48 <= addedChar && addedChar <= 57) {
                    result += addedChar;
                    drawFrameSeed(result);
                    StdDraw.text(width / 2, height / 2, result);
                }
                if (addedChar == 'S') {
                    finish = true;
                    break;
                }
            }
        }
        //System.out.println(result);
        //String sub = result.substring(0, result.length());
        return Long.parseLong(result);
    }

    /**
     * function to draw frame to ask user chose player
     */
    public void choosePlayer() {
        StdDraw.clear(Color.black);
        Font font = new Font("Arial", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width / 2, height / 2 + 10, "Please choose your player using keyboard");
        StdDraw.text(width / 2 - 8, height / 2, "1: Player 1");
        StdDraw.text(width / 2 + 8, height / 2, "2: Player 2");
        StdDraw.show();
        boolean finish = false;
        while (!finish) {
            if (StdDraw.hasNextKeyTyped()) {
                StdDraw.clear(Color.black);
                char addedChar = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (addedChar == 49) {
                    finish = true;
                    name1 = true;
                    name2 = false;
                } else if (addedChar == 50) {
                    name2 = true;
                    finish = true;
                    name1 = false;
                }
            }
        }
        StdDraw.show();
    }

    /**
     * function to draw frame to ask user to input a name
     * @param s
     */
    public void drawFrameName(String s) {
        //Take the string and display it in the center of the screen
        StdDraw.clear(Color.black);
        Font font = new Font("Arial", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width / 2, height / 2 + 10, "Please enter your name");
        StdDraw.text(width / 2, height / 2 + 8, "Please do not enter the name has length over 20");
        StdDraw.text(width / 2, height / 2 + 6, " and using SPACE BAR to complete ");
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.show();
    }

    /**
     * function to draw frame while user is inputting player's name
     */
    public String setName() {
        // Read n letters of player input
        drawFrameName("");
        String result = "";
        boolean finish = false;
        while (result.length() <= nameLength && !finish) {
            if (StdDraw.hasNextKeyTyped()) {
                StdDraw.clear(Color.black);
                char addedChar = StdDraw.nextKeyTyped();
                result += addedChar;
                drawFrameName(result);
                if (addedChar != 32) {
                    StdDraw.text(width / 2, height / 2, result);
                } else {
                    finish = true;
                    break;
                }
            }
        }
        menu();
        return result;
    }

    /**
     * method to display screen when there is a winner
     *
     */
    public void drawFrameWin(String s) {
        //Take the string and display it in the center of the screen
        StdDraw.clear(Color.black);
        Font font = new Font("Arial", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width / 2, height / 2 + 10, "Congratulation!!!");
        StdDraw.text(width / 2, height / 2 + 8, s + " win!");
        StdDraw.text(width / 2, height / 2 + 6, " Type b to comeback to the main menu");
        StdDraw.show();
        boolean finish = false;
        while (!finish) {
            if (StdDraw.hasNextKeyTyped()) {
                if (StdDraw.nextKeyTyped() == 'b') {
                    finish = true;
                }
            }
        }
        menu();
        StdDraw.show();
    }

    /**
     * method to display screen when there is a winner
     *
     */
    public void drawFrameGameover() {
        //Take the string and display it in the center of the screen
        StdDraw.clear(Color.black);
        Font font = new Font("Arial", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width / 2, height / 2 + 10, "UUUUUUUUU!!!");
        StdDraw.text(width / 2, height / 2 + 8, " NO ONE WIN!");
        StdDraw.text(width / 2, height / 2 + 6, " Type b to comeback to the main menu");
        StdDraw.show();
        boolean finish = false;
        while (!finish) {
            if (StdDraw.hasNextKeyTyped()) {
                if (StdDraw.nextKeyTyped() == 'b') {
                    finish = true;
                }
            }
        }
        menu();
        StdDraw.show();
    }

    /**
     * method to display screen the instruction
     *
     */
    public void Instruction() {
        //Take the string and display it in the center of the screen
        TERenderer ter = new TERenderer();
        TETile[][] tile = new TETile[width][height];
        ter.initialize(width, height);
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tile[x][y] = Tileset.NOTHING;
            }
        }
        tile[width / 2 - 8][height - 10] = Tileset.DUMBO;
        tile[width / 2 - 8][height - 12] = Tileset.SMART;
        tile[width / 2 - 8][height - 14] = Tileset.BLUBOI;
        tile[width / 2 - 8][height - 16] = Tileset.CROWN;
        System.out.println(tile[width/2][height -10].description());
        ter.renderFrame(tile);

        Font font = new Font("Arial", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(40, height - 2, "INSTRUCTION   ");
        StdDraw.text(40 , height - 4, " Type b to comeback to the main menu");
        StdDraw.textLeft(35, height - 9.5, " White Ghost");
        StdDraw.textLeft(35, height - 11.5, " Chasing Ghost");
        StdDraw.textLeft(35, height - 13.5, " Blue Star");
        StdDraw.textLeft(35, height - 15.5, " Crown");
        StdDraw.textLeft(35, height - 17.5, " Location of players flip for every period of time");
        StdDraw.show();
        boolean finish = false;
        while (!finish) {
            if (StdDraw.hasNextKeyTyped()) {
                if (StdDraw.nextKeyTyped() == 'b') {
                    finish = true;
                }
            }
        }
        menu();
        StdDraw.show();
    }
    public static void main(String[] args) {
        MainMenu n = new MainMenu();
        n.menu();
    }
}
