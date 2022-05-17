package pl.edu.mimuw.matrix;

import java.util.ArrayList;
import java.util.List;

public class DoubleMatrixFactory {

  private DoubleMatrixFactory() {
  }

  public static IDoubleMatrix sparse(Shape shape, MatrixCellValue... values){
    assert values.length > 0;
    List<MatrixCellValue> cells = new ArrayList<>();
    for (MatrixCellValue value : values) {
      shape.assertInShape(value.row, value.column);
      cells.add(value);
    }
    MatrixCellValue.CellComparatorRowFirst comparator = new MatrixCellValue.CellComparatorRowFirst();
    cells.sort(comparator);
    return new SparseMatrix(shape, cells);
  }

  public static IDoubleMatrix full(double[][] values) {
    assert values != null;
    assert values.length > 0;
    for (int i = 1; i < values.length; i++) {
      assert values[i].length == values[0].length;
    }
    Shape shape = Shape.matrix(values.length, values[0].length);
    return new FullMatrix(values, shape);
  }

  public static IDoubleMatrix identity(int size) {
    assert size > 0;
    return new IdentityMatrix(Shape.matrix(size, size));
  }

  public static IDoubleMatrix diagonal(double... diagonalValues) {
    int size = diagonalValues.length;
    assert size > 0;
    return new DiagonalMatrix(Shape.matrix(size, size), diagonalValues);
  }

  public static IDoubleMatrix antiDiagonal(double... antiDiagonalValues) {
    int size = antiDiagonalValues.length;
    assert size > 0;
    return new AntiDiagonalMatrix(Shape.matrix(size, size), antiDiagonalValues);
  }

  public static IDoubleMatrix vector(double... values){
    int size = values.length;
    assert size > 0;
    return new Vector(Shape.vector(size), values);
  }

  public static IDoubleMatrix zero(Shape shape) {
    return new ZeroMatrix(shape);
  }

  public static IDoubleMatrix constant(Shape shape, double value) {
    return new ConstantMatrix(value, shape);
  }

  public static IDoubleMatrix row(int rows, double... rowValues) {
    assert rows > 0;
    assert rowValues.length > 0;
    return new RowMatrix(Shape.matrix(rows, rowValues.length), rowValues);
  }

  public static IDoubleMatrix column(int columns, double... columnValues) {
    assert columns > 0;
    assert columnValues.length > 0;
    return new ColumnMatrix(Shape.matrix(columnValues.length, columns), columnValues);
  }
}
