package model;

import com.sun.xml.internal.bind.v2.TODO;
import sun.awt.image.ImageWatched;

import java.util.*;
import java.util.function.IntToDoubleFunction;

public class Dijkstras extends PathingAlgorithm {
    private ArrayList<NodeObj> GenPath;


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
                // TODO: There might be an issue running multiple times as parents aren't cleared
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

    //getter
    public ArrayList<NodeObj> getGenPath() {
        System.out.println("DIJKSTRAS");
        return GenPath;
    }
}
