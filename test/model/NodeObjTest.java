package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NodeObjTest {

    private Node node1;
    private Node node2;
    private Node node3;
    private NodeObj nodeA;
    private NodeObj nodeB;
    private NodeObj nodeC;

    private Edge edge1;
    private Edge edge2;
//    private Edge edge3;
    private EdgeObj edgeA;
    private EdgeObj edgeB;

    @Before
    public void setUp() throws Exception {
        node1 = new Node("20", "100", "1", "45 Francis", "HALL", "Hall1", "Hallway 2", "Team E", "EHALL00101");
        node2 = new Node("50", "30", "1", "45 Francis", "HALL", "Hall2", "Hallway 2", "Team E", "EHALL00201");
        node3 = new Node("1000", "400", "1", "45 Francis", "STAI", "Stair1", "Stairway 2", "Team E", "ESTAI00101");
        nodeA = new NodeObj(node1);
        nodeB = new NodeObj(node2);
        nodeC = new NodeObj(node3);

        edge1 = new Edge(node1.getNodeID(), node2.getNodeID(), node1.getNodeID() + "_" + node2.getNodeID());
        edge2 = new Edge(node2.getNodeID(), node3.getNodeID(), node2.getNodeID() + "_" + node3.getNodeID());
        edgeA = new EdgeObj(nodeA, nodeB);
        edgeB = new EdgeObj(nodeB, nodeC);

        nodeA.addEdge(nodeB);
        nodeB.addEdge(nodeC);
        //nodeB.addEdge(nodeA);     // nodeB needs to have an edge set to nodeA in order for getListOfNeighbors to function correctly
    }




    @Test
    public void getListOfNeighbors() throws Exception {
        ArrayList<NodeObj> nodeList = new ArrayList<NodeObj>();
        nodeList.add(nodeA);
        nodeList.add(nodeB);

        assertEquals(1, nodeA.getListOfNeighbors().size());
        assertEquals(2, nodeB.getListOfNeighbors().size());
        assertEquals(1, nodeC.getListOfNeighbors().size());
    }

    @Test
    public void getListOfEdgeObjs() throws Exception {
        for (EdgeObj e: nodeA.getListOfEdgeObjs()){
            assertEquals("EHALL00101", e.getNodeA().node.getNodeID());
            assertEquals("EHALL00201", e.getNodeB().node.getNodeID());
        }

    }

    @Test
    public void addEdge() throws Exception {
        assertEquals(1, nodeA.getListOfEdgeObjs().size());
        assertEquals(2, nodeB.getListOfEdgeObjs().size());
    }

    @Test
    public void addEdge1() throws Exception {
        Node otherNode4 = new Node("1000", "400", "1", "45 Francis", "STAI", "Stair1", "Stairway 2", "Team E", "ESTAI00201");
        Node otherNode5 = new Node("1000", "400", "1", "45 Francis", "STAI", "Stair1", "Stairway 2", "Team E", "ESTAI00201");
        NodeObj otherNodeObjD = new NodeObj(otherNode4);
        NodeObj otherNodeObjE = new NodeObj(otherNode5);
        Edge otherEdge = new Edge(otherNode4.getNodeID(), otherNode5.getNodeID(), otherNode4.getNodeID() + "_" + otherNode5.getNodeID());
        EdgeObj otherEdgeObj = new EdgeObj(otherNodeObjD, otherNodeObjE);

        otherNodeObjD.addEdge(otherEdgeObj);
        assertEquals(1, otherNodeObjD.getListOfEdgeObjs().size());
        assertEquals(0, otherNodeObjE.getListOfEdgeObjs().size());
    }

    @Test
    public void killEdge() throws Exception {
        // Same problem as addEdge, needs to be able to delete both subsequent edges;
        // Edges should be shared between nodes, ideally all nodes should share a single given edge between them, not have a forwards and backwards edge
    }

//    @Test
//    public void getDistToGoal() throws Exception {        // Should this test still be here now that getDistance is overloaded and takes two parameters?
//
//    }

    @Test
    public void getHeuristic() throws Exception {
        nodeA.setHeuristic(45);
        assertEquals(45, nodeA.getHeuristic(), 0);
    }

    @Test
    public void setHeuristic() throws Exception {
        nodeB.setHeuristic(12);
        assertEquals(12, nodeB.getHeuristic(), 0);
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