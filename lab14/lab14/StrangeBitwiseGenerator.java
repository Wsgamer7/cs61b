package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    protected int state;
    protected int period;
    private int shift1;
    private int shift2;
    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        this.state = 0;
        this.shift1 = 3;
        this.shift2 = 5;
    }
    public StrangeBitwiseGenerator(int period, int shift1, int shift2) {
        this.period = period;
        this.state = 0;
        this.shift1 = shift1;
        this.shift2 = shift2;
    }
    @Override
    public double next() {
        state = state + 1;
        int weirdState = state & (state >>> shift1) & (state >>> shift2) % period;
        return normalize(weirdState);
    }
    /* converts values between 0 and period - 1 to values between -1.0 and 1.0.*/
    protected double normalize(int x) {
        double xFormat = ((double) x) / (period - 1);
        return 2 * xFormat - 1;
//        return Math.cos(xFormat * 2 * Math.PI);
    }
}
