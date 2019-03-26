package Engine.GameObjects.Entities;

import CustomMisc.DataStructureUtils;
import Engine.GameObjects.Tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wall extends Entity {
    static ArrayList<WallConnector> connectors = new ArrayList<>();

    private static WallConnector northEast = null;
    private static WallConnector northSouthEast = null;
    private static WallConnector northSouth = null;
    private static WallConnector southEast = null;
    private static WallConnector westEast = null;
    private static WallConnector westNorthEast = null;
    private static WallConnector westNorthSouthEast = null;
    private static WallConnector westNorthSouth = null;
    private static WallConnector westNorth = null;
    private static WallConnector westSouthEast = null;
    private static WallConnector westSouth = null;
    private static WallConnector north = null;
    private static WallConnector south = null;
    private static WallConnector west = null;
    private static WallConnector east = null;
    private static WallConnector end = null;
    static {
        try {//TODO: Change names so that they are north > east > south > west
            northEast = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "northEastWall.png")), "0011");
            northSouthEast = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "northSouthEastWall.png")), "0111");
            northSouth = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "northSouthWall.png")), "0101");
            north = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "northSouthWall.png")), "0100");
            south = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "northSouthWall.png")), "0001");
            southEast = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "southEastWall.png")), "0110");
            westEast = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "westEastWall.png")), "1010");
            west = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "westEastWall.png")), "1000");
            east = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "westEastWall.png")), "0010");
            westNorthEast = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "westNorthEastWall.png")), "1011");
            westNorthSouthEast = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "westNorthSouthEastWall.png")), "1111");
            westNorthSouth = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "westNorthSouthWall.png")), "1101");
            westNorth = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "westNorthWall.png")), "1001");
            westSouthEast = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "westSouthEastWall.png")), "1110");
            westSouth = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "westSouthWall.png")), "1100");
            end = new WallConnector(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "wallEnd.png")), "0000");
        } catch (IOException e) {
        }
    }

    static class WallConnector {
        Image icon;
        int directions;

        WallConnector(Image icon, String directions) {
            this.icon = icon;
            this.directions = Integer.parseInt(directions, 2);

            connectors.add(this);
        }

    }

//    boolean[] directionsWithWallOrDoor;

    static WallConnector findConnector(int directions) {
        for (WallConnector connector : connectors) {
            if ((directions >> Tile.WEST & 1) != (connector.directions >> Tile.WEST & 1)) {
                continue;
            }

            if ((directions >> Tile.NORTH & 1) != (connector.directions >> Tile.NORTH & 1)) {
                continue;
            }

            if ((directions >> Tile.SOUTH & 1) != (connector.directions >> Tile.SOUTH & 1)) {
                continue;
            }

            if ((directions >> Tile.EAST & 1) != (connector.directions >> Tile.EAST & 1)) {
                continue;
            }

            return connector;
        }

        return null;
    }

    int directions;

    public Wall(Tile location, Image icon, int directions) {
        super(location, icon, Tile.LOWEST_PRIORITY, true);
        this.directions = directions;
        setMovementModifier(0);
    }

    public Wall(Tile location) {
        super(location, null, Tile.LOWEST_PRIORITY, true);
        setIcon();
        setMovementModifier(0);
    }

    private void setIcon() {
        HashMap<Integer, Tile> neighbors = location.getNeighborsAndDirection();
        directions = 0;
        if (neighbors.size() != 0) {
            for (Map.Entry<Integer, Tile> entry : neighbors.entrySet()) {
                Object wallOrDoor = DataStructureUtils.findObjectWithClass(entry.getValue().getOccupiers(), Door.class, Wall.class);
                if (wallOrDoor != null) {
                    directions = (1 << entry.getKey()) | directions;
                    if (Wall.class.isAssignableFrom(wallOrDoor.getClass())) {
                        Wall adjWall = (Wall) wallOrDoor;
                        int oppDirection = entry.getKey() + 2;
                        if (oppDirection > 3) {
                            oppDirection -= 4;
                        }
                        adjWall.directions = (1 << oppDirection) | adjWall.directions;
                        adjWall.icon = findConnector(adjWall.directions).icon;
                    }
                }
            }
        }

        setIcon(findConnector(directions).icon);
    }

    public static Wall createWall(Tile location) {
        HashMap<Integer, Tile> neighbors = location.getNeighborsAndDirection();
        int directions = 0;
        if (neighbors.size() != 0) {
            for (Map.Entry<Integer, Tile> entry : neighbors.entrySet()) {
                Object wallOrDoor = DataStructureUtils.findObjectWithClass(entry.getValue().getOccupiers(), Door.class, Wall.class);
                if (wallOrDoor != null) {
                    directions = (1 << entry.getKey()) | directions;
                    if (Wall.class.isAssignableFrom(wallOrDoor.getClass())) {
                        Wall adjWall = (Wall) wallOrDoor;
                        int oppDirection = entry.getKey() + 2;
                        if (oppDirection > 3) {
                            oppDirection -= 4;
                        }
                        adjWall.directions = (1 << oppDirection) | adjWall.directions;
                        adjWall.icon = findConnector(adjWall.directions).icon;
                    }
                }
            }
        }

        Image icon = findConnector(directions).icon;

        return new Wall(location, icon, directions);
    }

}