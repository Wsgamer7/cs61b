package hw4.puzzle;

import java.util.ArrayList;

public class Board implements WorldState {
    private final int[][] tiles;
    private final int size;
    private int[][] allIndexOfNum;
    /* Constructs a board from an N-by-N array of tiles */
    public Board(int[][] tilesPassed) {
        this.tiles = copyOfTiles(tilesPassed);
        size = tiles[0].length;
        getAllIndex();
    }
    private void getAllIndex() {
        allIndexOfNum = new int[size * size][2];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                allIndexOfNum[tileAt(i, j)] = new int[] {i, j};
            }
        }
    }
    /* Returns value of tile at row i, column j (or 0 if blank) */
    public int tileAt(int i, int j) {
        if (!validIndex(new int[]{i, j})) {
            throw new java.lang.IndexOutOfBoundsException("a bad index");
        }
        return tiles[i][j];
    }
    private int tileAt(int[] index) {
        return tileAt(index[0], index[1]);
    }
    public int size() {
        return size;
    }
    private int[] numIndexOfGoal(int n) {
        validNumber(n);
        if (n == 0) {
            return new int[] {size - 1, size - 1};
        }
        return new int[] {(n - 1) / size, (n - 1) % size};
    }
    private void validNumber(int n) {
        if (n < 0 || n > size * size - 1) {
            throw new IllegalArgumentException("the number passed is out of edge");
        }
    }
    private int[] blankIndex() {
        return allIndexOfNum[0];
    }
    private int[][] copyOfTiles(int[][] tilesPassed) {
        int sizeOfTile = tilesPassed[0].length;
        int[][] copied = new int[sizeOfTile][sizeOfTile];
        for (int i = 0; i < sizeOfTile; i++) {
            System.arraycopy(tilesPassed[i], 0, copied[i], 0, sizeOfTile);
        }
        return copied;
    }
    private Board swap(int[] index1, int[] index2) {
        int[][] tilesCopied = copyOfTiles(tiles);
        int temp = tileAt(index1);
        tilesCopied[index1[0]][index1[1]] = tileAt(index2);
        tilesCopied[index2[0]][index2[1]] = temp;
        return new Board(tilesCopied);
    }
    private boolean validIndex(int[] index) {
        boolean validInX = index[0] >= 0 && index[0] < size();
        boolean validInY = index[1] >= 0 && index[1] < size();
        return validInX && validInY;
    }
    private Iterable<int[]> getNeighborsIndex(int[] index) {
        ArrayList<int[]> neighborsIndex = new ArrayList<>();
        neighborsIndex.add(new int[]{index[0] - 1, index[1]});
        neighborsIndex.add(new int[]{index[0] + 1, index[1]});
        neighborsIndex.add(new int[]{index[0], index[1] - 1});
        neighborsIndex.add(new int[]{index[0], index[1] + 1});
        neighborsIndex.removeIf(neighborId -> !validIndex(neighborId));
        return neighborsIndex;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /* Returns the neighbors of the current board */
    @Override
    public Iterable<WorldState> neighbors() {
        ArrayList<WorldState> allNeighbors = new ArrayList<>();
        Iterable<int[]> neighborsIndex = getNeighborsIndex(blankIndex());
        for (int[] index : neighborsIndex) {
            Board theNeighbors = swap(blankIndex(), index);
//            theNeighbors.entryIndexOfBlankTile(index);
            allNeighbors.add(theNeighbors);
        }
        return allNeighbors;
    }

    private int manhattanOf2Index(int[] index0, int[] index1) {
        return Math.abs(index1[1] - index0[1]) + Math.abs(index1[0] - index0[0]);
    }
    public int hamming() {
        int numOfDiff = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (i + j == 2 * size() - 2) {
                    continue;
                }
                if (tileAt(i, j) != i * size + j + 1) {
                    numOfDiff += 1;
                }
            }
        }
        return numOfDiff;
    }
    public int manhattan() {
        int sumOfManhattan = 0;
        for (int n = 1; n < size * size; n++) {
            sumOfManhattan += manhattanOf2Index(allIndexOfNum[n], numIndexOfGoal(n));
        }
        return sumOfManhattan;
    }
    @Override
    public int hashCode() {
        return java.util.Arrays.deepHashCode(tiles);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Board other = (Board) obj;
        if (size != other.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (tileAt(i, j) != other.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
