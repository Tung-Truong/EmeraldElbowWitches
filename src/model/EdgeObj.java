package model;

public class EdgeObj {

    public NodeObj nodeA;      // declaring the start and end nodes for the edge to use
    public NodeObj nodeB;
    public String nodeAStr;    // strings to hold the value of nodeA and nodeB's ID
    public String nodeBStr;
    public String edgeID;
    private double weight;     // weight of the edge, used in the A* algorithm to find best path

    /**
     * Overloaded constructor so that edge can take multiple parameters for entry, depending on the situation
     * @param nodeA This is the first node.
     * @param nodeB This is the second node.
     */
    public EdgeObj(NodeObj nodeA, NodeObj nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = this.genWeightFromDistance();
        this.nodeAStr = nodeA.node.getNodeID();
        this.nodeBStr = nodeB.node.getNodeID();
        this.edgeID = nodeA.node.getNodeID() + "_" + nodeB.node.getNodeID();
    }

    /**
     * Generates an edge object, from the provided parameters.
     * @param nodeA This is the first node.
     * @param nodeB This is the second node.
     * @param edgeWeight This is the edge weight.
     */
    public EdgeObj(NodeObj nodeA, NodeObj nodeB, double edgeWeight) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = edgeWeight;
        this.nodeAStr = nodeA.node.getNodeID();
        this.nodeBStr = nodeB.node.getNodeID();
        this.edgeID = nodeA.node.getNodeID() + "_" + nodeB.node.getNodeID();
    }

    /**
     * Generates an edge object, from the provided parameters.
     * @param nodeAStr This is the first node name.
     * @param nodeBStr This is the second node name.
     * @param edgeID This is the IDF of the edge, between the two nodes.
     */
    public EdgeObj(String nodeAStr, String nodeBStr, String edgeID) {
        this.nodeAStr = nodeAStr;
        this.nodeBStr = nodeBStr;
        this.edgeID = edgeID;
    }

    /**
     * Finds the node that is linked to the input node
     * @param node Node to be checked.
     * @return NodeObj the node that is linked.
     * @throws InvalidNodeException Thrown if node is not linked to any other node.
     */
    public NodeObj getOtherNodeObj(NodeObj node) throws InvalidNodeException {
        if (node == this.nodeA)
            return nodeB;
        else if (node == this.nodeB)
            return nodeA;
        else
            throw new InvalidNodeException("given node not associated with this edge");
    }


    /**
     * Returns the weight of the current edge.
     * @return double
     */
    public double getWeight() {
        return weight;
    }


    /**
     * Set the weight of the edge to a specific value.
     * @param edgeWeight The weight of the edge to be set.
     */
    public void setWeight(double edgeWeight) {
        weight = edgeWeight;
    }

    /**
     * Performs mathematical distance between the start and end location nodes (in pixels)
     * @return double distance between the two nodes.
     */
    public double genWeightFromDistance() {
        int ax = nodeA.node.getxLoc();
        int ay = nodeA.node.getyLoc();
        int bx = nodeB.node.getxLoc();
        int by = nodeB.node.getyLoc();

        return Math.abs(Math.sqrt(((ax - bx) * (ax - bx)) + ((ay - by) * (ay - by))));
    }

    /**
     * Converts the EdgeEntity to the EdgeObject.
     * @return Edge the generated edge.
     */
    public Edge objToEntity() {
        return new Edge(this.nodeAStr, this.nodeBStr, this.edgeID);
    }

    // getters and setters--------------------------------------------------------------------

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
