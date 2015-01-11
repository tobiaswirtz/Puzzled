
package puzzled;

import com.googlecode.lanterna.input.Key;
import static com.googlecode.lanterna.input.Key.Kind.ArrowDown;
import static puzzled.Main.terminal;

public class GameControls {
    
    public void readKeyInput() {
        Key keyPressed = terminal.readInput();
        if(Main.gameState == 0) {
            if(keyPressed.getKind() == ArrowDown) {
                //TODO: Implement algorithm for menu scrolling
            }
        }
        else {
                //TODO: Implement game controls
        }
    }
    
    public void checkField(int x, int y) {
        
    }
}
