package puzzled;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.ResizeListener;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

public class Main implements ResizeListener {
    
    static SwingTerminal terminal;
    static int widthMaze;
    static int heightMaze;
    static int widthTerminal;
    static int heightTerminal;
    static String levelName = "Level.properties";
    static int gameState = 1;
    static Screen screen;
    static int xZero;
    static int upperX;
    static int yZero;
    static int upperY;
    static int lives = 3;
    static int keysCollected = 0;
    static int keysOfLevel;
    static boolean levelFinished;
    static TerminalSize screenSize;
    
    static Object[][] maze;
    static ArrayList dynamicObjs = new ArrayList(50);
    static String[] mapArray;
    
    static Hero hero;
    
    public static void main(String[] args) throws Exception {
        
        terminal = TerminalFacade.createSwingTerminal();
        terminal.setCursorVisible(false);
        
        screenSize = terminal.getTerminalSize();
        screen = TerminalFacade.createScreen();
        terminal.applyBackgroundColor(Terminal.Color.BLACK);
        widthTerminal = screenSize.getColumns();
        heightTerminal = screenSize.getRows();

        //sets up Window
        if(gameState == 0) {
            new MainMenu();
            terminal.enterPrivateMode();
        }
        else if(gameState == 1) {
            terminal.enterPrivateMode();
            readLevel(levelName);
            drawView();
            GameControls control = new GameControls();
            while(true) {
                control.readKeyInput();
                updateView();
            }
            
        }
        
        
    }

    
    //reads level information in and processes it
    
    public static void readLevel(String level) throws Exception {
        
        Set mapSet;
        Object[] tmp;
        ArrayList<String> tmpList;
        
        
        try (FileReader reader = new FileReader(level)) {      //opening file stream
            Properties properties = new Properties();                       
            properties.load(reader);                                        //filling properties object with output of "stream"
            Enumeration em = properties.keys();
            tmpList = new ArrayList(properties.size());
            mapSet = properties.entrySet();
            tmp = mapSet.toArray();
            mapArray = new String[tmp.length];
            
            for(int index = 0; index < tmp.length; index++) {
                mapArray[index] = tmp[index].toString();                      //casting Object[] tmp to String[] mapArray
            }
            
            for(int index = 0; index < mapArray.length; index++) {
                if(mapArray[index].contains("Width") == true) {             //catches special case "Width" in properties file
                    String[] tmpString = mapArray[index].split("=");
                    widthMaze = Integer.parseInt(tmpString[1]);
                } else if(mapArray[index].contains("Height") == true) {     //catches special case "Height" in properties file
                    String[] tmpString = mapArray[index].split("=");
                    heightMaze = Integer.parseInt(tmpString[1]);
                }
                else {                                                      //initializes Objects for every other Hash Map entry
                    tmpList.add(mapArray[index]);
                }
            }
            
            maze = new Object[widthMaze][heightMaze];
            
            for(int index = 0; index < tmpList.size(); index++) {
                initObject(tmpList.get(index));
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    //initializes objects to @param maze
    
    public static void initObject(String setInput) {
        
        String[] tmp = setInput.split("=");
        
        int typeOfObject = Integer.parseInt(tmp[1]);                        //gets number of object to place at x, y
        
        tmp = tmp[0].split(",");                                            //separates x and y coordinates
        
        int xCoord = Integer.parseInt(tmp[0]);
        int yCoord = Integer.parseInt(tmp[1]);
        
        switch(typeOfObject) {                                              //creates the objects for each type of obstacle/object
            case 0:
                maze[xCoord][yCoord] = new Wall(xCoord, yCoord);
                break;
            case 1:
                maze[xCoord][yCoord] = new Entrance(xCoord, yCoord);
                hero = new Hero(xCoord, yCoord);
                maze[xCoord][yCoord] = hero;
                break;
            case 2:
                maze[xCoord][yCoord] = new Exit(xCoord, yCoord);
                break;
            case 3:
                maze[xCoord][yCoord] = new StaticObstacle(xCoord, yCoord);
                break;
            case 4:
                DynamicObstacle aux = new DynamicObstacle(xCoord, yCoord);
                maze[xCoord][yCoord] = aux;
                dynamicObjs.add(aux);
                break;
            case 5:
                maze[xCoord][yCoord] = new KeyObj(xCoord,yCoord);
                keysOfLevel++;
                break;
        }
    }
    
    //initializes the view on the terminal layer
    
    public static void drawView() {
        
        if(widthMaze < widthTerminal && heightMaze < heightTerminal) {
            xZero = (widthTerminal / 2) - (widthMaze / 2);
            yZero = (heightTerminal / 2) - (heightMaze / 2);
        } else {
            xZero = 0;
            yZero = 1;
        }
        
        upperX = xZero + widthMaze;
        upperY = yZero + heightMaze;
        
        for(int indexX = xZero; indexX < upperX; indexX++) {
            for(int indexY = yZero; indexY < upperY; indexY++) {
                
                if(maze[indexX-xZero][indexY-yZero] instanceof Wall) {
                    TextModification.putChar('X', indexX, indexY);
                }
                else if(maze[indexX-xZero][indexY-yZero] instanceof Entrance) {
                    //add char for entrance
                }
                else if(maze[indexX-xZero][indexY-yZero] instanceof Exit) {
                    //TextModification.putChar('\u2023', indexX, indexY);
                }
                else if(maze[indexX-xZero][indexY-yZero] instanceof Hero) {
                    TextModification.putChar('\u265B', indexX, indexY);
                }
                else if(maze[indexX-xZero][indexY-yZero] instanceof KeyObj) {
                    TextModification.putChar('\u1A57', indexX, indexY);
                }
                else if(maze[indexX-xZero][indexY-yZero] instanceof StaticObstacle) {
                    TextModification.putChar('\u2268', indexX, indexY);
                }
                else if(maze[indexX-xZero][indexY-yZero] instanceof DynamicObstacle) {
                    TextModification.putChar('\u3244', indexX, indexY);
                }
                
            }
        }
    }
    
    //updates positions in @param maze of moving objects and displays them on the terminal
    
    public static void updateView() throws Exception{
        
        TextModification.putChar('\u265B', hero.getxCoord() + xZero, hero.getyCoord() + yZero);
        TextModification.printToTerminal("Lives left: " + lives, 1, 0);
        TextModification.printToTerminal("Keys collected: " + keysCollected + "/" + keysOfLevel, 16, 0);
        
        if(lives == 0) {
            gameState = 0;
        }
        if(levelFinished == true) {
            Thread.sleep(250);
            terminal.clearScreen();
            TextModification.printToTerminal("Level done! Congratulations!", TextModification.xCentered(28), heightTerminal/2);
        }
    }
    
    @Override
    public void onResized(TerminalSize newSize) {
        //TODO
    }
    
}
