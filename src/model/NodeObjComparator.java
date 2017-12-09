package model;

import java.util.Comparator;

public class NodeObjComparator implements Comparator<NodeObj> {
    @Override
    public int compare(NodeObj n1, NodeObj n2) {
        if (n1.getHeuristic() < n2.getHeuristic()) {
            return 1;
        } else {
            return -1;
        }
    }
}