package lab11.graphs;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return -1;
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    private int manhattan(int v) {
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        int vX = maze.toX(v);
        int vY = maze.toY(v);
        return Math.abs(targetX - vX) + Math.abs((targetY - vY));
    }
    private class Node {
        private final int v;
        private final int totalDisEstimate;
        private Node(int v) {
            this.v = v;
            totalDisEstimate = distTo[v] + manhattan(v);
        }
    }
    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            return node1.totalDisEstimate - node2.totalDisEstimate;
        }
    }
    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        PriorityQueue<Node> pQ = new PriorityQueue<>(new NodeComparator());
        Node firstNode = new Node(s);
        pQ.add(firstNode);
        while (!pQ.isEmpty()) {
            Node nodeToMark = pQ.remove();
            int v = nodeToMark.v;
            marked[v] = true;
            announce();
            if (v == t) {
                return;
            }
            for (int w : maze.adj(v)) {
                if (marked[w]) {
                    continue;
                }
                edgeTo[w] = v;
                distTo[w] = distTo[v] + 1;
                pQ.add(new Node(w));
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

