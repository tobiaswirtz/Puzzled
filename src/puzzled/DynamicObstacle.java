package puzzled;

import com.googlecode.lanterna.terminal.Terminal;
import java.util.ArrayList;
import java.util.Random;
import static puzzled.Main.heightMaze;
import static puzzled.Main.heightTerminal;
import static puzzled.Main.hero;
import static puzzled.Main.lives;
import static puzzled.Main.maze;
import static puzzled.Main.terminal;
import static puzzled.Main.widthMaze;
import static puzzled.Main.widthTerminal;
import static puzzled.Main.xZero;
import static puzzled.Main.yZero;

public class DynamicObstacle extends Objects {
    //instantiable coordinates of Dynamic Obstacle objects

    private int xCoord;
    private int yCoord;
    private ArrayList path = new ArrayList(80000);
    static GameControls mobControl = new GameControls();
    private boolean normalMovement = true;

    public void setNormalMovement(boolean normalMovement) {
        this.normalMovement = normalMovement;
    }

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

        Random r = new Random();
        int random = r.nextInt();

        switch (random % 2) {
            case 0:
                if (mobControl.checkField(xCoord + 1, yCoord) == 4) {
                    xCoord = xCoord + 1;
                    if (inTerminalWindow(xCoord, yCoord)) {
                        TextModification.putChar('\u0020', xCoord + xZero - 1, yCoord + yZero);
                        TextModification.putChar('\u2658', xCoord + xZero, yCoord + yZero, Terminal.Color.BLUE);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    }
                } else if (mobControl.checkField(xCoord - 1, yCoord) == 4) {
                    xCoord = xCoord - 1;
                    if (inTerminalWindow(xCoord, yCoord)) {
                        TextModification.putChar('\u0020', xCoord + 1, yCoord + yZero);
                        TextModification.putChar('\u2658', xCoord + xZero, yCoord + yZero + 1, Terminal.Color.BLUE);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    }
                } else {
                    random = r.nextInt();
                }
                break;
            case 1:
                if (mobControl.checkField(xCoord, yCoord + 1) == 4) {
                    yCoord = yCoord + 1;
                    if (inTerminalWindow(xCoord, yCoord)) {
                        TextModification.putChar('\u0020', xCoord + xZero, yCoord + yZero - 1);
                        TextModification.putChar('\u2658', xCoord + xZero, yCoord + yZero, Terminal.Color.BLUE);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    }
                } else if (mobControl.checkField(xCoord, yCoord - 1) == 4) {
                    yCoord = yCoord - 1;
                    if (inTerminalWindow(xCoord, yCoord)) {
                        TextModification.putChar('\u0020', xCoord + xZero, yCoord + yZero + 1);
                        TextModification.putChar('\u2658', xCoord + xZero, yCoord + yZero, Terminal.Color.BLUE);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    } else {
                        random = r.nextInt();
                    }
                    break;
                }

        }
    }

    public boolean inTerminalWindow(int x, int y) {
        if(widthMaze < widthTerminal && heightMaze < heightTerminal) {
        if ((((x + xZero) > xZero) && ((x + xZero) < xZero + widthTerminal)) && (y + yZero > yZero) && (y + yZero < yZero + heightTerminal)) {
            return true;
        } else {
            return false;
        }
        } else {
            if(((xZero + (x - xZero) > xZero) && ((x + xZero) < xZero + widthTerminal)) && (yZero + (y - yZero) > yZero) && (yZero + y < yZero + heightTerminal)) {
                return true;
            } else {
                return false;
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
