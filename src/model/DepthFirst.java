package model;

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirst extends PathingAlgorithm {
    private ArrayList<NodeObj> GenPath;

    //depth first search using a stack that takes in a start node and goal node. It goes until it runs out of nodes to explore, or the popped node is the goal
    public boolean pathfind(NodeObj start, NodeObj goal) {
        ArrayList<NodeObj> explored = new ArrayList<NodeObj>();
        Stack<NodeObj> stack = new Stack<NodeObj>();
        stack.add(start);
        explored.add(start);
        while (!stack.isEmpty()) {
            NodeObj current = stack.pop();
            if (current.node.getNodeID().equals(goal.node.getNodeID())) {
                explored.add(current);
                GenPath = constructPath(goal, start);
                return true;
            }

            ArrayList<NodeObj> neighbours = current.getListOfNeighbors();
            for (int i = 0; i < neighbours.size(); i++) {
                NodeObj node = neighbours.get(i);
                if (node != null && !explored.contains(node)) {
                    stack.add(node);
                    explored.add(node);
                    node.setParent(current);
                }
            }
        }
        return false;
    }

    //getter
    public ArrayList<NodeObj> getGenPath() {
        System.out.println("DEPTH FIRST");
        return GenPath;
    }
}
