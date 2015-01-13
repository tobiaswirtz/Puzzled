package puzzled;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

public class Main {
    
    static SwingTerminal terminal;
    static int widthMaze;
    static int heightMaze;
    static int widthTerminal;
    static int heightTerminal;
    static String levelName = "Level.properties";
    static int gameState = 1;
    static Screen screen;
    
    static Object[][] maze;
    static ArrayList dynamicObjs = new ArrayList(50);
    static String[] mapArray;
    
    public static void main(String[] args) throws Exception {
        
        terminal = TerminalFacade.createSwingTerminal();
        terminal.setCursorVisible(false);
        
        TerminalSize screenSize = terminal.getTerminalSize();
        screen = TerminalFacade.createScreen();
        terminal.applyBackgroundColor(Terminal.Color.BLACK);
        widthTerminal = screenSize.getColumns();
        heightTerminal = screenSize.getRows();

        //sets up Window
        if(gameState == 0) {
            //terminal.enterPrivateMode();
            new MainMenu();
            terminal.enterPrivateMode();
        }
        else if(gameState == 1) {
            terminal.enterPrivateMode();
            readLevel(levelName);
            drawView();
            
            TextModification.printToTerminal("Level was read!" + widthTerminal + " " + heightTerminal, widthMaze + 1, heightMaze + 1);
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
                    System.out.println(widthMaze);
                } else if(mapArray[index].contains("Height") == true) {     //catches special case "Height" in properties file
                    String[] tmpString = mapArray[index].split("=");
                    heightMaze = Integer.parseInt(tmpString[1]);
                    System.out.println(heightMaze);
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
                maze[xCoord][yCoord] = new Hero(xCoord, yCoord);
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
                System.out.println(Arrays.toString(dynamicObjs.toArray()));
                break;
            case 5:
                maze[xCoord][yCoord] = new KeyObj(xCoord,yCoord);
                break;
        }
    }
    
    public static void drawView() {
 
        for(int indexX = 0; indexX < widthMaze; indexX++) {
            for(int indexY = 0; indexY < heightMaze; indexY++) {
                
                if(maze[indexX][indexY] instanceof Wall) {
                    TextModification.putChar('X', indexX, indexY);
                }
                else if(maze[indexX][indexY] instanceof Entrance) {
                    //add char for entrance
                }
                else if(maze[indexX][indexY] instanceof Exit) {
                    TextModification.putChar('\u2023', indexX, indexY);
                }
                else if(maze[indexX][indexY] instanceof Hero) {
                    //add char for Hero
                }
                else if(maze[indexX][indexY] instanceof KeyObj) {
                    TextModification.putChar('\u1A57', indexX, indexY);
                }
                else if(maze[indexX][indexY] instanceof StaticObstacle) {
                    TextModification.putChar('\u2268', indexX, indexY);
                }
                else if(maze[indexX][indexY] instanceof DynamicObstacle) {
                    TextModification.putChar('\u3244', indexX, indexY);
                }
                
            }
        }
    }
    
}
