package model;

import javafx.scene.image.Image;

public class ImageLoader {
    Map mapL2;
    Map mapL1;
    Map mapG;
    Map map01;
    Map map02;
    Map map03;

    public ImageLoader() {
    }

    public Image getLoadedMap(String mapName) {
        Image finalMap = null;
        switch (mapName) {
            case "btn_mapL2":
                if (mapL2 == null) {
                    this.mapL2 = new Map("file:src/view/media/mapL2.png");
                }
                finalMap = this.mapL2.getMap();
                break;
            case "btn_mapL1":
                if (mapL1 == null) {
                    this.mapL1 = new Map("file:src/view/media/mapL1.png");
                }
                finalMap = this.mapL1.getMap();
                break;
            case "btn_mapG":
                if (mapG == null) {
                    this.mapG = new Map("file:src/view/media/mapG.png");
                }
                finalMap = this.mapG.getMap();
                break;
            case "btn_map01":
                if (map01 == null) {
                    this.map01 = new Map("file:src/view/media/map01.png");
                }
                finalMap = this.map01.getMap();
                break;
            case "btn_map02":
                if (map02 == null) {
                    this.map02 = new Map("file:src/view/media/map02.png");
                }
                finalMap = this.map02.getMap();
                break;
            case "btn_map03":
                if (map03 == null) {
                    this.map03 = new Map("file:src/view/media/map03.png");
                }
                finalMap = this.map03.getMap();
                break;
        }
        return finalMap;
    }
}
