package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class ConstantMatrix extends DoubleMatrix {
    protected final double constant;

    protected ConstantMatrix(double constant, Shape shape) {
        super(shape);
        this.constant = constant;
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        Shape otherShape = other.shape();
        shape.assertValidTimes(otherShape);
        if (other instanceof ConstantMatrix) {
            Shape resultShape = Shape.matrix(shape.rows, otherShape.columns);
            double resultConstant = ((ConstantMatrix) other).constant * constant * shape.columns;
            return new ConstantMatrix(resultConstant, resultShape);
        }
        return super.times(other);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        if (scalar == 0) {
            return new ZeroMatrix(shape);
        }
        double resultConstant = constant * scalar;
        return new ConstantMatrix(resultConstant, shape);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return constant;
    }

    @Override
    public double[][] data() {
        double[][] data = new double[shape.rows][shape.columns];
        for (double[] row: data) {
            Arrays.fill(row, constant);
        }
        return data;
    }

    @Override
    public double normOne() {
        return shape.rows * Math.abs(constant);
    }

    @Override
    public double normInfinity() {
        return shape.columns * Math.abs(constant);
    }

    @Override
    public double frobeniusNorm() {
        return Math.sqrt(shape.rows * shape.columns * constant * constant);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (shape.columns >= 3) {
            sb.append((constant + " ... " + constant + " \n").repeat(shape.rows));
        } else {
            sb.append((constant + " \n").repeat(shape.rows));
        }
        return sb.toString();
    }
}
