package edu.union.view;

import edu.union.utils.Observable;
import javafx.scene.Scene;

/**
 * a basic interface for edu.union.view
 *
 * @author Khai Dong
 */
public interface View {
    /**
     * return the scene of the edu.union.view
     * @return the scene of the edu.union.view
     */
    Scene getScene();

    /**
     * bind the edu.union.view to a new edu.union.model
     * @throws IllegalArgumentException if the edu.union.model is not the right type to that edu.union.view
     */
    void bindModel(Observable observableModel);
}
