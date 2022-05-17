package pl.edu.mimuw.matrix;

import pl.edu.mimuw.operation.Multiply;

import java.util.Arrays;

public class RowMatrix extends LineMatrix {

    protected RowMatrix(Shape shape, double[] line) {
        super(shape, line);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        if (scalar == 0) {
            return new ZeroMatrix(shape);
        }
        return new RowMatrix(shape, this.scalarLineOperation(new Multiply(), scalar));
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return line[column];
    }

    @Override
    public double[][] data() {
        double[][] resultData = new double[shape.rows][shape.columns];
        for (int i = 0; i < shape.rows; i++) {
            resultData[i] = Arrays.copyOf(line, shape.columns);
        }
        return resultData;
    }

    @Override
    public double normOne() {
        double maxAbsValue = 0;
        for (double value : line) {
            maxAbsValue = Math.max(maxAbsValue, Math.abs(value));
        }
        return maxAbsValue * shape.rows;
    }

    @Override
    public double normInfinity() {
        double absSum = 0;
        for (double value : line) {
            absSum += Math.abs(value);
        }
        return absSum;
    }

    @Override
    public double frobeniusNorm() {
        double squareSum = 0;
        for (double value : line) {
            squareSum += shape.rows * Math.abs(value) * Math.abs(value);
        }
        return Math.sqrt(squareSum);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ROW MATRIX\n");
        sb.append(shape);
        for (int i = 0; i < shape.rows; i++) {
            for (double rowValue : line) {
                sb.append(rowValue).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
