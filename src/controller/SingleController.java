package controller;

import model.PathingAlgorithm;
import model.PathingContainer;

public class SingleController {
    private static SingleController control = new SingleController();

    private SingleController(){}

    private int XTrans = 0;
    private int YTrans = 0;
    private double Zoom = 1;
    private PathingContainer algorithm = new PathingContainer();

    public static SingleController getController(){
        return control;
    }

    /*
        Controllers not included:
            ControllerListener
            Controller
     */

    public double getZoom() {
        return Zoom;
    }

    public int getXTrans() {
        return XTrans;
    }

    public int getYTrans() {
        return YTrans;
    }

    public PathingContainer getAlgorithm() {
        return algorithm;
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
