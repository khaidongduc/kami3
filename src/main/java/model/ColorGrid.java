package model;

import java.util.*;

public class ColorGrid {
    private int numRows;
    private int numCols;
    private int [][] grid;

    public ColorGrid(){
        numRows = numCols = 0;
        grid = null;
    }

    public ColorGrid(int numRows, int numCols){
        this();
        this.numRows = numRows;
        this.numCols = numCols;
        this.grid = new int[numRows][numCols];
    }


    public int getNumRows() {
        return this.numRows;
    }


    public int getNumCols() {
        return this.numCols;
    }


    public int getColorOfEntry(int row, int col) {
        return grid[row][col];
    }


    public void setColor(int colorId, int row, int col) {
        if(0 > row || row >= this.numRows || 0 > col || col >= this.numCols)
            throw new IllegalArgumentException("index out of bound");
        if(colorId != this.grid[row][col]){
            this.grid[row][col] = colorId;
        } else {
            throw new IllegalArgumentException("color cant be the same as original color");
        }
    }


    public void setColorFlood(int colorId, int row, int col) {
        if(0 > row || row >= this.numRows || 0 > col || col >= this.numCols){
            throw new IllegalArgumentException("index out of bound");
        }
        int orgColorId = getColorOfEntry(row, col);
        if(colorId == orgColorId){
            throw new IllegalArgumentException("color cant be the same as original color");
        }
        setColor(colorId, row, col);
        Queue<CellPosition> queue = new LinkedList<>();
        queue.add(new CellPosition(row, col));
        while(!queue.isEmpty()){
            CellPosition curPos = queue.poll();
            for (CellPosition nextCell : getNeighborPositions(curPos)){
                if(orgColorId == this.getColorOfEntry(nextCell.row, nextCell.col)){
                    setColor(colorId, nextCell.row, nextCell.col);
                    queue.add(new CellPosition(nextCell.row, nextCell.col));
                }
            }
        }
    }


    public Set<Integer> getAvailableColorIds() {
        Set<Integer> colorIds = new HashSet<>();
        for(int i = 0 ; i < numRows ; ++ i)
            for(int j = 0 ; j < numCols ; ++ j) colorIds.add(getColorOfEntry(i, j));
        return colorIds;
    }

    private Iterable<CellPosition> getNeighborPositions(CellPosition pos){
        List<CellPosition> neighbors = new LinkedList<>();
        int [] dr = {0, 1, 0, -1}, dc = {-1, 0, 1, 0};
        for(int i = 0 ; i < 4 ; ++ i){
            int nextRow = pos.row + dr[i], nextCol = pos.col + dc[i];
            if(0 <= nextRow && nextRow < this.getNumRows() && 0 <= nextCol && nextCol < this.getNumCols()){
                neighbors.add(new CellPosition(nextRow, nextCol));
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

    static class CellPosition{
        public final int row;
        public final int col;

        public CellPosition(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) {
        ColorGrid grid = new ColorGrid(5, 5);
        System.out.println(grid);
        grid.setColor(2, 1, 1);
        grid.setColorFlood(1, 0, 0);
        System.out.println(grid);
    }

}
