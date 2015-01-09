
package puzzled;

public class Exit {
    
    //instantiable coordinates of Exit object
    
    private int xCoord;
    private int yCoord;
    
    //constructor for Exit object
    
    public Exit(int x, int y) {
        
        xCoord = x;
        yCoord = y;
        Main.putChar('\u2023', x, y);
    }
    
}
