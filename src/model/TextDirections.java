package model;

import java.util.ArrayList;

import static java.lang.Math.*;

public class TextDirections {

    private ArrayList<NodeObj> path;

    public TextDirections() {
        ArrayList<NodeObj> path = new ArrayList<NodeObj>();

        this.path = path;
    }

    /**
     * Path needs to consist of at least 3 nodes
     * <p>
     * add if statement to handle less than 2 nodes
     */
    public void getTextDirections() {
        for (int i = 1; i < path.size() - 1; i++) {
            int x1 = path.get(i - 1).getNode().getxLoc();
            int y1 = path.get(i - 1).getNode().getyLoc();

            int x2 = path.get(i).getNode().getxLoc();
            int y2 = path.get(i).getNode().getyLoc();

            int x3 = path.get(i + 1).getNode().getxLoc();
            int y3 = path.get(i + 1).getNode().getyLoc();

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

            float angle = (float) Math.toDegrees(Math.atan2(By - Ay, Bx - By));

            if (angle < 0) {
                angle += 360;
            }

//            int cross = (x2 - x1) * (y3 - y2) - (y2 - y1) * (x3 - x2);

            String msg = "";

            // turn around
            if (angle < 45 || angle > 315) {
                msg = "Turn Around";
            }

            // continue straight
            if (angle > 135 && angle < 225) {
                msg = "Continue Straight";
            }

            // turn left
            if (angle >= 225 && angle <= 315) {
                msg = "Turn Left";
            }

            // turn right
            if (angle >= 45 && angle <= 135) {
                msg = "Turn Right";
            }

            System.out.println(msg);





        }
    }
}
