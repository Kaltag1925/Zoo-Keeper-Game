package UserInterface.Panels.ControlPanel;

import UserInterface.Panels.DescriptionPanel.ObjectLongDescriptionPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ObjectPanel extends ControlPanel {

    private Listed object;
    private List<Listed.ControlPanelObject> controlPanelObjects;

    public ObjectPanel(Listed object) {
        super();

        this.object = object;
        this.controlPanelObjects = object.getControlPanelControls();
        setAttachedViewPanel(new ObjectLongDescriptionPanel(object));
    }

    @Override
    public void open() {
        super.open();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.drawString("ESC", getFontWidth(), getFontHeight() * (getMaxY() - 1));

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(": Back", getFontWidth() * 4, getFontHeight() * (getMaxY() - 1));

        if (controlPanelObjects != null && controlPanelObjects.size() != 0) {
            int i = 0;
            for (Listed.ControlPanelObject control : controlPanelObjects) {
                i++;
                String key = control.getControl();
                int keyLength = key.length() + 1;

                g.setColor(Color.GREEN);
                g.drawString(key, getFontWidth(), getFontHeight() * i);

                g.setColor(Color.LIGHT_GRAY);
                g.drawString(": " + control.getControlName(), getFontWidth() * keyLength, getFontHeight() * i);
            }
        }
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch (key) {
            case KeyEvent.VK_ESCAPE:
                backKey();
                return true;
        }

        for (Listed.ControlPanelObject control : controlPanelObjects) {
            if (key == control.getKeyCode()) {
                try {
                    control.getControlMethod().invoke(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "ObjectPanel";
    }
}