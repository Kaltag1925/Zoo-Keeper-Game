package UserInterface.Panels.ControlPanel;

import Engine.GameObjects.Tiles.Zone;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ZoneAssignmentPanel extends ControlPanel {

    private Zone zone;

    public ZoneAssignmentPanel() {
        super();
    }

    public void open(Zone zone) {
        super.open();

        this.zone = zone;
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
        switch (key) {
            case KeyEvent.VK_ESCAPE:
                backKey();
                return true;
        }

        return false;
    }
}
