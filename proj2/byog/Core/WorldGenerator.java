package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

class WorldGenerator {
    final TETile[][] world;
    final int[][] isFloorMatrix;
    final int weightOFWorld;
    final int heightOfWorld;
    final Random RANDOM;
    WorldGenerator(TETile[][] world, long seed) {
        this.world = world;
        weightOFWorld = world.length;
        heightOfWorld = world[0].length;
        isFloorMatrix = new int[weightOFWorld][heightOfWorld];
        RANDOM = new Random(seed);
    }
    /* draw a HallWay between two rooms */
    void drawAHallWayBetweenTwoRooms(Room room1, Room room2) {
        Position pInRoom1 = room1.getAPosition(RANDOM.nextInt(9), RANDOM.nextInt(9));
        Position pInRoom2 = room2.getAPosition(RANDOM.nextInt(9), RANDOM.nextInt(9));
        HallWay hw = new HallWay(pInRoom1, pInRoom2, Tileset.WALL, Tileset.FLOOR);
        room1.drawRoom();
        room2.drawRoom();
        if (RANDOM.nextInt(0, 2) == 0) {
            hw.drawRowColum();
        } else {
            hw.drawColumRow();
        }
    }
}
