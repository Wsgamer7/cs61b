package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Game {
    private Player player;
    private int depth;
    private final TERenderer ter = new TERenderer();
    private WorldGenerator wG;
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 60;
    public static final int downEmpty = 2;
    public static final int upEmpty = 5;
    public static final int leftEmpty = 5;
    public static final int rightEmpty = 5;
    public static final int widthOfMenu = 30;
    public static final int heightOfMenu = 50;
    private boolean inMenu = false;
    private final Font titleFont = new Font("Monaco", Font.BOLD, 40);
    private final Font smallFont = new Font("Monaco", Font.PLAIN, 20);
    public long seed;
    public Queue<Character> actionList = new LinkedList<>();

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        getMenu();
        inMenu = true;
        depth = 0;
        keyListener();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        getSeedAndActionFrom(input);
        TETile[][] newWorld = getWorldFormSeed();
        //apply all actions in actionList
        boolean isActing = true;
        try {
            while (isActing) {
                char action = actionList.remove();
                isActing = actionQuite(action);
            }
        } catch (RuntimeException ignore) {}
        return newWorld;
    }
    //top layer update###########################################
    void updateTheWorld() {
        updateUHD();
        ter.renderFrame(wG.world);
    }
    void keyListener() {
        boolean isListening = true;
        while (isListening) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char input = StdDraw.nextKeyTyped();
            isListening = applyActionOfPlayer(input);
        }
        //save and exit
    }
    boolean actionQuite(Character action) {
        if (action.equals('q') || action.equals('Q')) {
            return false;
        }else {
            player.moveToward(action);
        }
        return true;
    }
    boolean applyActionOfPlayer(Character action) {
        if (inMenu) {
            if (action.equals('q') || action.equals('Q')) {
                return false;
            }else if (action.equals('n') || action.equals('N')) {
                getSeedUseKey();
                getNextWorld();
                return true;
            }
        }else {
            if (action.equals('q') || action.equals('Q')) {
                return false;
            }else {
                boolean toDeeper = player.moveToward(action);
                if (toDeeper) {
                    getNextWorld();
                }
                updateTheWorld();
                return true;
            }
        }
        return true;
    }
    //HUD
    void updateUHD() {
        Font UHDFont = new Font("Monaco", Font.PLAIN, 16);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(UHDFont);
        StdDraw.textLeft(0, HEIGHT - 1, "Depth = " );
    }
    //Menu block##############################################################
    void getMenu() {
        StdDraw.setCanvasSize(widthOfMenu * 16, heightOfMenu * 16);
        StdDraw.setXscale(0, widthOfMenu);
        StdDraw.setYscale(0, heightOfMenu);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        int midWidth = widthOfMenu / 2;
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(titleFont);
        StdDraw.text(midWidth, (double) heightOfMenu * 7 / 9, "BYoG");
        StdDraw.setFont(smallFont);
        StdDraw.text(midWidth, (double) heightOfMenu * 5 / 9, "New Game (N)");
        StdDraw.text(midWidth, (double) heightOfMenu * 4 / 9, "Load Game (L)");
        StdDraw.text(midWidth, (double) heightOfMenu * 3 / 9, "Quit (Q)");
        StdDraw.show();
    }
    private void drawFrameForSeed(String s) {
        int midWidth = widthOfMenu / 2;

        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);

        StdDraw.text(midWidth, (double) heightOfMenu * 5 / 9, "Entry seed (press S to end)");
        StdDraw.text(midWidth, (double) heightOfMenu * 4 / 9, s);
        StdDraw.show();
    }
    void getSeedUseKey() {
        seed = 0;
        drawFrameForSeed("");

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char typed = StdDraw.nextKeyTyped();
            if (Character.isDigit(typed)) {
                int added = Character.getNumericValue(typed);
                seed = seed * 10 + added;
                drawFrameForSeed("" + seed);
            }
            if (typed == 's' || typed == 'S') {
                break;
            }
        }
    }
    //##################################
    void getNextWorld() {
        depth += 1;
        seed += 1;
        ter.initialize(WIDTH, HEIGHT, leftEmpty, downEmpty);//inti for StDraw
        getWorldFormSeed();
        updateTheWorld();
        inMenu = false;
    }
    private TETile[][] getWorldFormSeed() {
//        ter.initialize(WIDTH, HEIGHT, leftEmpty, downEmpty);
        TETile[][] finalWorldFrame = new TETile[WIDTH - leftEmpty - rightEmpty][HEIGHT - downEmpty - upEmpty];
        wG = new WorldGenerator(seed, finalWorldFrame, leftEmpty, downEmpty);
        wG.toGenerator();
        this.player = wG.player;
        return finalWorldFrame;
    }

    public void getSeedAndActionFrom(String input) {
        seed = 0;
        char theChar = 'a';
        int index = 0;
        boolean isFindingSeed = false;
        while (index < input.length()) {
            theChar = input.charAt(index);
            boolean isDigit = Character.isDigit(theChar);
            if (isFindingSeed){
                if (isDigit) {
                    int theInt = Character.getNumericValue(theChar);
                    seed = seed * 10 + theInt;
                }else if (theChar == 's' || theChar == 'S') {
                    isFindingSeed = false;
                    break;
                }
            }else if (theChar == 'n' || theChar == 'N') {
                isFindingSeed = true;
            }
            index += 1;
        }
        //get action
        while (index < input.length()) {
            theChar = input.charAt(index);
            actionList.add(theChar);
            index += 1;
        }
    }
}
