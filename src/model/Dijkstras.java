package model;

import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class Dijkstras {
    private ArrayList<NodeObj> GenPath;

    public Dijkstras() {
    }

    public boolean dijkstras(NodeObj start, NodeObj goal) {
        //ArrayList<NodeObj> shortestPath = new ArrayList<NodeObj>();
        //shortestPath.add(start);
        TreeMap<NodeObj, Double> map = new TreeMap<>();
        start.setgCost(0);
        map.put(start, start.getgCost());
        while (map.size() > 0) {
            NodeObj current = map.firstKey();
            map.remove(map.firstKey());
            if (current.node.getNodeID().equals(goal.node.getNodeID())) {
                constructPath(goal, start);
                return true;
            }
            ArrayList<NodeObj> neighbors = current.getListOfNeighbors();
            for (NodeObj neighbor : neighbors) {
                //if the neighbor has not been explored yet, set the value as infinity
                //There might be an issue running multiple times as parents aren't cleared
                if (neighbor.getParent().node.getNodeID() == null) {
                    neighbor.setgCost(Integer.MAX_VALUE);
                }
                double tentativeCost = current.getgCost() + neighbor.getEdgeObj(current).getWeight();
                if (tentativeCost < neighbor.getgCost()) {
                    neighbor.setgCost(tentativeCost);
                    neighbor.setParent(current);
                    map.put(neighbor, neighbor.getgCost());
                }
            }
        }
        return false;
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
