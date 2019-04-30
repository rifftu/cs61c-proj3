package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;

/**
 * class for main menu of the game
 * source: Editor by Professor Hug
 */
public class MainMenu implements Serializable {
    private int width = 60;
    private int height = 30;
    private int nameLength = 30;
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
        StdDraw.text(midWidth, midHeight - 4, "Change name of avatar (C)");
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
        StdDraw.text(width / 2, height / 2 + 10, "Please enter a number for seed");
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.setPenColor(Color.white);
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
            if (StdDraw.hasNextKeyTyped() && !finish) {
                StdDraw.clear(Color.black);
                char addedChar = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (addedChar != 'S' && 48 <= addedChar && addedChar <= 57) {
                    result += addedChar;
                    drawFrameSeed(result);
                    StdDraw.text(width / 2, height / 2, result);
                }
                if (addedChar == 'S'){
                    finish = true;
                    break;
                }
            }
        }
        String sub = result.substring(0,result.length());

        return Long.parseLong(result);
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
        StdDraw.text(width / 2, height / 2 + 10, "Please enter your name");
        StdDraw.text(width / 2, height / 2 + 8, "Please do not enter the name has length over 30");
        StdDraw.text(width / 2, height / 2 + 6, " and using SPACE BAR to complete ");
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.setPenColor(Color.white);
        StdDraw.show();
    }
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
    public static void main(String[] args) {
        MainMenu n = new MainMenu();
        n.menu();
    }
}
