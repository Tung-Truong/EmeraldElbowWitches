package model;

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
                constructPath(goal);
                return true;
            }
            open_queue.remove(current);
            closed_queue.add(current);

            //creates a list of neighbors from the current node, and looks at the neighbors for the next optimal path.
            ArrayList<NodeObj> exploreList = current.getListOfNeighbors();
            for (NodeObj neighbor : exploreList) {
                //if closed queue does not have the neighbor, then evaluates the cost of travelling to the next node.
                if (!closed_queue.contains(neighbor)) {
                    //sets the gCost of neighbor which is the distance between neighbor and current as well as sets the heuristic of neighbor
                    neighbor.setgCost(current.getDistance(current,neighbor));
                    neighbor.setHeuristic(neighbor.getgCost()+ neighbor.getDistToGoal());
                    //if the open queue does not have it then add it to open queue.
                    if (!open_queue.contains(neighbor)) {
                        open_queue.add(neighbor);
                    }
                    //if it is in the open queue then look at different one
                    else {
                        //gets the neighbor from the open queue and if the cost is lower than the current one, then set the openNeighbor as the best one.
                        NodeObj openNeighbor = open_queue.peek();
                        openNeighbor.setgCost(current.getDistance(neighbor,current));
                        if(neighbor.getgCost()< openNeighbor.getgCost()){
                            openNeighbor.setgCost(neighbor.getgCost());
                            openNeighbor.setParent(neighbor.getParent());
                        }
                    }

                }
            }
        }
        return false; //No path exists
    }

    //function to construct a path from the goal path.
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