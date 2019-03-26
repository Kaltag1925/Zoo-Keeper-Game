package Engine.Logic;

import Engine.GameObjects.Tiles.Tile;

import java.util.Vector;

public interface Pathing {

    Path getPath(Tile start, Tile destination);

    class Path extends Vector<Tile> {

        public Path() {
            super();
        }
    }
}
