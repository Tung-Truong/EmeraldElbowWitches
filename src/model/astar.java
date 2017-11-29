package model;

import controller.Main;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class astar extends PathingAlgorithm {

    private ArrayList<NodeObj> GenPath;     // list of node objects generated at the end that show the route - start to finish

    public boolean pathfind(NodeObj start, NodeObj goal) {
        LinkedList<NodeObj> open_queue = new LinkedList<>();    //open_queue contains all unexplored NodeObjs, starts with just the start NodeObj at the first position
        LinkedList<NodeObj> closed_queue = new LinkedList<>();  //closed_queue contains all explored NodeObjs, which starts empty
        start.setgCost(0);
        open_queue.add(start);

        while (open_queue.size() > 0) {
            NodeObj current = getLowest(open_queue);
            closed_queue.add(current);
            open_queue.remove(current);

            if (current == goal) {
                GenPath = constructPath(goal, start);
                return true;
            }
            ArrayList<NodeObj> neighbors = current.getListOfNeighbors();
            for (NodeObj neighbor : neighbors) {
                if (closed_queue.contains(neighbor)) {
                    continue;
                } else if (!open_queue.contains(neighbor)) {
                    neighbor.setParent(current);
                    ArrayList<EdgeObj> edges = current.getListOfEdgeObjs();
                    for (EdgeObj edge : edges) {
                        if (edge.getNodeA().getNode().getNodeID().equals(current.node.getNodeID()) && edge.getNodeB().getNode().getNodeID().equals(neighbor.getNode().getNodeID())) {
                            neighbor.setgCost(neighbor.getEdgeObj(current).getWeight());
                        }
                    }
                    open_queue.add(neighbor);
                    neighbor.setHeuristic(neighbor.getDistance(goal) + neighbor.getgCost() + floorDifference(neighbor, current));
                } else {
                    if (neighbor.getgCost() > neighbor.getEdgeObj(current).getWeight()) {
                        neighbor.setParent(current);
                        neighbor.setgCost(current.getgCost());

                    }
                }
            }
        }
        return false;
    }

    //method that checks if the NodeA is on the same floor as nodeB and it is not, return a higher heuristic
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

    //function to add nodes based on their heuristic cost from increasing order
    protected ArrayList<NodeObj> addToQueue(ArrayList<NodeObj> list, NodeObj node) {
        for (NodeObj listNode : list) {
            if (listNode.getHeuristic() > node.getHeuristic())
                list.add(list.indexOf(listNode), node);
            return list;
        }
        list.add(node);
        return list;
    }

    //getter
    public ArrayList<NodeObj> getGenPath() {
        System.out.println("ASTAR");
        return GenPath;
    }
}