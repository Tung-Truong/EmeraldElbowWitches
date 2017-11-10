package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EdgeObjTest {
    NodeObj nodeA;
    NodeObj nodeB;
    int edgeWeight;
    EdgeObj edge;

    @Before
    public void setUp() throws Exception {
        nodeA = new NodeObj();
        nodeB = new NodeObj();
        edgeWeight = 3;
        edge = new EdgeObj(nodeA, nodeB, edgeWeight);
    }

    @Test
    public void getOtherNodeObj() throws Exception {
        assertEquals(edge.getOtherNodeObj(nodeA), nodeB);
    }

    @Test
    public void getWeight() throws Exception {
        assertEquals(3, edge.getWeight());
    }

    @Test
    public void setWeight() throws Exception {
        edge.setWeight(5);
        assertEquals(5, edge.getWeight());
    }

    @Test
    public void genWeightFromDistance() throws Exception {
    }

}
