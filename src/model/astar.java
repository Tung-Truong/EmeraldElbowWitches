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
        //PriorityQueue<NodeObj> open_queue = new PriorityQueue<NodeObj>();
        ArrayList<NodeObj> open_queue = new ArrayList<NodeObj>();
        open_queue.add(0,start);
        System.out.println(start);
        ArrayList<NodeObj> closed_queue = new ArrayList<NodeObj>();
        //PriorityQueue<NodeObj> closed_queue = new PriorityQueue<NodeObj>();
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
            //System.out.println("Removing stuff from open queue" + current);
            closed_queue.add(current);

            //creates a list of neighbors from the current node, and looks at the neighbors for the next optimal path.
            ArrayList<NodeObj> exploreList = current.getListOfNeighbors();
            for (NodeObj neighbor : exploreList){
                if (!closed_queue.contains(neighbor)){
                    if (!open_queue.contains(neighbor)){
                        neighbor.setParent(current);
                    }
                }
            }
            for (NodeObj neighbor : exploreList) {
                //if closed queue does not have the neighbor, then evaluates the cost of travelling to the next node.
                if (!closed_queue.contains(neighbor)) {
                    //sets the gCost of neighbor which is the distance between neighbor and current as well as sets the heuristic of neighbor
                    neighbor.setgCost(current.getDistance(neighbor));
                    neighbor.setHeuristic(neighbor.getgCost()+ neighbor.getDistance(goal));
                    //if the open queue does not have it then add it to open queue.
                    if (!open_queue.contains(neighbor)) {
                        //System.out.println("Neighbor:" + neighbor);
                        //System.out.println("Open_queue status" + open_queue);
                        addToQueue(open_queue,neighbor);
                        //System.out.println("Open_queue with neighbor" + open_queue);
                    }
                    //if it is in the open queue then look at different one
                    else {
                        /*System.out.println("open neighbor process");
                        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                        //gets the neighbor from the open queue and if the cost is lower than the current one, then set the openNeighbor as the best one.
                        NodeObj openNeighbor = open_queue.get(0);
                        System.out.println(openNeighbor);
                        openNeighbor.setgCost(current.getDistance(neighbor));
                        openNeighbor.setHeuristic(openNeighbor.getDistance(goal)+openNeighbor.getgCost());
                        if(neighbor.getHeuristic()< openNeighbor.getHeuristic()){
                            openNeighbor.setgCost(neighbor.getgCost());
                            openNeighbor.setParent(neighbor.getParent());
                        }*/
                    }

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