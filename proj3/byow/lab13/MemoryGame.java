package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.rand = new Random(seed);
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random();
    }

    public String generateRandomString(int n) {
        //DO: Generate random string of letters of length n
        String result = "";
        for (int i = 0; i < n; i++) {
            result += CHARACTERS[rand.nextInt(26)];
        }
        return result;
    }

    public void drawFrame(String s) {

        //DO: Take the string and display it in the center of the screen
        StdDraw.clear(Color.black);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.setPenColor(Color.white);

        //DO: If game is not over, display relevant game information at the top of the screen
        StdDraw.text(10, height - 2, "Round: " + round);
        StdDraw.text(10, height - 4, Boolean.toString(playerTurn));
        StdDraw.show();
        //StdDraw.pause(1000);
    }

    public void flashSequence(String letters) {
        //DO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0;i < letters.length(); i++) {
            Font font = new Font("Arial", Font.BOLD, 30);
            StdDraw.setFont(font);
            StdDraw.text(width / 2, height / 2, Character.toString(letters.charAt(i)));
            StdDraw.setPenColor(Color.white);
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear(Color.black);

            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //DO: Read n letters of player input
        String result = "";
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        while (result.length() != n) {
            if (StdDraw.hasNextKeyTyped()) {
                StdDraw.clear(Color.black);
                drawFrame(result);
                result += StdDraw.nextKeyTyped();
                StdDraw.text(width / 2, height / 2, result);
                StdDraw.show();
                StdDraw.pause(500);
            }
        }
        return result;
    }

    public void startGame() {
        //DO: Set any relevant variables before the game starts
        round = 1;
        playerTurn = true;
        gameOver = false;
        StdDraw.setPenColor(Color.white);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        //StdDraw.pause(5000);
        //DO: Establish Engine loop
        while (!gameOver) {
            StdDraw.text(width / 2, height / 2, "Round: "+ round);
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear(Color.black);
            String rd = generateRandomString(round);
            flashSequence(rd);
            drawFrame(rd);
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear(Color.black);
            String player = solicitNCharsInput(round);
            StdDraw.clear(Color.black);
            if (player.equals(rd)) {
                round ++;
            } else {
                StdDraw.text(width / 2, height / 2, "Game Over! You made it to round: " + round);
                StdDraw.show();
                StdDraw.pause(5000);
                gameOver = true;
            }
        }
    }

}
