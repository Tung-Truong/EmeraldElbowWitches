package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class BeamFirst extends PathingAlgorithm {
    private ArrayList<NodeObj> GenPath;

    public boolean pathfind(NodeObj start, NodeObj goal) {
        // Do shit here
        return false;
    }

    //getter
    public ArrayList<NodeObj> getGenPath() {
        System.out.println("BEAM FIRST");
        return GenPath;
    }
}
