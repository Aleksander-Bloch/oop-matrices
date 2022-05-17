package pl.edu.mimuw.matrix;

public class FullMatrix extends DoubleMatrix {
    private final double[][] data;

    public FullMatrix(double[][] data, Shape shape) {
        super(shape);
        this.data = data;
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return data[row][column];
    }

    @Override
    public double[][] data() {
        return data;
    }

    // Calculates maximum absolute column sum of the matrix.
    @Override
    public double normOne() {
        double[][] data = this.data();
        double maxAbsColSum = 0.0;

        for (int i = 0; i < shape.columns; i++) {
            double absColSum = 0.0;
            for (int j = 0; j < shape.rows; j++) {
                absColSum += Math.abs(data[j][i]);
            }
            maxAbsColSum = Math.max(maxAbsColSum, absColSum);
        }

        return maxAbsColSum;
    }

    // Calculates maximum absolute row sum of the matrix.
    @Override
    public double normInfinity() {
        double maxAbsRowSum = 0.0;

        for (int i = 0; i < shape.rows; i++) {
            double absRowSum = 0.0;
            for (int j = 0; j < shape.columns; j++) {
                absRowSum += Math.abs(data[i][j]);
            }
            maxAbsRowSum = Math.max(maxAbsRowSum, absRowSum);
        }

        return maxAbsRowSum;
    }

    // Square root of sum of squares of all elements in the matrix.
    @Override
    public double frobeniusNorm() {
        double sumOfSquares = 0.0;

        for (int i = 0; i < shape.rows; i++) {
            for (int j = 0; j < shape.columns; j++) {
                sumOfSquares += data[i][j] * data[i][j];
            }
        }

        return Math.sqrt(sumOfSquares);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FULL MATRIX\n");
        sb.append(shape);
        for (int i = 0; i < shape.rows; i++) {
            for (int j = 0; j < shape.columns; j++) {
                sb.append(data[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
