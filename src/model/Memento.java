package model;

import controller.PatientController;
import javafx.scene.Scene;

/**
 * Has the scenes for admin and patient
 *
 * If on admin for a while, set scene to patient
 * Ideally, also resets scene to patient
 */

public class Memento {

    Scene patientScene;
    Scene adminScene;

    public Memento() {

    }
}
