package byog.Core;
import byog.TileEngine.TETile;

class Room {
    final int weight;
    final int height;
    final Position startPoint;
    final Position endPoint;
    final WorldGenerator worldGenerator;
    private final TETile floor;
    private final TETile wall;
    private final int diffX;
    private final int diffY;
    /* constructor*/
    Room(Position startPoint, Position endPoint, TETile wall, TETile floor) {
        this.worldGenerator = startPoint.worldGenerator;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.weight = endPoint.xPosition - startPoint.xPosition;
        this.height = endPoint.yPosition - startPoint.yPosition;
        this.wall = wall;
        this.floor = floor;
        diffX = sgn(weight);
        diffY = sgn(height);
    }
    /* return ture if all tiles in this room are in the world Token*/
    boolean inWorld(TETile[][] world) {
        return startPoint.inWorld() && endPoint.inWorld();
    }
    /* return ture if this room is not overlap with room passed in */
    boolean notOverlap(Room room) {
        return !startPoint.inARoom(room) && !endPoint.inARoom(room);
    }
    /* draw the four wall of this room */
    private void drawWall() {
        startPoint.drawARow(weight, wall);
        startPoint.drawAColum(height, wall);
        endPoint.drawARow(- weight, wall);
        endPoint.drawAColum(- height, wall);
    }
    /* draw the inside of this room */
    static int sgn(int x) {
        return Integer.compare(x, 0);
    }
    private void drawInside() {
        Position startOfNewRow = startPoint.moveTo(diffX, diffY);
        while (startOfNewRow.yPosition != endPoint.yPosition) {
            startOfNewRow.drawAColum(weight - 2 * diffX, floor);
            startOfNewRow = startOfNewRow.moveTo(0, diffY);
        }
    }
    void drawRoom() {
        drawWall();
        drawInside();
    }
}
