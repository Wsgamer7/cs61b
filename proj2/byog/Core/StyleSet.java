package byog.Core;
import byog.TileEngine.TETile;
import static byog.TileEngine.Tileset.*;

import java.util.HashMap;
import java.util.Map;

public class StyleSet {
    /* indexOfType:
        0 : nothing
        1 : wall
        2 : floor
        3 : lockedDoor
        4 : openedDoor
     */
    private static final TETile[] fixStyle = new TETile[] {LOCKED_DOOR, UNLOCKED_DOOR};
    private static final Map<String, TETile[]> styleMap = new HashMap<>();
    static TETile[] getAStyle(String name) {
        intiStyle();
        return styleMap.get(name);
    }
    private static void intiStyle() {
        TETile[] defaultS = new TETile[] {NOTHING, WALL, FLOOR};
        riseAStyle("default", defaultS);
    }
    private static void riseAStyle(String name, TETile[] specialStyle) {
        TETile[] totalStyle = new TETile[fixStyle.length + specialStyle.length];
        System.arraycopy(specialStyle, 0, totalStyle, 0, specialStyle.length);
        System.arraycopy(fixStyle, 0, totalStyle, specialStyle.length, fixStyle.length);
        styleMap.put(name, totalStyle);
    }
}
