package Engine.GameObjects.Tiles;

import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import Engine.Logic.GameObjectHolder;
import Engine.Logic.MainData;
import UserInterface.Panels.ControlPanel.ListObjectsPanel;
import UserInterface.Panels.ControlPanel.Listed;
import UserInterface.Panels.DescriptionPanel.DescriptionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnimalExhibit extends Zone {

    //TODO: At the creation of the zone, path from the entrance to each zone to see if it is reachable, and do so again at the start of each day

    //private ArrayList<Tile> possibleViewingTiles = new ArrayList<>();

    private GameObjectHolder<AnimalReal> animals = new GameObjectHolder<>();

    public void addAnimal(AnimalReal animal) {
        animals.add(animal);
    }

    public GameObjectHolder<AnimalReal> getAnimals() {
        return animals;
    }

    private ViewingZone viewingArea;

    public AnimalExhibit(String name, Tile start, Tile end) {
        super(name, end, start);

        addAssignableToThisZone(AnimalReal.class);

//        AnimalReal animal = new AnimalReal(start, AnimalReal.icon);
//
//        try {
//            MainData.mainData.getPlayerZoo().buyAnimal(animal);
//            animal.setExhibit(this);
//            animals.add(animal);
//        } catch (Zoo.InsufficientFundsException ex) {
//            animal.deleteObject();
//        }


        //TODO: Make it so that animals are assigned via UI

    }

    public Zone getViewingArea() {
        return viewingArea;
    }

    public void setViewingArea(ViewingZone viewingArea) {
        this.viewingArea = viewingArea;
    }

    @Override
    public ControlPanelFormat getControlPanelControls() {
        ControlPanelFormat controlPanelFormat = new ControlPanelFormat();
        controlPanelFormat.add(new ControlPanelObject("l", "Link To Viewing Zone", linkToViewZoneMethod, KeyEvent.VK_L));
        return controlPanelFormat;
    }

    private ViewingZone viewingZone;

    private Method linkToViewZoneMethod;
    {
        try {
            linkToViewZoneMethod = AnimalExhibit.class.getDeclaredMethod("linkToViewZone");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Method setZoneToLinkMethod;
    {
        try {
            setZoneToLinkMethod = AnimalExhibit.class.getDeclaredMethod("setZoneToLink", ViewingZone.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void linkToViewZone() {
        Listed.ListedList viewZones = new Listed.ListedList(MainData.mainData.getPlayerZoo().getViewingZones());
        Object cur = this;

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ListObjectsPanel(viewZones, " viewing zone", setZoneToLinkMethod, cur, MainData.mainData.getMapPanelManager().getCurrentMapPanel()).open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setZoneToLink(ViewingZone viewingZone) {
        this.viewingZone = viewingZone;
        linkZone();

    }

    public void linkZone() {
        setViewingArea(viewingZone);
    }

    @Override
    public void paintToLargeDescriptionPanel(DescriptionPanel panel, Graphics g) {
        int fontWidth = panel.getFontWidth();
        int fontHeight = panel.getFontHeight();

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(getName(), fontWidth, fontHeight);

        g.setColor(Color.LIGHT_GRAY);
        String dimensionsString = "Dimensions: ";
        g.drawString(dimensionsString, fontWidth, fontHeight * 2);
        g.setColor(Color.GREEN);
        g.drawString(getDimensions(X) + "x" + getDimensions(Y), fontWidth * (dimensionsString.length() + 1), fontHeight * 2);

        g.setColor(Color.LIGHT_GRAY);
        String viewingZoneString = "Assigned Viewing Zone: ";
        g.drawString(viewingZoneString, fontWidth, fontHeight * 3);
        if (viewingArea != null) {
            g.setColor(Color.GREEN);
            g.drawString(viewingArea.toString(), fontWidth * (viewingZoneString.length() + 1), fontHeight * 3);
        } else {
            g.setColor(Color.RED);
            g.drawString("None", fontWidth * (viewingZoneString.length() + 1), fontHeight * 3);
        }
    }

        /*private void setPossibleViewingTiles() {

        int xDimStart = tile.getCoordinate().getX();
        int xDimEnd = xDimStart + dimensions[X];
        int yDimStart = tile.getCoordinate().getY();
        int yDimEnd = yDimStart + dimensions[Y];
        GameMap map = tile.getMap();

        for (int i = 0; i < dimensions[X]; i++) {
            int currentXDim = xDimStart + i;
            try {
                possibleViewingTiles.add(map.getTile(currentXDim, yDimStart - 1));
            } catch (IndexOutOfBoundsException ignore) {

            }
        }

        for (int i = 0; i < dimensions[X]; i++) {
            int currentXDim = xDimStart + i;
            try {
                possibleViewingTiles.add(map.getTile(currentXDim, yDimEnd + 1));
            } catch (IndexOutOfBoundsException ignore) {

            }
        }

        for (int i = 0; i < dimensions[Y]; i++) {
            int currentYDim = xDimStart + i;
            try {
                possibleViewingTiles.add(map.getTile(xDimStart - 1, currentYDim));
            } catch (IndexOutOfBoundsException ignore) {

            }
        }

        for (int i = 0; i < dimensions[Y]; i++) {
            int currentYDim = xDimStart + i;
            try {
                possibleViewingTiles.add(map.getTile(xDimEnd + 1, currentYDim));
            } catch (IndexOutOfBoundsException ignore) {

            }
        }
    }

    public ArrayList<Tile> getPossibleViewingTiles() {
        return possibleViewingTiles;
    }
*/
}
