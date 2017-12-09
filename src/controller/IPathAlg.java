package controller;

import model.NodeObj;

import java.util.ArrayList;

// Interface to select between the four different admin-selected pathfinding algorithms
// (A*, BreadthFirst, DepthFirst & Dijkstra's, Beam, Best-First)
public interface IPathAlg {
    boolean pathfind(NodeObj start, NodeObj goal);
    ArrayList<NodeObj> getGenPath();
}
