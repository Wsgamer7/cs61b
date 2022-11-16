package byog.Core;
import byog.TileEngine.TETile;

import java.util.Random;

class WorldGenerator {
    private final TETile[][] world;
    private final int weightOFWorld;
    private final int heightOfWorld;
    private final TETile floor;
    private final Random RANDOM;
    WorldGenerator(TETile[][] world, TETile floor, int seed) {
        this.world = world;
        this.floor = floor;
        weightOFWorld = world.length;
        heightOfWorld = world[0].length;
        RANDOM = new Random(seed);
    }
}
