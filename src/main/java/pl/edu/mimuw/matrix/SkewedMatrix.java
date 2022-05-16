package pl.edu.mimuw.matrix;

public abstract class SkewedMatrix extends LineMatrix {

    protected SkewedMatrix(Shape shape, double[] line) {
        super(shape, line);
    }

    @Override
    public double normOne() {
        double maxAbsValue = 0.0;
        for (double value : line) {
            maxAbsValue = Math.max(maxAbsValue, Math.abs(value));
        }
        return maxAbsValue;
    }

    @Override
    public double normInfinity() {
        return normOne();
    }

    @Override
    public double frobeniusNorm() {
        double sum = 0.0;
        for (double value : line) {
            sum += Math.abs(value) * Math.abs(value);
        }
        return Math.sqrt(sum);
    }
}
