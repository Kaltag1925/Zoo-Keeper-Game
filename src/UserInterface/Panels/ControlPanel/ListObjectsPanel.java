package UserInterface.Panels.ControlPanel;

import Engine.GameObjects.Entities.LogicEntity;
import Engine.GameObjects.GameMap;
import Engine.GameObjects.Tiles.Coordinate;
import Engine.GameObjects.Tiles.Tile;
import Engine.GameObjects.Tiles.Zone;
import UserInterface.Panels.DescriptionPanel.ObjectShortDescriptionPanel;
import UserInterface.Panels.GamePanel;
import UserInterface.Panels.MapPanel.LocalMapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

public class ListObjectsPanel extends ControlPanel {

    private Listed.ListedList objectsToList;
    private int listIndex;

    public Listed getSelectedObject() {
        return objectsToList.get(listIndex);
    }

    private String what;
    private Method executeAfterSelectionChanged;
    private Method executeAfterSelectionFinalized;

    private Object openingObject;

    private GamePanel attachedViewPanel;

    private JList list;

    private List<LogicEntity> logicEntities = new ArrayList<>();

    public ListObjectsPanel(Listed.ListedList objectsToList, String what, Method executeAfterSelectionFinalized, Object openingObject, GamePanel attachedViewPanel) {
        super();

        this.objectsToList = objectsToList;
        this.what = what;
        this.executeAfterSelectionFinalized = executeAfterSelectionFinalized;
        this.openingObject = openingObject;
        this.attachedViewPanel = attachedViewPanel;

//        this.list = new JList(objectsToList.toNameList().toArray());
//        add(this.list);
//        this.list.setFont(MainData.mainData.getFontManager().getGameFont());
//        setLayout(null);
//        this.list.setBackground(Color.BLACK);
//        this.list.setForeground(Color.LIGHT_GRAY);

        setAttachedViewPanel(attachedViewPanel);
        setAttachedShortDescriptionPanel(new ObjectShortDescriptionPanel());
    }

    public ListObjectsPanel(Listed.ListedList objectsToList, String what, Method executeAfterSelectionChanged, Method executeAfterSelectionFinalized, Object openingObject, GamePanel attachedViewPanel) {
        super();

        this.objectsToList = objectsToList;
        this.what = what;
        this.executeAfterSelectionChanged = executeAfterSelectionChanged;
        this.executeAfterSelectionFinalized = executeAfterSelectionFinalized;
        this.openingObject = openingObject;
        this.attachedViewPanel = attachedViewPanel;

//        DefaultListModel model = new DefaultListModel<>();
//        for (Listed.ListedList.NameListEntry object : objectsToList.toNameList()) {
//            model.addElement(object);
//        }
//        this.list = new JList(model);
//        add(this.list); //TODO: Make a custom list class
//        this.list.setFont(MainData.mainData.getFontManager().getGameFont());
//        setLayout(null);
//        this.list.setBackground(Color.BLACK);
//        this.list.setForeground(Color.LIGHT_GRAY);

        setAttachedViewPanel(attachedViewPanel);
        setAttachedShortDescriptionPanel(new ObjectShortDescriptionPanel());
    }

    private boolean shown = false;
    private boolean markers = false;

