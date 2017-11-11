package model;

public class Edge {
    private String nodeAID;
    private String nodeBID;
    private String edgeID;

    public Edge(String nodeAID, String nodeBID, String edgeID){
        this.edgeID=edgeID;
        this.nodeAID=nodeAID;
        this.nodeBID=nodeBID;
    }

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
