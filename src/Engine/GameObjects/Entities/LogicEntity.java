package Engine.GameObjects.Entities;

import Engine.GameObjects.Tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LogicEntity extends Entity {

    public static Image zone = null;
    static {
        try {
            zone = ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "zone.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public LogicEntity(Tile location, Image icon) {
        super(location, icon, Tile.HIGHEST_PRIORITY, true);
    }
}
