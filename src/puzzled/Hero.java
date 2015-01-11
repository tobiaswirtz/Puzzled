package puzzled;

public class Hero {
    
    private int xCoord;
    private int yCoord;

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
    
    
    //initializes Hero
    public Hero(int x, int y) {
        xCoord = x;
        yCoord = y;
    }
    
    
}
