package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EdgeObjTest {

    private Node node1 = new Node("20", "100", "1", "45 Francis", "HALL", "Hall1", "Hallway 2", "Team E", "EHALL00101");
    private Node node2 = new Node("50", "30", "1", "45 Francis", "HALL", "Hall2", "Hallway 2", "Team E", "EHALL00201");
    private NodeObj nodeA = new NodeObj(node1);
    private NodeObj nodeB = new NodeObj(node2);
    private double weight = 2;

    private EdgeObj edge1 = new EdgeObj(nodeA, nodeB, weight);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getOtherNodeObj() throws Exception {
        assertEquals(edge1.nodeB, edge1.getOtherNodeObj(edge1.nodeA));
    }

    @Test
    public void getWeight() throws Exception {
        assertEquals(2, edge1.getWeight(), 0);
    }

    @Test
    public void setWeight() throws Exception {
        edge1.setWeight(3);
        assertEquals(3, edge1.getWeight(), 0);
    }

    @Test
    public void genWeightFromDistance() throws Exception {
//    FORMULA = sqrt((ax-bx)^2 + (ay-by)^2)) = -900 + 4900
        assertEquals(76.1, edge1.genWeightFromDistance(), 2);

        node1.setLoc(500, 500);     //Fuck the coordinates up
        node2.setLoc(7000, 7000);

        assertEquals(9192, edge1.genWeightFromDistance(), 1);
    }

}