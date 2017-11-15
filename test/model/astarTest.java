package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class astarTest {

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
    }

    @Test
    public void pathfind() throws Exception {

    }

    @Test
    public void constructPath() throws Exception {
    }

    @Test
    public void addToQueue() throws Exception {
    }

    @Test
    public void getGenPath() throws Exception {
    }

}