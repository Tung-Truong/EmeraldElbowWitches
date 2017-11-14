package model;

import java.util.ArrayList;

public class NodeObj {
    public Node node;
    private double heuristic;
    private NodeObj parent;
    private double gCost;
    private ArrayList<EdgeObj> listOfEdgeObjs;

    public NodeObj(Node myNode){
        node = myNode;
        listOfEdgeObjs = new ArrayList<EdgeObj>();
    }

    public ArrayList<NodeObj> getListOfNeighbors(){
        ArrayList<NodeObj> nodeList = new ArrayList<NodeObj>();
        for (EdgeObj edg: listOfEdgeObjs){ //find all nodes from all edges in node
            try {//thrown from getOtherNodeObj
                nodeList.add(edg.getOtherNodeObj(this));
            } catch (InvalidNodeException e) {
                e.printStackTrace();
            }
        }
        return nodeList;
    }

    //can throw null
    public ArrayList<EdgeObj> getListOfEdgeObjs(){
        return listOfEdgeObjs;
    }

    // ---------------------General Functionality----------------------------

    // creates an edge to another node, adds edge to the list for the map

    public void addEdge(NodeObj nodeB){
        EdgeObj edge = new EdgeObj(this, nodeB);
        listOfEdgeObjs.add(edge);
        edge.setWeight(edge.genWeightFromDistance());
    }

    public void addEdge(EdgeObj newEdge){
        listOfEdgeObjs.add(newEdge);
    }

    // creates an edge to another node, adds edge to the list for the map
    public void addEdge(NodeObj nodeB, int edgeWeight){
        EdgeObj edge = new EdgeObj(this, nodeB, edgeWeight);
        listOfEdgeObjs.add(edge);
    }

    public void killEdge(NodeObj nodeB){
        for (EdgeObj e:listOfEdgeObjs) {
            try {
                if (e.getOtherNodeObj(this).node.getNodeID() == nodeB.node.getNodeID()) {
                    listOfEdgeObjs.remove(e);
                }
            } catch (InvalidNodeException e1) {
                e1.printStackTrace();
            }
        }
    }

    // ---------------------Getters----------------------------


    public double getHeuristic(){
        return this.heuristic;
    }

    public void setHeuristic(double heuristic){
        this.heuristic = heuristic;
    }

    public NodeObj getParent() {
        return this.parent;
    }

    public void setParent(NodeObj parent) {
        this.parent = parent;
    }

    public double getgCost() {
        return this.gCost;
    }

    public void setgCost(double gCost) {
        this.gCost = gCost;
    }

    public EdgeObj getEdgeObj(NodeObj nodeB){
        for (EdgeObj e:listOfEdgeObjs) {
            try {
                if (e.getOtherNodeObj(this).node.getNodeID() == nodeB.node.getNodeID()) {
                    return e;
                }
            } catch (InvalidNodeException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public Node getNode() {
        return node;
    }

    public Double getDistance(NodeObj nodeB){
        int ax = node.getxLoc();
        int ay = node.getyLoc();
        int bx = nodeB.node.getxLoc();
        int by = nodeB.node.getyLoc();
        return Math.abs( Math.sqrt( ((ax-bx)*(ax-bx)) + ((ay-by)*(ay-by)) ) );
    }

    public Double getDistance(int ax, int ay){
        int bx = node.getxLoc();
        int by = node.getyLoc();
        return Math.abs( Math.sqrt( ((ax-bx)*(ax-bx)) + ((ay-by)*(ay-by)) ) );
    }

}
