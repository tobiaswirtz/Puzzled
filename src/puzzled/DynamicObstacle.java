
package puzzled;

public class DynamicObstacle {
    //instantiable coordinates of Dynamic Obstacle objects
    
    private int xCoord;
    private int yCoord;
    
    
    //getter and setter methods for coordinates to modify DynamicObstacle position
    
    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
    
    
    //constructor for DynamicObstacle objects
    
    public DynamicObstacle(int x, int y) {
        xCoord = x;
        yCoord = y;
    }
}
