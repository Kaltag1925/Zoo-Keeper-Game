package Engine.GameObjects.Entities;

import CustomMisc.ImageTools;
import Engine.GameObjects.Tiles.Tile;
import Engine.Logic.MainData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Grass extends Entity {

    static ArrayList<BufferedImage> grasses = new ArrayList<>();

    static {
        try {
            Color color1 = Color.GREEN;
            Color color2 = new Color(43, 122, 26);
            grasses.add(ImageTools.colorImage(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "grass1.png")), color1));
            grasses.add(ImageTools.colorImage(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "grass2.png")), color1));
            grasses.add(ImageTools.colorImage(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "grass3.png")), color1));
            grasses.add(ImageTools.colorImage(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "grass4.png")), color2));
            grasses.add(ImageTools.colorImage(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "grass5.png")), color2));
            grasses.add(ImageTools.colorImage(ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "grass6.png")), color2));
        } catch (IOException e) {
        }
    }


    public Grass(Tile tile, Image icon) {
        super(tile, icon, Tile.LOWEST_PRIORITY, false);
    }

    public static Grass createGrass(Tile tile) {
        Image icon = randomGrass();
        return new Grass(tile, icon);
    }

    private static Image randomGrass() {
        return grasses.get(MainData.mainData.getRandom().nextInt(grasses.size()));
    }
}
