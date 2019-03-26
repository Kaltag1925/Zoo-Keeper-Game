package Engine.GameObjects;

import Engine.GameObjects.Tiles.Coordinate;
import Engine.GameObjects.Tiles.IHasLocation;

public class MapSpot extends GameObject implements IHasLocation {

    Coordinate coordinate;
    GameMap map;

    public MapSpot(Coordinate coordinate) {
        this.coordinate = coordinate;
        map = generateMap();
    }

    private GameMap generateMap() {
        GameMap map = new GameMap(100, 100);
        return map;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

}
