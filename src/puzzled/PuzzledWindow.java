package puzzled;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;

public class PuzzledWindow extends Window {

    public PuzzledWindow() {
        super("");
        Panel selecOptions = new Panel(new Border.Invisible(), Panel.Orientation.VERTICAL);
        Panel header = new Panel(new Border.Invisible(), Panel.Orientation.VERTICAL);
        
        selecOptions.addComponent(header);
        addComponent(selecOptions);
        
        header.addComponent(new Label("Welcome to Puzzled"));
        selecOptions.addComponent(new Button("Start Game", new Action() {
            @Override
            public void doAction() {
                Main.gameState = 1;
            }
        }));
        
        selecOptions.addComponent(new Button("Load Level", new Action() {
            @Override
            public void doAction() {
                //TODO: implement level load function
            }
        }));
        
        selecOptions.addComponent(new Button("Help", new Action() {
            @Override
            public void doAction() {
                //TODO: Design Help page
            }
        }));
        
        selecOptions.addComponent(new Button("Exit", new Action() {
            @Override
            public void doAction() {
                Main.gameState = 1;
            }
        }));
    }
}
