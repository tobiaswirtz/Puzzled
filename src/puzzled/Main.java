/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzled;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;

public class Main {
    
    static SwingTerminal terminal;
    
    public static void main(String[] args) {
        terminal = TerminalFacade.createSwingTerminal();
        terminal.enterPrivateMode();
        TerminalSize size = terminal.getTerminalSize();
        
        //sets up Window
        
    }
    
    public static void putChar(char input, int x, int y) {
        terminal.moveCursor(x, y);
        terminal.putCharacter(input);
    }
    
}
