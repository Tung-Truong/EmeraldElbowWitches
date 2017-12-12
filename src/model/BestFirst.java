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

        while (queue.size() > 0) {
            //checks if the current node is the goal
            NodeObj current = getLowest(queue);
            queue.remove(current);
            explored.add(current);
            if (current.node.getNodeID().equals(goal.node.getNodeID())) {
                explored.add(current);
                GenPath = constructPath(goal, start);
                return true;
            }

            ArrayList<NodeObj> neighbours = current.getListOfNeighbors(); //list of neighboring nodes


            for (NodeObj node : neighbours) {
                if (!explored.contains(node)) {
                    if (!queue.contains(node)) {
                        node.setHeuristic(node.getDistance(goal));
                        node.setParent(current);
                        queue.add(node);
                    }
                }
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

    // Helper method to determine the edge with the lowest weight to prioritize
    private NodeObj getLowest(LinkedList<NodeObj> list) {
        double currentHeuristic = Integer.MAX_VALUE;
        NodeObj lowestNode = null;
        for (NodeObj node : list) {
            if (node.getHeuristic() < currentHeuristic) {
                currentHeuristic = node.getHeuristic();
                lowestNode = node;
            }
        }
        return lowestNode;
    }
}
