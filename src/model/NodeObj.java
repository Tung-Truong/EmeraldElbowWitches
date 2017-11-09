package EmeraldElbowWitches;

import javax.xml.soap.Node;
import java.util.ArrayList;

public class NodeObj {

   // private ArrayList<EdgeObj> listOfEdgeObjs;

    public NodeObj(){
        //listOfEdgeObjs.add(new EdgeObj(this, new NodeObj(), 0));
    }

    public ArrayList<NodeObj> getListOfNeighbors(){
        ArrayList<NodeObj> nodeList = new ArrayList<NodeObj>();
        nodeList.add(this);
        return nodeList;
    }

    public ArrayList<EdgeObj> getListOfEdgeObjs(){
        ArrayList<EdgeObj> edgeList = new ArrayList<EdgeObj>();
        edgeList.add(new EdgeObj(this, new NodeObj(), 0));
        return edgeList;
    }

    public EdgeObj getEdgeObj(NodeObj n){
        return new EdgeObj(this, new NodeObj(), 0);
    }

    // ---------------------Setters----------------------------

    public void changeLoc(NodeObj node, int xLoc, int yLoc){

    }

    public void changeFloor(NodeObj node, String floor){

    }

    public void changeBuilding(NodeObj node, String building){

    }

    public void changeType(NodeObj node, String typeOfNode){

    }

    public void changeNameShort(NodeObj node, String shortName){

    }

    public void changeNameLong(NodeObj node, String longName){

    }

    public void changeTeamAssigned(NodeObj node, String team){

    }

    // ---------------------General Functionality----------------------------

    public void addEdge(NodeObj nodeB, int edgeWeight){

    }
    public void killEdge(NodeObj nodeB){

    }

}
