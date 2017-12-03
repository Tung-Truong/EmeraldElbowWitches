package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class BeamFirst extends PathingAlgorithm {
    private ArrayList<NodeObj> GenPath;
    private int beamWidth = 2;

    public boolean pathfind(NodeObj start, NodeObj goal) {
        ArrayList<NodeObj> explored = new ArrayList<NodeObj>(); // List of explored nodes, or nodes that have been accounted for
        LinkedList<NodeObj> queue = new LinkedList<NodeObj>();  // List of nodes that have not been explored yet
        queue.add(start);
        explored.add(start);
        while (!queue.isEmpty()) {
            NodeObj current = queue.remove();
            if (current.node.getNodeID().equals(goal.node.getNodeID())) {
                explored.add(current);
                GenPath = constructPath(goal, start);
                return true;
            }
            ArrayList<NodeObj> neighbours = current.getListOfNeighbors();
            for (int i = 0; i < neighbours.size(); i++) {
                NodeObj n = neighbours.get(i);
                n.setHeuristic(n.getDistance(goal) + n.getgCost() + floorDifference(n,current));
                if (n != null && !explored.contains(n)) {
                    queue.add(n);
                    explored.add(n);
                    n.setParent(current);
                }
                Collections.sort(queue, new NodeObjComparator());
                while(queue.size() > beamWidth) {
                    queue.removeLast();
                }
            }
        }
        return false;
    }

    //getter
    public ArrayList<NodeObj> getGenPath() {
        System.out.println("BEAM FIRST");
        return GenPath;
    }

    //method that checks if the NodeA is on the same floor as nodeB and it is not, return a higher heuristic
    private double floorDifference(NodeObj nodeA, NodeObj nodeB) {
        if (nodeA.getNode().getFloor().equals(nodeB.getNode().getFloor())) {
            return 0;
        } else {
            return 50000;
        }
    }
}
