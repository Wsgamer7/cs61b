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
        long seed = 210919;
        WorldGenerator worldGenerator = new WorldGenerator(world, seed);
        Position p1 = new Position(1, 1, worldGenerator);
        Position p2 = new Position(4, 5, worldGenerator);
        Position p3 = new Position(30, 30, worldGenerator);
        Position p4= new Position(20, 20, worldGenerator);
        Room room12 = new Room(p1, p2, wall, floor);
        Room room34 = new Room(p3, p4, wall, floor);
        worldGenerator.drawAHallWayBetweenTwoRooms(room12, room34);
//        Room room1 = new Room(p2, p1, wall, floor);
//        Room room2 = new Room(p1, p2, wall, floor);
//        room1.drawRoom();
//        room2.drawRoom();
//        Room room = new Room(30,20,8,9, floor, wall);
//        wordGenerator.drawARoom(room);
//        // draws the world to the screen
        ter.renderFrame(world);
    }


}
