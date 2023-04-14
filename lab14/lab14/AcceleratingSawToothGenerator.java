package lab14;

public class AcceleratingSawToothGenerator extends SawToothGenerator {
    private final double factor;
    private int stateFormat;
    public AcceleratingSawToothGenerator(int period, double factor) {
        super(period);
        this.factor = factor;
        stateFormat = 1;
    }
    @Override
    public double next() {
        state += 1;
        double next = normalize(stateFormat);
        if (stateFormat == period - 1) {
            stateFormat = 0;
            period = (int) Math.floor(period * factor);
        } else {
            stateFormat += 1;
        }
        return next;
    }
}
