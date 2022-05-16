package pl.edu.mimuw.matrix;

public class ZeroMatrix extends ConstantMatrix {
    protected ZeroMatrix(Shape shape) {
        super(0.0, shape);
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        Shape otherShape = other.shape();
        shape.assertValidTimes(otherShape);
        Shape resultShape = Shape.matrix(shape.rows, otherShape.columns);
        return new ZeroMatrix(resultShape);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        return this;
    }

    @Override
    public IDoubleMatrix plus(double scalar) {
        return new ConstantMatrix(scalar, shape);
    }

    @Override
    public IDoubleMatrix minus(double scalar) {
        return new ConstantMatrix(-scalar, shape);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return 0;
    }

    @Override
    public double[][] data() {
        return new double[shape.rows][shape.columns];
    }

    @Override
    public double normOne() {
        return 0;
    }

    @Override
    public double normInfinity() {
        return 0;
    }

    @Override
    public double frobeniusNorm() {
        return 0;
    }
}