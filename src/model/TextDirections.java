package model;

import java.util.ArrayList;

import static java.lang.Math.*;

public class TextDirections {

    public TextDirections() {

    }

    /**
     * Path needs to consist of at least 3 nodes
     * <p>
     * add if statement to handle less than 2 nodes
     */
    public String getTextDirections(ArrayList<NodeObj> path) {

        String msg = "";

        int angle;
        Node lastNode;
        Node curNode;
        Node nextNode;

        for (int i = 1; i < path.size() - 1; i++) {

            lastNode = path.get(i - 1).getNode();
            curNode = path.get(i).getNode();
            nextNode = path.get(i + 1).getNode();

            // if the next node is a staircase
            if(nextNode.getNodeType() == "STAI")
                msg = msg + "Take the stairs to floor " + nextHallwayNode(path, i).getFloor();
            else if(nextNode.getNodeType() == "ELEV")
                msg = msg + "Take the elevator to floor " + nextHallwayNode(path, i).getFloor();
            else {

                // calculate the angle between the vector last->
                // starts from
                angle = angleBetweenNodes(lastNode, curNode, nextNode);

//            if (angle < 0) {
//                angle += 360;
//            }

//            int cross = (x2 - x1) * (y3 - y2) - (y2 - y1) * (x3 - x2);


                // if the next node is basically straight ahead
                if (angle >= 160 || angle <= -160) {
                    float prevAngle;

                    try {
                        prevAngle = angleBetweenNodes(path.get(i - 2).getNode(), lastNode, curNode);
                    } catch (IndexOutOfBoundsException e) {
                        // catch if the index is at the beginning of the path when i-2 is accessed
                        // in this case, the previous direction was automatically straight
                        prevAngle = 180;
                    }
                    // if the previous instruction was not to go straight and this was the first time down a hall
                    if( !(prevAngle >= 160 || prevAngle <= -160) && lastNode.getNodeType().equals("HALL") )
                        msg += "go straight\n";
                }

                // add how much to turn
                if (Math.abs(angle) < 45)
                    msg += "Turn sharply ";
                else if (Math.abs(angle) < 135)
                    msg += "Turn ";
                else if (Math.abs(angle) < 160) {
                    msg += "Turn slightly ";
                }

                // add which direction to turn
                if(angle >= 0)
                    msg += "right\n";
                else
                    msg += "left\n";
            }

        }

        return msg;
    }

    int angleBetweenNodes(Node lastNode, Node curNode, Node nextNode) {

        int x1 = lastNode.getxLoc();
        int y1 = lastNode.getyLoc();

        int x2 = curNode.getxLoc();
        int y2 = curNode.getyLoc();

        int x3 = nextNode.getxLoc();
        int y3 = nextNode.getyLoc();

//            int x12 = abs(x2 - x1);
//            int x23 = abs(x3 - x2);
//
//            int y12 = abs(y2 - y1);
//            int y23 = abs(y3 - y2);
//
        int Ax = (x2 - x1);
        int Ay = (y2 - y1);
        int Bx = (x3 - x2);
        int By = (y3 - y2);

        int angle = (int)Math.toDegrees(Math.atan2(Ax - Ay, Bx - By));
        System.out.println("THIS IS THE ANGLE: " + angle);
        return angle;

    }

    Node nextHallwayNode(ArrayList<NodeObj> path, int start) {
        Node node = path.get(start).getNode();
        int index = 1;
        while( !node.getNodeType().equals("HALL") ) {

            try {
                node = path.get(start + index).getNode();
            }
            catch (IndexOutOfBoundsException e) {
                return path.get(start + index - 1).getNode();
            }

            index++;
        }

        return node;

    }

}
