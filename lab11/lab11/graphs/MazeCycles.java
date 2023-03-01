package lab11.graphs;


import edu.princeton.cs.algs4.Stack;


/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private final int[] edgeCopyTo;
    public MazeCycles(Maze m) {
        super(m);
        edgeCopyTo = new int[distTo.length];
    }
    private void copyCycleEdges(int cycleEnd) {
        int timePassCycleEnd = 0;
        int vTo = cycleEnd;
        int vFrom = edgeCopyTo[vTo];
        while (timePassCycleEnd < 2) {
            edgeTo[vTo] = vFrom;
            announce();
            vTo = vFrom;
            vFrom = edgeCopyTo[vTo];
            if (vTo == cycleEnd || vFrom == cycleEnd) {
                timePassCycleEnd += 1;
            }
        }
    }
    @Override
    public void solve() {
        // TODO: Your code here!
        int s = 0;
        distTo[s] = 0;
        Stack<Integer> fringe = new Stack<>();
        fringe.push(s);

        while (!fringe.isEmpty()) {
            int v = fringe.pop();
            marked[v] = true;
            announce();

            for (int w : maze.adj(v)) {
                if (w == edgeCopyTo[v]) {
                    continue;
                }
                edgeCopyTo[w] = v;
                if (marked[w]) {
                    copyCycleEdges(w);
                    announce();
                    return;
                }
                distTo[w] = distTo[v] + 1;
                fringe.push(w);
            }
        }
    }

    // Helper methods go here
}

