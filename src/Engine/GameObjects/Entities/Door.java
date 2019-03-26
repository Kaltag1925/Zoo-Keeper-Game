package Engine.GameObjects.Entities;

import Engine.GameObjects.Tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Door extends Entity implements Openable {

    private boolean open;

    private static Image openIcon = null;
    static {
        try {
            openIcon = ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "doorOpen.png"));
        } catch (IOException e) {
        }
    }
    private static Image closedIcon = null;
    static {
        try {
            closedIcon = ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "doorClosed.png"));
        } catch (IOException e) {
        }
    }

    public Door(Tile location) {
        super(location, closedIcon, Tile.LOWEST_PRIORITY, true);
        setMovementModifier(0);
        open = false;
    }

    public void open() {
        setMovementModifier(1);
        setIcon(openIcon);
        open = true;
    }

    public void close() {
        setMovementModifier(0);
        setIcon(closedIcon); //TODO: When moving, need to add methods to open and close
        open = false;
    }

    public boolean isOpen() {
        return open;
    }
}
