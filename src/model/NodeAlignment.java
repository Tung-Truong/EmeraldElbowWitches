package model;

import java.util.ArrayList;

public class NodeAlignment {
    private ArrayList<NodeObj> changedNodes = new ArrayList<NodeObj>();

    //getters
    public ArrayList<NodeObj> getChangedNodes() {
        return changedNodes;
    }

    //setters
    public void setChangedNodes(ArrayList<NodeObj> changedNodes) {
        this.changedNodes = changedNodes;
    }

    //function that takes in a start and end node, and gets the nodes between them. Then creates a line based on the start and end.
    //then creates new nodes based on the closest points on the line to the original nodes in the middle and creates a ned set of nodes based on them
    public boolean nodeAlignment(NodeObj start, NodeObj end){
        ArrayList<NodeObj> nodes = new ArrayList<>();

        int startx = start.node.getxLoc();
        int starty = start.node.getyLoc();

        int endx = end.node.getxLoc();
        int endy = end.node.getyLoc();

        double slope = 0.0;
        double perpSlope = 0.0;
        int b = 0;
        int perpB = 0;

        slope = (endy-starty)/(endx-startx);

        //Uses astar to find the nodes between them
        astar path = new astar();
        path.pathfind(start, end);
        nodes = path.getGenPath();
        System.out.println(nodes.size());
        nodes.remove(nodes.size() - 1);
        System.out.println(nodes.size());
        //if the slope of the line is undefinited, just set all the nodes x coord to be the same as the start x coord
        if (slope >= 999 || slope < -999){
            for (NodeObj node:nodes) {
                node.node.setLoc(startx,node.node.getyLoc());
                changedNodes.add(node);
            }
            return true;
        }
        //else if the slope of the line is zero, just set all the nodes y coord to be the same as the start y coord
        else if(slope == 0){
            for (NodeObj node:nodes) {
                node.node.setLoc(node.node.getxLoc(),starty);
                changedNodes.add(node);
            }
            return true;
        }
        //else calculate the equation of the line, as well as the perpendicular line given a node line
        //use system of equations to find the closest point from the last node line, and set it to the new point on the line
        else{

            if(slope < 0){
                perpSlope = Math.pow(slope, -1.0)* -1;
            } else {
                perpSlope = Math.pow(slope, -1.0);
            }

            b = (int) (starty - (startx*slope));
            for (NodeObj node: nodes) {
                perpB = (int) (node.node.getyLoc() - (node.node.getxLoc()*perpSlope));
                int newX = (int) ((b-perpB)/(-slope+perpSlope));
                int newY = (int) (slope*newX + b);
                System.out.println("" + "   " + node + "   " + perpB + "   " + newX + "   " + newY);
                node.node.setLoc(newX,newY);
                System.out.println(" " + node);
                changedNodes.add(node);

            }
        }
        return false;
    }

}