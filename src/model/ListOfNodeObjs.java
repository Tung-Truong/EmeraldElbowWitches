package model;

import java.util.ArrayList;

public class ListOfNodeObjs {

    private ArrayList<NodeObj> nodes;
    private int i;

    public ListOfNodeObjs(ArrayList<NodeObj> nodes) {
        this.nodes = nodes;
        i = 0;
    }

    // ---------------------Getters----------------------------
    public Double getDistance(NodeObj nodeA, NodeObj nodeB) {
        return Math.random();

    }

/*public NodeObj getNearestNeighbor(int xLoc, int yLoc){
    return new NodeObj(node);
}*/

    // ---------------------Setters----------------------------
    public void setEdgeWeight(NodeObj nodeA, NodeObj nodeB, int edgeWeight) {

    }

    public void setAllNodeObjToGoal(NodeObj goalNode) {

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
