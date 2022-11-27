package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Menu {
    private int width;
    private int height;
    private final Font titleFont = new Font("Monaco", Font.BOLD, 40);
    private final Font smallFont = new Font("Monaco", Font.PLAIN, 20);
    Menu(int width, int height) {
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }
    void getGUI() {
        int midWidth = width / 2;
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(titleFont);
        StdDraw.text(midWidth, (double) height * 7 / 9, "BYoG");
        StdDraw.setFont(smallFont);
        StdDraw.text(midWidth, (double) height * 5 / 9, "New Game (N)");
        StdDraw.text(midWidth, (double) height * 4 / 9, "Load Game (L)");
        StdDraw.text(midWidth, (double) height * 3 / 9, "Quit (Q)");
        StdDraw.show();
    }
    private void drawFrame(String s) {
        int midWidth = width / 2;

        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);

        StdDraw.text(midWidth, (double) height * 5 / 9, "Entry seed (press S to end)");
        StdDraw.text(midWidth, (double) height * 4 / 9, s);
        StdDraw.show();
    }
    long getSeed() {
        long seed = 0;
        drawFrame("");

        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char typed = StdDraw.nextKeyTyped();
            if (Character.isDigit(typed)) {
                int added = Character.getNumericValue(typed);
                seed = seed * 10 + added;
                drawFrame("" + seed);
            }
            if (typed == 's' || typed == 'S') {
                break;
            }
        }
        return seed;
    }
}
