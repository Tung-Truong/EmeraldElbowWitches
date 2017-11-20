package model;

import java.sql.SQLException;
import java.util.ArrayList;

public class ListOfNodeObjs {

    private ArrayList<NodeObj> nodes;
    public String currentFloor;

    public ListOfNodeObjs(ArrayList<NodeObj> nodes) {
        this.nodes = nodes;
        this.currentFloor = "1";
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
            if((n.getDistance(xLoc, yLoc)<minDist) && (n.getNode().getFloor().equals(this.currentFloor))){
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

    public ArrayList<NodeObj> getFilteredNodes(){
        ArrayList<NodeObj> filteredNodes = new ArrayList<NodeObj>();
        for (NodeObj n : this.nodes){
            if(n.getNode().getFloor().equals(this.currentFloor)){
                filteredNodes.add(n);
            }
        }
        return filteredNodes;
    }

    public NodeObj getNodeObjByID(String nodeID){
        for(NodeObj n : this.nodes){
            if(n.node.getNodeID().equals(nodeID)){
                return n;
            }
        }
        return null;
    }

    // ---------------------Setters----------------------------
    public void setEdgeWeight(NodeObj nodeA, NodeObj nodeB, int edgeWeight) {

    }

    public void setCurrentFloor(String currentFloor) {
        this.currentFloor = currentFloor;
    }

    // ---------------------General Functionality--------------

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
    public void removeNode(NodeObj nodeToDelete){
        NodeObj actualDeleteNode = null;
        ArrayList<EdgeObj> actualDeleteEdges = null;
        for (NodeObj nodes: this.nodes) {
            if(nodes.getNode().getNodeID().equals(nodeToDelete.getNode().getNodeID())){
                System.out.println("NUM NEIGHBORS: " + nodes.getListOfEdgeObjs().size());
                for (int i = nodes.getListOfEdgeObjs().size()-1; i >= 0; i--){
                    EdgeObj e = nodes.getListOfEdgeObjs().get(i);
                    try {
                        deleteEdge(nodes.getNode().getNodeID(), e.getOtherNodeObj(nodes).node.getNodeID());
                    } catch (InvalidNodeException e1) {
                        e1.printStackTrace();
                    }
                }
                actualDeleteNode = nodes;
                System.out.println("found node to delete");
            }
        }
        this.nodes.remove(actualDeleteNode);
        try {
            DeleteDB.delNode(nodeToDelete.node.getNodeID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //addNode takes in a node to add, and a list of node objs to modify, if the node is already present, do nothing
    public void addEditNode(NodeObj nodeToAdd) throws SQLException {
        boolean alreadyExists = false;
        for(NodeObj node : this.nodes){
            if(node.getNode().getNodeID().equals(nodeToAdd.getNode().getNodeID())){
                alreadyExists = true;
                node.setNode(nodeToAdd.getNode());
            }
        }
        if(!alreadyExists) {
            this.nodes.add(nodeToAdd);
        }
    }

    public void deleteEdge(String nodeAID, String nodeBID){
        NodeObj nodeObjA = getNodeObjByID(nodeAID);
        NodeObj nodeObjB = getNodeObjByID(nodeBID);
        if(nodeObjA!=null && nodeObjB!=null) {
            EdgeObj thisEdgeToDelete = nodeObjA.getEdgeObj(nodeObjB);
            nodeObjA.getListOfEdgeObjs().remove(thisEdgeToDelete);
            nodeObjB.getListOfEdgeObjs().remove(thisEdgeToDelete);
            try {
                DeleteDB.delEdge(nodeAID + "_" + nodeBID);
                DeleteDB.delEdge(nodeBID + "_" + nodeAID);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

//Will need to deal with each edge


}
