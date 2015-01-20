package puzzled;

import com.googlecode.lanterna.input.Key;
import static com.googlecode.lanterna.input.Key.Kind.ArrowDown;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import static puzzled.Main.hero;
import static puzzled.Main.keysCollected;
import static puzzled.Main.keysOfLevel;
import static puzzled.Main.lives;
import static puzzled.Main.maze;
import static puzzled.Main.terminal;
import static puzzled.Main.xZero;
import static puzzled.Main.yZero;

public class GameControls {

    public GameControls() {

    }

    public void readKeyInput() throws Exception {

        Key keyPressed = terminal.readInput();

        while (keyPressed == null) {
            Thread.sleep(1);
            keyPressed = terminal.readInput();
        }
        try {

            switch (keyPressed.getKind()) {
                case ArrowDown:
                    if (checkField(hero.getxCoord(), hero.getyCoord()) == 2 || checkField(hero.getxCoord(), hero.getyCoord()) == 4) {
                        TextModification.putChar('\u0020', hero.getxCoord() - xZero, hero.getyCoord() - yZero + 1);
                        hero.setyCoord(hero.getyCoord() + 1);
                    } else if (checkField(hero.getxCoord(), hero.getyCoord()) == 1 || checkField(hero.getxCoord(), hero.getyCoord()) == 3) {
                        lives--;
                    }
                    break;
                case ArrowUp:
                    if (checkField(hero.getxCoord(), hero.getyCoord() - 2) == 2 || checkField(hero.getxCoord(), hero.getyCoord() - 2) == 4) {
                        TextModification.putChar('\u0020', hero.getxCoord() - xZero, hero.getyCoord() - yZero + 1);
                        hero.setyCoord(hero.getyCoord() - 1);
                    } else if (checkField(hero.getxCoord(), hero.getyCoord() - 2) == 1 || checkField(hero.getxCoord(), hero.getyCoord() - 2) == 3) {
                        lives--;
                    }
                    break;
                case ArrowLeft:
                    if (checkField(hero.getxCoord() - 1, hero.getyCoord() - 1) == 2 || checkField(hero.getxCoord() - 1, hero.getyCoord() - 1) == 4) {
                        TextModification.putChar('\u0020', hero.getxCoord() - xZero, hero.getyCoord() - yZero + 1);
                        hero.setxCoord(hero.getxCoord() - 1);
                    } else if (checkField(hero.getxCoord() - 1, hero.getyCoord() - 1) == 1 || checkField(hero.getxCoord() - 1, hero.getyCoord() - 1) == 3) {
                        lives--;
                    }
                    break;
                case ArrowRight:
                    if (checkField(hero.getxCoord() + 1, hero.getyCoord() - 1) == 2 || checkField(hero.getxCoord() + 1, hero.getyCoord() - 1) == 4) {
                        TextModification.putChar('\u0020', hero.getxCoord() - xZero, hero.getyCoord() - yZero + 1);
                        hero.setxCoord(hero.getxCoord() + 1);
                    } else if (checkField(hero.getxCoord() + 1, hero.getyCoord() - 1) == 1 || checkField(hero.getxCoord() + 1, hero.getyCoord() - 1) == 3) {
                        lives--;
                    }
                    break;
                case Escape:
                    terminal.clearScreen();
                    new IngameMenu();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {

        }
    }

    //checks field x, y in @param maze for present object and returns referenced number of object
    public int checkField(int x, int y) {

        if (maze[x][y] instanceof Exit && keysCollected == keysOfLevel) {
            Main.levelFinished = true;
        }

        if (maze[x][y] instanceof Wall) {
            return 0;                                                           //Field not free to walk on
        } else if (maze[x][y] instanceof StaticObstacle) {
            return 1;                                                           //Field free to walk on, but static obstacle is in place
        } else if (maze[x][y] instanceof KeyObj) {
            maze[x][y] = "";
            keysCollected++;
            return 2;
        } else if (maze[x][y] instanceof DynamicObstacle) {                      //Field occupied by Mob
            return 3;
        } else {
            return 4;                                                           //Field free to walk on
        }

    }

    //checks which degree the node has and returns the value as an integer
    public int degreeOfNode(int x, int y) {

        int counter = 0;

        if (checkField(x + 1, y) == 4) {
            counter++;
        }
        if (checkField(x - 1, y) == 4) {
            counter++;
        }
        if (checkField(x, y + 1) == 4) {
            counter++;
        }
        if (checkField(x, y - 1) == 4) {
            counter++;
        }

        return counter;
    }

    //jumps back to the last node with more than 2 options
    public void toLastNode(int x, int y) {

        if (degreeOfNode(x, y) > 2) {
            maze[x][y] = "1";
        } else {
            maze[x][y] = "|";
        }

        if (checkField(x + 1, y) == 4) {
            toLastNode(x + 1, y);
        } else if (checkField(x - 1, y) == 4) {
            toLastNode(x + 1, y);
        } else if (checkField(x, y + 1) == 4) {
            toLastNode(x + 1, y);
        } else if (checkField(x, y - 1) == 4) {
            toLastNode(x, y - 1);
        }
    }
}
