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

    /**
     * set the root stage to manipulate
     * @param stage the root stage
     */
    public static void setStage(Stage stage){
        ViewSwitcher.stage = stage;
    }

    /**
     * add a view to enable switching using ViewSwitcher
     * @param viewEnum the view enum
     * @param view the view
     */
    public static void addView(ViewEnum viewEnum, View view){
        viewMap.put(viewEnum, view);
    }

    /**
     * add a view to enable switching using ViewSwitcher
     * @param viewEnum the view enum
     * @throws IllegalArgumentException if viewEnum is not in the viewMap
     */
    public static void switchView(ViewEnum viewEnum){
        if(!viewMap.containsKey(viewEnum))
            throw new IllegalArgumentException(viewEnum + "not switchable");
        View view = viewMap.get(viewEnum);
        stage.setScene(view.getScene());
    }

    /**
     * add a view to enable switching using ViewSwitcher
     * and bind the new model to the view
     * @param viewEnum the view enum
     * @param model the new model
     * @throws IllegalArgumentException if viewEnum is not in the viewMap
     * @throws IllegalArgumentException if bind the model into the view fail
     */
    public static void switchView(ViewEnum viewEnum, Object model){
        if(!viewMap.containsKey(viewEnum))
            throw new IllegalArgumentException(viewEnum + "not switchable");
        View view = viewMap.get(viewEnum);
        if (model != null){
            view.bindModel(model);
        }
        stage.setScene(view.getScene());
    }

}
