
package puzzled;

import static puzzled.Main.terminal;

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
    
    //gets the x coordinate of a String that centers it
    
    public static int xCentered(int stringLength) {
        int centerOfTerminal = Main.widthTerminal / 2;
        int halfOfString = stringLength / 2;
        return centerOfTerminal - halfOfString;
    }
}
