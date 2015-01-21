package puzzled;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.File;
import java.util.ArrayList;
import static puzzled.Main.drawNewCutout;
import static puzzled.Main.newScreenSize;
import static puzzled.Main.readLevel;
import static puzzled.Main.screenSize;
import static puzzled.Main.terminal;
import static puzzled.TextModification.xCentered;
import static puzzled.TextModification.printToTerminal;
import static puzzled.TextModification.printToTerminal;

public class LoadMenu {

    int y = 6;
    int yStart = 6;
    boolean loop = true;

    Object[] toDisplay;
    ArrayList levels;

    public LoadMenu() throws Exception {
        menuLoop();
    }

    public void menuLoop() throws Exception {
        File folder = new File(System.getProperty("user.dir"));
        File[] files = folder.listFiles();
        levels = new ArrayList(files.length);

        for (File file : files) {
            if (file.getName().contains(".properties")) {
                levels.add(file.getName());
            }
        }

        toDisplay = levels.toArray();

        printToTerminal(xCentered(11), 4, "Saved Games", Terminal.Color.RED);

        for (int index = 0; index < toDisplay.length; index++) {
            String tmp = (String) toDisplay[index];
            printToTerminal(xCentered(41), 6, ">", Terminal.Color.RED);
            printToTerminal(xCentered(tmp.length()), index + yStart, tmp, Terminal.Color.DEFAULT);
        }

        Key key = terminal.readInput();
        
        
        while (loop == true) {
            
            while (key == null) {
                Thread.sleep(1);
                key = terminal.readInput();
            }

            switch (key.getKind()) {
                case ArrowDown:
                    y = y + 1;
                    selectOption(y);
                    break;
                case ArrowUp:
                    y = y  + 1;
                    selectOption(y);
                    break;
                case Escape:
                    terminal.clearScreen();
                    new MainMenu();
                    break;
                case Enter:
                    enterOption(y);
                    break;
            }
        }
    }

    public void selectOption(int yIn) {

        if (yIn > (yStart + (toDisplay.length - 1))) {
            y = 6;
        } else if (yIn < 6) {
            y = (yStart + (toDisplay.length - 1));
        } else {
            y = yIn;
        }
        printToTerminal(xCentered(41), y - 1, "  ", Terminal.Color.DEFAULT);
        printToTerminal(xCentered(41), y, ">", Terminal.Color.RED);
        printToTerminal(xCentered(41), y + 1, "  ", Terminal.Color.DEFAULT);
    }

    public void enterOption(int yIn) throws Exception {
        loop = false;
        int index = yIn - yStart;

        Main.levelName = (String) levels.get(index);
        readLevel(Main.levelName);
        Main.drawView();
        GameControls control = new GameControls();
        while (true) {
            control.readKeyInput();
            Main.updateView();
            Main.newScreenSize = terminal.getTerminalSize();
            if ((screenSize.getRows() != newScreenSize.getRows() || (screenSize.getColumns() != newScreenSize.getColumns()))) {
                drawNewCutout();
                screenSize = newScreenSize;
            }
        }
    }
}
