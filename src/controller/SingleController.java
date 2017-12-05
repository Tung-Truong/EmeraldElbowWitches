package controller;

import model.InvalidNodeException;

public class SingleController {
    private static SingleController control = new SingleController();

    private SingleController(){}

    public static SingleController getController(){
        return control;
    }

    /*
        Controllers not included:
            ControllerListener
            Controller
     */

    /*
     * setKioskLoc sets the default location for the floor
     */
    public void setKioskLoc(int xCoord, int yCoord) {
        try {
            Main.setKiosk(Main.getNodeMap().getNearestNeighborFilter(xCoord, yCoord));
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
    }


}
