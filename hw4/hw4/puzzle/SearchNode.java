package hw4.puzzle;

public class SearchNode implements Comparable<SearchNode>{
    private final WorldState worldState;
    private final int numOfMoved;
    private final int totalDistance;
    private final SearchNode preSearchNode;
    public SearchNode(WorldState worldState, int numOfMoved, SearchNode preSearchNode) {
        this.worldState = worldState;
        this.numOfMoved = numOfMoved;
        this.preSearchNode = preSearchNode;
        this.totalDistance = numOfMoved + worldState.estimatedDistanceToGoal();
    }

    public WorldState worldState() {
        return worldState;
    }
    public SearchNode getPreSearchNode() {
        return preSearchNode;
    }
    public int numOfMoved() {
        return numOfMoved;
    }
    public boolean isIntiNode() {
        return preSearchNode == null;
    }
    private int totalDistance() {
        return totalDistance;
    }
    @Override
    public int compareTo(SearchNode compared) {
        return totalDistance() - compared.totalDistance();
    }
}
