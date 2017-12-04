package model;


import java.util.ArrayList;
import java.util.LinkedList;

public class BreadthFirst extends PathingAlgorithm {
    private ArrayList<NodeObj> GenPath;

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
                if (n != null && !explored.contains(n)) {
                    queue.add(n);
                    explored.add(n);
                    n.setParent(current);
                }
            }
        }
        return false;
    }

    //getter
    public ArrayList<NodeObj> getGenPath() {
        System.out.println("BREADTH FIRST");
        return GenPath;
    }
}
