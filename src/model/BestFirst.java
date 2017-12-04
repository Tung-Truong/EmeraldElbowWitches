package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class BestFirst extends PathingAlgorithm {
    private ArrayList<NodeObj> GenPath;

    public boolean pathfind(NodeObj start, NodeObj goal) {


        ArrayList<NodeObj> explored = new ArrayList<NodeObj>(); // List of explored nodes, or nodes that have been accounted for
        LinkedList<NodeObj> queue = new LinkedList<NodeObj>();  // List of nodes that have not been explored yet

        queue.add(start);
        explored.add(start);

        while (!queue.isEmpty()) {
            //checks if the current node is the goal
            NodeObj current = queue.remove();
            if (current.node.getNodeID().equals(goal.node.getNodeID())) {
                explored.add(current);
                GenPath = constructPath(goal, start);
                return true;
            }

            ArrayList<NodeObj> neighbours = current.getListOfNeighbors(); //list of neighboring nodes
            LinkedList<NodeObj> queue2 = new LinkedList<NodeObj>();// temporary queue? for keeping nodes that are being explored

            double currentHeuristic = Integer.MAX_VALUE;
            NodeObj lowestNode = null;

            for (NodeObj node : neighbours) {
                if (node.getHeuristic() < currentHeuristic) {
                    currentHeuristic = node.getHeuristic();
                    lowestNode = node;
                }
            }
            queue2.add(lowestNode);

            for (int j = 0; j < queue2.size(); j++) {
                explored.add(queue2.get(j));
                queue.add(queue2.get(j));
            }
        }
        return false;
    }


    //getter
    public ArrayList<NodeObj> getGenPath() {
        System.out.println("BEST FIRST");
        return GenPath;
    }

    //is the floor the same?
    private double floorDifference(NodeObj nodeA, NodeObj nodeB) {
        if (nodeA.getNode().getFloor().equals(nodeB.getNode().getFloor())) {
            return 0;
        } else {
            return 50000;
        }
    }
}
