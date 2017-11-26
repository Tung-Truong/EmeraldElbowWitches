package controller;

import model.NodeObj;

import java.util.ArrayList;

public interface IPathAlg { // Interface to select between the four different admin-selected pathfinding algorithms (A*, BreadthFirst, DepthFirst & Dijkstra's)
    boolean pathfind(NodeObj start, NodeObj goal);
    ArrayList<NodeObj> getGenPath();
}
