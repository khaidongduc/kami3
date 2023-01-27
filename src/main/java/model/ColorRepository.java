package model;

import java.util.*;

public class ColorRepository {

    private static ColorRepository instance;

    private final Map<Integer, Color> colorMap;

    private int idCount;

    private ColorRepository(){
        idCount = 0;
        colorMap = new HashMap<>();
    }

    public ColorRepository getInstance(){
        if(instance == null)
            instance = new ColorRepository();
        return instance;
    }

    public List<Color> listColors(){
        return new ArrayList<>(colorMap.values());
    }

    public void addColor(Color color){
        if(colorMap.containsValue(color))
            throw new IllegalArgumentException("Color already in repository");
        color.setColorId(idCount++);
        colorMap.put(color.getColorId(), color);
    }

    public Color getColor(int id){
        if (!colorMap.containsKey(id))
            throw new IllegalArgumentException("unknown color id");
        return colorMap.get(id);
    }

}
