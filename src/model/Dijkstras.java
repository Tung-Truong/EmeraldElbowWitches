package model;


import java.util.*;

public class Dijkstras extends PathingAlgorithm {
    private ArrayList<NodeObj> GenPath;

    /**
     * Finds a path from start to goal using the Dijkstras algorithm.
     * @param start Origin node for the path.
     * @param goal Destination node for the path.
     * @return boolean Returns true when path is complete.
     */
    public boolean pathfind(NodeObj start, NodeObj goal) {
        LinkedList<NodeObj> open_queue = new LinkedList<>();
        Set<NodeObj> closed_queue = new HashSet<>();
        open_queue.add(start);
        while (open_queue.size() > 0) {
            NodeObj current = getLowest(open_queue);
            open_queue.remove(current);
            closed_queue.add(current);
            if (current.node.getNodeID().equals(goal.node.getNodeID())) {
                GenPath = constructPath(goal, start);
                return true;
            }
            ArrayList<NodeObj> neighbors = current.getListOfNeighbors();
            for (NodeObj neighbor : neighbors) {
                if (closed_queue.contains(neighbor)) {
                    continue;
                }
                //if the neighbor has not been explored yet, set the value as infinity
                else {
                    neighbor.setgCost(Integer.MAX_VALUE);
                    double tentativeCost = current.getgCost() + neighbor.getEdgeObj(current).getWeight();
                    if (tentativeCost < neighbor.getgCost()) {
                        neighbor.setgCost(tentativeCost);
                        neighbor.setParent(current);
                        open_queue.add(neighbor);
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method finds the node with the lowest weight from an array of nodes.
     * @param list This is an arrya list of node objects.
     * @return NodeObj Returns the node with the lowest weight.
     */
    private NodeObj getLowest(LinkedList<NodeObj> list) {
        double currentGCost = Integer.MAX_VALUE;
        NodeObj lowestNode = null;
        for (NodeObj node : list) {
            if (node.getgCost() < currentGCost) {
                currentGCost = node.getgCost();
                lowestNode = node;
            }
        }
        return lowestNode;
    }

    //getter
    public ArrayList<NodeObj> getGenPath() {
        System.out.println("DIJKSTRAS");
        return GenPath;
    }
}
