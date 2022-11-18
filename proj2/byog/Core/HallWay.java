package byog.Core;
import byog.TileEngine.TETile;

class HallWay {
    private final WorldGenerator worldGenerator;
    private final Position pOne;
    private final Position pTwo;
    private final TETile floor;
    private final TETile wall;
    private final int diffX;
    private final int diffY;

    HallWay(Position pOne, Position pTwo, TETile wall, TETile floor) {
        this.worldGenerator = pOne.worldGenerator;
        this.floor = floor;
        this.wall = wall;
        this.pOne = pOne;
        this.pTwo = pTwo;
        diffX = Room.sgn(pTwo.xPosition - pOne.xPosition);
        diffY = Room.sgn(pTwo.yPosition - pOne.yPosition);
    }
    void drawRowColum() {
        Position pA= pOne.moveTo(0, - diffY);
        Position pB = new Position(pTwo.xPosition + diffX, pOne.yPosition +  diffY, worldGenerator);
        Position pC= pB.moveTo(0, -diffY);
        Position pD = pTwo.moveTo(- diffX, 0);
        Room roomAB = new Room(pA, pB, wall, floor);
        Room roomCD = new Room(pC, pD, wall, floor);
        roomAB.drawRoom();
        roomCD.drawRoom();
    }
    void drawColumRow() {
        Position pA = pOne.moveTo(diffX, 0);
        Position pB = new Position(pOne.xPosition - diffX, pTwo.yPosition + diffY, worldGenerator);
        Position pC = pTwo.moveTo(0, - diffY);
        Room roomAB = new Room(pA, pB, wall, floor);
        Room roomBC = new Room(pB, pC, wall, floor);
        roomAB.drawRoom();;
        roomBC.drawRoom();
    }
}
