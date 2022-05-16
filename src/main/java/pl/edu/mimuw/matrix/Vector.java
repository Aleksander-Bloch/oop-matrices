package pl.edu.mimuw.matrix;

public class Vector extends ColumnMatrix {

    protected Vector(Shape shape, double[] line) {
        super(shape, line);
    }

    @Override
    public double normOne() {
        double absSum = 0.0;
        for (double value : line) {
            absSum += Math.abs(value);
        }
        return absSum;
    }

    @Override
    public double normInfinity() {
        double maxAbsValue = 0.0;
        for (double value : line) {
            maxAbsValue = Math.max(maxAbsValue, Math.abs(value));
        }
        return maxAbsValue;
    }
}
