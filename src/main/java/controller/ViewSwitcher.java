package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import view.View;

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
        stage.setScene(viewMap.get(viewName).getScene());
    }

}
