package UserInterface.Panels.MapPanel;

import Engine.Logic.MainData;

import java.awt.*;

public class MapPanelManager {

    //<editor-fold desc="LocalMapPanel">
    private LocalMapPanel localMapPanel;

    public LocalMapPanel getLocalMapPanel() {
        return localMapPanel;
    }
    //</editor-fold>

    //<editor-fold desc="Description">
    private MapPanel currentMapPanel;

    public MapPanel getCurrentMapPanel() {
        return currentMapPanel;
    }

    public void setCurrentMapPanel(MapPanel currentMapPanel) {
        this.currentMapPanel = currentMapPanel;
    }
    //</editor-fold>

    //<editor-fold desc="Constraints">
    private GridBagConstraints mapConstraints = new GridBagConstraints();
    {
        mapConstraints.fill = GridBagConstraints.BOTH;
        mapConstraints.weightx = 8;
        mapConstraints.weighty = 1;
        mapConstraints.gridheight = 2;
        mapConstraints.gridx = 0;
        mapConstraints.gridy = 0;
        mapConstraints.insets = new Insets(MainData.mainData.getFontManager().getFontHeight(), MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight(), MainData.mainData.getFontManager().getFontWidth());
    }

    public GridBagConstraints getMapConstraints() {
        return mapConstraints;
    }
    //</editor-fold>

    public MapPanelManager() {
        localMapPanel = new LocalMapPanel();
    }
}
