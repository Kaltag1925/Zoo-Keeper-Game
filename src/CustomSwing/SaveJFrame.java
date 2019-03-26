package CustomSwing;

import XML.CreateSaveFolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class SaveJFrame extends JFrame {

    public SaveJFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                CreateSaveFolder.save();
                System.exit(0);
            }
        });
    }
}
