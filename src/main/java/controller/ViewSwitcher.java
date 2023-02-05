package controller;

import javafx.stage.Stage;
import view.View;
import view.ViewEnum;

import java.util.EnumMap;

/**
 * a view switcher to supporting switching between views in controller
 * Singleton
 */
public class ViewSwitcher {

    private Stage stage;
    private final EnumMap<ViewEnum, View> viewMap;

    private static ViewSwitcher instance;

    private ViewSwitcher(){
        viewMap = new EnumMap<ViewEnum, View>(ViewEnum.class);
    }

    /**
     * return a singleton instance of ViewSwitcher
     * @return an instance of ViewSwitcher
     */
    public static ViewSwitcher getInstance(){
        if(instance == null)
            instance = new ViewSwitcher();
        return instance;
    }

    /**
     * set the root stage to manipulate
     * @param stage the root stage
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * add a view to enable switching using ViewSwitcher
     * @param viewEnum the view enum
     * @param view the view
     */
    public void addView(ViewEnum viewEnum, View view){
        viewMap.put(viewEnum, view);
    }

    /**
     * add a view to enable switching using ViewSwitcher
     * @param viewEnum the view enum
     * @throws IllegalArgumentException if viewEnum is not in the viewMap
     */
    public void switchView(ViewEnum viewEnum){
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
    public void switchView(ViewEnum viewEnum, Object model){
        if(!viewMap.containsKey(viewEnum))
            throw new IllegalArgumentException(viewEnum + "not switchable");
        View view = viewMap.get(viewEnum);
        if (model != null){
            view.bindModel(model);
        }
        stage.setScene(view.getScene());
    }

}
