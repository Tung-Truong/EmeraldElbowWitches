package model;

import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Dijkstras {
    private ArrayList<NodeObj> GenPath;

    public Dijkstras() {
    }

    public boolean dijkstras(NodeObj start, NodeObj goal) {
        ArrayList<NodeObj> shortestPath = new ArrayList<NodeObj>();
        shortestPath.add(start);
        LinkedList<NodeObj> map = new LinkedList<NodeObj>();
        map.add(start);
        start.setgCost(0);
        while (map.size() > 0) {
            NodeObj current = map.pop();
            if(current.node.getNodeID() == goal.node.getNodeID()) {
                constructPath(goal,start);
                return true;
            }
            ArrayList<NodeObj> neighbors = current.getListOfNeighbors();
            ArrayList<EdgeObj> neighborEdges = current.getListOfEdgeObjs();
            for (NodeObj neighbor : neighbors) {
                for (EdgeObj edge : neighborEdges) {
                    if (edge.getNodeB().node.getNodeID().equals(neighbor.node.getNodeID()) || edge.getNodeA().node.getNodeID().equals(current.node.getNodeID())) {
                        double tentativeCost = current.getgCost() + edge.getWeight();
                        if (tentativeCost < neighbor.getgCost()) {
                            neighbor.setgCost(tentativeCost);
                            neighbor.setParent(current);
                        }
                    }
                    if (edge.getNodeA().node.getNodeID().equals(neighbor.node.getNodeID()) || edge.getNodeA().node.getNodeID().equals(current.node.getNodeID())) {
                        double tentativeCost = current.getgCost() + edge.getWeight();
                        if (tentativeCost < neighbor.getgCost()) {
                            neighbor.setgCost(tentativeCost);
                            neighbor.setParent(current);
                        }
                    }
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
        while (!nextNode.node.getNodeID().equals(start.node.getNodeID())){
            path.add(nextNode);
            nextNode = nextNode.getParent();
        }
        path.add(start);
        return path;
    }
}
