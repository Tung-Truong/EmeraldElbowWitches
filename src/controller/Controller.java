package controller;

import javafx.event.Event;
import model.ImageLoader;
import model.NodeObj;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Controller {

    public static HashMap<ArrayList<NodeObj>, Integer> mostUsedPaths = new HashMap<>();

    abstract void getMap(Event e);
}
