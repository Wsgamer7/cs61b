package byog.Core;
import byog.TileEngine.TETile;

class Position {
    final int xPosition;
    final int yPosition;
    final WorldGenerator worldGenerator;
    final TETile[][] world;
    final int[][] isFloorMatrix;
    /*construct a Position in 2d*/
    Position(int x, int y, WorldGenerator worldGenerator) {
        xPosition = x;
        yPosition = y;
        this.worldGenerator = worldGenerator;
        world = worldGenerator.world;
        isFloorMatrix = worldGenerator.isFloorMatrix;
    }
    /* return a new position diff dx, dy*/
    Position moveTo(int dx, int dy) {
        return new Position(xPosition + dx, yPosition + dy, worldGenerator);
    }
    /* return true if this Position insides a room*/
    boolean inARoom(Room room) {
        int xFixed = xPosition - room.startPoint.xPosition;
        int yFixed = yPosition - room.startPoint.yPosition;
        return (xFixed > 0) && (yFixed > 0) && (xFixed < room.weight -1) && (yFixed < room.height - 1);
    }
    boolean inWorld() {
        int weightOfWorld = worldGenerator.weightOFWorld;
        int heightOfWorld = worldGenerator.heightOfWorld;
        return (xPosition >= 0) && (xPosition < weightOfWorld) && (yPosition >= 0) && (yPosition < heightOfWorld);
    }
    /* add a tile at this position in a world*/
    void drawThisPoint(TETile tile) {
        if (isFloorMatrix[xPosition][yPosition] == 0) {
            world[xPosition][yPosition] = tile;
        }
    }
    /* draw a row with length + 1 in a world, this position is the start Point, if length > 0
    draw toward right, otherwise draw toward left
     */
    void drawARow(int lengthMinus1, TETile tile) {
        drawThisPoint(tile);
        int toward = Room.sgn(lengthMinus1);
        try {
            for(int i = 0; i < Math.abs(lengthMinus1); i++) {
                Position nowPosition = this.moveTo((i + 1) * toward, 0);
                nowPosition.drawThisPoint(tile);
            }
        }catch (RuntimeException ignore) {};
    }
    void drawARow(Position p1, Position p2, TETile tile) {
        int weightOfRowMinus1 = p2.xPosition - p1.xPosition;
        drawARow(weightOfRowMinus1, tile);
    }
    /* draw a colum starting at this position with length in a world, if length > 0,
    draw toward up, otherwise draw toward right
     */
    void drawAColum(int lengthMinus1, TETile tile) {
        drawThisPoint(tile);
        int toward = Room.sgn(lengthMinus1);
        try {
            for(int i = 0; i < Math.abs(lengthMinus1); i++) {
                Position nowPosition = this.moveTo(0, (i + 1) * toward);
                nowPosition.drawThisPoint(tile);
            }
        }catch (RuntimeException ignore) {};
    }
    void drawAColum(Position p1, Position p2, TETile tile) {
        int heightOfColumMinus1 = p2.yPosition - p1.yPosition;
        drawAColum(heightOfColumMinus1, tile);
    }
}
