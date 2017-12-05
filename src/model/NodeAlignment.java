package model;

import java.util.ArrayList;

public class NodeAlignment {
    private ArrayList<Node> changedNodes;
    private ArrayList<Node> nodesToRemove;

    //getters
    public ArrayList<Node> getChangedNodes() {
        return changedNodes;
    }

    public ArrayList<Node> getNodesToRemove() {
        return nodesToRemove;
    }

    //setters
    public void setChangedNodes(ArrayList<Node> changedNodes) {
        this.changedNodes = changedNodes;
    }

    public void setNodesToRemove(ArrayList<Node> nodesToRemove) {
        this.nodesToRemove = nodesToRemove;
    }

    //function that takes in a start and end node, and gets the nodes between them. Then creates a line based on the start and end.
    //then creates new nodes based on the closest points on the line to the original nodes in the middle and creates a ned set of nodes based on them
    public boolean nodeAlignment(NodeObj start, NodeObj end){
        ArrayList<NodeObj> nodes = new ArrayList<>();

        int startx = start.node.getxLoc();
        int starty = start.node.getyLoc();

        int endx = end.node.getxLoc();
        int endy = end.node.getyLoc();

        int slope = 0;
        int perpSlope = 0;
        int b = 0;
        int perpB = 0;

        //Uses astar to find the nodes between them
        astar path = new astar();
        path.pathfind(start, end);
        nodes = path.getGenPath();

        //creates a list of nodes to modify/remove
        for (NodeObj node:nodes) {
            nodesToRemove.add(node.getNode());
        }

        //if the slope of the line is undefinited, just set all the nodes x coord to be the same as the start x coord
        if (startx == endx){
            for (NodeObj node:nodes) {
                Node newNode;
                newNode = node.node;
                newNode.setLoc(startx,node.node.getyLoc());
                changedNodes.add(newNode);
            }
            return true;
        }
        //else if the slope of the line is zero, just set all the nodes y coord to be the same as the start y coord
        else if(starty == endy){
            for (NodeObj node:nodes) {
                Node newNode;
                newNode = node.node;
                newNode.setLoc(node.node.getxLoc(),starty);
                changedNodes.add(newNode);
            }
            return true;
        }
        //else calculate the equation of the line, as well as the perpendicular line given a node line
        //use system of equations to find the closest point from the last node line, and set it to the new point on the line
        else{
            slope = (endy-starty)/(endx-startx);
            perpSlope = ~slope;

            b = starty - startx*slope;
            for (NodeObj node: nodes) {
                perpB = node.node.getyLoc() - node.node.getxLoc()*perpSlope;
                int newX = -(b-perpB)/(slope-perpSlope);
                int newY = slope*newX - b;
                Node newNode;
                newNode = node.node;
                newNode.setLoc(newX,newY);
                changedNodes.add(newNode);
            }
        }
        return false;
    }

}
