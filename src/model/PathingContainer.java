package model;

public class PathingContainer {
    public static PathingAlgorithm pathAlg;

    public PathingContainer(){}

    public PathingAlgorithm getPathAlg() {
        return pathAlg;
    }

    /**
     * Sets the pathfinding algorithm.
     * @param pathAlg Selected pathfinding algorithm.
     */
    public void setPathAlg(PathingAlgorithm pathAlg) {
        this.pathAlg = pathAlg;
    }
}
