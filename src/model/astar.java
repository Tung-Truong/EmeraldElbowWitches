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
        LinkedList<NodeObj> open_queue = new LinkedList();
        LinkedList<NodeObj> closed_queue = new LinkedList();
        open_queue.add(start);
        while (open_queue.size() > 0) {
            NodeObj current = getLowest(open_queue);
            closed_queue.add(current);
            open_queue.remove(current);

            if (current.node.getNodeID().equals(current.node.getNodeID())) {
                GenPath = constructPath(goal, start);
                return true;
            }
            ArrayList<NodeObj> neighbors = current.getListOfNeighbors();
            for (NodeObj neighbor : neighbors) {
                if (!open_queue.contains(neighbor)) {
                    neighbor.setParent(current);
                    ArrayList<EdgeObj> edges = current.getListOfEdgeObjs();
                    for (EdgeObj edge : edges) {
                        if (edge.getNodeB().node.getNodeID().equals(neighbor.node.getNodeID()) && edge.getNodeA().node.getNodeID().equals(current.node.getNodeID())) {
                            neighbor.setgCost(edge.getWeight());
                        }
                        if (edge.getNodeA().node.getNodeID().equals(neighbor.node.getNodeID()) && edge.getNodeA().node.getNodeID().equals(current.node.getNodeID())) {
                            neighbor.setgCost(edge.getWeight());
                        }
                    }
                    neighbor.setHeuristic(neighbor.getDistance(goal) + neighbor.getgCost());
                }
                else{
                    if(neighbor.getgCost() > neighbor.getgCost()){
                        neighbor.setParent(current);
                        neighbor.setgCost(current.getgCost());
                    }
                }

            }
        }
        return false;
    }

    private NodeObj getLowest(LinkedList<NodeObj> list) {
        double currentHeuristic = 0;
        NodeObj lowestNode = null;
        for (NodeObj node : list) {
            if (node.getHeuristic() < currentHeuristic) {
                currentHeuristic = node.getHeuristic();
                lowestNode = node;
            }
        }
        return lowestNode;
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