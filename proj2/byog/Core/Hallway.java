package byog.Core;
import java.io.*;

public class Hallway implements Serializable {
    Position posLeft;
    Position posRight;
    int[][] typeMatrix;
    int weight;
    int height;
    Hallway(Position pos1, Position pos2) {
        if (pos1.xPos < pos2.xPos) {
            posLeft = pos1;
            posRight = pos2;
        } else {
            posLeft = pos2;
            posRight = pos1;
        }
        weight = posRight.xPos - posLeft.xPos;
        height = posRight.yPos - posLeft.yPos;
        typeMatrix = posLeft.typeMatrix;
    }
    /* draw a L style Hallway*/
    void drawLHW() {
        Position p1 = posLeft.moveTo(0, -1);
        int weightOfRoom1 = weight + 1;
        int heightOfRoom1  = 2;
        int weightOfRoom2 = 2;
        int heightOfRoom2  = Math.abs(height) + 1;
        Room room1 = new Room(p1, weightOfRoom1, heightOfRoom1);
        int isMoveY = 0;
        if (height < 0) {
            isMoveY = 1;
        }
        Position p2 = p1.moveTo(weight - 1, height * isMoveY);
        Room room2 = new Room(p2, weightOfRoom2, heightOfRoom2);
        room1.drawRoom();
        room2.drawRoom();
    }
    /* draw a gama style Hallway*/
    void drawGamaHW() {
        int weightOfRoom1 = 2;
        int heightOfRoom1  = Math.abs(height) + 1;
        int weightOfRoom2 = weight + 1;
        int heightOfRoom2  = 2;
        int isMoveY = 0;
        if (height < 0) {
            isMoveY = 1;
        }
        Position p1 = posLeft.moveTo(-1, height * isMoveY);
        Room room1 = new Room(p1, weightOfRoom1, heightOfRoom1);
        Position p2 = posRight.moveTo(-weight - 1, -1);
        Room room2 = new Room(p2, weightOfRoom2, heightOfRoom2);
        room1.drawRoom();
        room2.drawRoom();
    }
    void drawHW(int way) {
        if (way == 0) {
            drawLHW();
        } else {
            drawGamaHW();
        }
    }
}
