package puzzled;

import com.googlecode.lanterna.input.Key;
import static com.googlecode.lanterna.input.Key.Kind.ArrowDown;
import static puzzled.Main.terminal;

public class GameControls {

    public void readKeyInput() {
        Key keyPressed = terminal.readInput();
        if (Main.gameState == 0) {
            switch(keyPressed.getKind()) {
                case ArrowDown:
                    break;
                case ArrowUp:
                    break;
            }
        } else {
            switch (keyPressed.getKind()) {
                case ArrowDown:
                    break;
                case ArrowUp:
                    break;
                case ArrowLeft:
                    break;
                case ArrowRight:
                    break;
                default:
                    break;
            }
        }
    }

    public void checkField(int x, int y) {

    }
}
