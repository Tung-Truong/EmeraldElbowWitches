package model;

import javafx.scene.image.Image;

public class ImageLoader {
    Map loadedMap;

    public ImageLoader(){
    }

    public Image getLoadedMap(String filePath){
        if(loadedMap==null){
            this.loadedMap = new Map(filePath);
        }
        return this.loadedMap.getMap();
    }
}
