//package UserInterface.Panels.ControlPanel;
//
//import Engine.GameObjects.Entities.LogicEntity;
//import Engine.GameObjects.Tiles.Tile;
//import Engine.GameObjects.Tiles.Zone;
//import Engine.Logic.MainData;
//import UserInterface.Panels.MapPanel.LocalMapPanel;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ZoneTypePanel extends ControlPanel {
//
//    private List<Zone> zones;
//
//    public ZoneTypePanel(List<Zone> zones) {
//        super();
//        this.zones = zones;
//        setAttachedViewPanel(MainData.mainData.getMapPanelManager().getLocalMapPanel());
//    }
//
//    private List<LogicEntity> logicEntities = new ArrayList<>();
//
//    @Override
//    public void open() {
//        super.open();
//
//        List<Tile> selectedTiles = new ArrayList<>();
//
//        for (Zone zone : zones) {
//            List<Tile> tiles = zone.getTiles();
//            selectedTiles.addAll(tiles);
//            for (Tile tile : tiles) {
//                logicEntities.add(new LogicEntity(tile, Zone.icon));
//            }
//        }
//
//        LocalMapPanel map = (LocalMapPanel) getAttachedViewPanel();
//        map.setSelectedTiles(selectedTiles);
//
//        map.setSelection(false);
//    }
//
//    @Override
//    public void makeVisible() {
//        super.makeVisible();
//
//        List<Tile> selectedTiles = new ArrayList<>();
//
//        for (Zone zone : zones) {
//            List<Tile> tiles = zone.getTiles();
//            selectedTiles.addAll(tiles);
//            for (Tile tile : tiles) {
//                logicEntities.add(new LogicEntity(tile, Zone.icon));
//            }
//        }
//
//        LocalMapPanel map = (LocalMapPanel) getAttachedViewPanel();
//        map.setSelectedTiles(selectedTiles);
//
//        map.setSelection(false);
//    }
//
//    @Override
//    public void close() {
//        super.close();
//
//        for (LogicEntity le : logicEntities) {
//            le.deleteObject();
//        }
//
//        logicEntities.clear();
//
//        LocalMapPanel map = (LocalMapPanel) getAttachedViewPanel();
//        map.setSelection(true);
//    }
//
//    @Override
//    public void makeHidden() {
//        super.makeHidden();
//
//        for (LogicEntity le : logicEntities) {
//            le.deleteObject();
//        }
//
//        logicEntities.clear();
//
//        LocalMapPanel map = (LocalMapPanel) getAttachedViewPanel();
//        map.setSelection(true);
//    }
//
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        g.setColor(Color.GREEN);
//        g.drawString("ESC", getFontWidth(), getFontHeight() * (getMaxY() - 1));
//        g.drawString("z", getFontWidth(), getFontHeight());
//
//        g.setColor(Color.LIGHT_GRAY);
//        g.drawString(": Back", getFontWidth() * 4, getFontHeight() * (getMaxY() - 1));
//        g.drawString(": List Zones", getFontWidth() * 2, getFontHeight());
//    }
//
//    @Override
//    public boolean checkKeyBinds(int key) {
//        switch (key) {
//            case KeyEvent.VK_ESCAPE:
//                backKey();
//                return true;
//
//            case KeyEvent.VK_L:
//                listZonesKey();
//                return true;
//        }
//
//        return false;
//    }
//}