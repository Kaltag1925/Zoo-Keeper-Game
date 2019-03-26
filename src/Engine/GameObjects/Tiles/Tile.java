package Engine.GameObjects.Tiles;

import CustomMisc.DataStructureUtils;
import CustomSwing.JPanelWithSelectable;
import CustomSwing.LabelResize;
import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.GameMap;
import Engine.GameObjects.GameObject;
import Engine.Logic.Ticks.Tick;
import UserInterface.ISelectable;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

public class Tile extends GameObject implements IHasLocation, ISelectable {

    private Coordinate coordinate;
    private double movementFactor = 0.8;
    private boolean blocked;
    private GameMap map;
    private int listThing = 2;
    private boolean hasChanged;

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    public static final int HIGHEST_PRIORITY = 0;
    public static final int LOWEST_PRIORITY = Integer.MAX_VALUE;

    private Comparator<Entity> entityDisplayPriority = new Comparator<Entity>() {
        @Override
        public int compare(Entity o1, Entity o2) {
            if (o1.getDisplayPriority() > o2.getDisplayPriority()) return 1;
            if (o1.getDisplayPriority() < o2.getDisplayPriority()) return -1;
            return 0;
        }
    };

    private RepeatingQueue<Entity> displayQueue;

    class RepeatingQueue<E> extends LinkedList<E> {

        public RepeatingQueue(Collection<? extends E> c, Comparator<E> comparator) {
            super(c);
            Collections.sort(this, comparator);
        }

        public E pollAndPutInBack() {
            int indexToAddAt = size() - 1;
            E first = getFirst();
            removeFirst();
            add(indexToAddAt, first);
            return first;
        }
    }

    @Deprecated
    public Entity getOccupier() {
        return DataStructureUtils.findObjectWithPropertyWithMethod(Entity.entities, this, findEntityMethod, null);
    }

    public List<Entity> getOccupiers() {
        return DataStructureUtils.findAllObjectsWithPropertyWithMethod(Entity.entities, this, findEntityMethod, null);
    }

    private static final int UPDATES_BETWEEN_CHANGES = Tick.getGameHertz();
    private int displayNextUpdate;
    private Entity currentDisplay;
    private boolean checkedForCoverage;
    private boolean checkForFilter;

    public Image getOccupierIcon() {
        if (hasChanged) {
            displayQueue = new RepeatingQueue(getOccupiers(), entityDisplayPriority); //TODO: Should paths be displayed behind visitors?
            checkedForCoverage = false;
            hasChanged = false;
            displayNextUpdate = 0;
            return getOccupierIcon();
        } else if (displayQueue == null) {
            return null;
        } else if (displayNextUpdate == 0) {
            if (!checkForFilter) checkForFilter();
            if (!checkedForCoverage) checkForCoverage();
            Entity entity = displayQueue.pollAndPutInBack();
            currentDisplay = entity;
            displayNextUpdate = UPDATES_BETWEEN_CHANGES;
            return entity != null ? entity.getIcon() : null;
        } else if (currentDisplay.isDeleting()) {
            displayQueue.remove(currentDisplay);
            hasChanged = true;
            return getOccupierIcon();
        } else {
            displayNextUpdate--;
            return currentDisplay.getIcon();
        }
    }

    private void checkForFilter() {
//        for (Entity e : displayQueue) {
//            if (e.isDisplayIfCovered()) {
//                remove.add(e);
//            }
//        }
    }

    private void checkForCoverage() {
        List<Entity> remove = new ArrayList<>();
        boolean atLeastOneEntityToDisplay = false;
        for (Entity e : displayQueue) {
            if (e.isDisplayIfCovered()) {
                atLeastOneEntityToDisplay = true;
            } else {
                remove.add(e);
            }
        }

        if (atLeastOneEntityToDisplay) {
            for (Entity e : remove) {
                displayQueue.remove(e);
            }
        }

        checkedForCoverage = true;
    }

    public void setHasChanged(boolean next) {
        hasChanged = next;
    }

    private static Method findEntityMethod;
    static {
        try {
            findEntityMethod = Entity.class.getMethod("getLocation");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Tile(Coordinate coordinate, GameMap map) {
        this.coordinate = coordinate;
        this.map = map;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getMovementFactor() {
        List<Entity> occupiers = getOccupiers();
        if (occupiers.size() != 0) {
            double movementFactor = 1;
            for (Entity entity : occupiers) {
                movementFactor *= entity.getMovementModifier();
            }
            return movementFactor;
        } else {
            return 1;
        }
    }

    public boolean isBlocked() {
        return blocked;
    }

    public GameMap getMap() {
        return map;
    }

    @Deprecated
    public JPanelWithSelectable toDisplay() {
        char icon;
        try {
            icon= 'A'; //DataStructureUtils.findObjectWithPropertyWithMethod(Entity.entities, this, findEntityMethod, null).getIcon();
        } catch (NullPointerException ex) {
            icon = GameMap.FLOOR;
        }
        LabelResize label = new LabelResize(String.valueOf(icon), SwingConstants.CENTER, .5);
        JPanelWithSelectable panel = new JPanelWithSelectable(this);
        panel.add(label);
        return panel;
    }

    public void selected() {
    }

    public List<Tile> getNeighbors() {
        int x = coordinate.getX();
        int y = coordinate.getY();
        GameMap map = getMap();
        List<Tile> neighbors = new ArrayList<>();


        for (int i = -1; i <= 1; i += 2) {
            try {
                neighbors.add(map.getTile(x + i, y));
            } catch (IndexOutOfBoundsException ignored) {

            }

            try {
                neighbors.add(map.getTile(x, y + i));
            } catch (IndexOutOfBoundsException ignored) {

            }
        }

        return neighbors;
    }

    public HashMap<Integer, Tile> getNeighborsAndDirection() {
        int x = coordinate.getX();
        int y = coordinate.getY();
        GameMap map = getMap();
        HashMap<Integer, Tile> neighbors = new HashMap<>();


        try {
            neighbors.put(EAST, map.getTile(x + 1, y));
        } catch (IndexOutOfBoundsException ignored) {

        }

        try {
            neighbors.put(WEST, map.getTile(x - 1, y));
        } catch (IndexOutOfBoundsException ignored) {

        }

        try {
            neighbors.put(SOUTH, map.getTile(x, y + 1));
        } catch (IndexOutOfBoundsException ignored) {

        }

        try {
            neighbors.put(NORTH, map.getTile(x, y - 1));
        } catch (IndexOutOfBoundsException ignored) {

        }

        return neighbors;
    }

    public HashSet<Tile> tilesBetweenPoints(Tile other) {
        Coordinate thisCoordinate = getCoordinate();
        Coordinate otherCoordinate = other.getCoordinate();
        int xDiff = Math.abs(thisCoordinate.getX() - otherCoordinate.getX());
        int yDiff = Math.abs(thisCoordinate.getY() - otherCoordinate.getY());
        int upperX = thisCoordinate.getX() < otherCoordinate.getX() ? thisCoordinate.getX() : otherCoordinate.getX();
        int upperY = thisCoordinate.getY() < otherCoordinate.getY() ? thisCoordinate.getY() : otherCoordinate.getY();

        HashSet<Tile> set = new HashSet<>(xDiff * yDiff);
        for (int x = upperX; x < upperX + xDiff + 1; x++) {
            for (int y = upperY; y < upperY + yDiff + 1; y++) {
                set.add(map.getTile(x, y));
            }
        }

        return set;
    }
}