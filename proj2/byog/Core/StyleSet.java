package byog.Core;
import byog.TileEngine.TETile;
import static byog.TileEngine.Tileset.*;
import java.io.*;

import java.util.HashMap;
import java.util.Map;

public class StyleSet implements Serializable {
    /* indexOfType:
        0 : nothing
        1 : wall
        2 : floor
        3 : lockedDoor
        4 : openedDoor
     */
    private static final TETile[] FIXSTYLE = new TETile[] {LOCKED_DOOR, UNLOCKED_DOOR};
    private static final Map<String, TETile[]> STYLEMAP = new HashMap<>();
    static TETile[] getAStyle(String name) {
        intiStyle();
        return STYLEMAP.get(name);
    }
    private static void intiStyle() {
        TETile[] defaultS = new TETile[] {NOTHING, WALL, FLOOR};
        riseAStyle("default", defaultS);
    }
    private static void riseAStyle(String name, TETile[] specialStyle) {
        TETile[] totalStyle = new TETile[FIXSTYLE.length + specialStyle.length];
        System.arraycopy(specialStyle, 0, totalStyle, 0, specialStyle.length);
        System.arraycopy(FIXSTYLE, 0, totalStyle, specialStyle.length, FIXSTYLE.length);
        STYLEMAP.put(name, totalStyle);
    }
}
