package model;

import sun.awt.image.ImageWatched;

import java.util.*;
import java.util.function.IntToDoubleFunction;

public class Dijkstras {
    private ArrayList<NodeObj> GenPath;


    public boolean pathfind(NodeObj start, NodeObj goal) {
        //ArrayList<NodeObj> shortestPath = new ArrayList<NodeObj>();
        //shortestPath.add(start);
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
                //There might be an issue running multiple times as parents aren't cleared
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


    //function to construct a path from the goal path.
    //uses the getParent function of nodes and adds the parent to the path and stops when the parent is the start node
    protected ArrayList<NodeObj> constructPath(NodeObj goal, NodeObj start) {
        ArrayList<NodeObj> path = new ArrayList<NodeObj>();
        path.add(goal);
        NodeObj nextNode = goal.getParent();
        while (!nextNode.node.getNodeID().equals(start.node.getNodeID())) {
            path.add(nextNode);
            nextNode = nextNode.getParent();
        }
        path.add(start);
        return path;
    }

    //getter
    public ArrayList<NodeObj> getGenPath() {
        return GenPath;
    }
}
