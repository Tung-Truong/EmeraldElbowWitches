package model;

public class Node {
    private String xLoc;
    private String yLoc;
    private String floor;
    private String building;
    private String nodeType;
    private String shortName;
    private String longName;
    private String team;
    private String nodeID;

    public Node(String xLoc, String yLoc, String floor, String building, String nodeType, String shortName, String longName, String team, String nodeID) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.shortName = shortName;
        this.longName = longName;
        this.team = team;
        this.nodeID = nodeID;
    }

    public String getxLoc() {
        return xLoc;
    }

    public String getyLoc() {
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

    public void changeLoc(String x, String y) {
        this.xLoc = x;
        this.yLoc = y;
    }
    public void changeFloor(String floor) {
        this.floor = floor;
    }

    public void changeBuilding(String building) {
        this.building = building;
    }

    public void changeNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public void changeShortName(String shortName) {
        this.shortName = shortName;
    }

    public void changeLongName(String longName) {
        this.longName = longName;
    }

    public void changeTeam(String team) {
        this.team = team;
    }

    public void changeNodeID(String nodeID) {
        this.nodeID = nodeID;
    }


}
