package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.*;

import java.util.HashMap;
import java.util.Map;

 class Player implements Serializable {
    Position p;
    private final WorldGenerator wG;
    private final int[][] typeMatrix;
    private final Map<Character, int[]> towardMap = new HashMap<>();
    private TETile styleOfPlayer;
    Player(Position intiP) {
        p = intiP;
        this.styleOfPlayer = Tileset.PLAYER;
        typeMatrix = p.typeMatrix;
        wG = p.wG;
    }
    private void intiTowardMap() {
        int[] up = new int[] {0, 1};
        int[] down = new int[] {0, -1};
        int[] left = new int[] {-1, 0};
        int[] right = new int[] {1, 0};
        towardMap.put('w', up);
        towardMap.put('s', down);
        towardMap.put('a', left);
        towardMap.put('d', right);
    }
    void drawPlayer() {
        int x = p.xPos;
        int y = p.yPos;
        wG.world[x][y] = styleOfPlayer;
    }
    /* return true if the player get in door */
    boolean moveToward(Character toward) {
        intiTowardMap();
        int xDiff = 0;
        int yDiff = 0;
        int[] towardDetail = towardMap.get(toward);
        try {
            xDiff = towardDetail[0];
            yDiff = towardDetail[1];
        } catch (RuntimeException e) {
            e.getStackTrace();
        }
        Position newPosition = p.moveTo(xDiff, yDiff);
        Position oldPosition = p;
        int type = typeMatrix[newPosition.xPos][newPosition.yPos];
        if (newPosition.equals(wG.positionOfDoor) && wG.numOfOpenedDoor == 1) {
            return true;
        } else if (type == 2) {
            if (newPosition.equals(wG.positionOfKey)) {
                wG.openTheDoor();
            }
            p = newPosition;
            drawPlayer();
            wG.backAPoint(oldPosition);
        }
        return false;
    }
    void changePlayerStyle(TETile newStyle) {
        this.styleOfPlayer = newStyle;
        drawPlayer();
    }
}

