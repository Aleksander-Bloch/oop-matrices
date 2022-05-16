package pl.edu.mimuw.matrix;

import java.util.Comparator;

public final class MatrixCellValue {

  public final int row;
  public final int column;
  public final double value;

  public MatrixCellValue(int row, int column, double value) {
    this.column = column;
    this.row = row;
    this.value = value;
  }

  @Override
  public String toString() {
    return "{" + value + " @[" + row + ", " + column + "]}";
  }

  public static MatrixCellValue cell(int row, int column, double value) {
    return new MatrixCellValue(row, column, value);
  }

  public static class CellComparatorRowFirst implements Comparator<MatrixCellValue> {
    @Override
    public int compare(MatrixCellValue o1, MatrixCellValue o2) {
      int rowCompare = Integer.compare(o1.row, o2.row);
      int columnCompare = Integer.compare(o1.column, o2.column);

      return (rowCompare == 0) ? columnCompare : rowCompare;
    }
  }

  public static class CellComparatorColumnFirst implements Comparator<MatrixCellValue> {
    @Override
    public int compare(MatrixCellValue o1, MatrixCellValue o2) {
      int columnCompare = Integer.compare(o1.column, o2.column);
      int rowCompare = Integer.compare(o1.row, o2.row);

      return (columnCompare == 0) ? rowCompare : columnCompare;
    }
  }
}
