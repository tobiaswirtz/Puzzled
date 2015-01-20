package puzzled;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import static puzzled.Main.drawView;
import static puzzled.Main.gameState;
import static puzzled.Main.terminal;
import static puzzled.Main.updateView;
import static puzzled.TextModification.printToTerminal;
import static puzzled.TextModification.xCentered;

public class IngameMenu {

    boolean loop = true;
    int y = 8;

    public IngameMenu() throws Exception {
        menuLoop();
    }

    public void menuLoop() throws Exception {
        printToTerminal(xCentered(11), 4, "Game Paused!", Terminal.Color.RED);
        printToTerminal(xCentered(13), 8, "Continue Game", Terminal.Color.WHITE);
        printToTerminal(xCentered(9), 10, "Load Game", Terminal.Color.WHITE);
        printToTerminal(xCentered(4), 12, "Help", Terminal.Color.WHITE);
        printToTerminal(xCentered(11), 14, "Save & Exit", Terminal.Color.WHITE);
        printToTerminal(xCentered(4), 16, "Exit", Terminal.Color.WHITE);
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

    public void selectOption(int yInt) {

        if (y > 16) {
            y = 8;
        } else if (y < 8) {
            y = 16;
        }

        if (y == 8) {
            printToTerminal(xCentered(13) - 2, 8, ">", Terminal.Color.RED);
            printToTerminal(xCentered(9) - 2, 10, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(4) - 2, 16, "  ", Terminal.Color.DEFAULT);
        } else if (y == 10) {
            printToTerminal(xCentered(13) - 2, 8, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(9) - 2, 10, ">", Terminal.Color.RED);
            printToTerminal(xCentered(4) - 2, 12, "  ", Terminal.Color.DEFAULT);
        } else if (y == 12) {
            printToTerminal(xCentered(9) - 2, 10, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(4) - 2, 12, ">", Terminal.Color.RED);
            printToTerminal(xCentered(11) - 2, 14, "  ", Terminal.Color.DEFAULT);
        } else if (y == 14){
            printToTerminal(xCentered(4) - 2, 12, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(11) - 2, 14, ">", Terminal.Color.RED);
            printToTerminal(xCentered(4) - 2, 16, "  ", Terminal.Color.DEFAULT);
        } else {
            printToTerminal(xCentered(13) - 2, 8, " ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(11) - 2, 14, "  ", Terminal.Color.DEFAULT);
            printToTerminal(xCentered(4) - 2, 16, ">", Terminal.Color.RED);
        }
    }
    
    public void enterOption(int yInt) throws Exception {

        if (y > 16) {
            y = 8;
        } else if (y < 8) {
            y = 16;
        }

        if (y == 8) {
            loop = false;
            drawView();
            updateView();
        } else if (y == 10) {
            //TODO: Load Game
        } else if (y == 12) {
            //TODO: Legende
        } else if (y == 14) {
            //TODO: Save & Exit
        }
        else if (y == 16) {
            System.exit(0);
        }

    }
}
