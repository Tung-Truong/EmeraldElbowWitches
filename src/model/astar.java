package model;

import javax.xml.soap.Node;
import java.util.*;

public class astar {
    private ArrayList<NodeObj> GenPath;

    protected boolean Pathfind(NodeObj start, NodeObj goal) {
        //open_queue contains all unexplored NodeObjs, starts with just the start NodeObj
        //closed_queue contains all explored NodeObjs, which starts empty
        PriorityQueue<NodeObj> open_queue = new PriorityQueue<NodeObj>();
        open_queue.add(start);
        PriorityQueue<NodeObj> closed_queue = new PriorityQueue<NodeObj>();
        //G cost of going to start from start is zero
        double startG = 0;
        start.setHeuristic(startG + start.getDistToGoal());

        while (open_queue.size() > 0) {
            NodeObj current = open_queue.peek(); //gets the element with the lowest f cost
            if (current == goal) {
                //should create the found path with helper function
                return true;
            }
            open_queue.remove(current);
            closed_queue.add(current);

            ArrayList<NodeObj> exploreList = current.getListOfNeighbors();
            for (NodeObj neighbor : exploreList) {
                if (!closed_queue.contains(neighbor)) {
                    neighbor.setHeuristic(neighbor.setgCost(neighbor.getDistance(neighbor,current)); + neighbor.getDistToGoal());
                    if (!open_queue.contains(neighbor)) {
                        open_queue.add(neighbor);
                        }
                    else {
                        NodeObj openNeighbor = open_queue.peek();
                        if(neighbor.getgCost()< openNeighbor.setgCost(getDistance(neighbor, current))){
                            openNeighbor.setgCost(neighbor.getgCost());
                            openNeighbor.setParent(neighbor.getParent());
                        }
                    }

                }
            }
        }
        return false; //No path exists
    }

    protected ArrayList<NodeObj> constructPath(NodeObj goal){
        ArrayList<NodeObj> path = new ArrayList<NodeObj>();
        path.add(goal);
        while (!goal.getParent().equals(null)){
            NodeObj nextNode = goal.getParent();
            path.add(nextNode);
        }
        return path;
    }
}