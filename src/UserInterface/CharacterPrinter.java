package UserInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CharacterPrinter extends JPanel {

    public static final char[] PRINT = new char[]
            {'m'};

    public static Font font;
    static int fontWidth;
    static int fontHeight;
    static int fontAscent;

    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Px437_IBM_VGA9.ttf")).deriveFont(16f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public CharacterPrinter() {
        super();
        repaint(100);

        BufferedImage off_Image =
                new BufferedImage(1, 1,
                        BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = off_Image.createGraphics();
        g2.setFont(font);
        FontMetrics fontMetrics = g2.getFontMetrics(font);
        fontWidth = fontMetrics.getMaxAdvance();
        fontHeight = fontMetrics.getHeight();
        fontAscent = fontMetrics.getAscent();
        g2.dispose();

        printImage();
        //new File("characters").mkdirs();
//        for (char c = 0x0000; c <= 0xFFFF - 1; c++) {
//            if (font.canDisplay(c)) {
//
//            }
//        }
    }

    public static void main(String[] args) {
        new CharacterPrinter();

    }

    public void printImage() {
        for (char ch : PRINT) {
            BufferedImage off_Image =
                    new BufferedImage(fontWidth, fontHeight,
                            BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = off_Image.createGraphics();
            g2.setFont(font);
            g2.drawString(String.valueOf(ch), 0, fontAscent);
            g2.dispose();
            File outputfile = new File("characters" + File.separator + Character.hashCode(ch) + ".png");
            try {
                ImageIO.write(off_Image, "png", outputfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
