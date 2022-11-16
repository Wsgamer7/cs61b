package byog.lab5;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Hexagon {
    private static final int seed = 1900;
    private static final Random RANDOM = new Random(seed);
    /* get a random tile */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0 : return Tileset.FLOOR;
            case 1 : return Tileset.SAND;
            case 2 : return Tileset.WATER;
            case 3 : return Tileset.MOUNTAIN;
            case 4 : return Tileset.FLOWER;
            default: return Tileset.FLOOR;
        }
    }
    /* add a Hexagon to world*/
    public static void addAHexagon(TETile[][] world, int[] startPoint, int size, TETile tile) {
        int[] startPointCP = {startPoint[0], startPoint[1]};
        int height = getHeight(size);
        for (int i = 0; i < getWidth(size); i++) {
            int numbers = getNumbersOfIth(i, size);
            int indentation = (height - numbers) / 2;
            addAColumn(world, startPointCP, indentation, numbers, tile);
            startPointCP[0] = startPointCP[0] + 1;
        }
    }
    private static void addAColumn(TETile[][] world, int[] startPoint, int indentation, int numbers, TETile tile) {
        int xStart = startPoint[0];
        int yStart = startPoint[1];
        for (int i = 0; i < numbers; i++) {
            if (tile.description().equals("mountain")) {
                tile = TETile.colorVariant(tile, 30, 30, 30, RANDOM);
            }
            world[xStart][yStart + indentation + i] = tile;
        }
    }
    public static int getNumbersOfIth(int i, int size) {
        if (i < size) {
            return 2 * (i + 1);
        } else if (i >= 2 * size - 2) {
            return getNumbersOfIth(getWidth(size) - 1 - i, size);
        }else {
            return getHeight(size);
        }
    }
    private static int getWidth(int size) {
        return size + 2 * (size - 1);
    }
    private static int getHeight(int size) {
        return 2 * size;
    }
    /* partII : Drawing a tesselation of hexagon */
    public static void addColumnOfHexagon(TETile[][] world, int[] startPoint, int numbers, int size) {
        int[] startPointCP = {startPoint[0], startPoint[1]};
        while (numbers >= 1) {
            TETile aRandomTile = randomTile();
            addAHexagon(world, startPointCP, size, aRandomTile);
            startPointCP[1] += getHeight(size);
            numbers -= 1;
        }
    }
    public static void addManyHexagon(TETile[][] world, int size) {
        int[] leftStartPoint = {world.length / 2, 0};
        int[] rightStartPoint = {world.length / 2, 0};
        for (int i = 0; i < 3; i ++) {
            addColumnOfHexagon(world, leftStartPoint, 5 - i, size);
            addColumnOfHexagon(world, rightStartPoint, 5 - i, size);
            leftStartPoint[0] -= 2 * size - 1;
            rightStartPoint[0] += 2 * size - 1;
            leftStartPoint[1] += size;
            rightStartPoint[1] += size;
        }
    }
}
