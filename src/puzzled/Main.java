/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    static int width;
    static int height;
    
    public static void main(String[] args) throws Exception {
        terminal = TerminalFacade.createSwingTerminal();
        terminal.enterPrivateMode();
        TerminalSize size = terminal.getTerminalSize();
        terminal.applyForegroundColor(Terminal.Color.RED);
        terminal.applyBackgroundColor(Terminal.Color.BLACK);
        readLevel();
        
        //sets up Window
        
    }
    
    //allows for insertion of character at coordinates x, y
    
    public static void putChar(char input, int x, int y) {
        terminal.moveCursor(x, y);
        terminal.putCharacter(input);
    }
    
    //reads level information in and processes it
    
    public static void readLevel() throws Exception {
        
        Set mapSet;
        Object[] tmp;
        String[] mapArray;
        
        try (FileReader reader = new FileReader("Level.properties")) {      //opening file stream
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
                    width = Integer.parseInt(tmpString[1]);
                } else if(mapArray[index].contains("Height") == true) {     //catches special case "Height" in properties file
                    String[] tmpString = mapArray[index].split("=");
                    height = Integer.parseInt(tmpString[1]);
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
        
        switch(typeOfObject) {
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
