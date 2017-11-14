package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.*;
import sun.plugin.javascript.navig4.Layer;
import java.util.ArrayList;

public class UI_v1 {

    private GraphicsContext gc1;

    @FXML
    private MenuItem Map1;

    @FXML
    private Canvas gc;

    @FXML
    private ImageView currentMap;

    @FXML
    private MenuButton MapDropDown;

    @FXML
    private MenuItem Map2;

    @FXML
    private AnchorPane mapContainer;

    @FXML
    private Layer lineLayer;

    private static class Mpoint {
        double x, y;
    }

    @FXML
    //This is where pathfinding is currently
    //this shall be renamed later to something better
    void addLine(MouseEvent event) throws InvalidNodeException {
        //getX/stageWidth = w/5000
        //5000*getX/stageWidth

        //get the mouses location and convert that to the corrected map coordinates for the original image
        Scene currScene = model.Main.getCurrScene();
        double mapWidth = currentMap.getFitWidth();
        double mapHeight = currentMap.getFitHeight();
       // System.out.println(mapWidth + " " + mapHeight);

        double mousex = (5000*event.getX())/mapWidth;
        double mousey = (3400*event.getY())/mapHeight;
        //Print to confirm

        //convert click resolution to map ratio
        System.out.println((5000*event.getX())/mapWidth + " " + (3400*event.getY())/mapHeight);
        //far left stair node
        System.out.println("Far left stair coords: " + 2190 + " " + 910);
        //Rehab center
        System.out.println("Rehab center coords: " + 2790 + " " + 1380);

        //create a new astar object
        astar newpathGen = new astar();
        this.gc1 = gc.getGraphicsContext2D();
        gc1.setLineWidth(10);
        gc1.setStroke(Color.BLUE);
        gc1.setFill(Color.RED);

        //get node that corr. to click from ListOfNodeObjects made in main
        NodeObj goal = model.Main.getNodeMap().getNearestNeighborFilter
                ((int)Math.floor(mousex), (int)Math.floor(mousey));

       /*NodeObj goal = model.Main.getNodeMap().getNearestNeighborFilter
                (1636, 1933);*/

       // NodeObj goal = model.Main.getNodeMap().getNearestNeighborFilter
              //  (2790, 1380);
//2788 1389
        //getStart
        NodeObj Kiosk = model.Main.getKiosk();
        //set the path to null
        ArrayList<NodeObj> path = null;
        /*gc1.fillOval(goal.node.getxLoc()*mapWidth/5000-5,
                goal.node.getyLoc()*mapHeight/3400-5,
                10,
                10);*/
        gc1.fillOval(Kiosk.node.getxLoc()*mapWidth/5000-5,
                Kiosk.node.getyLoc()*mapHeight/3400-5,
                10,
                10);
        //try a*
        /*if(newpathGen.pathfind(Kiosk,goal,gc1))
            path = newpathGen.getGenPath();
        else
            throw new InvalidNodeException("this is not accessable with the current map");
        */
        gc1.setGlobalAlpha(.5);
        gc1.setStroke(Color.BLUE);
        gc1.setFill(Color.BLUE);
        int i = 0;
        /*for(NodeObj n: Main.getNodeMap().getNodes()){
            for(EdgeObj e: n.getListOfEdgeObjs()){
                gc1.setStroke(Color.BLUE);

                gc1.fillText(""+i,e.getNodeA().node.getxLoc()*mapWidth/5000,
                        e.getNodeA().node.getyLoc()*mapHeight/3400);
                i++;

                gc1.strokeLine(e.getNodeA().node.getxLoc()*mapWidth/5000,
                        e.getNodeA().node.getyLoc()*mapHeight/3400,
                        e.getNodeB().node.getxLoc()*mapWidth/5000,
                        e.getNodeB().node.getyLoc()*mapHeight/3400);
            }

        }*/



        gc1.setFill(Color.RED);
        System.out.println(mapContainer.getChildren());

        gc1.fillText(goal.node.getNodeID(),goal.node.getxLoc()*mapWidth/5000-5,
                goal.node.getyLoc()*mapHeight/3400-5);
        gc1.fillOval(Kiosk.node.getxLoc()*mapWidth/5000-5,
                Kiosk.node.getyLoc()*mapHeight/3400-5,
                10,
                10);


    }

    @FXML
    void GetMap1() {
        setKioskLoc(2460, 910);
        gc1.clearRect(0,0,currentMap.getFitWidth(), currentMap.getFitHeight());
        MapDropDown.setText("45 Francis Floor 1 Center");
        Image m1 =  new Image("file:src/view/media/basicMap.png");
        currentMap.setImage(m1);
        Main.getNodeMap().setCurrentBuilding("45 Francis");
        //need to set currentMap of ListOfNodeObjs to "
    }

    @FXML
    void GetMap2() {
        setKioskLoc(1580, 1810);
        gc1.clearRect(0,0,currentMap.getFitWidth(), currentMap.getFitHeight());
        MapDropDown.setText("Shapiro Building Floor 2");
        Image m2 =  new Image("file:src/view/media/Shapiro.png");
        currentMap.setImage(m2);
        Main.getNodeMap().setCurrentBuilding("Shapiro");
    }

    void setKioskLoc(int xCoord, int yCoord){
        try {
            Main.setKiosk(Main.getNodeMap().getNearestNeighbor(xCoord, yCoord));
        }
        catch (InvalidNodeException e){
            e.printStackTrace();
        }
    }
}
