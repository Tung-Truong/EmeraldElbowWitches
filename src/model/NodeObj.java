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
        nodeList.add(this);
        return nodeList;
    }

    public ArrayList<EdgeObj> getListOfEdgeObjs(){
        ArrayList<EdgeObj> edgeList = new ArrayList<EdgeObj>();
        edgeList.add(new EdgeObj(this, new NodeObj(node), 0));
        return edgeList;
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

    public void killEdge(NodeObj nodeB) throws InvalidNodeException {
        for (EdgeObj e:listOfEdgeObjs) {
            if (e.nodeA == this && e.nodeB == nodeB)
                listOfEdgeObjs.remove(e);
            else if (e.nodeB == this && e.nodeA == nodeB)
                listOfEdgeObjs.remove(e);
            else
                throw new InvalidNodeException("no corresponding edge");
        }
    }

    // ---------------------Getters----------------------------

    public double getDistToGoal(){
        return 1;
    }

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

    public EdgeObj getEdgeObj(NodeObj nodeB) throws InvalidNodeException {
        for (EdgeObj e:listOfEdgeObjs) {
            if (e.nodeA == this && e.nodeB == nodeB)
                return e;
            else if (e.nodeB == this && e.nodeA == nodeB)
                return e;
            else
                throw new InvalidNodeException("no corresponding edge");
        }
        return null; // this garbage...
    }

}
