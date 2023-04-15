package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    protected int state;
    protected int period;
    public SawToothGenerator(int period) {
        this.period = period;
        this.state = 0;
    }
    @Override
    public double next() {
        state = state + 1;
        return normalize(state % period);
    }
    /* converts values between 0 and period - 1 to values between -1.0 and 1.0.*/
    protected double normalize(int x) {
        double xFormat = ((double) x) / (period - 1);
        return 2 * xFormat - 1;
    }
}
