package byog.Core;
import byog.TileEngine.TETile;

class Room {
    final int weight;
    final int height;
    final Position startPoint;
    final Position endPoint;
    private final TETile floor;
    private final TETile wall;
    /* constructor*/
    Room(Position startPoint, int weight, int height, TETile wall, TETile floor) {
        this.startPoint = startPoint;
        this.weight = weight;
        this.height = height;
        this.wall = wall;
        this.floor = floor;
        endPoint = startPoint.moveTo(weight - 1, height - 1);
    }
    /* return ture if all tiles in this room are in the world Token*/
    boolean inWorld(TETile[][] world) {
        return startPoint.inWorld(world) && endPoint.inWorld(world);
    }
    /* return ture if this room is not overlap with room passed in */
    boolean notOverlap(Room room) {
        return !startPoint.inARoom(room) && !endPoint.inARoom(room);
    }
    /* draw the four wall of this room */
    void drawWall(TETile[][] world) {
        startPoint.drawARow(weight, world, wall);
        startPoint.drawAColum(height, world, wall);
        endPoint.drawARow(- weight, world, wall);
        endPoint.drawAColum(- height, world, wall);
    }
    /* draw the inside of this room */
    void drawInside(TETile[][] world) {
       for (int i = 1; i < height - 1; i++) {
           Position startOfNewRow = new Position(startPoint.xPosition + 1, startPoint.yPosition + i);
           startOfNewRow.drawARow(weight - 2, world, floor);
       }
    }
    void drawRoom(TETile[][] world) {
        drawWall(world);
        drawInside(world);
    }
}
