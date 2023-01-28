package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import view.View;

import java.io.ObjectInputFilter;
import java.util.HashMap;
import java.util.Map;

public class ViewSwitcher {

    private static Scene scene;
    private static Stage stage;
    private static final Map<String, View> viewMap = new HashMap<>();

    public static void setScene(Scene scene){
        ViewSwitcher.scene = scene;
    }
    public static void setStage(Stage stage){
        ViewSwitcher.stage = stage;
    }


    public static void addView(String viewName, View view){
        viewMap.put(viewName, view);
    }

    public static void switchView(String viewName){
        View view = viewMap.get(viewName);
        stage.setScene(view.getScene());
    }

    public static void switchView(String viewName, Object bindedObject){
        View view = viewMap.get(viewName);
        if (bindedObject != null){
            view.bindModel(bindedObject);
        }
        stage.setScene(view.getScene());
    }

}
