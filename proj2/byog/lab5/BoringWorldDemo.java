package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT, 5, 5);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH - 10][HEIGHT - 10];
        for (int x = 0; x < world.length; x += 1) {
            for (int y = 0; y < world[0].length; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a block 14 tiles wide by 4 tiles tall
//        for (int x = 10; x < 18; x += 1) {
//            for (int y = 0; y < 16; y += 1) {
//                world[x][y] = Tileset.FLOOR;
//            }
//        }
//        for (int x = 10; x < 40; x += 1) {
//            for (int y = 16; y < 24; y += 1) {
//                world[x][y] = Tileset.FLOOR;
//            }
//        }
//        for (int x = 32; x < 40; x += 1) {
//            for (int y = 24; y < 40; y += 1) {
//                world[x][y] = Tileset.FLOOR;
//            }
//        }

//        int[] startPoint = {10, 0};
//        Hexagon.addColumnOfHexagon(world, startPoint, 5, 3);
        Hexagon.addManyHexagon(world, 3);
        // draws the world to the screen
        ter.renderFrame(world);
        drawUHD();
    }
    private static void drawUHD() {
        Font UHDFont = new Font("Monaco", Font.PLAIN, 16);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(UHDFont);
        StdDraw.textLeft(0, HEIGHT - 1, "Depth = " );
        StdDraw.show();
    }

}
