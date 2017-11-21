package model;

import javafx.scene.image.Image;

public class Map {

    Image map;

    public Map(String filePath){
        this.map = new Image(filePath);
    }

    public Image getMap(){
        return map;
    }
}
