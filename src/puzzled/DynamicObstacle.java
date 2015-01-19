package puzzled;

import java.util.ArrayList;
import java.util.Random;
import static puzzled.Main.hero;
import static puzzled.Main.lives;
import static puzzled.Main.maze;
import static puzzled.Main.normalMovement;
import static puzzled.Main.xZero;
import static puzzled.Main.yZero;

public class DynamicObstacle {
    //instantiable coordinates of Dynamic Obstacle objects

    private int xCoord;
    private int yCoord;
    private ArrayList path = new ArrayList(80000);

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

    
    //handles mob movement when outside of 400*400 square surrounding @param hero
    public void randomMovement() {

        GameControls mobControl = new GameControls();

        Random r = new Random();
        int random = r.nextInt();

        while (normalMovement == true) {
            switch (random % 2) {
                case 0:
                    if (mobControl.checkField(xCoord + 1, yCoord) == 4) {
                        xCoord = xCoord + 1;
                    } else if (mobControl.checkField(xCoord - 1, yCoord) == 4) {
                        xCoord = xCoord - 1;
                    } else {
                        random = r.nextInt();
                    }
                    break;
                case 1:
                    if (mobControl.checkField(xCoord, yCoord + 1) == 4) {
                        yCoord = yCoord + 1;
                    } else if (mobControl.checkField(xCoord, yCoord - 1) == 4) {
                        yCoord = yCoord - 1;
                    } else {
                        random = r.nextInt();
                    }
                    break;
            }
        }
    }

    //loops drawPath() so that mob focusses on @param hero
    
    public void moveToHero() {
        if (recPath(xCoord, yCoord) == true) {
            drawPath(xCoord, yCoord);
        }
        normalMovement = true;
    }

    
    //recursive function which follows the path laid out by recPath and moves mobs along the path of "1"s
    
    public void drawPath(int x, int y) {
        xCoord = x;
        yCoord = y;
        TextModification.putChar('\u3244', x - xZero, y - yZero + 1);

        if (maze[xCoord + 1][yCoord].equals("1")) {
            TextModification.putChar('\u0020', x - xZero, y - yZero + 1);
            drawPath(xCoord + 1, yCoord);
        } else if (maze[xCoord - 1][yCoord].equals("1")) {
            TextModification.putChar('\u0020', x, y);
            drawPath(xCoord - 1, yCoord);
        } else if (maze[xCoord][yCoord + 1].equals("1")) {
            TextModification.putChar('\u0020', x, y);
            drawPath(xCoord, yCoord + 1);
        } else if (maze[xCoord][yCoord - 1].equals("1")) {
            TextModification.putChar('\u0020', x, y);
            drawPath(xCoord, yCoord - 1);
        }

    }
    
    //marks path to hero with String "1" and blocks off other ways from nodes with degrees > 2 with "|"

    //TODO: FIX STACK OVERFLOW ERROR
    
    public boolean recPath(int x, int y) {

        if (x == hero.getxCoord() && y == hero.getyCoord()) {
            return true;
        } else {

            GameControls mobControl = new GameControls();

            if ((mobControl.checkField(x - 1, y) == 4) && (maze[x - 1][y] != "|")) {
                maze[x - 1][y] = "1";
                recPath(x - 1, y);
            } else if ((mobControl.checkField(x, y - 1) == 4) && (maze[x][y - 1] != "|")) {
                maze[x][y - 1] = "1";
                recPath(x, y - 1);
            } else if ((mobControl.checkField(x, y + 1) == 4) && (maze[x][y + 1] != "|")) {
                maze[x][y + 1] = "1";
                recPath(x, y + 1);
            } else if ((mobControl.checkField(x + 1, y) == 4) && (maze[x + 1][y - 1] != "|")) {
                maze[x + 1][y] = "1";
                recPath(x + 1, y);
            } else {
                mobControl.toLastNode(x, y);
            }
            return false;
        }
    }
}
