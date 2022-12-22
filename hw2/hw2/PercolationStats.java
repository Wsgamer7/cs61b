package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private final int N;
    private final int T;
    private final double[] allThresholds;
    /* constructor*/
    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.N = N;
        this.T = T;
        allThresholds = new double[T];
        for (int i = 0; i < T; i++) {
            allThresholds[i] = getTs1Time(pf);
        }
    }
    /* get a estimation of threshold */
    private double getTs1Time(PercolationFactory pf) {
        Percolation percolation = pf.make(N);
        boolean isPercolated = false;
        while (!isPercolated) {
            int xRandom = StdRandom.uniform(N);
            int yRandom = StdRandom.uniform(N);
            percolation.open(xRandom, yRandom);
            isPercolated = percolation.percolates();
        }
        return (double) percolation.numberOfOpenSites() / (N * N);
    }
    /* get the mean of allThreshold*/
    public double mean() {
        return StdStats.mean(allThresholds);
    }
    /* get the standard deviation of allThreshold*/
    public double stddev() {
        if (T == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(allThresholds);
    }
    /* low endpoint of 95% confidence interval*/
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    /* high endpoint of 95% confidence interval*/
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
