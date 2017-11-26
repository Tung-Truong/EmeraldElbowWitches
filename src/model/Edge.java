package model;

public class Edge {
    private String nodeAID;     // Node ID from the first node (Start location)
    private String nodeBID;     // Node ID from the last node (End location)
    private String edgeID;      // Edge ID combining the two nodes (startLocation_endLocation)

    public Edge(String nodeAID, String nodeBID, String edgeID) {     // constructor for edges
        this.edgeID = edgeID;
        this.nodeAID = nodeAID;
        this.nodeBID = nodeBID;
    }

    // getters--------------------------------------
    public String getEdgeID() {
        return edgeID;
    }

    public String getNodeBID() {
        return nodeBID;
    }

    public String getNodeAID() {
        return nodeAID;
    }
}
