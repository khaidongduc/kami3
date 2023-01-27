package model;

import java.util.Objects;

public class Color {
    private int colorId;
    private final int rValue;
    private final int gValue;
    private final int bValue;

    public Color(int rValue, int gValue, int bValue){
        if(0 <= rValue && rValue < 256 
        && 0 <= gValue && gValue < 256 
        && 0 <= bValue && bValue < 256){
            this.rValue = rValue;
            this.gValue = gValue;
            this.bValue = bValue;
        } else {
            throw new RuntimeException("Illegal parameters");
        }
    }

    public int getColorId(){
        return colorId;
    }

    void setColorId(int colorId){
        this.colorId = getColorId();
    }

    public int getRValue(){
        return rValue;
    }

    public int getGValue(){
        return gValue;
    }

    public int getBValue(){
        return bValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Color color = (Color) obj;
        return this.getRValue() == color.getRValue()
            && this.getGValue() == color.getGValue()
            && this.getBValue() == color.getBValue();
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getRValue(), this.getGValue(), this.getBValue()); 
    }

    @Override
    public String toString(){
        return String.format("%d", this.getColorId());
    }

}
