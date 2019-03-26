package Engine.GameObjects.Tiles;

import Engine.GameObjects.GameMap;
import Engine.GameObjects.GameObject;
import Engine.Logic.Assignments.Assignable;
import UserInterface.Panels.ControlPanel.Listed;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Zone extends GameObject implements Listed {

    /**
     * Returns the upper-left-most tile contained in this zone.
     */
    private Tile anchor;
    private Tile end;
    private int[] dimensions = new int[2];
    private String name;

    //<editor-fold desc="Listed">
    @Override
    public String getListName() {
        return name;
    }

    @Override
    public List<Tile> getTilesForList() {
        return new ArrayList<>(anchor.tilesBetweenPoints(end));
    }

    @Override
    public boolean hiddenOnMap() {
        return true;
    }

    @Override
    public DescriptorsFormat getShortDescriptors() {
        DescriptorsFormat descriptors = new DescriptorsFormat();

        String dimensionsString = dimensions[X] + "x" + dimensions[Y];
        descriptors.put("Dimensions", dimensionsString);

        return descriptors;
    }
    //</editor-fold>

    private ArrayList<Class<? extends Assignable>> assignableToThisZone = new ArrayList<>();
    protected void addAssignableToThisZone(Class<? extends Assignable> clazz) {
        assignableToThisZone.add(clazz);
    }

    public ArrayList<Class<? extends Assignable>> getAssignableToThisZone() {
        return assignableToThisZone;
    }

    public static final int X = 0;
    public static final int Y = 1;

    public static Image icon = null;
    static {
        try {
            icon = ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "zone.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Zone(String name, Tile end, Tile start) {
        this.name = name;

        Coordinate startCoordinate = start.getCoordinate();
        Coordinate endCoordinate = end.getCoordinate();
        dimensions[X] = Math.abs(startCoordinate.getX() - endCoordinate.getX()) + 1;
        dimensions[Y] = Math.abs(startCoordinate.getY() - endCoordinate.getY()) + 1;
        int upperX = startCoordinate.getX() < endCoordinate.getX() ? startCoordinate.getX() : endCoordinate.getX();
        int upperY = startCoordinate.getY() < endCoordinate.getY() ? startCoordinate.getY() : endCoordinate.getY();
        anchor = start.getMap().getTile(upperX, upperY);
        this.end = start.getMap().getTile(upperX + dimensions[X], upperY + dimensions[Y]);
    }

    public int getDimensions(int index) {
        return dimensions[index];
    }

    public List<Tile> getTiles() {
        List<Tile> list = new ArrayList<>();
        Coordinate tileCoordinate = anchor.getCoordinate();
        GameMap map = anchor.getMap();
        for (int x = tileCoordinate.getX(); x < tileCoordinate.getX() + dimensions[X]; x++) {
            for (int y = tileCoordinate.getY(); y < tileCoordinate.getY() + dimensions[Y]; y++) {
                list.add(map.getTile(x, y));
            }
        }

        return list;
    }

    public Tile getAnchor() {
        return anchor;
    }

    public Tile getEnd() {
        return end;
    }

    public String getName() {
        return name;
    }
}
