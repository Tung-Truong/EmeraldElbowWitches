package model;

import java.util.*;

public class astar {
    //getHeuristic() Never used
    public astar(){

    }
    private ArrayList<NodeObj> GenPath;

    public boolean Pathfind(NodeObj start, NodeObj goal) {
        //open_queue contains all unexplored NodeObjs, starts with just the start NodeObj
        //closed_queue contains all explored NodeObjs, which starts empty
        ArrayList<NodeObj> open_queue = new ArrayList<NodeObj>();
        open_queue.add(0,start);
        System.out.println(start);
        ArrayList<NodeObj> closed_queue = new ArrayList<NodeObj>();
        //G cost of going to start from start is zero
        double startG = 0;
        start.setHeuristic(startG + start.getDistance(goal));

        while (open_queue.size() > 0) {
            NodeObj current = open_queue.get(0); //gets the element with the lowest f cost
            if (current == goal) {
                System.out.println("Goal is " + goal);
                GenPath = constructPath(goal,start);
                System.out.println(GenPath);
                return true;
            }
            open_queue.remove(0);
            closed_queue.add(current);

            //creates a list of neighbors from the current node, and looks at the neighbors for the next optimal path.
            ArrayList<NodeObj> exploreList = current.getListOfNeighbors();
            for (NodeObj neighbor : exploreList) {
                //if closed queue does not have the neighbor, then evaluates the cost of travelling to the next node.
                if (!closed_queue.contains(neighbor)) {
                    //sets the gCost of neighbor which is the distance between neighbor and current as well as sets the heuristic of neighbor
                    neighbor.setgCost(current.getDistance(neighbor));
                    neighbor.setHeuristic(neighbor.getgCost()+ neighbor.getDistance(goal));
                    //if the open queue does not have it then add it to open queue and set the parent to the current node.
                    if (!open_queue.contains(neighbor)) {
                        addToQueue(open_queue,neighbor);
                        neighbor.setParent(current);
                                            }
                    //else do nothing
                    else{
                        continue;
                    }
                }
                //else ignore it as it has been evaluated already
                else{
                    continue;
                }
            }
        }
        return false; //No path exists
    }

    //function to construct a path from the goal path.
    protected ArrayList<NodeObj> constructPath(NodeObj goal, NodeObj start){
        ArrayList<NodeObj> path = new ArrayList<NodeObj>();
        path.add(goal);
        NodeObj nextNode = goal.getParent();
        while(nextNode != null){
            path.add(nextNode);
            nextNode = nextNode.getParent();
            //System.out.println("Next Node" + nextNode.getHeuristic());
        }
        return path;
    }

    //function to add nodes based on their heuristic cost from increasing order
    protected boolean addToQueue(ArrayList<NodeObj> list, NodeObj node){
        for (NodeObj listNode:list) {
            if (listNode.getHeuristic() > node.getHeuristic())
                list.add(list.indexOf(listNode),node);
                return true;
        }
        list.add(node);
        return false;
    }

    public ArrayList<NodeObj> getGenPath() {
        return GenPath;
    }
}