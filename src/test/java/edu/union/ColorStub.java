package edu.union;

import edu.union.model.Color;
import edu.union.utils.Observer;

public class ColorStub {
    private int red, green, blue;

    public void setRed(int value){
        this.red = value;
    }

    public void setGreen(int value){
        this.green = value;
    }

    public void setBlue(int value){
        this.blue = value;
    }

    public int getRed(){
        return this.red;
    }

    public int getGreen(){
        return this.green;
    }

    public int getBlue(){
        return this.blue;
    }
}
