package UserInterface.Panels.ControlPanel;

import Engine.GameObjects.GameMap;
import Engine.GameObjects.Tiles.*;
import Engine.Logic.MainData;
import UserInterface.Panels.MapPanel.LocalMapPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

//TODO: This thing is shit, you donkey dick mother fucker
public class CreateZonePanel extends ControlPanel {

    private ArrayList<String> selectionList = new ArrayList<>();
    private int selectedIndex = -1;

    public CreateZonePanel() {
        super();
        setBackground(Color.BLACK);
        selectionList.add("Delivery Zone");
        selectionList.add("Animal Exhibit");
        selectionList.add("Viewing Zone");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.drawString("d", getFontWidth(), getFontHeight());
        g.drawString("a", getFontWidth(), getFontHeight() * 2);
        g.drawString("v", getFontWidth(), getFontHeight() * 3);
        g.drawString("ESC", getFontWidth(), getFontHeight() * (getMaxY() - 1));

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(": Delivery Zone", getFontWidth() * 2, getFontHeight());
        g.drawString(": Animal Exhibit", getFontWidth() * 2, getFontHeight() * 2);
        g.drawString(": Viewing Zone", getFontWidth() * 2, getFontHeight() * 3);
        g.drawString(": Back", getFontWidth() * 4, getFontHeight() * (getMaxY() - 1));

        if (selectedIndex != -1) {
            selectString(selectionList.get(selectedIndex), g, selectedIndex + 1);
        }
    }

    @Override
    public void close() {
        super.close();

        selectedIndex = -1;
        MainData.mainData.getMapPanelManager().getLocalMapPanel().setFirstPosSelected(false);
        zoneClass = null;
        selectNumber = 0;
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch(key) {
            case KeyEvent.VK_ESCAPE:
                backKey();
                return true;

            case KeyEvent.VK_ENTER:
                selectKey();
                return true;

            case KeyEvent.VK_D:
                deliveryZoneKey();
                return true;

            case KeyEvent.VK_A:
                animalExhibitKey();
                return true;

            case KeyEvent.VK_V:
                viewingZoneKey();
                return true;
        }

        return false;
    }

    private int selectNumber = 0;
    private Class<? extends Zone> zoneClass;

    private void selectKey() {
        LocalMapPanel mapPanel = MainData.mainData.getMapPanelManager().getLocalMapPanel();

        switch (selectNumber) {
            case 0:
                mapPanel.setFirstPos((Coordinate) mapPanel.getSelectedTile().createCopy());
                mapPanel.setFirstPosSelected(true);
                selectNumber++;
                break;

            case 1:
                if (zoneClass != null) {
                    GameMap map = MainData.mainData.getMap();
                    Constructor constructor = null;
                    try {
                        constructor = zoneClass.getConstructor(String.class, Tile.class, Tile.class);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    try {
                        MainData.mainData.getPlayerZoo().addZone(zoneClass.cast(constructor.newInstance("Zone" + System.currentTimeMillis(), map.getTile(mapPanel.getFirstPos()), map.getTile(mapPanel.getSelectedTile()))));
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    selectNumber = 0;
                    mapPanel.setFirstPosSelected(false);
                }
                break;
        }
    }

    private void deliveryZoneKey() {
        zoneClass = DeliveryZone.class;
        selectedIndex = 0;
    }

    private void animalExhibitKey() {
        zoneClass = AnimalExhibit.class;
        selectedIndex = 1;
    }

    private void viewingZoneKey() {
        zoneClass = ViewingZone.class;
        selectedIndex = 2;
    }
}
