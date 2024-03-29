package pl.edu.mimuw.matrix;

import pl.edu.mimuw.operation.*;

public abstract class DoubleMatrix implements IDoubleMatrix {
    // Every matrix will have its dimensions as attribute.
    protected final Shape shape;

    protected DoubleMatrix(Shape shape) {
        this.shape = shape;
    }

    // Helper function for all scalar operations.
    // Used to prevent possible duplicates.
    private IDoubleMatrix scalarDataOperation(Operation operation, double scalar) {
        double[][] data = this.data();
        double[][] resultData = new double[shape.rows][shape.columns];

        for (int i = 0; i < shape.rows; i++) {
            for (int j = 0; j < shape.columns; j++) {
                resultData[i][j] = operation.executeOperation(data[i][j], scalar);
            }
        }

        return new FullMatrix(resultData, shape);
    }

    // Helper function for matrix operations: times, plus, minus.
    // Used to prevent possible duplicates.
    private IDoubleMatrix matrixOperation(Operation operation, IDoubleMatrix other) {
        assert shape.equals(other.shape());
        double[][] data = this.data();
        double[][] otherData = other.data();
        double[][] resultData = new double[shape.rows][shape.columns];

        for (int i = 0; i < shape.rows; i++) {
            for (int j = 0; j < shape.columns; j++) {
                resultData[i][j] = operation.executeOperation(data[i][j], otherData[i][j]);
            }
        }

        return new FullMatrix(resultData, shape);
    }

    // Standard O(n^3) matrix multiplication algorithm.
    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        Shape otherShape = other.shape();
        shape.assertValidTimes(otherShape);
        Shape resultShape = Shape.matrix(shape.rows, otherShape.columns);
        double[][] data = this.data();
        double[][] otherData = other.data();
        double[][] resultData = new double[resultShape.rows][resultShape.columns];

        for (int i = 0; i < resultShape.rows; i++) {
            for (int j = 0; j < resultShape.columns; j++) {
                for (int k = 0; k < shape.columns; k++) {
                    resultData[i][j] += data[i][k] * otherData[k][j];
                }
            }
        }

        return new FullMatrix(resultData, resultShape);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        if (scalar == 0) {
            return new ZeroMatrix(shape);
        }
        return scalarDataOperation(new Multiply(), scalar);
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        return matrixOperation(new Add(), other);
    }

    @Override
    public IDoubleMatrix plus(double scalar) {
        return scalarDataOperation(new Add(), scalar);
    }

    @Override
    public IDoubleMatrix minus(IDoubleMatrix other) {
        return matrixOperation(new Subtract(), other);
    }

    @Override
    public IDoubleMatrix minus(double scalar) {
        return scalarDataOperation(new Subtract(), scalar);
    }

    @Override
    public Shape shape() {
        return shape;
    }
}
