package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FontManager {

    private Font font;

    private Font gameFont;
    public Font getGameFont() {
        return gameFont;
    }

    private int fontWidth;
    public int getFontWidth() {
        return fontWidth;
    }

    private int fontHeight;
    public int getFontHeight() {
        return fontHeight;
    }

    private int fontDescent;
    public int getFontDescent() {
        return fontDescent;
    }

    private int fontAscent;
    public int getFontAscent() {
        return fontAscent;
    }

    private int fontLeading;
    public int getFontLeading() {
        return fontLeading;
    }

    private FontMetrics fontMetrics;
    public FontMetrics getFontMetrics() {
        return fontMetrics;
    }

    private float fontSizePerWidthPixel = 0.00911458333f;
    private float maxFontSize = Toolkit.getDefaultToolkit().getScreenSize().width * fontSizePerWidthPixel;
    public float getMaxFontSize() {
        return maxFontSize;
    }

    public static final int X = 0;
    public static final int Y = 1;

    public HashMap<Integer, Integer> getFontInfoForPanel(JPanel panel) {
        HashMap<Integer, Integer> returnMap = new HashMap<>();

        double panelHeight = panel.getHeight();
        returnMap.put(Y, (int) Math.floor(panelHeight / (double) fontHeight));

        double panelWidth = panel.getWidth();
        returnMap.put(X, (int) Math.floor(panelWidth / (double) fontWidth));

        return returnMap;
    }

    public FontManager() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Px437_IBM_VGA9.ttf"));
            gameFont = font.deriveFont(35f);
            Canvas c = new Canvas();
            FontMetrics fontMetrics = c.getFontMetrics(gameFont);
            fontWidth = fontMetrics.getMaxAdvance();
            fontHeight = fontMetrics.getHeight();
            fontDescent = fontMetrics.getDescent();
            fontAscent = fontMetrics.getAscent();
            fontLeading = fontMetrics.getLeading();
            this.fontMetrics = fontMetrics;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void resizeFont(float size) {
        gameFont = font.deriveFont(size);
        Canvas c = new Canvas();
        FontMetrics fontMetrics = c.getFontMetrics(gameFont);
        fontWidth = fontMetrics.getMaxAdvance();
        fontHeight = fontMetrics.getHeight();
        fontDescent = fontMetrics.getDescent();
        fontAscent = fontMetrics.getAscent();
        fontLeading = fontMetrics.getLeading();
        this.fontMetrics = fontMetrics;
    }
}
