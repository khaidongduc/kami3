package edu.union.model;

import java.util.Objects;

/**
 * a basic RGB color edu.union.model
 *
 * @author Khai Dong
 */
public class Color {

    private static final int MAX_COLOR_VALUE = 255;

    private int colorId;
    private final int rValue;
    private final int gValue;
    private final int bValue;

    /**
     * constructor
     * create a new Color with RGB values
     * @param rValue red value
     * @param gValue green value
     * @param bValue blue value
     */
    public Color(int rValue, int gValue, int bValue){
        if(0 <= rValue && rValue <= MAX_COLOR_VALUE
        && 0 <= gValue && gValue <= MAX_COLOR_VALUE
        && 0 <= bValue && bValue <= MAX_COLOR_VALUE){
            this.rValue = rValue;
            this.gValue = gValue;
            this.bValue = bValue;
        } else {
            throw new RuntimeException("Illegal parameters");
        }
    }

    /**
     * get the colorId
     * colorId is assigned by the ColorRepository
     * @return the color id
     */
    public int getColorId(){
        return colorId;
    }

    /**
     * set the colorId
     * only to be use with ColorRepository
     * package-private
     * @param colorId the new colorId
     */
    public void setColorId(int colorId){
        this.colorId = colorId;
    }

    /**
     * @return the red value of the color
     */
    public int getRValue(){
        return rValue;
    }

    /**
     * @return the green value of the color
     */
    public int getGValue(){
        return gValue;
    }

    /**
     * @return the blue value of the color
     */
    public int getBValue(){
        return bValue;
    }

    public String getReadableColor(String original){
        switch (original) {
            case "RGB(0,0,255)":
                return "blue";
            case "RGB(0,255,0)":
                return "green";
            case "RGB(255,0,0)":
                return "red";
            default:
                return "light blue";
        }
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
