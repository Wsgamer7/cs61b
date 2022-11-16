package byog.Core;
import byog.TileEngine.TETile;

class HallWay {
    private final Position pLeft;
    private final Position pRight;
    private final TETile floor;
    private final TETile wall;
    private final int lengthOfRow;
    private final int lengthOfColum;

    HallWay(Position pOne, Position pTwo, TETile wall, TETile floor) {
        this.floor = floor;
        this.wall = wall;
        if (pOne.xPosition < pTwo.xPosition) {
            pLeft = pOne;
            pRight = pTwo;
        }else {
            pLeft = pTwo;
            pRight = pOne;
        }
        lengthOfRow = pRight.xPosition - pLeft.xPosition + 1;
        lengthOfColum = Math.abs(pRight.yPosition - pLeft.yPosition) + 1;
    }
    void drawRowColum(TETile[][] world) {
        Position startOfRoom0 = pLeft.moveTo(0, -1);
        Room room0 = new Room(startOfRoom0, lengthOfRow, 3, wall, floor);
        if (pLeft.yPosition < pRight.yPosition) {
            Position startOfRoom1 = startOfRoom0.moveTo(lengthOfRow - 2, 0);
            Room room1 = new Room(startOfRoom1, 3, lengthOfColum + 1, wall, floor);
            room0.drawWall(world);
            room1.drawRoom(world);
            room0.drawInside(world);
        }else if (pLeft.yPosition == pRight.yPosition) {
            room0.drawRoom(world);
        }else {
            Position startOfRoom1 = startOfRoom0.moveTo((lengthOfRow - 2), -(lengthOfColum -2));
            Room room1 = new Room(startOfRoom1, 3, lengthOfColum + 1, wall, floor);
            room0.drawWall(world);
            room1.drawRoom(world);
            room0.drawInside(world);
        }
    }
    void drawColumRow() {

    }
}
