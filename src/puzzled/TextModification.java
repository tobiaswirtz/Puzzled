
package puzzled;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import static puzzled.Main.heightTerminal;
import static puzzled.Main.terminal;
import static puzzled.Main.widthTerminal;

public class TextModification {
    
    //allows for insertion of character at coordinates x, y
    
    public static void putChar(char input, int x, int y) {
        terminal.moveCursor(x, y);
        terminal.putCharacter(input);
    }
    
    //allows for insertion of strings at coordinates x, y
    
    public static void printToTerminal(String input, int x, int y) {
        char[] charToPrint = new char[input.length()];
        
        for(int index = 0; index < input.length(); index++) {
            charToPrint[index] = input.charAt(index);
            putChar(charToPrint[index], x + index, y);
        }
    }
    
    //colored printToTerminal()
    
    public static void printToTerminal(int x, int y, String input, Color color) {
        terminal.applyForegroundColor(color);
        printToTerminal(input, x, y);
        terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
    }
    //gets the x coordinate of a String that centers it
    
    public static int xCentered(int stringLength) {
        int centerOfTerminal = Main.widthTerminal / 2;
        int halfOfString = stringLength / 2;
        return centerOfTerminal - halfOfString;
    }
    
    public static void resizeTerminal() {
        
        if(widthTerminal != 100 || heightTerminal != 30) {
            Main.drawView();
        }
        
    }
}
