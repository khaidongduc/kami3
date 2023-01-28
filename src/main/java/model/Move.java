package model;

public class Move {

    private final int row;
    private final int col;
    private final Color color;

    public Move(Color color, int row, int col){
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Color getColor() {
        return color;
    }


}
