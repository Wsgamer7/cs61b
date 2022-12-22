package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;

public class Percolation {
    private final boolean[][] isOpen;
    private final int N;
    private int numberOfOpenSites;
    private final int numOfUpVirSite;
    private final int numOfDownVirSite;
    private final WeightedQuickUnionUF dSet;
    /* the constructor */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N is too small");
        }
        isOpen = new boolean[N][N];
        this.N = N;
        numberOfOpenSites = 0;
        dSet = new WeightedQuickUnionUF(N * N + 2);
        //union to virtual sites
        numOfUpVirSite = N * N;
        numOfDownVirSite = N * N + 1;
    }
    /* check out if (row, col) is in the grid */
    private void checkOut(int row, int col) {
        boolean goodInX = (row >= 0) && (row < N);
        boolean goodInY = (col >= 0) && (col < N);
        if (!(goodInX && goodInY)) {
            throw new java.lang.IndexOutOfBoundsException("(row, col) is outside");
        }
    }
    /*trans a pair to a number */
    private int xyToNum(int x, int y) {
        checkOut(x, y);
        return N * x + y;
    }
    /*open the site (row, col) if it is not open */
    public void open(int row, int col) {
        checkOut(row, col);
        if (isOpen(row, col)) {
            return;
        }
        isOpen[row][col] = true;
        numberOfOpenSites += 1;
        int thisSite1D = xyToNum(row, col);
        // conner case for first and last row
        if (row == 0) {
            dSet.union(thisSite1D, numOfUpVirSite);
        }
        if (row == N - 1) {
            dSet.union(thisSite1D, numOfDownVirSite);
        }

        ArrayList<int[]> sitesAround = sitesAround(row, col);
        for (int[] site : sitesAround) {
            if (isOpen(site[0], site[1])) {
                int aroundSite1D = xyToNum(site[0], site[1]);
                dSet.union(thisSite1D, aroundSite1D);
            }
        }
    }
    /*get sites around the site (row, col) */
    private ArrayList<int[]> sitesAround(int row, int col) {
        ArrayList<int[]> sitesList = new ArrayList<>();
        sitesList.add(new int[] {row, col - 1});
        sitesList.add(new int[] {row, col + 1});
        sitesList.add(new int[] {row - 1, col});
        sitesList.add(new int[] {row + 1, col});
        //remove the site out of grid
        ArrayList<int[]> toReturn = new ArrayList<>();
        for (int[] site : sitesList) {
            try {
                checkOut(site[0], site[1]);
            } catch (java.lang.IndexOutOfBoundsException e) {
                continue;
            }
            toReturn.add(site);
        }
        return toReturn;
    }
    /* is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        checkOut(row, col);
        return isOpen[row][col];
    }
    /* is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        checkOut(row, col);
        int numOfTheSite = xyToNum(row, col);
        boolean thisIsOpen = isOpen(row, col);
        return dSet.connected(numOfUpVirSite, numOfTheSite) && thisIsOpen;
    }
    /* number of open sites*/
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }
    /* return ture if the system percolate */
    public boolean percolates() {
        return dSet.connected(numOfUpVirSite, numOfDownVirSite);
    }
    public static void main(String[] args) {
    }
}
