package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TextDirectionsTest {

    private TextDirections td;
    private ArrayList<NodeObj> path;

    private Node n0;
    private Node n1;
    private Node n2;
    private Node n3;
    private Node n4;
    private Node n5;
    private Node n6;
    private Node n7;
    private Node n8;
    private Node n9;
    private Node n10;

    private NodeObj node0;
    private NodeObj node1;
    private NodeObj node2;
    private NodeObj node3;
    private NodeObj node4;
    private NodeObj node5;
    private NodeObj node6;
    private NodeObj node7;
    private NodeObj node8;
    private NodeObj node9;
    private NodeObj node10;

    private Edge e01;
    private Edge e12;
    private Edge e23;
    private Edge e34;
    private Edge e45;
    private Edge e56;
    private Edge e67;
    private Edge e78;
    private Edge e79;
    private Edge e710;

    private EdgeObj edge01;
    private EdgeObj edge12;
    private EdgeObj edge23;
    private EdgeObj edge34;
    private EdgeObj edge45;
    private EdgeObj edge56;
    private EdgeObj edge67;
    private EdgeObj edge78;
    private EdgeObj edge79;
    private EdgeObj edge710;

    /*
    The structure of the test nodes are roughly as follows.

    Nodes 0 through 4 are all in a slight zig-zag, to test the robustness of going straight.

    Node # : Type : Floor #

    Node 0 : Hall : 1
    Node 1 : Hall : 1
    Node 2 : Hall : 1
    Node 3 : Hall : 1
    Node 4 : Elev : 1
    Node 5 : Elev : 2
    Node 6 : Elev : 3
    Node 7 : Hall : 3
    Node 8 : Hall : 3
    Node 9 : Stair : 3
    Node10 : Hall : 3

    9 ------------------- 7 ---- 10
                        / /
                       / /
    3 --- 4 ~~~ 5 ~~~ 6  8
    |
    2
    |
    1
    |
    0
     */

    @Before
    public void setUp() {

        n0 = new Node("20", "50", "1", "45 Francis", "HALL", "Hall0", "Hallway 1", "Team E", "EHALL00100");
        n1 = new Node("20", "100", "1", "45 Francis", "HALL", "Hall1", "Hallway 1", "Team E", "EHALL00101");
        n2 = new Node("25", "200", "1", "45 Francis", "HALL", "Hall2", "Hallway 1", "Team E", "EHALL00102");
        n3 = new Node("19", "350", "1", "45 Francis", "HALL", "Hall3", "Hallway 1", "Team C", "CHALL00101");
        n4 = new Node("100", "355", "1", "45 Francis", "ELEV", "Elevator1", "Elevator 1", "Team Q", "QELEV00101");
        n5 = new Node("103", "360", "2", "45 Francis", "ELEV", "Elevator2", "Elevator 2", "Team A", "AELEV00102");
        n6 = new Node("100", "350", "3", "45 Francis", "ELEV", "Elevator 3", "Elevator 1", "Team E", "EELEV00101");
        n7 = new Node("150", "400", "3", "45 Francis", "HALL", "Hall6", "Hallway 2", "Team E", "EHALL00105");
        n8 = new Node("125", "350", "3", "45 Francis", "HALL", "Hall7", "Hallway 2", "Team E", "EHALL00110");
        n9 = new Node("10", "400", "3", "45 Francis", "STAI", "Stair1", "Staircase 1", "Team E", "EHALL00106");
        n10 = new Node("200", "400", "3", "45 Francis", "HALL", "Hall9", "Hallway 2", "Team E", "EHALL00121");

        node0 = new NodeObj(n0);
        node1 = new NodeObj(n1);
        node2 = new NodeObj(n2);
        node3 = new NodeObj(n3);
        node4 = new NodeObj(n4);
        node5 = new NodeObj(n5);
        node6 = new NodeObj(n6);
        node7 = new NodeObj(n7);
        node8 = new NodeObj(n8);
        node9 = new NodeObj(n9);
        node10= new NodeObj(n10);

        e01 = new Edge(n0.getNodeID(), n1.getNodeID(), n0.getNodeID() + " " + n1.getNodeID());
        e12 = new Edge(n1.getNodeID(), n2.getNodeID(), n1.getNodeID() + " " + n2.getNodeID());
        e23 = new Edge(n2.getNodeID(), n3.getNodeID(), n2.getNodeID() + " " + n3.getNodeID());
        e34 = new Edge(n3.getNodeID(), n4.getNodeID(), n3.getNodeID() + " " + n4.getNodeID());
        e45 = new Edge(n4.getNodeID(), n5.getNodeID(), n4.getNodeID() + " " + n5.getNodeID());
        e56 = new Edge(n5.getNodeID(), n6.getNodeID(), n5.getNodeID() + " " + n6.getNodeID());
        e67 = new Edge(n6.getNodeID(), n7.getNodeID(), n6.getNodeID() + " " + n7.getNodeID());
        e78 = new Edge(n7.getNodeID(), n8.getNodeID(), n7.getNodeID() + " " + n8.getNodeID());
        e79 = new Edge(n7.getNodeID(), n9.getNodeID(), n7.getNodeID() + " " + n9.getNodeID());
        e710= new Edge(n7.getNodeID(), n10.getNodeID(), n7.getNodeID() + " " + n10.getNodeID());

        edge01 = new EdgeObj(node0, node1);
        edge12 = new EdgeObj(node1, node2);
        edge23 = new EdgeObj(node2, node3);
        edge34 = new EdgeObj(node3, node4);
        edge45 = new EdgeObj(node4, node5);
        edge56 = new EdgeObj(node5, node6);
        edge67 = new EdgeObj(node6, node7);
        edge78 = new EdgeObj(node7, node8);
        edge79 = new EdgeObj(node7, node9);
        edge710= new EdgeObj(node7, node10);

        path = new ArrayList<NodeObj>();

    }

    /*
    @Test
    public void testStraightNavigation() {
        path.add(node0);
        path.add(node1);
        path.add(node2);
        path.add(node3);

        //assertEquals("Continue straight", td.getTextDirections(path));
    }
    */

}
