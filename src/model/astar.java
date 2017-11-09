package EmeraldElbowWitches;

import javax.xml.soap.Node;
import java.util.*;

public class astar {

    protected boolean Pathfind(node start, node goal){
        //open_queue contains all unexplored nodes, starts with just the start node
        //closed_queue contains all explored nodes, which starts empty
        PriorityQueue<node> open_queue = new PriorityQueue<node>();
        open_queue.add(start);
        PriorityQueue<node> closed_queue = new PriorityQueue<node>();

        //G cost of going to start from start is zero
        start.g = 0;
        start.f = start.g + getDistance(start, goal);

        while (open_queue.size()>0){
            node current = open_queue.peek();
            if (current == goal){
                return GenPath(goal);
            }
            open_queue.remove(current);
            closed_queue.add(current);
            List exploreList = node.getListOfNeighbors();
            for (Object neighbor: exploreList){
                if (closed_queue.contains(neighbor) && !closed_queue.contains(neighbor)){
                    neighbor.f = neighbor.g + getDistance(neighbor, goal);
                    if (open_queue.contains(neighbor) && !open_queue.contains(neighbor)){
                        open_queue.add(neighbor);
                    }
                    else{
                        node nextNeighbor = neighbor in open_list;
                                if (neighbor.g < nextNeighbor.g){
                                    nextNeighbor.g = neighbor.g;
                                    nextNeighbor.parent = neighbor.parent;
                                }
                    }
                }
            }
        }
        return false; //No path exists
    }
    private ArrayList<Nodes> GenPath;
}

