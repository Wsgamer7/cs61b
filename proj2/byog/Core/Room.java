package byog.Core;
import byog.TileEngine.TETile;

class Room {
    final int weight;
    final int height;
    final Position startPoint;
    final Position endPoint;
    final WorldGenerator worldGenerator;
    final int[][] isFloorMatrix;
    private final TETile floor;
    private final TETile wall;
    private final int diffX;
    private final int diffY;
    /* constructor*/
    Room(Position startPoint, Position endPoint, TETile wall, TETile floor) {
        this.worldGenerator = startPoint.worldGenerator;
        this.isFloorMatrix = worldGenerator.isFloorMatrix;
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
            startOfNewRow.drawARow(weight - 2 * diffX, floor);
            for(int i = 0; i != weight - diffX; i += diffX) {
                int xPosition = startOfNewRow.xPosition;
                int yPosition = startOfNewRow.yPosition;
                isFloorMatrix[xPosition + i][yPosition] = 1;
            }
            startOfNewRow = startOfNewRow.moveTo(0, diffY);
        }
    }
    void drawRoom() {
        drawWall();
        drawInside();
    }
    /* get a Position in room by two integers */
    private int getANumInTwoInts(int num1, int num2, double ratio) {
        return (int) Math.round(num1 * ratio + num2 * (1 - ratio));
    }
    Position getAPosition(int xRandom, int yRandom) {
        double xRatio = (double) xRandom / 9;
        double yRatio = (double) yRandom / 9;
        int newXPosition = getANumInTwoInts(startPoint.xPosition, endPoint.xPosition, xRatio);
        int newYPosition = getANumInTwoInts(startPoint.yPosition, endPoint.yPosition, yRatio);
        return new Position(newXPosition, newYPosition, worldGenerator);
    }
}
