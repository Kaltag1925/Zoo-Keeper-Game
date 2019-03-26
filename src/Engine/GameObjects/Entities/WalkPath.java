package Engine.GameObjects.Entities;

import Engine.GameObjects.Tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WalkPath extends Entity {
    private static Image icon = null;
    static {
        try {
            icon = ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "walkPath.png"));
        } catch (IOException e) {
        }
    }

    public WalkPath(Tile location) {
        super(location, icon, Tile.LOWEST_PRIORITY, true);
        setMovementModifier(1);
    }
}