package edu.union.service;

import edu.union.Config;
import edu.union.model.Color;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Singleton class that manages the color being used
 *
 * @author Khai Dong
 */
public class ColorRepository {

    private static ColorRepository instance;

    private final Map<Integer, Color> colorMap;

    private int idCount;

    /**
     * create an instance
     */
    private ColorRepository(){
        idCount = 0;
        colorMap = new HashMap<>();
    }

    /**
     * get the current instance of ColorRepository. If it does not exist, create one
     * and return
     * @return a singleton instance of ColorRepository
     */
    public static ColorRepository getInstance(){
        if(instance == null)
            instance = new ColorRepository();
        return instance;
    }

    /**
     * list all colors being used
     * @return a List of all colors
     */
    public List<Color> listColors(){
        return new ArrayList<>(colorMap.values());
    }

    /**
     * list all colors with the specified ids
     * @param ids the color ids
     * @return a list of all colors
     */
    public List<Color> listColors(Iterable<Integer> ids){
        return StreamSupport.stream(ids.spliterator(), false)
                .map(id -> getColor(id)).collect(Collectors.toList());
    }

    /**
     * add a color to the color repository
     * @param color the new color
     * @throws IllegalArgumentException if the color is already in the repository
     */
    public void addColor(Color color){
        if(colorMap.containsValue(color))
            throw new IllegalArgumentException("Color already in repository");
        color.setColorId(idCount++);
        colorMap.put(color.getColorId(), color);
    }

    /**
     * get a color by its id
     * @param id the color id
     * @return the corresponding Color obj
     * @throws IllegalArgumentException if the no color correspond with that colorId
     */
    public Color getColor(int id){
        if (!colorMap.containsKey(id))
            throw new IllegalArgumentException("unknown color id");
        return colorMap.get(id);
    }

    /**
     * clear the color in the repository
     */
    public void clear(){
        colorMap.clear();
        idCount = 0;
    }

}
