package byog.Core;
import byog.TileEngine.TETile;
import static byog.TileEngine.Tileset.*;

import java.util.Map;

public class StyleSet {
    /* indexOfType:
        0 : nothing
        1 : wall
        2 : floor
        3 : lockedDoor
        4 : openedDoor
     */
    final static TETile[] defaultStyle = new TETile[] {NOTHING, WALL, FLOOR, LOCKED_DOOR, UNLOCKED_DOOR};
    final static TETile[] forest = new TETile[] {NOTHING, MOUNTAIN, GRASS, LOCKED_DOOR, UNLOCKED_DOOR};
}
