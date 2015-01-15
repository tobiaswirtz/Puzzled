
package puzzled;

import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import static puzzled.Main.screen;
import static puzzled.Main.terminal;

public class MainMenu{
    
    int cursorPos = 10;
    
    public MainMenu() {
        
        //creates menu items
        GUIScreen gui = new GUIScreen(screen);
        gui.getScreen().startScreen();
        PuzzledWindow window = new PuzzledWindow();
        gui.showWindow(window, GUIScreen.Position.CENTER);
        gui.getScreen().stopScreen();
        
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
