
package puzzled;

public class KeyObj {
    
    //instantiable coordinates for KeyObj object
    
    private int xCoord;
    private int yCoord;
    
    //constructor for KeyObj object
    
    public KeyObj(int x, int y) {
        
        xCoord = x;
        yCoord = y;
        Main.putChar('\u1F51', x, y);
        
    }
    
}
