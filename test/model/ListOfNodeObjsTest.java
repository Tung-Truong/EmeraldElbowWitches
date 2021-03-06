package model;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ListOfNodeObjsTest {

    private Node node1 = new Node("20", "100", "1", "45 Francis", "HALL", "Hall1", "Hallway 2", "Team E", "EHALL00101");
    private Node node2 = new Node("50", "30", "1", "45 Francis", "HALL", "Hall2", "Hallway 2", "Team E", "EHALL00201");
    private Node node3 = new Node("500", "500", "1", "45 Francis", "HALL", "Hall2", "Hallway 2", "Team E", "EHALL00201");
    private NodeObj nodeA = new NodeObj(node1);
    private NodeObj nodeB = new NodeObj(node2);
    private NodeObj nodeC = new NodeObj(node3);
    private double weight = 2;

    private EdgeObj edge1 = new EdgeObj(nodeA, nodeB, weight);

    ListOfNodeObjs loNO;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public ListOfNodeObjsTest(){
        ArrayList<NodeObj> ANO = new ArrayList<NodeObj>();
        ANO.add(nodeA);
        ANO.add(nodeB);
        ANO.add(nodeC);
        loNO = new ListOfNodeObjs(ANO);

    }

    @Test
    public void getNearestNeighbor() throws Exception {
        ArrayList<NodeObj> ANO = new ArrayList<NodeObj>();
        ListOfNodeObjs loNO2 = new ListOfNodeObjs(ANO);
        assertEquals(loNO.getNearestNeighborFilter(10, 90), nodeA);
        assertEquals(loNO.getNearestNeighborFilter(35, -100), nodeB);
        assertEquals(loNO.getNearestNeighborFilter(0, 500), nodeA);       // same y value but slightly smaller x value
        assertEquals(loNO.getNearestNeighborFilter(35,35), nodeB);        //Equally even between two nodes but values are odd
        exception.expect(InvalidNodeException.class);
        loNO2.getNearestNeighborFilter(10, 90);

    }

    @Test
    public void pair() throws Exception {
        //Basic "does it work?" tests. Still should test edge cases
        /*-----------------------------------------------------------------------------------------*/
        Node nodeA = new Node("2175","910","1","45 Francis", "HALL","Hallway Connector 2 Floor 1", "HallwayW0201", "Team E", "EHALL00201");
        NodeObj nodeAObj = new NodeObj(nodeA);
        Node nodeB = new Node("2190", "910","1","45 Francis","STAI","Staircase K1 Floor 1","Stair K1","Team E","ESTAI00101");
        NodeObj nodeBObj = new NodeObj(nodeB);
        ArrayList<NodeObj> nodeObs = new ArrayList<>();
        nodeObs.add(nodeAObj);
        nodeObs.add(nodeBObj);
        ListOfNodeObjs lON = new ListOfNodeObjs(nodeObs);
        EdgeObj edgeObj = new EdgeObj("EHALL00201", "ESTAI00101", "EHALL00201_ESTAI00101");
        assertEquals(true, lON.pair(edgeObj));
        assertEquals(lON.getNodes().get(0).getEdgeObj(nodeBObj), edgeObj);
        assertEquals(lON.getNodes().get(1).getEdgeObj(nodeAObj), edgeObj);
        assertEquals(lON.getNodes().get(0).getEdgeObj(nodeBObj).nodeA, nodeAObj);
        assertEquals(lON.getNodes().get(0).getEdgeObj(nodeBObj).nodeB, nodeBObj);
        /*-----------------------------------------------------------------------------------------*/


    }

    @Test
    public void getDistance() throws Exception {
        //Basic "does it work?" tests. Still should test edge cases
        /*-----------------------------------------------------------------------------------------*/
        Node nodeA = new Node("2175","910","1","45 Francis", "HALL","Hallway Connector 2 Floor 1", "HallwayW0201", "Team E", "EHALL00201");
        NodeObj nodeAObj = new NodeObj(nodeA);
        Node nodeB = new Node("2190", "910","1","45 Francis","STAI","Staircase K1 Floor 1","Stair K1","Team E","ESTAI00101");
        NodeObj nodeBObj = new NodeObj(nodeB);
        ArrayList<NodeObj> nodeObs = new ArrayList<>();
        nodeObs.add(nodeAObj);
        nodeObs.add(nodeBObj);
        ListOfNodeObjs lON = new ListOfNodeObjs(nodeObs);

        assertEquals(lON.getDistance(nodeAObj,nodeBObj), (Double)15.0);
        /*-----------------------------------------------------------------------------------------*/
        nodeA.setLoc(2190, 910);    // if the nodes have the same coordinates?
        assertEquals(0, lON.getDistance(nodeAObj, nodeBObj), 0);    // make sure getDistance shows there is no distance between them
        
    }

    @Test
    public void setEdgeWeight() throws Exception {
    }

    @Test
    public void setAllNodeObjToGoal() throws Exception {
    }

    @Test
    public void addNodeObjToList() throws Exception {
    }

    @Test
    public void removeNodeObj() throws Exception {
    }

}