package model;

import java.util.ArrayList;

public class ListOfNodeObjs {

    private ArrayList<NodeObj> nodes;
    public String currentBuilding;

    public ListOfNodeObjs(ArrayList<NodeObj> nodes) {
        this.nodes = nodes;
        this.currentBuilding = "45 Francis";
    }

    // ---------------------Getters----------------------------


    public ArrayList<NodeObj> getNodes() {
        return this.nodes;
    }

    public Double getDistance(NodeObj nodeA, NodeObj nodeB) {
        return nodeA.getDistance(nodeB);
    }

    public NodeObj getNearestNeighbor(int xLoc, int yLoc)throws InvalidNodeException{
        double minDist = Double.MAX_VALUE;
        NodeObj closestNode = null;
        for (NodeObj n: nodes){
            if((n.getDistance(xLoc, yLoc)<minDist) && (n.getNode().getBuilding().equals(this.currentBuilding))){
                closestNode = n;
                minDist = n.getDistance(xLoc, yLoc);
            }
        }
        if(closestNode == null)
            throw new InvalidNodeException("no corresponding edge");
        return closestNode;

    }

    // ---------------------Setters----------------------------
    public void setEdgeWeight(NodeObj nodeA, NodeObj nodeB, int edgeWeight) {

    }

    public void setCurrentBuilding(String currentBuilding) {
        this.currentBuilding = currentBuilding;
    }

    // ---------------------General Functionality--------------
    public void addNodeObjToList(NodeObj node) {
        nodes.add(node);
        // todo: needs to also add the node to the csv file
    }

    public boolean removeNodeObj(NodeObj node) {
        boolean wasSuccessful;
        wasSuccessful = nodes.remove(node);
        return wasSuccessful;
        // todo: needs to also remove the node from the csv file
    }

    public boolean pair(EdgeObj newEdge){
        int flag = 0;
        for(NodeObj n: this.nodes){
            if(n.node.getNodeID().equals(newEdge.nodeAStr)){
                n.addEdge(newEdge);
                newEdge.setNodeA(n);
                flag++;
            }else if(n.node.getNodeID().equals(newEdge.nodeBStr)){
                n.addEdge(newEdge);
                newEdge.setNodeB(n);
                flag++;
            }
        }
        if(flag<2){
            return false;
        }else{
            return true;
        }
    }

//Will need to deal with each edge


}
