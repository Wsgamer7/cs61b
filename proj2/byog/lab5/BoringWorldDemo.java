package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
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
    }


}
