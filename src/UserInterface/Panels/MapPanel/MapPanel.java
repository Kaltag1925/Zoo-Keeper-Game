package UserInterface.Panels.MapPanel;

import Engine.Logic.MainData;
import Engine.Logic.Ticks.IHasKeyBinds;
import Engine.Logic.Ticks.Tick;
import UserInterface.GameMain;
import UserInterface.Panels.DescriptionPanel.DescriptionPanel;
import UserInterface.Panels.DescriptionPanel.DescriptionPanelManager;
import UserInterface.Panels.GamePanel;

import java.awt.*;

public abstract class MapPanel extends GamePanel implements IHasKeyBinds {

    MapPanel() {
        super();
        setBackground(Color.BLACK);
    }

    @Override
    public void open() {
        GameMain gameMain = MainData.mainData.getGameMain();
        MapPanelManager manager = MainData.mainData.getMapPanelManager();

        if (manager.getCurrentMapPanel() != null && !manager.getCurrentMapPanel().equals(this)) {
            manager.getCurrentMapPanel().close();
        }

        DescriptionPanelManager descriptionPanelManager = MainData.mainData.getDescriptionPanelManager();
        DescriptionPanel currentLongDesc = descriptionPanelManager.getCurrentLongDescriptionPanel();
        if (currentLongDesc != null) {
            gameMain.remove(currentLongDesc);
        }

        manager.setCurrentMapPanel(this);
        Tick.addToInFocus(this, Tick.LAST_BINDER);

        gameMain.add(this, manager.getMapConstraints());
        MainData.mainData.getMainFrame().revalidate();
    }

    public void load() {
        GameMain gameMain = MainData.mainData.getGameMain();
        MapPanelManager manager = MainData.mainData.getMapPanelManager();

        manager.setCurrentMapPanel(this);
        Tick.addToInFocus(this, Tick.LAST_BINDER);

        gameMain.add(this, manager.getMapConstraints());
        MainData.mainData.getMainFrame().revalidate();
    }

    @Override
    public void close() {
        GameMain gameMain = MainData.mainData.getGameMain();

        DescriptionPanelManager descriptionPanelManager = MainData.mainData.getDescriptionPanelManager();
        DescriptionPanel currentLongDesc = descriptionPanelManager.getCurrentLongDescriptionPanel();
        if (currentLongDesc != null) {
            gameMain.add(currentLongDesc, DescriptionPanelManager.getLongDescriptionConstraints());
        }


        Tick.removeFromInFocus(this);

        gameMain.remove(this);
        MainData.mainData.getMainFrame().revalidate();
    }
}
