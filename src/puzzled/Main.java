package puzzled;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import java.io.FileReader;
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
    static int gameState = 0;
    
    
    public static void main(String[] args) throws Exception {
        
        terminal = TerminalFacade.createSwingTerminal();
        terminal.setCursorVisible(false);
        terminal.enterPrivateMode();
        TerminalSize screenSize = terminal.getTerminalSize();
        terminal.applyBackgroundColor(Terminal.Color.BLACK);
        widthTerminal = screenSize.getColumns();
        heightTerminal = screenSize.getRows();

        //sets up Window
        if(gameState == 0) {
            new MainMenu();
        }
        else if(gameState == 1) {
            readLevel(levelName);
            TextModification.printToTerminal("Level was read!" + widthTerminal + " " + heightTerminal, widthMaze + 1, heightMaze + 1);
        }
        
        
    }

    
    //reads level information in and processes it
    
    public static void readLevel(String level) throws Exception {
        
        Set mapSet;
        Object[] tmp;
        String[] mapArray;
        
        try (FileReader reader = new FileReader(level)) {      //opening file stream
            Properties properties = new Properties();                       
            properties.load(reader);                                        //filling properties object with output of "stream"
            Enumeration em = properties.keys();
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
                    initObject(mapArray[index]);
                }
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
                new Wall(xCoord, yCoord);
                break;
            case 1:
                new Entrance(xCoord, yCoord);
                break;
            case 2:
                new Exit(xCoord, yCoord);
                break;
            case 3:
                new StaticObstacle(xCoord, yCoord);
                break;
            case 4:
                new DynamicObstacle(xCoord, yCoord);
                break;
            case 5:
                new KeyObj(xCoord,yCoord);
        }
    }
    
}
