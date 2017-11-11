package model;

public class Node {
    private int xLoc;
    private int yLoc;
    private String floor;
    private String building;
    private String nodeType;
    private String shortName;
    private String longName;
    private String team;
    private String nodeID;

    public Node(String xLoc, String yLoc, String floor, String building, String nodeType, String shortName, String longName, String team, String nodeID) {
        this.xLoc = Integer.parseInt(xLoc); // converts xLoc and yLoc into ints, from the db's strings
        this.yLoc = Integer.parseInt(yLoc);
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.shortName = shortName;
        this.longName = longName;
        this.team = team;
        this.nodeID = nodeID;
    }

    public int getxLoc() {
        return xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public String getFloor() {
        return floor;
    }

    public String getBuilding() {
        return building;
    }

    public String getNodeType() {
        return nodeType;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public String getTeam() {
        return team;
    }

    public String getNodeID() {
        return nodeID;
    }

    public void setLoc(int x, int y) {
        this.xLoc = x;
        this.yLoc = y;
    }
    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }


}
