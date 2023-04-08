import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private final double lonDPP0;
    public Rasterer() {
        // YOUR CODE HERE
        this.lonDPP0 = lonDPP(MapServer.ROOT_ULLON, MapServer.ROOT_LRLON, MapServer.TILE_SIZE);
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        //extract args from param
        double ullon = params.get("ullon");
        double lrlon = params.get("lrlon");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        double width = params.get("w");
        results.put("query_success", true);

        //get good depth
        int depth = bestDepth(ullon, lrlon, width);
        results.put("depth", depth);

        //select what images we need
        int[] leftUpTile = whichTile(ullon, ullat, depth);
        int[] rightLowTile = whichTile(lrlon, lrlat, depth);
        String[][] render_grid = getRenderGrid(leftUpTile, rightLowTile, depth);
        results.put("render_grid", render_grid);

        //add ullon, ullat, lrlat, lrlon of raster
        results.putAll(rasterBounding(leftUpTile, rightLowTile, depth));
        return results;
    }
    private double deltaLonPerTile(int depth) {
        return (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / tilesNumInXOrY(depth);
    }
    private double deltaLatPerTile(int depth) {
        return (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / tilesNumInXOrY(depth);
    }
    // make x belong to [bound1, bound2]
    private static double inBounding(double x, double bound1, double bound2) {
        double min = Math.min(bound1, bound2);
        double max = Math.max(bound1, bound2);
        if (x < min) {
            return min;
        } else if (x > max) {
            return max;
        }
        return x;
    }
    private int[] whichTile(double lon, double lat, int depth) {
        lon = inBounding(lon, MapServer.ROOT_ULLON, MapServer.ROOT_LRLON - deltaLonPerTile(depth) / 2);
        lat = inBounding(lat, MapServer.ROOT_ULLAT, MapServer.ROOT_LRLAT - deltaLatPerTile(depth) / 2);
        int x = (int) Math.floor((lon - MapServer.ROOT_ULLON) / deltaLonPerTile(depth));
        int y = (int) Math.floor((lat - MapServer.ROOT_ULLAT) / deltaLatPerTile(depth));
        return new int[]{x, y};
    }
    private Map<String, Double> rasterBounding(int[] leftUpTile, int[] rightLow, int depth) {
        double deltaLon = deltaLonPerTile(depth);
        double deltaLat = deltaLatPerTile(depth);
        double raster_ul_lon = MapServer.ROOT_ULLON + deltaLon * leftUpTile[0];
        double raster_ul_lat = MapServer.ROOT_ULLAT + deltaLat * leftUpTile[1];
        double raster_lr_lon = MapServer.ROOT_ULLON + deltaLon * (rightLow[0]+ 1);
        //raster_lr_lon = inBounding(raster_lr_lon, MapServer.ROOT_ULLON, MapServer.ROOT_LRLON);
        double raster_lr_lat = MapServer.ROOT_ULLAT + deltaLat * (rightLow[1] + 1);
        Map<String, Double> bounding = new HashMap<>();
        bounding.put("raster_ul_lon", raster_ul_lon);
        bounding.put("raster_ul_lat", raster_ul_lat);
        bounding.put("raster_lr_lon", raster_lr_lon);
        bounding.put("raster_lr_lat", raster_lr_lat);
        return bounding;
    }

    private String[][] getRenderGrid(int[] leftUpTile, int[] rightLowTile, int depth) {
        // 0 stands for x-axis; 1 stands for y-axis
        int xNumGrid = rightLowTile[0] - leftUpTile[0] + 1;
        int yNumGrid = rightLowTile[1] - leftUpTile[1] + 1;
        String[][] renderGrid = new String[yNumGrid][xNumGrid];
        for (int y = leftUpTile[1]; y <= rightLowTile[1]; y++) {
            for (int x = leftUpTile[0]; x <= rightLowTile[0]; x++) {
                String fileName = "d" + depth + "_x" + x + "_y" + y + ".png";
                int xRenderGrid = x - leftUpTile[0];
                int yRenderGrid = y - leftUpTile[1];
                renderGrid[yRenderGrid][xRenderGrid] = fileName;
            }
        }
        return renderGrid;
    }
    private boolean inMap(double lon, double lat) {
        boolean inLon = (lon >= MapServer.ROOT_ULLON) && (lon <= MapServer.ROOT_LRLON);
        boolean inLat = (lat >= MapServer.ROOT_LRLON) && (lon <= MapServer.ROOT_ULLAT);
        return !inLat || !inLon;
    }
    private double lonDPP(double ullon, double lrlon, double width) {
        return (lrlon - ullon) / width;
    }
    private int tilesNumInXOrY(int depth) {
        return (int) Math.pow(2, depth);
    }
    private double lonDPP(int depth) {
        return lonDPP0 / tilesNumInXOrY(depth);
    }
    private int bestDepth(double ullon, double lrlon, double width) {
        double lonDPPQuery = lonDPP(ullon, lrlon, width);
        for (int depth = 0; depth < MapServer.MAX_DEPTH + 1; depth++) {
            double lonDPPDepth = lonDPP(depth);
            if (lonDPPDepth < lonDPPQuery) {
                return depth;
            }
        }
        return MapServer.MAX_DEPTH;
    }

}
