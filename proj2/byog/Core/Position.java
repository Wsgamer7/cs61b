package byog.Core;

public class Position {
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
    Position moveTo(int diffX, int diffY){
        int newXPos = xPos + diffX;
        int newYPos = yPos + diffY;
        return new Position(newXPos, newYPos, wG);
    }
    /* add a integer in typeMatrix at this position*/
    void drawThePos(int typeOfTile){
        int nowTypeInPos = typeMatrix[xPos][yPos];
        if (nowTypeInPos != 2) {
            typeMatrix[xPos][yPos] = typeOfTile;
        }
    }
    /* add a colum of integers in typeMatrix*/
    void drawAColum(int typeOfTile, int height){
        Position toDraw = this;
        try{
            for(int i = 0; i < height + 1; i++) {
               toDraw.drawThePos(typeOfTile);
               toDraw = toDraw.moveTo(0, 1);
            }
        }catch (RuntimeException ignore){}
    }
    /* add a row of integers in typeMatrix*/
    void drawARow(int typeOfTile, int weight){
        Position toDraw = this;
        try{
            for(int i = 0; i < weight + 1; i++) {
                toDraw.drawThePos(typeOfTile);
                toDraw = toDraw.moveTo(1, 0);
            }
        }catch (RuntimeException ignore){}
    }
    /* return true if this position is in the world*/
    boolean inWorld() {
        boolean withInX = xPos >= 0 && xPos < weightOfw;
        boolean withInY = yPos >= 0 && yPos < heightOfw;
        return withInY && withInX;
    }
    /* return true if this pos is contained in room*/
    boolean isContain(Room room){
        boolean containInX = this.xPos >= room.origin.xPos && this.xPos <= room.fasterP.xPos;
        boolean containInY = this.yPos >= room.origin.yPos && this.yPos <= room.fasterP.yPos;
        return containInY && containInX;
    }
}
