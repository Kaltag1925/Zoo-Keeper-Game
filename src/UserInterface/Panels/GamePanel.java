package UserInterface.Panels;

import Engine.Logic.MainData;
import UserInterface.FontManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class GamePanel extends JPanel {

    //<editor-fold desc="Font Width">
    private int fontWidth;

    public int getFontWidth() {
        return fontWidth;
    }
    //</editor-fold>

    //<editor-fold desc="Font Height">
    private int fontHeight;

    public int getFontHeight() {
        return fontHeight;
    }
    //</editor-fold>

    //<editor-fold desc="Font Ascent">
    private int fontAscent;

    protected int getFontAscent() {
        return fontAscent;
    }
    //</editor-fold>

    //<editor-fold desc="Font Descent">
    private int fontDescent;

    protected int getFontDescent() {
        return fontDescent;
    }
    //</editor-fold>

    //<editor-fold desc="Font Leading">
    private int fontLeading;

    protected int getFontLeading() {
        return fontLeading;
    }
    //</editor-fold>

    //<editor-fold desc="Max X">
    private int maxX;

    protected int getMaxX() {
        return maxX;
    }
    //</editor-fold>

    //<editor-fold desc="Max Y">
    private int maxY;

    protected int getMaxY() {
        return maxY;
    }
    //</editor-fold>

    public GamePanel() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        FontManager fontManager = MainData.mainData.getFontManager();

        fontWidth = fontManager.getFontWidth();
        fontHeight = fontManager.getFontHeight();
        fontAscent = fontManager.getFontAscent();
        fontDescent = fontManager.getFontDescent();
        fontLeading = fontManager.getFontLeading();

        HashMap<Integer, Integer> panelFontStats = fontManager.getFontInfoForPanel(this);
        maxX = panelFontStats.get(FontManager.X);
        maxY = panelFontStats.get(FontManager.Y);

        g.setFont(fontManager.getGameFont());
    }

    public abstract void open();

    public abstract void close();
}
