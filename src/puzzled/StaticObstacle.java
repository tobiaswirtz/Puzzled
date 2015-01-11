
package puzzled;

public class StaticObstacle {
    
    //instantiable coordinates for StaticObstacle object
    
    private int xCoord;
    private int yCoord;
    
    //constructor for StaticObstacle object
    
    public StaticObstacle(int x, int y) {
        
        xCoord = x;
        yCoord = y;
        TextModification.putChar('\u2268', x, y);
        String coordinates = x + "," + y;
        Main.staticObstacles.add(coordinates);
    }
    
}
