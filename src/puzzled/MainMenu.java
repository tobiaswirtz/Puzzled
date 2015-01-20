package puzzled;

import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import static puzzled.Main.gameState;
import static puzzled.Main.screen;
import static puzzled.Main.terminal;
import static puzzled.TextModification.printToTerminal;
import static puzzled.TextModification.xCentered;

public class MainMenu {

    
    int y = 8;
    boolean loop = true;

    public MainMenu() throws Exception {

        //creates menu items
        menuLoop();

    }

    //loops the menu
    public void menuLoop() throws Exception {
        
        printToTerminal(xCentered(19), 5, "Welcome to Puzzled!", Terminal.Color.RED);
        printToTerminal(xCentered(10), 8, "Start Game", Terminal.Color.WHITE);
        printToTerminal(xCentered(9), 10, "Load Game", Terminal.Color.WHITE);
        printToTerminal(xCentered(4), 12, "Help", Terminal.Color.WHITE);
        printToTerminal(xCentered(4), 14, "Exit", Terminal.Color.WHITE);
        selectOption(y);
        
        while (loop == true) {

            Key keyPressed = terminal.readInput();

            while (keyPressed == null) {
                Thread.sleep(1);
                keyPressed = terminal.readInput();
            }
            
            switch (keyPressed.getKind()) {
                case ArrowDown:
                    y += 2;
                    selectOption(y);
                    break;
                case ArrowUp:
                    y -= 2;
                    selectOption(y);
                    break;
                case Enter:
                    enterOption(y);
                    break;
            }
        }

    }

    public void enterOption(int yInt) {

        if (y > 14) {
            y = 8;
        } else if (y < 8) {
            y = 14;
        }

        if (y == 8) {
            loop = false;
            gameState = 1;
        } else if (y == 10) {
            //TODO
        } else if( y == 12) {
            //TODO: Legende
        }
        else if (y == 14) {
            System.exit(0);
        }

    }

    public void selectOption(int yInt) {

        if (y > 14) {
            y = 8;
        } else if (y < 8) {
            y = 14;
        }

        if (y == 8) {
            printToTerminal(xCentered(10) - 2, 8, ">", Terminal.Color.RED);
            printToTerminal(xCentered(9) - 2, 10, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(4) - 2, 12, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(4) - 2, 14, "  ", Terminal.Color.DEFAULT);
        } else if (y == 10) {
            printToTerminal(xCentered(10) - 2, 8, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(9) - 2, 10, ">", Terminal.Color.RED);
            printToTerminal(xCentered(4) - 2, 12, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(4) - 2, 14, "  ", Terminal.Color.DEFAULT);
        } else if (y == 12) {
            printToTerminal(xCentered(10) - 2, 8, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(9) - 2, 10, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(4) - 2, 12, ">", Terminal.Color.RED);
            printToTerminal(xCentered(4) - 2, 14, "  ", Terminal.Color.DEFAULT);
        } else {
            printToTerminal(xCentered(10) - 2, 8, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(9) - 2, 10, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(4) - 2, 12, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(4) - 2, 14, ">", Terminal.Color.RED);
        }
    }
}
