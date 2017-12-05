package controller;

import javafx.scene.canvas.GraphicsContext;
import model.ImageLoader;
import model.PathingAlgorithm;
import model.PathingContainer;

public class SingleController {
    private static SingleController control = new SingleController();

    private SingleController(){}

    private int XTrans = 0;
    private int YTrans = 0;
    private double Zoom = 1;
    private PathingContainer algorithm = new PathingContainer();
    private GraphicsContext gc = null;
    double mapWidth;
    double mapHeight;
    ImageLoader mapImage = new ImageLoader();

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

    public double getMapHeight() {
        return mapHeight;
    }

    public double getMapWidth() {
        return mapWidth;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public ImageLoader getMapImage() {
        return mapImage;
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

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public void setMapHeight(double mapHeight) {
        this.mapHeight = mapHeight;
    }

    public void setMapWidth(double mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapImage(ImageLoader mapImage) {
        this.mapImage = mapImage;
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
