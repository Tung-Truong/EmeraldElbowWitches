package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class BreadthFirst {
    private ArrayList<NodeObj> GenPath;

    public boolean bfs(NodeObj start, NodeObj goal){
        ArrayList<NodeObj> explored = new ArrayList<NodeObj>();
        LinkedList<NodeObj> queue = new LinkedList<NodeObj>();
        queue.add(start);
        explored.add(start);
        while (!queue.isEmpty()){
            NodeObj node = queue.remove();
            if(node.node.getNodeID().equals(goal.node.getNodeID())){
                explored.add(node);
                GenPath = constructPath(goal, start);
                return true;
            }
            ArrayList<NodeObj> neighbours = node.getListOfNeighbors();
            for (int i = 0; i < neighbours.size(); i++){
                NodeObj n=neighbours.get(i);
                if(n != null && !explored.contains(n)){
                    queue.add(n);
                    explored.add(n);
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
        while (!nextNode.node.getNodeID().equals(start.node.getNodeID())) {
            path.add(nextNode);
            nextNode = nextNode.getParent();
        }
        path.add(start);
        return path;
    }

    //getter
    public ArrayList<NodeObj> getGenPath() {
        return GenPath;
    }
}
