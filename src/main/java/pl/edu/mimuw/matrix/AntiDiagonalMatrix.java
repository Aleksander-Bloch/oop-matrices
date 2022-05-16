package pl.edu.mimuw.matrix;

import pl.edu.mimuw.operation.Multiply;

public class AntiDiagonalMatrix extends SkewedMatrix {

    protected AntiDiagonalMatrix(Shape shape, double[] line) {
        super(shape, line);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        if (scalar == 0) {
            return new ZeroMatrix(shape);
        }
        return new AntiDiagonalMatrix(shape, this.scalarLineOperation(new Multiply(), scalar));
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return 0;
    }

    @Override
    public double[][] data() {
        double[][] resultData = new double[shape.rows][shape.columns];
        for (int i = 0; i < shape.rows; i++) {
            resultData[i][shape.rows - 1 - i] = line[shape.rows - 1 - i];
        }
        return resultData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = shape.rows - 1; i >= 0; i--) {
            if (i >= 3) {
                sb.append("0 ... 0 ");
            } else {
                sb.append("0 ".repeat(i));
            }
            if (line[i] != 0) {
                sb.append(line[i]).append(" ");
                if (shape.rows - 1 - i >= 3) {
                    sb.append("0 ... 0 ");
                } else {
                    sb.append("0 ".repeat(i));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
