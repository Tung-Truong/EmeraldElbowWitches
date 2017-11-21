package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BreadthFirstTest {

    private Node node1, node2, node3, node4, node5;
    private NodeObj nodeA, nodeB, nodeC, nodeD, nodeE;

    private Edge edge1, edge2, edge3, edge4;
    private EdgeObj edgeA, edgeB, edgeC, edgeD;

    @Before
    public void setUp() throws Exception {
        node1 = new Node("50", "0", "1", "45 Francis", "HALL", "Hall1", "Hallway 2", "Team E", "EHALL00101");
        node2 = new Node("50", "50", "1", "45 Francis", "HALL", "Hall2", "Hallway 2", "Team E", "EHALL00201");
        node3 = new Node("0", "50", "1", "45 Francis", "REST", "Rest1", "Restroom 1", "Team E", "EREST00101");
        node4 = new Node("100", "50", "1", "45 Francis", "STAI", "Stair1", "Staircase 1", "Team E", "ESTAI00201");
        node5 = new Node("0", "100", "1", "45 Francis", "LABS", "Labs1", "Laboratory 1", "Team E", "ELABS00101");
        nodeA = new NodeObj(node1);
        nodeB = new NodeObj(node2);
        nodeC = new NodeObj(node3);
        nodeD = new NodeObj(node4);
        nodeE = new NodeObj(node5);

        edge1 = new Edge(node1.getNodeID(), node2.getNodeID(), node1.getNodeID() + "_" + node2.getNodeID());
        edge2 = new Edge(node2.getNodeID(), node3.getNodeID(), node2.getNodeID() + "_" + node3.getNodeID());
        edge3 = new Edge(node2.getNodeID(), node4.getNodeID(), node2.getNodeID() + "_" + node4.getNodeID());
        edge4 = new Edge(node3.getNodeID(), node5.getNodeID(), node3.getNodeID() + "_" + node5.getNodeID());
        edgeA = new EdgeObj(nodeA, nodeB);
        edgeB = new EdgeObj(nodeB, nodeC);
        edgeA = new EdgeObj(nodeB, nodeD);
        edgeB = new EdgeObj(nodeC, nodeE);

        BreadthFirst bf = new BreadthFirst();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void bfs() throws Exception {
        ArrayList<NodeObj> nodeList = new ArrayList<NodeObj>();
        nodeList.add(nodeA);
        nodeList.add(nodeB);
        nodeList.add(nodeC);
        nodeList.add(nodeD);
        nodeList.add(nodeE);

    }

    @Test
    public void getGenPath() throws Exception {
    }

}