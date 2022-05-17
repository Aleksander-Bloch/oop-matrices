package pl.edu.mimuw.matrix;

import pl.edu.mimuw.operation.Operation;

public abstract class LineMatrix extends DoubleMatrix {
    // Abstract representation of matrices that can be compressed onto one dimension.
    protected final double[] line;

    protected LineMatrix(Shape shape, double[] line) {
        super(shape);
        this.line = line;
    }

    protected double[] scalarLineOperation(Operation operation, double scalar) {
        double[] resultLine = new double[line.length];
        for (int i = 0; i < line.length; i++) {
            resultLine[i] = operation.executeOperation(line[i], scalar);
        }
        return resultLine;
    }

    protected double[] lineOperation(Operation operation, double[] other) {
        double[] resultLine = new double[line.length];
        for (int i = 0; i < line.length; i++) {
            resultLine[i] = operation.executeOperation(line[i], other[i]);
        }
        return resultLine;
    }
}
