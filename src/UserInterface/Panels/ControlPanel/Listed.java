package UserInterface.Panels.ControlPanel;

import Engine.GameObjects.Tiles.Tile;
import Engine.Logic.GameObjectHolder;
import UserInterface.Panels.DescriptionPanel.DescriptionPanel;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public interface Listed {
    String getListName();

    List<Tile> getTilesForList(); //TODO: Replace with call to see if HasLocation

    class ListedList extends java.util.ArrayList<Listed> {

        public ListedList() {
            super();
        }

        public ListedList(GameObjectHolder<? extends Listed> list) {
            super(list);
        }

        public ListedList(Collection<? extends Listed> list) {
            super(list);
        }

        public List<NameListEntry> toNameList() {
            List<NameListEntry> list = new ArrayList<>();

            for (Listed object : this) {
                list.add(new NameListEntry(object));
            }

            return list;
        }

        public static class NameListEntry {
            private Listed object;

            private NameListEntry(Listed object) {
                this.object = object;
            }

            @Override
            public String toString() {
                return object.getListName();
            }
        }
    }

    boolean hiddenOnMap();

    DescriptorsFormat getShortDescriptors();

    class DescriptorsFormat extends TreeMap<String, String> {
    }

    ControlPanelFormat getControlPanelControls();

    class ControlPanelFormat extends ArrayList<ControlPanelObject> {

    }

    class ControlPanelObject {
        private String control;
        private String controlName;
        private Method controlMethod;
        private int keyCode;

        public ControlPanelObject(String control, String controlName, Method controlMethod, int keyCode) {
            this.control = control;
            this.controlName = controlName;
            this.controlMethod = controlMethod;
            this.keyCode = keyCode;
        }

        public String getControl() {
            return control;
        }

        public String getControlName() {
            return controlName;
        }

        public Method getControlMethod() {
            return controlMethod;
        }

        public int getKeyCode() {
            return keyCode;
        }
    }

    void paintToLargeDescriptionPanel(DescriptionPanel panel, Graphics g);
}
