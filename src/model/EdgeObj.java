package model;

public class EdgeObj {

    private NodeObj nodeA;
    private NodeObj nodeB;
    private int weight;

    public EdgeObj(NodeObj nodeA, NodeObj nodeB, int edgeWeight) {
        this.nodeA = new NodeObj();
        this.nodeB = new NodeObj();
        this.weight = edgeWeight;
    }

    //find the node that is linked to the input node
    public NodeObj getOtherNodeObj(NodeObj nodeA) {
        return nodeB;
    }

    //return the weight of the current edge
    public int getWeight(){
        return weight;
    }

    //set the weight of the edge to a specific value
    public void setWeight(int edgeWeight){
        weight = edgeWeight;
    }

    public double genWeightFromDistance(){
        return 10;
    }
}
