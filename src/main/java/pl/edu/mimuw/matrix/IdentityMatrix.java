package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class IdentityMatrix extends DoubleMatrix {

    protected IdentityMatrix(Shape shape) {
        super(shape);
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        shape.assertValidTimes(other.shape());
        return other;
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        if (scalar == 0) {
            return new ZeroMatrix(shape);
        }
        double[] line = new double[shape.rows];
        Arrays.fill(line, scalar);
        return new DiagonalMatrix(shape, line);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return (row == column) ? 1 : 0;
    }

    @Override
    public double[][] data() {
        double[][] resultData = new double[shape.rows][shape.columns];
        for (int i = 0; i < shape.rows; i++) {
            resultData[i][i] = 1;
        }
        return resultData;
    }

    @Override
    public double normOne() {
        return 1;
    }

    @Override
    public double normInfinity() {
        return 1;
    }

    @Override
    public double frobeniusNorm() {
        return Math.sqrt(shape.rows);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IDENTITY MATRIX\n");
        sb.append(shape);
        for (int i = 0; i < shape.rows; i++) {
            if (i >= 3) {
                sb.append("0 ... 0 ");
            } else {
                sb.append("0 ".repeat(i));
            }
            sb.append("1 ");
            if (shape.rows - 1 - i >= 3) {
                sb.append("0 ... 0 ");
            } else {
                sb.append("0 ".repeat(shape.rows - 1 - i));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
