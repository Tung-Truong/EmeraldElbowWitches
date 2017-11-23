package controller;

import model.NodeObj;

import java.util.ArrayList;

public interface IPathAlg {
    boolean pathfind(NodeObj start, NodeObj goal);
    ArrayList<NodeObj> getGenPath();
}
