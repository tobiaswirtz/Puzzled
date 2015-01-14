package puzzled;

import com.googlecode.lanterna.input.Key;
import static com.googlecode.lanterna.input.Key.Kind.ArrowDown;
import static puzzled.Main.hero;
import static puzzled.Main.maze;
import static puzzled.Main.terminal;

public class GameControls {

    public GameControls() {

    }

    public void readKeyInput() throws Exception {
        
            Key keyPressed = terminal.readInput();
            
            while(keyPressed == null) {
                Thread.sleep(1);
                keyPressed = terminal.readInput();
            }
            
            if (Main.gameState == 0) {
                switch (keyPressed.getKind()) {
                    case ArrowDown:
                        break;
                    case ArrowUp:
                        break;
                }
            } else if (Main.gameState == 1) {
                switch (keyPressed.getKind()) {
                    case ArrowDown:
                        if (checkField(hero.getxCoord(), hero.getyCoord() + 1) == true) {
                            TextModification.putChar(' ', hero.getxCoord(), hero.getyCoord());
                            hero.setyCoord(hero.getyCoord() + 1);
                        }
                        break;
                    case ArrowUp:
                        if (checkField(hero.getxCoord(), hero.getyCoord() - 1) == true) {
                            TextModification.putChar(' ', hero.getxCoord(), hero.getyCoord());
                            hero.setyCoord(hero.getyCoord() - 1);
                        }
                        break;
                    case ArrowLeft:
                        if (checkField(hero.getxCoord() - 1, hero.getyCoord()) == true) {
                            TextModification.putChar(' ', hero.getxCoord(), hero.getyCoord());
                            hero.setxCoord(hero.getxCoord() - 1);
                        }
                        break;
                    case ArrowRight:
                        if (checkField(hero.getxCoord() + 1, hero.getyCoord()) == true) {
                            TextModification.putChar(' ', hero.getxCoord(), hero.getyCoord());
                            hero.setyCoord(hero.getyCoord() + 1);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    

    public boolean checkField(int x, int y) {
        if (maze[x][y].equals('X') || maze[x][y].equals('\u2268')) {
            return false;
        } else {
            return true;
        }
    }
}
