package model;

import java.util.ArrayList;

public class BestFirst extends PathingAlgorithm {
    private ArrayList<NodeObj> GenPath;

    public boolean pathfind(NodeObj start, NodeObj goal) {
        // Do shit here
        return false;
    }

    //getter
    public ArrayList<NodeObj> getGenPath() {
        System.out.println("BEST FIRST");
        return GenPath;
    }
}
