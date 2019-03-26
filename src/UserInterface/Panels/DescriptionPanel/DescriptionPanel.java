package UserInterface.Panels.DescriptionPanel;

import Engine.Logic.MainData;
import UserInterface.GameMain;
import UserInterface.Panels.GamePanel;
import UserInterface.Panels.MapPanel.MapPanelManager;

import java.awt.*;

public abstract class DescriptionPanel extends GamePanel {

    private GridBagConstraints constraints;

    protected void setConstraints(GridBagConstraints constraints) {
        this.constraints = constraints;
    }

    DescriptionPanel() {
        super();
        setBackground(Color.BLACK);

    }

    public void open() {
        GameMain gameMain = MainData.mainData.getGameMain();
        DescriptionPanelManager manager = MainData.mainData.getDescriptionPanelManager();

        //TODO: This should all be centralized
        if (constraints.equals(manager.getLongDescriptionConstraints())) {
            MapPanelManager mapPanelManager = MainData.mainData.getMapPanelManager();
            gameMain.remove(mapPanelManager.getCurrentMapPanel());
            manager.setCurrentLongDescriptionPanel(this);
        } else {
            manager.setCurrentShortDescriptionPanel(this);
        }

        gameMain.add(this, constraints);
        MainData.mainData.getMainFrame().revalidate();
    }

    public void close() {
        GameMain gameMain = MainData.mainData.getGameMain();
        DescriptionPanelManager manager = MainData.mainData.getDescriptionPanelManager();

        gameMain.remove(this);

        if (constraints.equals(manager.getLongDescriptionConstraints())) {
            MapPanelManager mapPanelManager = MainData.mainData.getMapPanelManager();
            mapPanelManager.getCurrentMapPanel().load();
            manager.setCurrentLongDescriptionPanel(null);
        } else {
            manager.setCurrentShortDescriptionPanel(null);
        }

        MainData.mainData.getMainFrame().revalidate();
    }
}
