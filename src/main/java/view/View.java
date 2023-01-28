package view;

import javafx.scene.Parent;
import javafx.scene.Scene;

public interface View {
    Scene getScene();
    void bindModel(Object model);
}
