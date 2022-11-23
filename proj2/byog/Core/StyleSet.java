package byog.Core;
import byog.TileEngine.TETile;
import static byog.TileEngine.Tileset.*;

import java.util.Map;

public class StyleSet {
    /* indexOfType:
        0 : nothing
        1 : wall
        2 : floor
     */
    final static TETile[] defaultStyle = new TETile[] {NOTHING, WALL, FLOOR};
    final static TETile[] forest = new TETile[] {NOTHING, MOUNTAIN, GRASS};
}
