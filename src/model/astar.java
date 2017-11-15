package model;

import controller.Main;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class astar {
    int i;

    //getHeuristic() Never used
    public astar() {

    }

    private ArrayList<NodeObj> GenPath;

    public boolean pathfind(NodeObj start, NodeObj goal, GraphicsContext gc1) {
        i = 0;
        //open_queue contains all unexplored NodeObjs, starts with just the start NodeObj
        //closed_queue contains all explored NodeObjs, which starts empty
        ArrayList<NodeObj> open_queue = new ArrayList<NodeObj>();
        open_queue.add(0, start);
        System.out.println(start);
        ArrayList<NodeObj> closed_queue = new ArrayList<NodeObj>();
        //G cost of going to start from start is zero
        double startG = 0;
        start.setHeuristic(startG + start.getDistance(goal));

        while (true) {
                while (open_queue.size() > 0) {
                    NodeObj current = open_queue.get(0); //gets the element with the lowest f cost

                    /*gc1.fillText("" + i, current.node.getxLoc() * 1397 / 5000,
                            current.node.getyLoc() * 950 / 3400);*/
                    i++;


                    if (current == goal) {
                        System.out.println("Goal is " + goal);
                        GenPath = constructPath(goal, start);
                        System.out.println(GenPath);
                        return true;
                    }
                    open_queue.remove(0);

                    //creates a list of neighbors from the current node, and looks at the neighbors for the next optimal path.
                    ArrayList<NodeObj> exploreList = current.getListOfNeighbors();
                    for (NodeObj neighbor : exploreList) {
                        //if closed queue does not have the neighbor, then evaluates the cost of travelling to the next node.
                        if (!closed_queue.contains(neighbor)) {
                            //sets the the gCost of the neighbor based on the edge weight, if not uses getDistance function
                            boolean edgeWeightFlag = false;
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
                for (NodeObj exploredNodes : closed_queue) {
                    ArrayList<NodeObj> exploredList = exploredNodes.getListOfNeighbors();
                    for (NodeObj nodes : exploredList) {
                        if (!closed_queue.contains(nodes)) {
                            //sets the gCost of neighbor which is the distance between neighbor and current as well as sets the heuristic of neighbor
                            //if the open queue does not have it then add it to open queue and set the parent to the current node.
                            open_queue = addToQueue(open_queue, nodes);
                            // if the parent of the node is null and if the node is not the start node then set the parent
                            if (nodes.getParent() == null && nodes.node.getNodeID() != start.node.getNodeID()) {
                                nodes.setParent(exploredNodes);
                            }
                        }
                    }
                }
                if (open_queue.size() == 0) {
                    break;

                }
        }
        return false; //No path exists

    }

    //function to construct a path from the goal path.
    protected ArrayList<NodeObj> constructPath(NodeObj goal, NodeObj start) {
        ArrayList<NodeObj> path = new ArrayList<NodeObj>();
        path.add(goal);
        NodeObj nextNode = goal.getParent();
        while (!nextNode.node.getNodeID().equals(start.node.getNodeID())){
            path.add(nextNode);
            nextNode = nextNode.getParent();
            //System.out.println("Next Node" + nextNode.getHeuristic());
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

    public ArrayList<NodeObj> getGenPath() {
        return GenPath;
    }
}