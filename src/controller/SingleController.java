package controller;

import model.InvalidNodeException;

public class SingleController {
    private static SingleController control = new SingleController();

    private SingleController(){}

    private int XTrans = 0;
    private int YTrans = 0;
    private double Zoom = 1;

    public static SingleController getController(){
        return control;
    }

    /*
        Controllers not included:
            ControllerListener
            Controller
     */
    public void setKioskLoc(int xCoord, int yCoord) {
        try {
            Main.setKiosk(Main.getNodeMap().getNearestNeighborFilter(xCoord, yCoord));
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
    }

    public double getZoom() {
        return Zoom;
    }

    public int getXTrans() {
        return XTrans;
    }

    public int getYTrans() {
        return YTrans;
    }

    public void setZoom(double zoom) {
        Zoom = zoom;
    }

    public void setXTrans(int xTrans) {
        XTrans = xTrans;
    }

    public void setYTrans(int yTrans) {
        YTrans = yTrans;
    }

    public void addX(int add){
        setXTrans(getXTrans() + add);
    }

    public void addY(int add) {
        setYTrans(getYTrans() + add);
    }

    public void subX(int sub){
        setXTrans(getXTrans() - sub);
    }

    public void subY(int sub){
        setYTrans(getYTrans() - sub);
    }
}
