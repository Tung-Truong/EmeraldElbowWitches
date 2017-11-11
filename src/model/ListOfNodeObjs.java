package model;

import java.util.ArrayList;

public class ListOfNodeObjs {

    private ArrayList<NodeObj> nodes;

    public ListOfNodeObjs(ArrayList<NodeObj> nodes) {
        this.nodes = nodes;
    }

    // ---------------------Getters----------------------------
public Double getDistance(NodeObj nodeA, NodeObj nodeB){
        return Math.random();

}

/*public NodeObj getNearestNeighbor(int xLoc, int yLoc){
    return new NodeObj(node);
}*/

    // ---------------------Setters----------------------------
public void setEdgeWeight(NodeObj nodeA, NodeObj nodeB, int edgeWeight){

}

public void setAllNodeObjToGoal(NodeObj goalNode){

   }

    // ---------------------General Functionality--------------
public void addNodeObjToList(NodeObj node){
    nodes.add(node);
    // todo: needs to also add the node to the csv file
}

public boolean removeNodeObj(NodeObj node){
    boolean wasSuccessful;
    wasSuccessful = nodes.remove(node);
    return wasSuccessful;
    // todo: needs to also remove the node from the csv file
}
//Will need to deal with each edge


}
