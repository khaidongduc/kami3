package edu.union.model;

import java.util.*;

/**
 * a color grid to be use within Level and LevelBuilder
 *
 * @author Khai Dong
 */
public class ColorGrid {
    private int numRows;
    private int numCols;
    private int [][] grid;

    /**
     * basic initialization
     */
    public ColorGrid(){
        numRows = numCols = 0;
        grid = null;
    }

    public ColorGrid(ColorGrid grid){
        this.numRows = grid.numRows;
        this.numCols = grid.numCols;
        this.grid = new int[this.numRows][this.numCols];
        for(int i = 0 ; i < this.numRows ; ++ i)
            System.arraycopy(grid.grid[i], 0, this.grid[i], 0, this.numCols);
    }

    /**
     * create a color grid with specified sizes
     * all initial colorId will be 0, which correspond to the first color added into ColorRepository
     * @param numRows the number of rows
     * @param numCols the number of columns
     */
    public ColorGrid(int numRows, int numCols){
        this();
        this.numRows = numRows;
        this.numCols = numCols;
        this.grid = new int[numRows][numCols];
    }

    /**
     * @return the grid number of rows
     */
    public int getNumRows() {
        return this.numRows;
    }

    /**
     * @return the grid number of columns
     */
    public int getNumCols() {
        return this.numCols;
    }

    /**
     * get the colorId of an entry in a colorGrid
     * @param row the row
     * @param col the column
     * @return  the colorId of the specified entry in a colorGrid
     */
    public int getColorOfEntry(int row, int col) {
        return grid[row][col];
    }

    /**
     * set color of a specified entry
     * @param colorId the new colorId
     * @param row the row
     * @param col the column
     * @throws IllegalArgumentException will fail if the index is out of bounds or color
     * is the same as the current color
     */
    public void setColor(int colorId, int row, int col) {
        if(0 > row || row >= this.numRows || 0 > col || col >= this.numCols)
            throw new IllegalArgumentException("index out of bound");
        if(colorId != this.grid[row][col]){
            this.grid[row][col] = colorId;
        } else {
            throw new IllegalArgumentException("color cant be the same as original color");
        }
    }

    /**
     * set color of a specified entry and the surrounding entries with the same color
     * @param colorId the new colorId
     * @param row the row
     * @param col the column
     * @throws IllegalArgumentException will fail if the index is out of bounds or color
     * is the same as the current color
     */
    public void setColorFlood(int colorId, int row, int col) {
        if(0 > row || row >= this.numRows || 0 > col || col >= this.numCols){
            throw new IllegalArgumentException("index out of bound");
        }
        int orgColorId = getColorOfEntry(row, col);
        if(colorId == orgColorId){
            throw new IllegalArgumentException("color cant be the same as original color");
        }
        setColor(colorId, row, col);
        Queue<GridCellPosition> queue = new LinkedList<>();
        queue.add(new GridCellPosition(row, col));
        while(!queue.isEmpty()){
            GridCellPosition curPos = queue.poll();
            for (GridCellPosition nextCell : getNeighborPositions(curPos)){
                if(orgColorId == this.getColorOfEntry(nextCell.row, nextCell.col)){
                    setColor(colorId, nextCell.row, nextCell.col);
                    queue.add(new GridCellPosition(nextCell.row, nextCell.col));
                }
            }
        }
    }

    /**
     * get all colorIds being used in this grid
     * @return all colorIds being used in this grid
     */
    public Set<Integer> getAvailableColorIds() {
        Set<Integer> colorIds = new HashSet<>();
        for(int i = 0 ; i < numRows ; ++ i)
            for(int j = 0 ; j < numCols ; ++ j) colorIds.add(getColorOfEntry(i, j));
        return colorIds;
    }

    /**
     * a helper function defining neighbors of a particular cell
     * @param pos the cell specified
     * @return an Iterable containing the neighboring cells
     */
    public Iterable<GridCellPosition> getNeighborPositions(GridCellPosition pos){
        List<GridCellPosition> neighbors = new LinkedList<>();
        int [] dr = {0, 1, 0, -1}, dc = {-1, 0, 1, 0};
        for(int i = 0 ; i < 4 ; ++ i){
            int nextRow = pos.row + dr[i], nextCol = pos.col + dc[i];
            if(0 <= nextRow && nextRow < this.getNumRows() && 0 <= nextCol && nextCol < this.getNumCols()){
                neighbors.add(new GridCellPosition(nextRow, nextCol));
            }
        }
        return neighbors;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.numRows).append(' ').append(this.numCols);
        for(int i = 0 ; i < this.numRows ; ++ i){
            builder.append('\n');
            for(int j = 0 ; j < this.numCols ; ++ j)
                builder.append(String.format("%10s", getColorOfEntry(i, j))).append(' ');
        }
        return builder.toString();
    }

    /**
     * basic cell position with row and column
     */
    public static class GridCellPosition {
        public final int row;
        public final int col;

        public GridCellPosition(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GridCellPosition that = (GridCellPosition) o;
            return row == that.row && col == that.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

}
