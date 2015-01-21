package puzzled;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main {

    static SwingTerminal terminal;
    static int widthMaze;
    static int heightMaze;
    static int widthTerminal;
    static int heightTerminal;
    static String levelName = "Level.properties";
    static int gameState = 0;
    static Screen screen;
    static int xZero;
    static int upperX;
    static int yZero;
    static int upperY;
    static int lives = 3;
    static int keysCollected = 0;
    static int keysOfLevel;
    static boolean levelFinished;
    static boolean newLevel = true;
    static TerminalSize screenSize;
    static TerminalSize newScreenSize;
    static int turnsPlayed = 0;

    static Object[][] maze;
    static ArrayList<DynamicObstacle> dynamicObjs = new ArrayList(50);
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
        
        JFXPanel fxpanel = new JFXPanel();
        
        newScreenSize = screenSize;
        boolean mainLoop = true;
        
        while (mainLoop == true) {
            playMusic();
            if (gameState == 0) {
                terminal.enterPrivateMode();
                new MainMenu();
            } else if (gameState == 1) {
                
                readLevel(levelName);
                drawView();
                GameControls control = new GameControls();
                while (true) {
                    
                    control.readKeyInput();
                    updateView();
                    newScreenSize = terminal.getTerminalSize();
                    if ((screenSize.getRows() != newScreenSize.getRows() || (screenSize.getColumns() != newScreenSize.getColumns()))) {
                        drawNewCutout();
                        screenSize = newScreenSize;
                    }
                }

            }
        }
    }
    
    public static void playMusic() {
        
        String filename = "song.mp3";
        
        Media song = new Media(Paths.get(filename).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(song);
        mediaPlayer.play();
    }

    //reads level information in and processes it
    public static void readLevel(String level) throws Exception {

        Set mapSet;
        Object[] tmp;
        ArrayList<String> tmpList;
        String tmpHeroCoords = "";

        try (FileReader reader = new FileReader(level)) {      //opening file stream
            Properties properties = new Properties();
            properties.load(reader);                                        //filling properties object with output of "stream"
            tmpList = new ArrayList(properties.size());
            mapSet = properties.entrySet();
            tmp = mapSet.toArray();
            mapArray = new String[tmp.length];

            for (int index = 0; index < tmp.length; index++) {
                mapArray[index] = tmp[index].toString();                      //casting Object[] tmp to String[] mapArray
            }

            for (int index = 0; index < mapArray.length; index++) {
                if (mapArray[index].contains("Width") == true) {             //catches special case "Width" in properties file
                    String[] tmpString = mapArray[index].split("=");
                    widthMaze = Integer.parseInt(tmpString[1]);
                } else if (mapArray[index].contains("Height") == true) {     //catches special case "Height" in properties file
                    String[] tmpString = mapArray[index].split("=");
                    heightMaze = Integer.parseInt(tmpString[1]);
                } else if (mapArray[index].contains("Lives") == true) {
                    String[] tmpString = mapArray[index].split("=");
                    lives = Integer.parseInt(tmpString[1]);
                } else if (mapArray[index].contains("KeysCollected") == true) {
                    String[] tmpString = mapArray[index].split("=");
                    keysCollected = Integer.parseInt(tmpString[1]);
                } else if (mapArray[index].contains("KeysOfLevel") == true) {
                    String[] tmpString = mapArray[index].split("=");
                    keysOfLevel = Integer.parseInt(tmpString[1]);
                } else if (mapArray[index].contains("NewLevel") == true) {
                    String[] tmpString = mapArray[index].split("=");
                    if (tmpString[1].equals("false")) {
                        newLevel = false;
                    } else {
                        newLevel = true;
                    }
                } else if (mapArray[index].contains("Hero") == true) {
                    tmpHeroCoords = mapArray[index];
                } else {                                                      //initializes Objects for every other Hash Map entry
                    tmpList.add(mapArray[index]);
                }
            }
            maze = new Object[widthMaze][heightMaze];
            System.out.println(maze.length);

            for (int index = 0; index < tmpList.size(); index++) {
                initObject(tmpList.get(index));
            }
            if (newLevel == false) {
                String[] transition = tmpHeroCoords.split("=");
                tmpHeroCoords = transition[1] + "=" + transition[0];
                initObject(tmpHeroCoords);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //initializes objects to @param maze
    public static void initObject(String setInput) {

        String[] tmp = setInput.split("=");

        String typeOfObject = tmp[1];                        //gets number of object to place at x, y

        tmp = tmp[0].split(",");                                            //separates x and y coordinates

        int xCoord = Integer.parseInt(tmp[0]);
        int yCoord = Integer.parseInt(tmp[1]);

        switch (typeOfObject) {                                              //creates the objects for each type of obstacle/object
            case "0":
                maze[xCoord][yCoord] = new Wall(xCoord, yCoord);
                break;
            case "1":
                maze[xCoord][yCoord] = new Entrance(xCoord, yCoord);
                if (newLevel == true) {
                    hero = new Hero(xCoord, yCoord);
                    maze[xCoord][yCoord] = hero;
                }
                break;
            case "2":
                maze[xCoord][yCoord] = new Exit(xCoord, yCoord);
                break;
            case "3":
                maze[xCoord][yCoord] = new StaticObstacle(xCoord, yCoord);
                break;
            case "4":
                DynamicObstacle aux = new DynamicObstacle(xCoord, yCoord);
                maze[xCoord][yCoord] = aux;
                dynamicObjs.add(aux);
                break;
            case "5":
                maze[xCoord][yCoord] = new KeyObj(xCoord, yCoord);
                keysOfLevel++;
                break;
            case "Hero":
                if (newLevel == false) {
                    hero = new Hero(xCoord, yCoord);
                    maze[xCoord][yCoord] = hero;
                }
        }
    }

    //initializes the view on the terminal layer
    public static void drawView() {
        terminal.clearScreen();
        if (widthMaze < widthTerminal && heightMaze < heightTerminal) {
            xZero = (widthTerminal / 2) - (widthMaze / 2);
            yZero = (heightTerminal / 2) - (heightMaze / 2);
            System.out.println(widthTerminal + " " + heightTerminal);

            for (int indexX = 0; indexX < widthMaze; indexX++) {
                for (int indexY = 0; indexY < heightMaze; indexY++) {

                    if (maze[indexX][indexY] instanceof Wall) {
                        TextModification.putChar('X', indexX + xZero, indexY + yZero, Terminal.Color.DEFAULT);
                    } else if (maze[indexX][indexY] instanceof Exit) {
                        TextModification.putChar('O', indexX + xZero, indexY + yZero, Terminal.Color.CYAN);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    } else if (maze[indexX][indexY] instanceof Hero) {
                        TextModification.putChar('\u265B', indexX + xZero, indexY + yZero, Terminal.Color.RED);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    } else if (maze[indexX][indexY] instanceof KeyObj) {
                        TextModification.putChar('\u0463', indexX + xZero, indexY + yZero, Terminal.Color.YELLOW);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    } else if (maze[indexX][indexY] instanceof StaticObstacle) {
                        TextModification.putChar('\u0470', indexX + xZero, indexY + yZero, Terminal.Color.GREEN);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    } else if (maze[indexX][indexY] instanceof DynamicObstacle) {
                        TextModification.putChar('\u2658', indexX + xZero, indexY + yZero, Terminal.Color.BLUE);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    }

                }
            }
        } else {
            xZero = hero.getxCoord() - (widthTerminal / 2);
            yZero = 1;

            for (int indexX = 0; indexX < widthTerminal; indexX++) {
                for (int indexY = 0; indexY < heightTerminal; indexY++) {

                    if (maze[indexX + xZero][indexY - 1 + yZero] instanceof Wall) {
                        TextModification.putChar('X', indexX, indexY + 1, Terminal.Color.DEFAULT);
                    } else if (maze[indexX + xZero][indexY - 1 + yZero] instanceof Exit) {
                        TextModification.putChar('O', indexX, indexY + 1, Terminal.Color.CYAN);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    } else if (maze[indexX + xZero][indexY - 1 + yZero] instanceof Hero) {
                        TextModification.putChar('\u265B', indexX, indexY + 1, Terminal.Color.RED);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    } else if (maze[indexX + xZero][indexY - 1 + yZero] instanceof KeyObj) {
                        TextModification.putChar('\u0463', indexX, indexY + 1, Terminal.Color.YELLOW);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    } else if (maze[indexX + xZero][indexY - 1 + yZero] instanceof StaticObstacle) {
                        TextModification.putChar('\u0470', indexX, indexY + 1, Terminal.Color.GREEN);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    } else if (maze[indexX + xZero][indexY - 1 + yZero] instanceof DynamicObstacle) {
                        TextModification.putChar('\u2658', indexX, indexY + 1, Terminal.Color.BLUE);
                        terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                    }

                }
            }
        }

    }

    //updates positions in @param maze of moving objects and displays them on the terminal
    public static void updateView() throws Exception {

        if (widthMaze <= widthTerminal && heightMaze <= heightTerminal) {
            generalUpdateView();
        } else {
            drawNewCutout();
            generalUpdateView();
        }
    }

    //updates xZero and yZero and draws a new cutout of the map on the terminal
    public static void drawNewCutout() {

        int xCoordHero = hero.getxCoord();
        int yCoordHero = hero.getyCoord();
        widthTerminal = newScreenSize.getColumns();
        heightTerminal = newScreenSize.getRows();

        boolean hasChanged = false;

        if (xCoordHero > xZero + widthTerminal) {
            xZero = xZero + (widthTerminal - 1);
            hasChanged = true;
        } else if (xCoordHero < xZero) {
            xZero = xZero - widthTerminal + 1;
            hasChanged = true;
        } else if (yCoordHero >= yZero + heightTerminal) {
            yZero = yZero + heightTerminal - 2;
            hasChanged = true;
        } else if (yCoordHero < yZero) {
            yZero = yZero - heightTerminal + 2;
            hasChanged = true;
        }
        try {
            if (hasChanged == true) {
                for (int indexX = 0; indexX < widthTerminal; indexX++) {
                    for (int indexY = 0; indexY < heightTerminal; indexY++) {

                        if (maze[indexX + xZero][indexY - 1 + yZero] instanceof Wall) {
                            TextModification.putChar('X', indexX, indexY + 1);
                        } else if (maze[indexX + xZero][indexY - 1 + yZero] instanceof Entrance) {
                            //add char for entrance
                        } else if (maze[indexX + xZero][indexY - 1 + yZero] instanceof Exit) {
                            TextModification.putChar('O', indexX, indexY + 1, Terminal.Color.CYAN);
                            terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                        } else if (maze[indexX + xZero][indexY - 1 + yZero] instanceof KeyObj) {
                            TextModification.putChar('\u0463', indexX, indexY + 1, Terminal.Color.YELLOW);
                            terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                        } else if (maze[indexX + xZero][indexY - 1 + yZero] instanceof StaticObstacle) {
                            TextModification.putChar('\u0470', indexX, indexY + 1, Terminal.Color.GREEN);
                            terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                        } else if (maze[indexX + xZero][indexY - 1 + yZero] instanceof DynamicObstacle) {
                            TextModification.putChar('\u2658', indexX, indexY + 1, Terminal.Color.BLUE);
                            terminal.applyForegroundColor(Terminal.Color.DEFAULT);
                        } else {
                            TextModification.putChar('\u0020', indexX, indexY + 1, Terminal.Color.DEFAULT);
                        }

                    }
                }
                hasChanged = false;
            }
        } catch (Exception e) {

        }

    }

    //handles @param hero and mob movements as well as health, keys and gameState
    public static void generalUpdateView() throws Exception {

        if (widthMaze < widthTerminal && heightMaze < heightTerminal) {
            TextModification.putChar('\u265B', hero.getxCoord() + xZero, hero.getyCoord() + yZero, Terminal.Color.RED);
            TextModification.printToTerminal(1, 0, "Lives left: " + lives, Terminal.Color.DEFAULT);
            TextModification.printToTerminal("Keys collected: " + keysCollected + "/" + keysOfLevel, 16, 0);
        } else {

            TextModification.putChar('\u265B', hero.getxCoord() - xZero, hero.getyCoord() - yZero + 1, Terminal.Color.RED);
            TextModification.printToTerminal(1, 0, "Lives left: " + lives, Terminal.Color.DEFAULT);
            TextModification.printToTerminal("Keys collected: " + keysCollected + "/" + keysOfLevel, 16, 0);
        }
        /*DynamicObstacle tmp;
         try {
         for (int indexX = hero.getxCoord() - 5; indexX < hero.getxCoord() + 5; indexX++) {
         for (int indexY = hero.getyCoord() - 5; indexY < hero.getyCoord() + 5; indexY++) {
         if (maze[indexX][indexY] instanceof DynamicObstacle) {
         tmp = (DynamicObstacle) maze[indexX][indexY];
         tmp.moveToHero();
         }
         }
         }
         } catch (ArrayIndexOutOfBoundsException e) {

         }
         */

        turnsPlayed++;

        if (turnsPlayed % 2 == 0) {
            for (DynamicObstacle mob : dynamicObjs) {
                mob.randomMovement();

            }
        }
        if (lives == 0) {
            terminal.clearScreen();
            gameState = 0;
            TextModification.printToTerminal("You lost!", TextModification.xCentered(9), heightTerminal / 2);
            Thread.sleep(3000);
            terminal.clearScreen();
            lives = 3;
            keysCollected = 0;
            new MainMenu();
        }
        if (levelFinished == true) {
            Thread.sleep(250);
            terminal.clearScreen();
            TextModification.printToTerminal("Level done! Congratulations!", TextModification.xCentered(28), heightTerminal / 2);
            Thread.sleep(3000);
            terminal.clearScreen();
            levelFinished = false;
            gameState = 0;
            keysCollected = 0;
            lives = 3;
            new MainMenu();

        }
    }

    public static void saveGame(Date d) {

        String s = d.toString();
        s = s.substring(11);
        s = s.replace(":", "");

        try {
            Properties p = new Properties();
            File f = new File(s + ".properties");
            System.out.println(Integer.toString(lives));
            p.setProperty("Width", Integer.toString(widthMaze));
            p.setProperty("Height", Integer.toString(heightMaze));
            p.setProperty("Lives", Integer.toString(lives));
            p.setProperty("KeysCollected", Integer.toString(keysCollected));
            p.setProperty("KeysOfLevel", Integer.toString(keysOfLevel));
            p.setProperty("NewLevel", "false");

            for (int indexX = 0; indexX < widthMaze; indexX++) {
                for (int indexY = 0; indexY < heightMaze; indexY++) {
                    if (maze[indexX][indexY] instanceof Wall) {
                        p.setProperty(indexX + "," + indexY, "0");
                    } else if (maze[indexX][indexY] instanceof Entrance) {
                        p.setProperty(indexX + "," + indexY, "1");
                    } else if (maze[indexX][indexY] instanceof Exit) {
                        p.setProperty(indexX + "," + indexY, "2");
                    } else if (maze[indexX][indexY] instanceof StaticObstacle) {
                        p.setProperty(indexX + "," + indexY, "3");
                    } else if (maze[indexX][indexY] instanceof DynamicObstacle) {
                        p.setProperty(indexX + "," + indexY, "4");
                    } else if (maze[indexX][indexY] instanceof KeyObj) {
                        p.setProperty(indexX + "," + indexY, "5");
                    } else if (maze[indexX][indexY] instanceof Hero) {
                        p.setProperty("Hero", indexX + "," + indexY);
                    }
                }
            }

            OutputStream out = new FileOutputStream(f);
            p.store(out, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