    @Override
    public void open() {
        super.open();

        selectedObject = objectsToList.get(0);
        ((ObjectShortDescriptionPanel) getAttachedShortDescriptionPanel()).setObject(selectedObject);

        LocalMapPanel mapPanel = (LocalMapPanel) getAttachedViewPanel();
        if (selectedObject.getTilesForList() != null) {
            shown = true;

            mapPanel.setSelectedTiles(selectedObject.getTilesForList());

            if (selectedObject.hiddenOnMap()){
                markers = true;

                for (Tile tile : selectedObject.getTilesForList()) {
                    logicEntities.add(new LogicEntity(tile, Zone.icon));
                }
            }
        }

        mapPanel.setSelection(false);
        mapPanel.setFirstPosSelected(false);
        mapPanel.setSecondPosSelected(false);

        if (executeAfterSelectionChanged != null) {
            try {
                executeAfterSelectionChanged.invoke(openingObject, selectedObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Tile> getTiles(Tile firstPos, Tile lastPos) {
        List<Tile> list = new ArrayList<>();
        Coordinate firstPosCoordinate = firstPos.getCoordinate();
        Coordinate lastPosCoordinate = lastPos.getCoordinate();
        GameMap map = firstPos.getMap();

        int topX = firstPosCoordinate.getX() < lastPosCoordinate.getX() ? firstPosCoordinate.getX() : lastPosCoordinate.getX();
        int topY = firstPosCoordinate.getY() < lastPosCoordinate.getY() ? firstPosCoordinate.getY() : lastPosCoordinate.getY();
        int dimensionsX = Math.abs(firstPosCoordinate.getX() - lastPosCoordinate.getX());
        int dimensionsY = Math.abs(firstPosCoordinate.getY() - lastPosCoordinate.getY());
        Coordinate topCoordinate = new Coordinate(topX, topY);

        for (int x = topCoordinate.getX(); x < topCoordinate.getX() + dimensionsX + 1; x++) {
            for (int y = topCoordinate.getY(); y < topCoordinate.getY() + dimensionsY + 1; y++) {
                list.add(map.getTile(x, y));
            }
        }

        return list;
    }

    @Override
    public void makeVisible() {
        super.makeVisible();
        List<Tile> seletectedTiles = selectedObject.getTilesForList();
        LocalMapPanel mapPanel = (LocalMapPanel) getAttachedViewPanel();
        if (selectedObject.getTilesForList() != null && selectedObject.getTilesForList().size() != 0) {

            if(objectsToList.get(0).hiddenOnMap()) {
                Iterator<Listed> iterator = objectsToList.iterator();
                while (iterator.hasNext()) {
                    Listed next = iterator.next();
                    for (Tile tile : selectedObject.getTilesForList()) {
                        logicEntities.add(new LogicEntity(tile, LogicEntity.zone));
                    }
                }
            }

            mapPanel.setSelectedTiles(seletectedTiles);
        }

        mapPanel.setSelection(false);
        mapPanel.setFirstPosSelected(false);
        mapPanel.setSecondPosSelected(false);
    }

    @Override
    public void close() {
        super.close();

        Iterator<LogicEntity> iterator = logicEntities.iterator();
        while (iterator.hasNext()) {
            LogicEntity le = iterator.next();
            le.deleteObject();
        }

        logicEntities.clear();

        LocalMapPanel mapPanel = (LocalMapPanel) getAttachedViewPanel();
        mapPanel.setSelection(true);
    }

    @Override
    public void makeHidden() {
        super.makeHidden();

        Iterator<LogicEntity> iterator = logicEntities.iterator();
        while (iterator.hasNext()) {
            LogicEntity le = iterator.next();
            le.deleteObject();
        }

        getAttachedShortDescriptionPanel().close();
        LocalMapPanel mapPanel = (LocalMapPanel) getAttachedViewPanel();
        mapPanel.setSelection(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.drawString("ESC", getFontWidth(), getFontHeight() * (getMaxY() - 1));

        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Select a " + what, getFontWidth(), getFontHeight());
        g.drawString(": Back", getFontWidth() * 4, getFontHeight() * (getMaxY() - 1));

//        list.setBounds(getFontWidth(), getFontHeight() * 3, getFontWidth() * getMaxX(), getFontHeight() * objectsToList.size());

        for (int i = 3; i < objectsToList.size() + 3; i++) {
            Listed object = objectsToList.get(i - 3);
            String name = object.getListName();
            g.drawString(name, getFontWidth(), getFontHeight() * i);
        }

        if (listIndex != -1) {
            String selectedString = selectedObject.getListName();
            selectString(selectedString, g, listIndex + 3);
        }
    }

    @Override
    public String toString() {
        return "ListObjectPanel";
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch(key) {
            case KeyEvent.VK_ESCAPE :
                super.backKey();
                return true;

            case KeyEvent.VK_OPEN_BRACKET:
                upKey();
                return true;

            case KeyEvent.VK_CLOSE_BRACKET:
                downKey();
                return true;

            case KeyEvent.VK_ENTER:
                selectionKey();
                return true;
        }

        return false;
    }

    private void upKey() {
        listIndex++;
        if (listIndex >= objectsToList.size()) {
            listIndex = 0;
        }

        setSelectedZone();
    }

    private void downKey() {
        listIndex--;
        if (listIndex < 0) {
            listIndex = objectsToList.size() - 1;
        }

        setSelectedZone();
    }

    private Listed selectedObject;

    private void selectionKey() {
        Listed selected = objectsToList.get(listIndex);
        try {
            executeAfterSelectionFinalized.invoke(openingObject, selected);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void setSelectedZone() {
        LocalMapPanel mapPanel = (LocalMapPanel) getAttachedViewPanel();

        selectedObject = objectsToList.get(listIndex);

        if (shown) {
            mapPanel.setSelectedTiles(selectedObject.getTilesForList());

            if (markers){

                for (Tile tile : selectedObject.getTilesForList()) {
                    logicEntities.add(new LogicEntity(tile, Zone.icon));
                }
            }
        }

        ((ObjectShortDescriptionPanel) getAttachedShortDescriptionPanel()).setObject(selectedObject);

        if (executeAfterSelectionChanged != null) {
            try {
                executeAfterSelectionChanged.invoke(openingObject, selectedObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static abstract class ListableString implements Listed {

        private String listName;

        public ListableString(String listName) {
            this.listName = listName;
        }

        @Override
        public String getListName() {
            return listName;
        }

    }
}