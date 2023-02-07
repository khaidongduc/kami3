package edu.union.controller;

import edu.union.utils.Observable;
import javafx.stage.Stage;
import edu.union.view.View;
import edu.union.view.ViewEnum;

import java.util.EnumMap;

/**
 * a edu.union.view switcher to supporting switching between views in edu.union.controller
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
     * add a edu.union.view to enable switching using ViewSwitcher
     * @param viewEnum the edu.union.view enum
     * @param view the edu.union.view
     */
    public void addView(ViewEnum viewEnum, View view){
        viewMap.put(viewEnum, view);
    }

    /**
     * add a edu.union.view to enable switching using ViewSwitcher
     * @param viewEnum the edu.union.view enum
     * @throws IllegalArgumentException if viewEnum is not in the viewMap
     */
    public void switchView(ViewEnum viewEnum){
        if(!viewMap.containsKey(viewEnum))
            throw new IllegalArgumentException(viewEnum + "not switchable");
        View view = viewMap.get(viewEnum);
        stage.setScene(view.getScene());
    }

    /**
     * add a edu.union.view to enable switching using ViewSwitcher
     * and bind the new edu.union.model to the edu.union.view
     * @param viewEnum the edu.union.view enum
     * @param model the new edu.union.model
     * @throws IllegalArgumentException if viewEnum is not in the viewMap
     * @throws IllegalArgumentException if bind the edu.union.model into the edu.union.view fail
     */
    public void switchView(ViewEnum viewEnum, Observable model){
        if(!viewMap.containsKey(viewEnum))
            throw new IllegalArgumentException(viewEnum + "not switchable");
        View view = viewMap.get(viewEnum);
        if (model != null){
            view.bindModel(model);
        }
        stage.setScene(view.getScene());
    }

}
