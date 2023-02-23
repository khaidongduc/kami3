package edu.union;

import edu.union.model.Color;

public class ColorStub extends Color {

    /**
     * constructor
     * create a new Color with RGB values
     *
     * @param rValue red value
     * @param gValue green value
     * @param bValue blue value
     */
    public ColorStub(int rValue, int gValue, int bValue) {
        super(rValue, gValue, bValue);
    }

    public ColorStub getColor(int id){
        if (id == 0){
            return new ColorStub(255, 0, 0);
        } else if(id == 1){
            return new ColorStub(0, 255, 0);
        } else if(id == 2){
            return new ColorStub(0, 0, 255);
        } else if(id == 3){
            return new ColorStub(0, 255, 255);
        } else{
            return null;
        }
    }
}
