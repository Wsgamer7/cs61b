package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import java.io.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Game implements Serializable {
    private Player player;
    private int depth;
    private String tileDescription;
    private final TERenderer ter = new TERenderer();
    private WorldGenerator wG;
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    public static final int DOWN_EMPTY = 2;
    public static final int UP_EMPTY = 5;
    public static final int LEFT_EMPTY = 5;
    public static final int RIGHT_EMPTY = 5;
    public static final int WIDTH_OF_MENU = 30;
    public static final int HEIGHT_OF_MENU = 50;
    private boolean inMenu = false;
    private boolean isLoading = false;
    private boolean isQuiting = false;
    private final Font titleFont = new Font("Monaco", Font.BOLD, 40);
    private final Font smallFont = new Font("Monaco", Font.PLAIN, 20);
    private final Font hUDFont = new Font("Monaco", Font.PLAIN, 16);
    private long seed;
    private Queue<Character> actionList = new LinkedList<>();

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        getMenu();
        inMenu = true;
        depth = 0;
        try {
            keyAndMouseListener();
            if (isLoading) {
                Game lastSave = loadGame();
                ter.initialize(WIDTH, HEIGHT, LEFT_EMPTY, DOWN_EMPTY); //inti for StDraw
                lastSave.keyAndMouseListener();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.getStackTrace();
        }
        System.exit(0);
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
        applyActionList();
        if (isLoading) {
            try {
                Game lastGame = loadGame();
                lastGame.entryActionList(actionList);
                lastGame.applyActionList();
                newWorld = lastGame.wG.world;
            } catch (IOException | ClassNotFoundException exception) {
                exception.getStackTrace();
            }
        }
        return newWorld;
    }
    //save and load
    private void saveGame()
        throws IOException {
        FileOutputStream fos = new FileOutputStream("../lastGame.load");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }
    private Game loadGame()
        throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("../lastGame.load");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Game lastSave = (Game) ois.readObject();
        ois.close();
        return lastSave;
    }
    private void applyActionList() {
        boolean isActing = true;
        try {
            while (isActing) {
                char action = actionList.remove();
                isActing = actionQuite(action);
            }
        } catch (RuntimeException | IOException e) {
            e.getStackTrace();
        }
    }
    private void entryActionList(Queue<Character> actions) {
        this.actionList = actions;
    }
    //top layer update###########################################
    private void updateTheWorld() {
        ter.renderFrame(wG.world);
        updateHUD();
        StdDraw.show();
    }
    private void keyAndMouseListener() throws IOException {
        boolean isListening = true;
        while (isListening) {
            //mouse
            if (!inMenu) {
                double xMouse = StdDraw.mouseX();
                double yMouse = StdDraw.mouseY();
                String capturedByMouse = getTypeFromXY(xMouse, yMouse);
                if (capturedByMouse != null) {
                    tileDescription = capturedByMouse;
                }
            }
            //key
            if (wG != null) {
                updateTheWorld();
            }
            if (!StdDraw.hasNextKeyTyped()) {
                StdDraw.pause(20);
                continue;
            }
            char input = StdDraw.nextKeyTyped();
            isListening = applyActionOfPlayer(input);
        }
        //save and exit
    }
    private boolean actionQuite(Character action) throws IOException {
        if (action.equals('L') || action.equals('l')) {
            isLoading = true;
            return false;
        }
        if (action.equals(':')) {
            isQuiting = true;
        } else if ((action.equals('q') || action.equals('Q')) && isQuiting) {
            //save and exit
            isQuiting = false;
            saveGame();
            return false;
        } else {
            player.moveToward(action);
        }
        return true;
    }
    private boolean applyActionOfPlayer(Character action) throws IOException {
        if (inMenu) {
            if (action.equals('q') || action.equals('Q')) {
                return false;
            } else if (action.equals('n') || action.equals('N')) {
                getSeedUseKey();
                getNextWorld();
                return true;
            } else if (action.equals('L') || action.equals('l')) {
                isLoading = true;
                return false;
            }
        } else {
            if (action.equals(':')) {
                isQuiting = true;
            } else if ((action.equals('q') || action.equals('Q')) && isQuiting) {
                //save and exit
                isQuiting = false;
                saveGame();
                return false;
            } else {
                isQuiting = false;
                boolean toDeeper = player.moveToward(action);
                if (toDeeper) {
                    getNextWorld();
                }
                return true;
            }
        }
        return true;
    }
    //HUD
    private int[] transCoordinate(int x, int y) {
        int[] toReturn = new int[2];
        toReturn[0] = x - LEFT_EMPTY;
        toReturn[1] = y - DOWN_EMPTY;
        return toReturn;
    }
    private String getTypeFromXY(double x, double y) {
        int xInt = (int) Math.floor(x);
        int yInt = (int) Math.floor(y);
        int[] posFixed = transCoordinate(xInt, yInt);
        Position nowPosition = new Position(posFixed[0], posFixed[1], wG);
        return nowPosition.typeOfTile();
    }
    private void updateHUD() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(hUDFont);
        //Depth
        StdDraw.textLeft(0, HEIGHT - 1, "---Depth = " + depth + "---");
        StdDraw.textRight(WIDTH - 1, HEIGHT - 1, tileDescription);
    }
    //Menu block##############################################################
    private void getMenu() {
        StdDraw.setCanvasSize(WIDTH_OF_MENU * 16, HEIGHT_OF_MENU * 16);
        StdDraw.setXscale(0, WIDTH_OF_MENU);
        StdDraw.setYscale(0, HEIGHT_OF_MENU);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        int midWidth = WIDTH_OF_MENU / 2;
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(titleFont);
        StdDraw.text(midWidth, (double) HEIGHT_OF_MENU * 7 / 9, "BYoG");
        StdDraw.setFont(smallFont);
        StdDraw.text(midWidth, (double) HEIGHT_OF_MENU * 5 / 9, "New Game (N)");
        StdDraw.text(midWidth, (double) HEIGHT_OF_MENU * 4 / 9, "Load Game (L)");
        StdDraw.text(midWidth, (double) HEIGHT_OF_MENU * 3 / 9, "Quit (Q)");
        StdDraw.show();
    }
    private void drawFrameForSeed(String s) {
        int midWidth = WIDTH_OF_MENU / 2;

        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);

        StdDraw.text(midWidth, (double) HEIGHT_OF_MENU * 5 / 9, "Entry seed (press S to end)");
        StdDraw.text(midWidth, (double) HEIGHT_OF_MENU * 4 / 9, s);
        StdDraw.show();
    }
    private void getSeedUseKey() {
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
    private void getNextWorld() {
        depth += 1;
        seed += 1;
        ter.initialize(WIDTH, HEIGHT, LEFT_EMPTY, DOWN_EMPTY); //inti for StDraw
        getWorldFormSeed();
        tileDescription = "";
        ter.renderFrame(wG.world);
        inMenu = false;
    }
    private TETile[][] getWorldFormSeed() {
        TETile[][] finalWorldFrame = new TETile[WIDTH - LEFT_EMPTY - RIGHT_EMPTY][HEIGHT - DOWN_EMPTY - UP_EMPTY];
        wG = new WorldGenerator(seed, finalWorldFrame, LEFT_EMPTY, DOWN_EMPTY);
        wG.toGenerator();
        this.player = wG.player;
        return finalWorldFrame;
    }

    private void getSeedAndActionFrom(String input) {
        seed = 0;
        char theChar = 'a';
        int index = 0;
        boolean isFindingSeed = false;
        while (index < input.length()) {
            theChar = input.charAt(index);
            if (theChar == 'L' || theChar == 'l') {
                break;
            }
            boolean isDigit = Character.isDigit(theChar);
            if (isFindingSeed){
                if (isDigit) {
                    int theInt = Character.getNumericValue(theChar);
                    seed = seed * 10 + theInt;
                } else if (theChar == 's' || theChar == 'S') {
                    isFindingSeed = false;
                    break;
                }
            } else if (theChar == 'n' || theChar == 'N') {
                isFindingSeed = true;
            }
            index += 1;
        }
        //fill in the action list
        while (index < input.length()) {
            theChar = input.charAt(index);
            actionList.add(theChar);
            index += 1;
        }
    }
}
