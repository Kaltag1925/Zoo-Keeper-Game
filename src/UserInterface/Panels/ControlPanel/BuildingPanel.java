package UserInterface.Panels.ControlPanel;

import Engine.GameObjects.Entities.Door;
import Engine.GameObjects.Entities.WalkPath;
import Engine.GameObjects.Entities.Wall;
import Engine.Logic.MainData;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BuildingPanel extends ControlPanel {

    public BuildingPanel() {
        super();
        setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.drawString("d", getFontWidth(), getFontHeight());
        g.drawString("w", getFontWidth(), getFontHeight() * 2);
        g.drawString("p", getFontWidth(), getFontHeight() * 3);
        g.drawString("ESC", getFontWidth(), getFontHeight() * (getMaxY() - 1));

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(": Door", getFontWidth() * 2, getFontHeight());
        g.drawString(": Wall", getFontWidth() * 2, getFontHeight() * 2);
        g.drawString(": Path", getFontWidth() * 2, getFontHeight() * 3);
        g.drawString(": Back", getFontWidth() * 4, getFontHeight() * (getMaxY() - 1));
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch(key) {
            case KeyEvent.VK_ESCAPE:
                backKey();
                return true;

            case KeyEvent.VK_D:
                doorKey();
                return true;

            case KeyEvent.VK_W:
                wallKey();
                return true;

            case KeyEvent.VK_P:
                pathKey();
                return true;
        }

        return false;
    }

    private void doorKey() {
        new Door(MainData.mainData.getMap().getTile(MainData.mainData.getMapPanelManager().getLocalMapPanel().getSelectedTile()));
    }

    private void wallKey() {
        new Wall(MainData.mainData.getMap().getTile(MainData.mainData.getMapPanelManager().getLocalMapPanel().getSelectedTile()));
    }

    private void pathKey() {
        new WalkPath(MainData.mainData.getMap().getTile(MainData.mainData.getMapPanelManager().getLocalMapPanel().getSelectedTile()));
    }

}
