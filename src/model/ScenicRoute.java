package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class ScenicRoute extends PathingAlgorithm {

    private ArrayList<NodeObj> GenPath;

    public boolean pathfind(NodeObj start, NodeObj goal) {
        //Make two linkedLists: Open and Closed
        //set the cost of g of start to 0
        //add start openqueue
        LinkedList<NodeObj> open_queue = new LinkedList<>();
        LinkedList<NodeObj> closed_queue = new LinkedList<>();

        start.setgCost(0);

        open_queue.add(start);

        //while open queue is bigger than 0
        //get the node in openqueue with the largest code and set current to that
        //add current to closedqueue

        while (open_queue.size() > 0) {
            NodeObj current = getHighest(open_queue);
            closed_queue.add(current);
            open_queue.remove(current);
            //if current is the goal then set GenPath to constructPath
            //return true;
            if (current == goal) {
                GenPath = constructPath(goal, start);
                return true;
            }

            //create an arraylist of neighbors of the current node
            ArrayList<NodeObj> neighbors = current.getListOfNeighbors();

            //for each loop of each neighbor
            for (NodeObj neighbor : neighbors) {
                //if neighbor is in closed queue then fuck off
                //else if openqueue does not have the neighbor
                //set the neighbors parent to current
                //set the heurisitcs for current

                if (closed_queue.contains(neighbor)) {
                    continue;
                } else if (!open_queue.contains(neighbor)) {
                    neighbor.setParent(current);

                    ArrayList<EdgeObj> edges = current.getListOfEdgeObjs();
                    // Heuristic?
                    for (EdgeObj edge : edges) {
                        if (edge.getNodeA().getNode().getNodeID().equals(current.node.getNodeID()) && edge.getNodeB().getNode().getNodeID().equals(neighbor.getNode().getNodeID())) {
                            neighbor.setgCost(neighbor.getEdgeObj(current).getWeight());
                        }
                    }
                    //else
                    //if neighbor's gcost is less than neighbors edge with current then
                    //set neighbors parent to current
                    //set the neighbors gcost
                    //if out of while loop return false;
                } else {
                    if (neighbor.getgCost() < neighbor.getEdgeObj(current).getWeight()) {
                        neighbor.setParent(current);
                        neighbor.setgCost(current.getgCost());
                    }
                }
            }

        }
        return false;
    }

    // Helper method to determine the edge with the highest weight to prioritize
    private NodeObj getHighest(LinkedList<NodeObj> list) {
        //create a currentHeuristic and set it to 0
        //create a Nodeobj highestNode and set it to null
        //for each node in list
        //if the node's heuristic is greater than the currentHeuristic
        //currentHeurisstic = node heuristic
        //highestNode = node
        //return highestNode
            double currentHeuristic = Integer.MAX_VALUE;
            NodeObj highestNode = null;
            for (NodeObj node : list) {
                if (node.getHeuristic() > currentHeuristic) {
                    currentHeuristic = node.getHeuristic();
                    highestNode = node;
                }
            }
            return highestNode;
    }

    @Override
    public ArrayList<NodeObj> getGenPath() {
        return GenPath;
    }
}