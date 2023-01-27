package model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ColorRepository {

    private static ColorRepository instance;

    private final Map<Integer, Color> colorMap;

    private int idCount;

    private ColorRepository(){
        idCount = 0;
        colorMap = new HashMap<>();

        addColor(new Color(255, 0, 0));
        addColor(new Color(0, 255, 0));
        addColor(new Color(0, 0, 255));

    }

    public static ColorRepository getInstance(){
        if(instance == null)
            instance = new ColorRepository();
        return instance;
    }

    public Set<Color> listColors(){
        return new HashSet<>(colorMap.values());
    }

    public Set<Color> listColors(Iterable<Integer> ids){
        return StreamSupport.stream(ids.spliterator(), false)
                .map(id -> getColor(id)).collect(Collectors.toSet());
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
