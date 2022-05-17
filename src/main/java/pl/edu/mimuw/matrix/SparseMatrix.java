package pl.edu.mimuw.matrix;

import pl.edu.mimuw.operation.*;
import java.util.ArrayList;
import java.util.List;

public class SparseMatrix extends DoubleMatrix {
    // It will be stored as a list of row, column, value triples.
    // The triples will be sorted by row first.
    private final List<MatrixCellValue> cells;

    protected SparseMatrix(Shape shape, List<MatrixCellValue> cells) {
        super(shape);
        this.cells = cells;
    }

    // Uses copy of list sorted by column first.
    // Sets row from the first list and column from copy to create value for result cell.
    // Finds dot product of the cells with the same value of column (first list) and row (copy).
    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        Shape otherShape = other.shape();
        shape.assertValidTimes(otherShape);
        if (other instanceof SparseMatrix) {
            // sorted by column first
            List<MatrixCellValue> cellsB = new ArrayList<>(((SparseMatrix) other).cells);
            cellsB.sort(new MatrixCellValue.CellComparatorColumnFirst());
            Shape resultShape = Shape.matrix(shape.rows, otherShape.columns);
            List<MatrixCellValue> resultCells = new ArrayList<>();

            // Letter A will refer to this matrix and B to other matrix.
            int indexA, indexB;
            for (indexA = 0; indexA < cells.size();) {
                int row = cells.get(indexA).row;
                for (indexB = 0; indexB < cellsB.size();) {
                    int column = cellsB.get(indexB).column;

                    double dotProduct = getDotProduct(cellsB, row, column, indexA, indexB);

                    if (dotProduct != 0) {
                        resultCells.add(MatrixCellValue.cell(row, column, dotProduct));
                    }

                    // Move to the next column of the cell in copy list.
                    while (indexB < cellsB.size() && cellsB.get(indexB).column == column) {
                        indexB++;
                    }
                }

                // Move to the next row of the cell in original list.
                while (indexA < cells.size() && cells.get(indexA).row == row) {
                    indexA++;
                }
            }
            return new SparseMatrix(resultShape, resultCells);
        }
        return super.times(other);
    }


    // Finds value of the result cell with corresponding row and column.
    // Index refers to starting index of cell in the list.
    private double getDotProduct(List<MatrixCellValue> cellsB, int row, int column, int indexA, int indexB) {
        double dotProduct = 0;
        while (indexA < cells.size() && cells.get(indexA).row == row &&
                indexB < cellsB.size() && cellsB.get(indexB).column == column) {

            if (cells.get(indexA).column < cellsB.get(indexB).row) {
                indexA++;
            } else if(cells.get(indexA).column > cellsB.get(indexB).row) {
                indexB++;
            } else {
                dotProduct += cells.get(indexA).value * cellsB.get(indexB).value;
                indexA++;
                indexB++;
            }
        }
        return dotProduct;
    }

    // Helper function that creates new list with values transformed by scalar operation.
    private IDoubleMatrix scalarSparseOperation(Operation operation, double scalar) {
        List<MatrixCellValue> resultCells = new ArrayList<>();
        for (MatrixCellValue cell : cells) {
            double resultValue = operation.executeOperation(cell.value, scalar);
            resultCells.add(MatrixCellValue.cell(cell.row, cell.column, resultValue));
        }
        return new SparseMatrix(shape, resultCells);
    }


    // Helper function that executes sparse matrix operation excluding times.
    // Merges two lists while looking for cancelling values.
    private IDoubleMatrix sparseOperation(Operation operation, IDoubleMatrix other) {
        List<MatrixCellValue> cellsB = ((SparseMatrix) other).cells;
        List<MatrixCellValue> resultCells = new ArrayList<>();
        MatrixCellValue.CellComparatorRowFirst comparator = new MatrixCellValue.CellComparatorRowFirst();
        int indexA = 0, indexB = 0;
        while (indexA < cells.size() && indexB < cellsB.size()) {
            MatrixCellValue cellA = cells.get(indexA);
            MatrixCellValue cellB = cellsB.get(indexB);
            int cellCompare = comparator.compare(cellA, cellB);
            if (cellCompare < 0) {
                resultCells.add(cellA);
                indexA++;
            } else if (cellCompare > 0) {
                resultCells.add(cellB);
                indexB++;
            } else {
                double resultValue = operation.executeOperation(cellA.value, cellB.value);
                if (resultValue != 0) {
                    resultCells.add(MatrixCellValue.cell(cellA.row, cellA.column, resultValue));
                }
                indexA++;
                indexB++;
            }
        }
        while (indexA < cells.size()) {
            resultCells.add(cells.get(indexA));
            indexA++;
        }
        while (indexB < cellsB.size()) {
            resultCells.add(cellsB.get(indexB));
            indexB++;
        }
        return new SparseMatrix(shape, resultCells);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        if (scalar == 0) {
            return new ZeroMatrix(shape);
        }
        return scalarSparseOperation(new Multiply(), scalar);
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        Shape otherShape = other.shape();
        assert shape.equals(otherShape);
        if (other instanceof SparseMatrix) {
            return sparseOperation(new Add(), other);
        }
        return super.plus(other);
    }

    @Override
    public IDoubleMatrix plus(double scalar) {
        if (scalar == 0) {
            return this;
        }
        return scalarSparseOperation(new Add(), scalar);
    }

    @Override
    public IDoubleMatrix minus(IDoubleMatrix other) {
        Shape otherShape = other.shape();
        assert shape.equals(otherShape);
        if (other instanceof SparseMatrix) {
            return sparseOperation(new Subtract(), other);
        }
        return super.minus(other);
    }

    @Override
    public IDoubleMatrix minus(double scalar) {
        if (scalar == 0) {
            return this;
        }
        return scalarSparseOperation(new Subtract(), scalar);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        for (MatrixCellValue cell : cells) {
            if (cell.row == row && cell.column == column) {
                return cell.value;
            }
        }
        return 0.0;
    }

    @Override
    public double[][] data() {
        double[][] resultData = new double[shape.rows][shape.columns];
        for (MatrixCellValue cell : cells) {
            resultData[cell.row][cell.column] = cell.value;
        }
        return resultData;
    }

    @Override
    public double normOne() {
        // Cells are sorted increasingly by column first.
        List<MatrixCellValue> cellsByCol = new ArrayList<>(cells);
        cellsByCol.sort(new MatrixCellValue.CellComparatorColumnFirst());
        double maxAbsColSum = 0.0;
        double absSum = Math.abs(cellsByCol.get(0).value);
        for (int i = 1; i < cellsByCol.size(); i++) {
            if (cellsByCol.get(i).column == cellsByCol.get(i - 1).column) {
                absSum += Math.abs(cellsByCol.get(i).value);
            } else {
                maxAbsColSum = Math.max(maxAbsColSum, absSum);
                absSum = Math.abs(cells.get(i).value);
            }
        }
        maxAbsColSum = Math.max(maxAbsColSum, absSum);
        return maxAbsColSum;
    }

    @Override
    public double normInfinity() {
        double maxAbsRowSum = 0.0;
        double absSum = Math.abs(cells.get(0).value);
        for (int i = 1; i < cells.size(); i++) {
            if (cells.get(i).row == cells.get(i - 1).row) {
                absSum += Math.abs(cells.get(i).value);
            } else {
                maxAbsRowSum = Math.max(maxAbsRowSum, absSum);
                absSum = Math.abs(cells.get(i).value);
            }
        }
        maxAbsRowSum = Math.max(maxAbsRowSum, absSum);
        return maxAbsRowSum;
    }

    @Override
    public double frobeniusNorm() {
        double squareSum = 0.0;
        for (MatrixCellValue cell : cells) {
            squareSum += cell.value * cell.value;
        }
        return Math.sqrt(squareSum);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SPARSE MATRIX\n");
        sb.append(shape);
        sb.append("row, column, value\n");
        for (MatrixCellValue cellValue : cells) {
            sb.append(cellValue.row).append(" ");
            sb.append(cellValue.column).append(" ");
            sb.append(cellValue.value).append("\n");
        }
        return sb.toString();
    }
}
