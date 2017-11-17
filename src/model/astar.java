package model;

import controller.Main;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class astar {


    public astar() {

    }

    private ArrayList<NodeObj> GenPath;

    public boolean pathfind(NodeObj start, NodeObj goal, GraphicsContext gc1) {
        //open_queue contains all unexplored NodeObjs, starts with just the start NodeObj at the first position
        //closed_queue contains all explored NodeObjs, which starts empty
        ArrayList<NodeObj> open_queue = new ArrayList<NodeObj>();
        open_queue.add(0, start);
        System.out.println(start);
        ArrayList<NodeObj> closed_queue = new ArrayList<NodeObj>();
        //G cost of going to start from start is zero
        //Heuristic of the start is just distance to goal using getDistance();
        double startG = 0;
        start.setHeuristic(startG + start.getDistance(goal));

        //while true loop which stops when open_queue is equal to 0.
        //this loop accounts of neighbor nodes not being evaluated
        while (true) {
                //while loop that stops when open queue is greater than zero.
                while (open_queue.size() > 0) {
                    //gets teh elemetn with the lowest f cost
                    NodeObj current = open_queue.get(0);

                    //if the current is goal, generate the path and print it out and end function
                    if (current == goal) {
                        System.out.println("Goal is " + goal);
                        GenPath = constructPath(goal, start);
                        System.out.println(GenPath);
                        return true;
                    }
                    //removes the element with the lowest f cost
                    open_queue.remove(0);

                    //creates a list of neighbors from the current node, and looks at the neighbors for the next optimal path.
                    ArrayList<NodeObj> exploreList = current.getListOfNeighbors();
                    for (NodeObj neighbor : exploreList) {
                        //if closed queue does not have the neighbor, then evaluates the cost of travelling to the next node.
                        if (!closed_queue.contains(neighbor)) {
                            //sets the the gCost of the neighbor based on the edge weight, if not uses getDistance function
                            //flag that keeps track if teh function was able to get the edgeweight from EdgeObj

                            boolean edgeWeightFlag = false;

                            //gets the list of edges from the neighbor and matches the edgeobj with the current node and hte neighbor node contained
                            //then sets the gCost of the neighbor to that weight
                            ArrayList<EdgeObj> edges = new ArrayList<EdgeObj>();
                            edges = neighbor.getListOfEdgeObjs();
                            for (EdgeObj edge: edges) {
                                if(edge.getNodeB().node.getNodeID().equals(neighbor.node.getNodeID())){
                                    if(edge.getNodeA().node.getNodeID().equals(current.node.getNodeID())){
                                        edgeWeightFlag = true;
                                        neighbor.setgCost(edge.getWeight());
                                    }
                                }
                                if(edge.getNodeA().node.getNodeID().equals(neighbor.node.getNodeID())){
                                    if(edge.getNodeB().node.getNodeID().equals(current.node.getNodeID())){
                                        edgeWeightFlag = true;
                                        neighbor.setgCost(edge.getWeight());
                                    }
                                }

                            }

                            //if function can't get the edgeWeight, set gCost with the get distance function
                            if(edgeWeightFlag == false){
                                neighbor.setgCost(current.getDistance(neighbor));
                            }
                            neighbor.setHeuristic(neighbor.getgCost() + neighbor.getDistance(goal));
                            //if the open queue does not have it then add it to open queue and set the parent to the current node.
                            if (!open_queue.contains(neighbor)) {
                                open_queue = addToQueue(open_queue, neighbor);
                                neighbor.setParent(current);
                            }
                            //else do nothing
                            else {
                                continue;
                            }
                        }
                        //else ignore it as it has been evaluated already
                        else {
                            continue;
                        }
                    }
                    closed_queue.add(current);
                }
                //for loop that looks at the neighbors in the closed queue, if it is not in the closed queue, then add to open queue
                for (NodeObj exploredNodes : closed_queue) {
                    ArrayList<NodeObj> exploredList = exploredNodes.getListOfNeighbors();
                    for (NodeObj nodes : exploredList) {
                        if (!closed_queue.contains(nodes)) {
                            open_queue = addToQueue(open_queue, nodes);
                            // if the parent of the node is null and if the node is not the start node then set the parent
                            if (nodes.getParent() == null && nodes.node.getNodeID() != start.node.getNodeID()) {
                                nodes.setParent(exploredNodes);
                            }
                        }
                    }
                }
                //statement that breaks out of the while(true) loop
                if (open_queue.size() == 0) {
                    break;

                }
        }
        return false; //No path exists

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
        return GenPath;
    }
}