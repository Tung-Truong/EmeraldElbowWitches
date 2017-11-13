package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class NodeObjTest {

    private Node node1 = new Node("20", "100", "1", "45 Francis", "HALL", "Hall1", "Hallway 2", "Team E", "EHALL00101");
    private Node node2 = new Node("50", "30", "1", "45 Francis", "HALL", "Hall2", "Hallway 2", "Team E", "EHALL00201");
    private Node node3 = new Node("1000", "400", "1", "45 Francis", "STAI", "Stair1", "Stairway 2", "Team E", "ESTAI00101");
    private NodeObj nodeA = new NodeObj(node1);
    private NodeObj nodeB = new NodeObj(node2);
    private NodeObj nodeC = new NodeObj(node3);

    private Edge edge1 = new Edge(node1.getNodeID(), node2.getNodeID(), node1.getNodeID() + "_" + node2.getNodeID());
    private Edge edge2 = new Edge(node2.getNodeID(), node3.getNodeID(), node2.getNodeID() + "_" + node3.getNodeID());
    private EdgeObj edgeA = new EdgeObj(nodeA, nodeB);
    private EdgeObj edgeB = new EdgeObj(nodeB, nodeC);

    @Test
    public void getListOfNeighbors() throws Exception {
    }

    @Test
    public void getListOfEdgeObjs() throws Exception {
    }

    @Test
    public void addEdge() throws Exception {
    }

    @Test
    public void addEdge1() throws Exception {
        //hi
    }

    @Test
    public void killEdge() throws Exception {
    }

    @Test
    public void getDistToGoal() throws Exception {
    }

    @Test
    public void getHeuristic() throws Exception {
    }

    @Test
    public void setHeuristic() throws Exception {
    }

    @Test
    public void getParent() throws Exception {
    }

    @Test
    public void setParent() throws Exception {
    }

    @Test
    public void getgCost() throws Exception {
    }

    @Test
    public void setgCost() throws Exception {
    }

    @Test
    public void getEdgeObj() throws Exception {
    }

    @Test
    public void getDistance() throws Exception {        // Node1: 20, 100  Node2: 50, 30    Node3: 1000, 400
        assertEquals(76.1, nodeA.getDistance(nodeB), 1);
        assertEquals(76.1, nodeB.getDistance(nodeA), 1);
        assertEquals(1024, nodeA.getDistance(nodeC), 1);
        assertEquals(1019, nodeC.getDistance(nodeB), 1);
    }

    @Test
    public void getDistance1() throws Exception {
        assertEquals(80, nodeA.getDistance(100, 100), 1);
        assertEquals(0, nodeC.getDistance(1000, 400), 1); // if the parameters are the same as the node x&y
        assertEquals(14084, nodeB.getDistance(9999, 9999), 1);
    }

}