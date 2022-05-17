package pl.edu.mimuw.matrix;

import pl.edu.mimuw.operation.Multiply;

import java.util.Arrays;

public class ColumnMatrix extends LineMatrix {

    protected ColumnMatrix(Shape shape, double[] line) {
        super(shape, line);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        if (scalar == 0) {
            return new ZeroMatrix(shape);
        }
        return new ColumnMatrix(shape, this.scalarLineOperation(new Multiply(), scalar));
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return line[row];
    }

    @Override
    public double[][] data() {
        double[][] resultData = new double[shape.rows][shape.columns];
        for (int i = 0; i < shape.rows; i++) {
            Arrays.fill(resultData[i], line[i]);
        }
        return resultData;
    }

    @Override
    public double normOne() {
        double absSum = 0;
        for (double value : line) {
            absSum += Math.abs(value);
        }
        return absSum;
    }

    @Override
    public double normInfinity() {
        double maxAbsValue = 0;
        for (double value : line) {
            maxAbsValue = Math.max(maxAbsValue, Math.abs(value));
        }
        return maxAbsValue * shape.columns;
    }

    @Override
    public double frobeniusNorm() {
        double squareSum = 0;
        for (double value : line) {
            squareSum += shape.columns * Math.abs(value) * Math.abs(value);
        }
        return Math.sqrt(squareSum);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("COLUMN MATRIX\n");
        sb.append(shape);
        if (shape.columns >= 3) {
            for (double ColValue : line) {
                sb.append(ColValue).append(" ... ").append(ColValue);
                sb.append("\n");
            }
        } else {
            for (double ColValue : line) {
                sb.append((ColValue + " ").repeat(shape.columns));
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
