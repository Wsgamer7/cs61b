package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.Stack;

public class Solver {
    private final SearchNode intiNode;
    private final SearchNode lastNode;
    public Solver(WorldState intiState) {
        intiNode = new SearchNode(intiState, 0, null);
        lastNode = bestFirstSearch();
    }

    public int moves() {
        return lastNode.numOfMoved();
    }

    public Iterable<WorldState> solution() {
        SearchNode pointer = lastNode;
        Stack<WorldState> allStates = new Stack<>();
        allStates.add(pointer.worldState());

        while (!pointer.isIntiNode()) {
            pointer = pointer.getPreSearchNode();
            allStates.add(pointer.worldState());
        }
        return allStates;
    }
    private SearchNode bestFirstSearch() {
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(intiNode);
        while (true) {
            SearchNode nearestNode = minPQ.delMin();
            if (nearestNode.worldState().isGoal()) {
                return nearestNode;
            }
            insertNeighbours(nearestNode, minPQ);
        }
    }
    private void insertNeighbours(SearchNode searchNode, MinPQ<SearchNode> minPQ) {
         Iterable<WorldState> neighbours= searchNode.worldState().neighbors();
         for(WorldState neighbour : neighbours) {
             if (neighbour.equals(searchNode.getPreSearchNode().worldState())) {
                 continue;
             }
             SearchNode neighbourNode = new SearchNode(neighbour, searchNode.numOfMoved() + 1, searchNode);
             minPQ.insert(neighbourNode);
         }
    }
}
