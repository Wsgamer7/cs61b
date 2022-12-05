package byog.Core;
import java.io.*;

public class Position implements Serializable {
    WorldGenerator wG;
    int[][] typeMatrix;
    int weightOfw;
    int heightOfw;
    int xPos;
    int yPos;
    /* the constructor of a position*/
    Position(int x, int y, WorldGenerator wG) {
        this.wG = wG;
        typeMatrix = wG.typeMatrix;
        xPos = x;
        yPos = y;
        heightOfw = wG.heightOfW;
        weightOfw = wG.weightOfW;
    }
    /* move to a new position by diffX and diffY */
    boolean equals(Position position) {
        boolean equalsInX = xPos == position.xPos;
        boolean equalsInY = yPos == position.yPos;
        return equalsInX && equalsInY;
    }
    Position moveTo(int diffX, int diffY) {
        int newXPos = xPos + diffX;
        int newYPos = yPos + diffY;
        return new Position(newXPos, newYPos, wG);
    }
    /* add a integer in typeMatrix at this position*/
    void drawThePos(int typeOfTile) {
        int nowTypeInPos = typeMatrix[xPos][yPos];
        if (nowTypeInPos != 2) {
            typeMatrix[xPos][yPos] = typeOfTile;
        }
    }
    /* add a colum of integers in typeMatrix*/
    void drawAColum(int typeOfTile, int height) {
        Position toDraw = this;
        try {
            for (int i = 0; i < height + 1; i++) {
                toDraw.drawThePos(typeOfTile);
                toDraw = toDraw.moveTo(0, 1);
            }
        } catch (RuntimeException ignore) { }
    }
    /* add a row of integers in typeMatrix*/
    void drawARow(int typeOfTile, int weight) {
        Position toDraw = this;
        try {
            for (int i = 0; i < weight + 1; i++) {
                toDraw.drawThePos(typeOfTile);
                toDraw = toDraw.moveTo(1, 0);
            }
        } catch (RuntimeException ignore) { }
    }
    /* return true if this position is in the world*/
    boolean inWorld() {
        boolean withInX = xPos >= 0 && xPos < weightOfw;
        boolean withInY = yPos >= 0 && yPos < heightOfw;
        return withInY && withInX;
    }
    /* return true if this pos is contained in room*/
    boolean isContained(Room room) {
        boolean containInX = this.xPos >= (room.origin.xPos - 3) && this.xPos <= (room.fasterP.xPos + 3);
        boolean containInY = this.yPos >= (room.origin.yPos - 3) && this.yPos <= (room.fasterP.yPos + 3);
        return containInY && containInX;
    }
    String typeOfTile() {
        try {
            int type = typeMatrix[xPos][yPos];
            String typeWithStr = wG.typeDescription.get(type);
            boolean unOpened = wG.numOfOpenedDoor == 0;
            boolean isKey = this.equals(wG.positionOfKey) && unOpened;
            if (isKey) {
                return "Key";
            } else {
                return typeWithStr;
            }
        } catch (RuntimeException e) {
            return "";
        }
    }
}
