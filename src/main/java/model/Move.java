package model;

public class Move {

    private int row, col;
    private Color color;

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
