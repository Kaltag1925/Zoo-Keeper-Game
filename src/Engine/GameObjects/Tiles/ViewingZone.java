package Engine.GameObjects.Tiles;

import UserInterface.Panels.DescriptionPanel.DescriptionPanel;

import java.awt.*;

public class ViewingZone extends Zone {

    public ViewingZone(String name, Tile end, Tile start) {
        super(name, end, start);
    }

    @Override
    public ControlPanelFormat getControlPanelControls() {
        return new ControlPanelFormat();
    }

    @Override
    public void paintToLargeDescriptionPanel(DescriptionPanel panel, Graphics g) {

    }
}
