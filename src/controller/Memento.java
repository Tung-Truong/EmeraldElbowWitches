package controller;

import javafx.scene.Scene;

/**
 * Has the scenes for admin and patient
 *
 * If on admin for a while, set scene to patient
 * Ideally, also resets scene to patient
 */

public class Memento {

    private Scene patientScene;
    private Scene adminScene;

    public Memento(Scene patientScene, Scene adminScene) {
        this.patientScene = patientScene;
        this.adminScene = adminScene;
    }

    Scene getPatientScene() {
        return patientScene;
    }

    Scene getAdminScene() {
        return adminScene;
    }


}
