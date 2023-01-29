package view;

import javafx.scene.Scene;

/**
 * a basic interface for view
 *
 * @author Khai Dong
 */
public interface View {
    /**
     * return the scene of the view
     * @return the scene of the view
     */
    Scene getScene();

    /**
     * bind the view to a new model
     * @throws IllegalArgumentException if the model is not the right type to that view
     */
    void bindModel(Object model);
}
