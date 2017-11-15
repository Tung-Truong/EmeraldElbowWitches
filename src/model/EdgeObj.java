package model;

public class EdgeObj {

    public NodeObj nodeA;
    public NodeObj nodeB;
    public String nodeAStr;
    public String nodeBStr;
    public String edgeID;
    private double weight;


    public EdgeObj(NodeObj nodeA, NodeObj nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = this.genWeightFromDistance();
        this.nodeAStr = nodeA.node.getNodeID();
        this.nodeBStr = nodeB.node.getNodeID();
        this.edgeID = nodeA.node.getNodeID() + "_" + nodeB.node.getNodeID();
    }

    public EdgeObj(NodeObj nodeA, NodeObj nodeB, double edgeWeight) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = edgeWeight;
        this.nodeAStr = nodeA.node.getNodeID();
        this.nodeBStr = nodeB.node.getNodeID();
        this.edgeID = nodeA.node.getNodeID() + "_" + nodeB.node.getNodeID();
    }

    public EdgeObj(String nodeAStr, String nodeBStr, String edgeID){
        this.nodeAStr = nodeAStr;
        this.nodeBStr = nodeBStr;
        this.edgeID = edgeID;
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
        System.out.println("EDGE ID: " + this.edgeID);
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

    public Edge objToEntity(){
        return new Edge(this.nodeAStr, this.nodeBStr, this.edgeID);
    }

    public NodeObj getNodeA() {
        return nodeA;
    }

    public void setNodeA(NodeObj nodeA) {
        this.nodeA = nodeA;
    }

    public NodeObj getNodeB() {
        return nodeB;
    }

    public void setNodeB(NodeObj nodeB) {
        this.nodeB = nodeB;
    }
}
