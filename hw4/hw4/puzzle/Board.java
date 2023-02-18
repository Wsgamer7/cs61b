package hw4.puzzle;

import java.lang.module.FindException;
import java.util.ArrayList;

public class Board {
    private final int[][] tiles;
    private final int size;
    private int[] blankIndex;
    /* Constructs a board from an N-by-N array of tiles where tiles[i][j] = tile at row i, column j */
    public Board(int[][] tiles) {
        this.tiles = tiles;
        size = tiles[0].length;
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
    private int[] findBlankIndex() {
        if (blankIndex != null) {
            return blankIndex;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) == 0) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("inti Tiles is filled");
    }
    private void entryIndexOfBlankTile(int[] blankIndex) {
        this.blankIndex = blankIndex;
    }
    private int[][] copyOfTiles(){
        int[][] copied = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copied[i][j] = tileAt(i, j);
            }
        }
        return copied;
    }
    private Board swap(int[] index1, int[] index2) {
        int[][] tilesCopied = copyOfTiles();
        int temp = tileAt(index1);
        tilesCopied[index1[0]][index1[1]] =tileAt(index2);
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
    /* Returns the neighbors of the current board */
    public Iterable<Board> neighbors() {
        int[] blankIndex = findBlankIndex();
        ArrayList<Board> allNeighbors = new ArrayList<>();
        Iterable<int[]> neighborsIndex = getNeighborsIndex(findBlankIndex());
        for (int[] index : neighborsIndex) {
            Board theNeighbors = swap(findBlankIndex(), index);
            theNeighbors.entryIndexOfBlankTile(index);
            allNeighbors.add(theNeighbors);
        }
        return allNeighbors;
    }
    /** Returns the string representation of the board.
      * Uncomment this method. */
    /*public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }*/

}
