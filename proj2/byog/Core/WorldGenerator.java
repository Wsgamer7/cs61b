package byog.Core;
import byog.TileEngine.TETile;

import java.util.Random;

class WorldGenerator {
    final TETile[][] world;
    final int[][] isFloorMatrix;
    final int weightOFWorld;
    final int heightOfWorld;
    final Random RANDOM;
    WorldGenerator(TETile[][] world, int seed) {
        this.world = world;
        weightOFWorld = world.length;
        heightOfWorld = world[0].length;
        isFloorMatrix = new int[weightOFWorld][heightOfWorld];
        RANDOM = new Random(seed);
    }

}
