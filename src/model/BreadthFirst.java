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
            NodeObj node=queue.remove();
            if(node.node.getNodeID() == goal.node.getNodeID()){
                explored.add(node);
                GenPath = explored;
                return true;
            }
            ArrayList<NodeObj> neighbours=node.getListOfNeighbors();
            for (int i = 0; i < neighbours.size(); i++){
                NodeObj n=neighbours.get(i);
                if(n!=null && !explored.contains(n)){
                    queue.add(n);
                    explored.add(n);
                }
            }
        }
        return false;
    }

    //getter
    public ArrayList<NodeObj> getGenPath() {
        return GenPath;
    }
}
