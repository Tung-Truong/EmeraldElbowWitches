package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/**
 * Serves as the caretaker class for the memento design pattern.
 *
 * Memento, in
 */

public class MementoController {


    @FXML
    void pathingHoverStop(MouseEvent event) {
        String hoveredID = ((JFXButton) event.getSource()).getId();
    }
}
