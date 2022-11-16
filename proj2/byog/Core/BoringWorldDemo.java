package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

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
        TETile floor = Tileset.FLOOR;
        TETile wall = Tileset.WALL;
        Position aPoint = new Position(10, 10);
        Position bPoint = new Position(15, 15);
        Position cPoint = new Position(20, 20);
        Position dPoint = new Position(25, 15);
        HallWay hw = new HallWay(aPoint, bPoint, wall, floor);
        HallWay hw2 = new HallWay(cPoint, dPoint, wall, floor);
        hw.drawRowColum(world);
        hw2.drawRowColum(world);
//        Room room = new Room(bPoint, 9, 3, wall, floor);
//        long seed = 262891;
//        WordGenerator wordGenerator = new WordGenerator(world, seed);
//        Room room = new Room(30,20,8,9, floor, wall);
//        wordGenerator.drawARoom(room);
//        // draws the world to the screen
        ter.renderFrame(world);
    }


}
