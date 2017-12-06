package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class ScenicRoute extends PathingAlgorithm {

    private ArrayList<NodeObj> GenPath;

    public boolean pathfind(NodeObj start, NodeObj goal) {
        //Make two linkedLists: Open and Closed
        //set the cost of g of start to 0
        //add start openqueue

        //while open queue is bigger than 0
            //get the node in openqueue with the largest code and set current to that
            //add current to closedqueue

            //if current is the goal then set GenPath to constructPath
            //return true;

            //create an arraylist of neighbors of the current node
            //for each loop of each neighbor
                //if neighbor is in closed queue then fuck off
                //else if openqueue does not have the neighbor
                    //set the neighbors parent to current
                    //set the heurisitcs for current
                //else
                    //if neighbor's gcost is less than neighbors edge with current then
                        //set neighbors parent to current
                        //set the neighbors gcost
        //if out of while loop return false;
        return true;
    }

    // Helper method to determine the edge with the highest weight to prioritize
    private NodeObj getHighest(LinkedList<NodeObj> list) {
        //create a currentHeuristic and set it to 0
        //create a Nodeobj highestNode and set it to null
        //for each node in list
            //if the node's heuristic is greater than the currentHeuristic
                //currentHeurisstic = node heuristic
                //highestNode = node
        //return highestNode

        return null;
    }

    @Override
    public ArrayList<NodeObj> getGenPath() {
        return GenPath;
    }
}
