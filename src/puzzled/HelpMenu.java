/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzled;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import static puzzled.Main.gameState;
import static puzzled.Main.terminal;
import static puzzled.TextModification.printToTerminal;
import static puzzled.TextModification.xCentered;

/**
 *
 * @author tobia_000
 */
public class HelpMenu {
    
    public HelpMenu() throws Exception {
        printToTerminal(xCentered(4), 4, "Help", Terminal.Color.RED);
        printToTerminal(xCentered(8), 6, "Controls", Terminal.Color.CYAN);
        printToTerminal(xCentered(30), 7, "-Use arrow keys to move around", Terminal.Color.WHITE);
        printToTerminal(xCentered(37), 8, "-Press Esc to get to the in-game menu", Terminal.Color.WHITE);
        printToTerminal(xCentered(11), 10, "Symbol Index", Terminal.Color.CYAN);
        printToTerminal(xCentered(8), 11, "X - Wall", Terminal.Color.WHITE);
        printToTerminal(xCentered(8), 12, "O - Exit", Terminal.Color.CYAN);       //FIND NEW UNICODE
        printToTerminal(xCentered(10), 13, "\u265B - Player", Terminal.Color.RED);
        printToTerminal(xCentered(7), 14, "\u0463 - Key", Terminal.Color.YELLOW);
        printToTerminal(xCentered(11), 15, "\u2658 - Monster", Terminal.Color.BLUE);
        printToTerminal(xCentered(10), 16, "\u0470 - Cactus", Terminal.Color.GREEN);
        printToTerminal(xCentered(19), 20, "Press Esc to return", Terminal.Color.WHITE);
        
        returnTo();
    }
    
    
    public void returnTo() throws Exception {
        
        Key key = terminal.readInput();
        
        while(key == null) {
            Thread.sleep(1);
            key = terminal.readInput();
        }
        
        switch(key.getKind()) {
            case Escape:
                if(gameState == 0) {
                    terminal.clearScreen();
                    new MainMenu();
                } else {
                    terminal.clearScreen();
                    new IngameMenu();
                }
                break;
        }
    }
    
}
