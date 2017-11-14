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

    public NodeObj getNearestNeighborFilter(int xLoc, int yLoc)throws InvalidNodeException{
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

    public NodeObj getNearestNeighbor(int xLoc, int yLoc)throws InvalidNodeException{
        double minDist = Double.MAX_VALUE;
        NodeObj closestNode = null;
        for (NodeObj n: nodes){
            if((n.getDistance(xLoc, yLoc)<minDist)){
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

    //removeNode takes in a node to add, and a list of node objs to modify, if the node is in the list remove it. If the node is not there, do nothing
    public boolean removeNode(NodeObj Node, ListOfNodeObjs list){
        for (NodeObj nodes: list.getNodes()) {
            if(nodes.node.getNodeID() == Node.node.getNodeID()){
                list.getNodes().remove(Node);
                return true;
            }
        }
        return false;
    }

    //addNode takes in a node to add, and a list of node objs to modify, if the node is already present, do nothing
    public boolean addNode(NodeObj Node, ListOfNodeObjs list){
        for (NodeObj nodes: list.getNodes()) {
            if(nodes.node.getNodeID() == Node.node.getNodeID()){
                return false;
            }
        }
        list.getNodes().add(Node);
        return true;

    }

    //editNode takes in a node, list of node objs to modify, and a string Operation that picks which operation to do to the list. Modify removes the node and adds the modified node.
    public boolean editNode(NodeObj editNode, ListOfNodeObjs list, String Operation) {
        if (Operation.equals("add")) {
            addNode(editNode, list);
        }
        if (Operation.equals("remove")) {
            removeNode(editNode, list);
        }
        if (Operation.equals("modify")) {
            removeNode(editNode, list);
            addNode(editNode, list);
        }
        return false;
    }

    public boolean removeEdge(){
        return true;
    }
    public boolean addEdge(){
        return true;
    }
    public boolean editEdge(){
        return true;
    }
//Will need to deal with each edge


}
