package pl.edu.mimuw;

import static pl.edu.mimuw.matrix.DoubleMatrixFactory.*;
import static pl.edu.mimuw.matrix.MatrixCellValue.cell;
import static pl.edu.mimuw.matrix.Shape.matrix;

// Built using IntelliJ IDE and Java 17.
public class Main {

  public static void main(String[] args) {
    final var sparseMatrix = sparse(
            matrix(10, 10),
            cell(0, 0, 1),
            cell(5, 5, 2),
            cell(8, 9, 3)
    );
    final var fullMatrix = full(new double[][]{
            new double[]{1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
            new double[]{1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            new double[]{1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            new double[]{1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            new double[]{1, 1, 1, 1, 0, 0, 1, 0, 0, 1},
            new double[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            new double[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            new double[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            new double[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            new double[]{1, 0, 0, 0, 0, 0, 1, 1, 1, 1},
    });
    final var identityMatrix = identity(10);
    final var diagonalMatrix = diagonal(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    final var antiDiagonalMatrix = antiDiagonal(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    final var vectorMatrix = vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    final var zeroMatrix = zero(matrix(10, 10));
    final var constantMatrix = constant(matrix(10, 10), 1);
    final var rowMatrix = row(10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    final var columnMatrix = column(10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    System.out.println(sparseMatrix);
    System.out.println(fullMatrix);
    System.out.println(identityMatrix);
    System.out.println(diagonalMatrix);
    System.out.println(antiDiagonalMatrix);
    System.out.println(vectorMatrix);
    System.out.println(zeroMatrix);
    System.out.println(constantMatrix);
    System.out.println(rowMatrix);
    System.out.println(columnMatrix);
  }
}
