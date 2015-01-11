package puzzled;

public class Wall {
    
    //instantiable coordinates of Wall objects
    
    private int xCoord;
    private int yCoord;
    
    
    //constructor for Wall objects
    
    public Wall(int x, int y) {
        xCoord = x;
        yCoord = y;
        TextModification.putChar('X', x, y);
        String coordinates = x + "," + y;
        Main.walls.add(coordinates);
    }
    
}
