
package puzzled;

import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import static puzzled.Main.terminal;

public class MainMenu {
    
    int cursorPos = 10;
    
    public MainMenu() {
        
        //creates menu items
        
        terminal.applyForegroundColor(Terminal.Color.RED);
        TextModification.printToTerminal("Welcome to Puzzled!", TextModification.xCentered(19), 5);
        terminal.applyForegroundColor(Terminal.Color.GREEN);
        TextModification.printToTerminal("> Start Game", TextModification.xCentered(12), 10);
        TextModification.printToTerminal("> Load Level", TextModification.xCentered(12), 12);
        TextModification.printToTerminal("> Help", TextModification.xCentered(6), 14);
        TextModification.printToTerminal("> Exit", TextModification.xCentered(6), 16);
        
    }
    
    
    //loops the menu
    public void menuLoop() {
        while(true) {
            if(cursorPos == 10) {
                
            }
        }
        
    }
    
    
    //cursor position modifying methods 
    
    public int newCursorPosUp() {
        if(cursorPos > 11) {
            cursorPos -= 2;
        } else {
            cursorPos = 16;
        }
        return cursorPos;
    }
    
    public int newCursorPosDown() {
        if(cursorPos < 15) {
            cursorPos += 2;
        } else {
            cursorPos = 10;
        }
        return cursorPos;
    }
    
}
