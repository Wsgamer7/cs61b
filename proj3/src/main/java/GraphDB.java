import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    private Map<Long, Node> nodeMap;
    private Map<Long, Set<Long>> adjMap;
    private Map<Long, Way> wayMap;

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        nodeMap = new HashMap<>();
        adjMap = new HashMap<>();
        wayMap = new HashMap<>();
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }
    public void addNode(Node node) {
        nodeMap.put(node.nodeId, node);
    }
    public void addWay(Way way) {
        List<Long> nodeListInWay = way.nodeList;
        long wayId = way.wayId;
        wayMap.put(wayId, way);

        // add wayId for node in the way
        for (long nodeId : nodeListInWay) {
            Node node = getNode(nodeId);
            if (node == null) {
                continue;
            }
            node.addWay(way.wayId);
        }

        //copy connection in the way to the graphDB
        for (int i = 0; i < nodeListInWay.size() - 1; i++) {
            long node0 = nodeListInWay.get(i);
            long node1 = nodeListInWay.get(i + 1);
            connect2Node(node0, node1);
        }
    }
    private void connect2Node(long node0, long node1) {
        if (adjMap.get(node0) == null) {
            adjMap.put(node0, new HashSet<>());
        }
        if (adjMap.get(node1) == null) {
            adjMap.put(node1, new HashSet<>());
        }
        adjMap.get(node0).add(node1);
        adjMap.get(node1).add(node0);
    }
    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // TODO: Your code here.
        Set<Long> nodeIdSet = nodeIdSetCurrent();
        for (Long nodeId : nodeIdSet){
            if (adjacent(nodeId) == null) {
                nodeMap.remove(nodeId);
            }
        }
        //maybe need to remove nodeId in adjMap that is no exist in nodeList
    }
     Set<Long> nodeIdSetCurrent() {
        Set<Long> nodeIdSet = new HashSet<>();
        for (long nodeId : vertices()) {
            nodeIdSet.add(nodeId);
        }
        return nodeIdSet;
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return nodeMap.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param nodeId The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long nodeId) {
        return adjMap.get(nodeId);
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        long closestId = 0;
        double closestDis = Double.MAX_VALUE;
        for (long nodeId : vertices()) {
            double nowDis = distance(lon, lat, lon(nodeId), lat(nodeId));
            if (nowDis < closestDis) {
                closestDis = nowDis;
                closestId = nodeId;
            }
        }
        return closestId;
    }
    private Node getNode(long nodeId) {
        return nodeMap.get(nodeId);
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return getNode(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return getNode(v).lat;
    }
    static class Way {
        Long wayId;
        String name;
        String highway;
        String maxspeed;
        List<Long> nodeList = new ArrayList<>();
        public Way (Long wayId) {
            this.wayId = wayId;
        }
        public void addNode(Long nodeId) {
            nodeList.add(nodeId);
        }
    }
    static class Node {
        long nodeId;
        double lat;
        double lon;
        String name;
        private List<Long> wayList;
        public Node (Long nodeId, double lat, double lon) {
            this.nodeId = nodeId;
            this.lat = lat;
            this.lon = lon;
        }
        public void addName(String name) {
            this.name = name;
        }
        public void addWay(Long wayId) {
            if (wayList == null) {
                wayList = new ArrayList<>();
            }
            wayList.add(wayId);
        }
        public List<Long> getWayList() {
            return wayList;
        }
    }
}
