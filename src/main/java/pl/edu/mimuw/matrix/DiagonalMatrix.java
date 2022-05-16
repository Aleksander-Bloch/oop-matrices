package pl.edu.mimuw.matrix;

import pl.edu.mimuw.operation.Add;
import pl.edu.mimuw.operation.Multiply;

public class DiagonalMatrix extends SkewedMatrix {

    protected DiagonalMatrix(Shape shape, double[] line) {
        super(shape, line);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        if (scalar == 0) {
            return new ZeroMatrix(shape);
        }
        return new DiagonalMatrix(shape, this.scalarLineOperation(new Multiply(), scalar));
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        assert shape.equals(other.shape());
        if (other instanceof DiagonalMatrix) {
            return new DiagonalMatrix(shape, this.lineOperation(new Add(), ((DiagonalMatrix) other).line));
        }
        return super.plus(other);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return (row == column) ? line[row] : 0;
    }

    @Override
    public double[][] data() {
        double[][] resultData = new double[shape.rows][shape.columns];
        for (int i = 0; i < shape.rows; i++) {
            resultData[i][i] = line[i];
        }
        return resultData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < shape.rows; i++) {
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
