package edu.union.model;

import java.util.Objects;


/**
 * a cell in RectangleGridLevel and RectangleGridLevelBuilder
 */
public class RectangleGridCell extends ColoredGraph.ColoredVertex {

    public final int row;
    public final int col;

    /**
     * constructor
     * @param row the row of the cell
     * @param col the col of the cell
     */
    public RectangleGridCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RectangleGridCell that = (RectangleGridCell) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    /**
     * define how a RectangleGridCell can be adjacent
     * other type passed in will be ignored
     * @param o the other vertex
     * @return true if 2 vertex are adjacent
     */
    @Override
    public boolean adjacentTo(ColoredGraph.ColoredVertex o) {
        if (this == o) return false;
        if (o == null || getClass() != o.getClass()) return false;
        RectangleGridCell vertex = (RectangleGridCell) o;
        return ((Math.abs(vertex.row - this.row) == 1 && Math.abs(vertex.col - this.col) == 0)) ||
                ((Math.abs(vertex.row - this.row) == 0 && Math.abs(vertex.col - this.col) == 1));
    }

    @Override
    public String toString() {
        return String.format("%s %d %d", getClass().getName(), row, col);
    }
}
