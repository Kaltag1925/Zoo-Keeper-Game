package UserInterface.Panels.ControlPanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class EmployeePanel extends ControlPanel {

    public EmployeePanel() {
        super();
        setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.drawString("ESC", getFontWidth(), getFontHeight() * (getMaxY() - 1));

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(": Back", getFontWidth() * 4, getFontHeight() * (getMaxY() - 1));
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch(key) {
            case KeyEvent.VK_ESCAPE :
                super.backKey();
                return true;
        }

        return false;
    }
}
