package controller;

import javafx.event.Event;

import java.util.ArrayList;

public class ControllerListener {
    ArrayList<Controller> observers;

    public ControllerListener() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(Controller controller) {
        observers.add(controller);
    }

    public void updateAllMaps(Event e) {
        for (Controller c : observers) {
            c.getMap(e);
        }
    }
}
