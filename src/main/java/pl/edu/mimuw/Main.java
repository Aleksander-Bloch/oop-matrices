package pl.edu.mimuw;

import pl.edu.mimuw.matrix.DoubleMatrixFactory;
import pl.edu.mimuw.matrix.IDoubleMatrix;
import pl.edu.mimuw.matrix.SparseMatrix;

import static pl.edu.mimuw.matrix.MatrixCellValue.cell;
import static pl.edu.mimuw.matrix.Shape.matrix;

public class Main {

  public static void main(String[] args) {
    // Tu trzeba wpisać kod testujący toString dla poszczególnych macierzy i wyników operacji
    final var l = DoubleMatrixFactory.sparse(
            matrix(1_000_000, 1_000_000_000),
            cell(0, 0, 42),
            cell(767, 123_123, 24),
            cell(999_999, 999_999_999, 66)
    );
    final var r = DoubleMatrixFactory.sparse(
            matrix(1_000_000, 1_000_000_000),
            cell(0, 0, 24),
            cell(767, 123_123, 42)
    );
  }
}
