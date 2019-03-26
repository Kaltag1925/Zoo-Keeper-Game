package UserInterface.Panels.DescriptionPanel;

import UserInterface.Panels.ControlPanel.Listed;

import java.awt.*;

public class ObjectLongDescriptionPanel extends DescriptionPanel {

    private Listed object;

    public Listed getObject() {
        return object;
    }

    public void setObject(Listed object) {
        this.object = object;
    }

    public ObjectLongDescriptionPanel(Listed object) {
        super();
        this.object = object;
        setConstraints(DescriptionPanelManager.getLongDescriptionConstraints());
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        object.paintToLargeDescriptionPanel(this, g);
    }
}
