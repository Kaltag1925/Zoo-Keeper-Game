package Engine.GameObjects;

import CustomMisc.Tree;
import CustomSwing.JPanelWithSelectable;
import Engine.GameObjects.Entities.Grass;
import Engine.GameObjects.Tiles.Coordinate;
import Engine.GameObjects.Tiles.Tile;

import java.awt.*;
import java.util.ArrayList;

public class GameMap extends GameObject {

    public static final String XML_NODE_NAME = "GameMap";

    public static final char WALL = 'W';
    public static final char PERSON = '0';
    public static final char FLOOR = '`';
    public static final char FINISH = 'F';
    public static boolean finish = false;
    public static char[] list = new char[]{WALL, PERSON, FLOOR, FINISH};

    private int xMax;
    private int yMax;

    private ArrayList<ArrayList<Tile>> mapTiles; //TODO: Make map a class?

    public GameMap(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;

        mapTiles = createMapTiles(xMax, yMax);
        mapDisplay = toDisplay();
    }

    private ArrayList<ArrayList<Tile>> createMapTiles(int xMax, int yMax) {
        ArrayList<ArrayList<Tile>> tileMatrix = new ArrayList<ArrayList<Tile>>(){};

        for (int x = 0; x < xMax; x++) {
            tileMatrix.add(new ArrayList<>());
            for (int y = 0; y < yMax; y++) {
                Tile tile = new Tile(new Coordinate(x, y), this);
                tileMatrix.get(x).add(tile);
                Grass.createGrass(tile);
            }
        }

        return tileMatrix;
    }

    public int getXMax() {
        return xMax;
    }

    public int getYMax() {
        return yMax;
    }

    public Tile getTile(Coordinate coordinate) throws IndexOutOfBoundsException{
        try {
            Tile tile = mapTiles.get(coordinate.getX()).get(coordinate.getY());

            if (tile == null) {
                throw new IndexOutOfBoundsException("Reached end of map " + this);
            }

            return tile;
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Reached end of map " + this);
        }
    }

    public Tile getTile(int x, int y) throws IndexOutOfBoundsException {
        try {
            Tile tile = mapTiles.get(x).get(y);

            if (tile == null) {
                throw new IndexOutOfBoundsException("Reached end of map " + this);
            }

            return tile;
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Reached end of map " + this);
        }
    }

    public ArrayList<ArrayList<Tile>> getMapTiles() {
        return mapTiles;
    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {}

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        super.fillStorageTree(storageTree);
        StorableForm head = storageTree.getHead();
        head.put(NODE_NAME, XML_NODE_NAME);
    }

    @Override
    public void assignReferences(Tree<StorableForm> storageTree) {
        super.assignReferences(storageTree);
    }

    private JPanelWithSelectable[][] mapDisplay;

    public JPanelWithSelectable[][] toDisplay() {
        JPanelWithSelectable[][] tilePanels = new JPanelWithSelectable[xMax][yMax];
        GridBagConstraints c = new GridBagConstraints();
        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                tilePanels[x][y] = mapTiles.get(x).get(y).toDisplay();
            }
        }
        return tilePanels;
    }
}
