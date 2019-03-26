package UserInterface.Panels.ControlPanel;

import Engine.GameObjects.Companies.Zoo;
import Engine.GameObjects.Entities.LogicEntity;
import Engine.GameObjects.Tiles.Tile;
import Engine.GameObjects.Tiles.Zone;
import Engine.Logic.MainData;
import UserInterface.Panels.DescriptionPanel.DescriptionPanel;
import UserInterface.Panels.MapPanel.LocalMapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ZoneMainPanel extends ControlPanel {

    public ZoneMainPanel() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.drawString("ESC", getFontWidth(), getFontHeight() * (getMaxY() - 1));
        g.drawString("z", getFontWidth(), getFontHeight());
        g.drawString("c", getFontWidth(), getFontHeight() * 2);

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(": List Zones", getFontWidth() * 2, getFontHeight());
        g.drawString(": Create Zone", getFontWidth() * 2, getFontHeight() * 2);
        g.drawString(": Back", getFontWidth() * 4, getFontHeight() * (getMaxY() - 1));
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch (key) {
            case KeyEvent.VK_ESCAPE:
                backKey();
                return true;

            case KeyEvent.VK_Z:
                listZoneTypesKey();
                return true;

            case KeyEvent.VK_C:
                createZoneKey();
                return true;
        }

        return false;
    }

    private ListableZone zoneType;
    private Method setZoneMethod;

    {
        try {
            setZoneMethod = ZoneMainPanel.class.getDeclaredMethod("setZoneType", ListableZone.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Method highlightZoneMethod;

    {
        try {
            highlightZoneMethod = ZoneMainPanel.class.getDeclaredMethod("highlightZones", ListableZone.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void listZoneTypesKey() {
        try {
            Listed.ListedList listableZones = new Listed.ListedList();
            Zoo zoo = MainData.mainData.getPlayerZoo();
            listableZones.add(new ListableZone("Delivery Zone", "A zone designating a place for delivery trucks to drop off goods.", new ArrayList<>(zoo.getDeliveryZones())));
            listableZones.add(new ListableZone("Animal Exhibit", "A zone designating a place for animals to live in your zoo.", new ArrayList<>(zoo.getAnimalExhibits())));
            ZoneMainPanel cur = this;
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ListObjectsPanel(listableZones, "Zone", setZoneMethod, cur, MainData.mainData.getMapPanelManager().getCurrentMapPanel()).open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setZoneType(ListableZone zoneType) {
        this.zoneType = zoneType;
        openZoneTypeInfo();
    }

    private List<LogicEntity> logicEntities = new ArrayList<>();

    public void highlightZones(ListableZone zoneType) {
        for (LogicEntity entity : logicEntities) {
            entity.deleteObject();
        }
        logicEntities.clear();

        List<Zone> zones = zoneType.getZoneList();
        LocalMapPanel map = (LocalMapPanel) MainData.mainData.getControlPanelManager().getCurrentViewPanel();
        List<Tile> highlight = new ArrayList<>();
        for (Zone zone : zones) {
            List<Tile> tiles = zone.getTiles();
            highlight.addAll(tiles);
            for (Tile tile : tiles) {
                logicEntities.add(new LogicEntity(tile, Zone.icon));
            }
        }
        map.setSelectedTiles(highlight);
    }

    private void openZoneTypeInfo() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ObjectPanel(zoneType).open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private class ListableZone extends ListObjectsPanel.ListableString {

        private String description;
        private List<Zone> zoneTypeZones;

        private static final String ZONE_NAME_KEY = "Type";
        private static final String DESCRIPTION_KEY = "Description";

        private ListableZone(String listName, String description, List<Zone> zoneTypeZones) {
            super(listName);
            this.description = description;
            this.zoneTypeZones = zoneTypeZones;
        }

        @Override
        public List<Tile> getTilesForList() {
            List<Tile> tiles = new ArrayList<>();
            for (Zone zone : zoneTypeZones) {
                tiles.addAll(zone.getTiles());
            }

            return tiles;
        }

        @Override
        public boolean hiddenOnMap() {
            return true;
        }

        @Override
        public DescriptorsFormat getShortDescriptors() {
            DescriptorsFormat descriptors = new DescriptorsFormat();
            descriptors.put(ZONE_NAME_KEY, getListName());
            descriptors.put(DESCRIPTION_KEY, description);
            return descriptors;
        }

        private List<Zone> getZoneList() {
            return zoneTypeZones;
        }

        public ControlPanelFormat getControlPanelControls() {
            ControlPanelFormat controls = new ControlPanelFormat();
            controls.add(new ControlPanelObject("l", "List Zones", listZonesKeyMethod, KeyEvent.VK_L));
            return controls;
        }

        //<editor-fold desc="Select Zone">
        private Method listZonesKeyMethod;
        {
            try {
                listZonesKeyMethod = ListableZone.class.getDeclaredMethod("listZonesKey");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        private Method setSelectedZone;

        {
            try {
                setSelectedZone = ListableZone.class.getDeclaredMethod("setZone", Zone.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        public void listZonesKey() {
            Listed.ListedList zones = new Listed.ListedList(zoneTypeZones);
            Object cur = this;

            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        new ListObjectsPanel(zones, "zone", setSelectedZone, cur, MainData.mainData.getMapPanelManager().getCurrentMapPanel()).open();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        private Zone zone;

        public void setZone(Zone zone) {
            this.zone = zone;
            openZoneInfo();
        }

        private void openZoneInfo() {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        new ObjectPanel(zone).open();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        //</editor-fold>

        public void paintToLargeDescriptionPanel(DescriptionPanel panel, Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            g.drawString(description, panel.getFontWidth(), panel.getFontHeight());
        }
    }

    @Override
    public String toString() {
        return "ZoneMainPanel";
    }

    private void createZoneKey() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new CreateZonePanel().open();
                } //TODO: Change to a list or make a class that is an extension of a basic selection panel because right now its `ListObjectPanel("
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
