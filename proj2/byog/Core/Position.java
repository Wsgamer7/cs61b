package byog.Core;
import byog.TileEngine.TETile;

class Position {
    final int xPosition;
    final int yPosition;
    /*construct a Position in 2d*/
    Position(int x, int y) {
        xPosition = x;
        yPosition = y;
    }
    /* return a new position diff dx, dy*/
    Position moveTo(int dx, int dy) {
        return new Position(xPosition + dx, yPosition + dy);
    }
    /* return true if this Position insides a room*/
    boolean inARoom(Room room) {
        int xFixed = xPosition - room.startPoint.xPosition;
        int yFixed = yPosition - room.startPoint.yPosition;
        return (xFixed > 0) && (yFixed > 0) && (xFixed < room.weight -1) && (yFixed < room.height - 1);
    }
    boolean inWorld(TETile[][] world) {
        int weightOfWorld = world.length;
        int heightOfWorld = world[0].length;
        return (xPosition >= 0) && (xPosition < weightOfWorld) && (yPosition >= 0) && (yPosition < heightOfWorld);
    }
    /* add a tile at this position in a world*/
    void drawThisPointIn(TETile[][] world, TETile tile) {
        world[xPosition][yPosition] = tile;
    }
    /* draw a row with length in a world, this position is the start Point, if length > 0
    draw toward right, otherwise draw toward left
     */
    void drawARow(int length, TETile[][] world, TETile tile) {
        int toward = 1;
        if (length < 0) {
            toward = -1;
        }
        try {
            for(int i = 0; i < Math.abs(length); i++) {
                Position nowPosition = new Position(xPosition + i * toward, yPosition);
                nowPosition.drawThisPointIn(world, tile);
            }
        }catch (RuntimeException ignore) {};
    }
    /* draw a colum starting at this position with length in a world, if length > 0,
    draw toward up, otherwise draw toward right
     */
    void drawAColum(int length, TETile[][] world, TETile tile) {
        int toward = 1;
        if (length < 0) {
            toward = -1;
        }
        try {
            for(int i = 0; i < Math.abs(length); i++) {
                Position nowPosition = new Position(xPosition, yPosition + i * toward);
                nowPosition.drawThisPointIn(world, tile);
            }
        }catch (RuntimeException ignore) {};
    }
}
