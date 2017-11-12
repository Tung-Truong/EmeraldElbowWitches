package model;

public class EdgeObj {

    public NodeObj nodeA;
    public NodeObj nodeB;
    private double weight;

    public EdgeObj(NodeObj nodeA, NodeObj nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = this.genWeightFromDistance();
    }

    public EdgeObj(NodeObj nodeA, NodeObj nodeB, double edgeWeight) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = edgeWeight;
    }

    //find the node that is linked to the input node
    public NodeObj getOtherNodeObj(NodeObj node) throws InvalidNodeException {
        if (node == this.nodeA)
            return nodeB;
        else if (node == this.nodeB)
            return nodeA;
        else
            throw new InvalidNodeException("given node not associated with this edge");
    }

    //return the weight of the current edge
    public double getWeight(){
        return weight;
    }

    //set the weight of the edge to a specific value
    public void setWeight(double edgeWeight){
        weight = edgeWeight;
    }

    public double genWeightFromDistance() {
        int ax = nodeA.node.getxLoc();
        int ay = nodeA.node.getyLoc();
        int bx = nodeB.node.getxLoc();
        int by = nodeB.node.getyLoc();

        return Math.abs( Math.sqrt( ((ax-bx)*(ax-bx)) + ((ay-by)*(ay-by)) ) );
    }
}
