package edu.union.model;

/**
 * Basic class containing data on a move that can be made on a level
 */
public class Move {

    private final int row;
    private final int col;
    private final Color color;

    /**
     * initialization
     * note that create with index out of bound is accepted
     * but should be rejected when passed into Level
     * @param color the color of the move
     * @param row the row of the move
     * @param col the col of the move
     */
    public Move(Color color, int row, int col){
        this.color = color;
        this.row = row;
        this.col = col;
    }

    /**
     * row getter
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * col getter
     * @return the column
     */
    public int getCol() {
        return col;
    }

    /**
     * color getter
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    public String toString(){
        String toReturn = getColor().toString() + " ";
        toReturn += "row:" + getRow() + " ";
        toReturn += "col:" + getCol();
        return toReturn;
    }
}
