package controller;

import javafx.stage.Stage;
import view.View;
import view.ViewEnum;

import java.util.EnumMap;

/**
 * a view switcher to supporting switching between views in controller
 */
public class ViewSwitcher {

    private static Stage stage;
    private static final EnumMap<ViewEnum, View> viewMap = new EnumMap<ViewEnum, View>(ViewEnum.class);

    public static void setStage(Stage stage){
        ViewSwitcher.stage = stage;
    }


    public static void addView(ViewEnum viewEnum, View view){
        viewMap.put(viewEnum, view);
    }

    public static void switchView(ViewEnum viewEnum){
        View view = viewMap.get(viewEnum);
        stage.setScene(view.getScene());
    }

    public static void switchView(ViewEnum viewEnum, Object bindedObject){
        View view = viewMap.get(viewEnum);
        if (bindedObject != null){
            view.bindModel(bindedObject);
        }
        stage.setScene(view.getScene());
    }

}
