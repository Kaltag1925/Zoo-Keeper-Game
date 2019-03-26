package UserInterface.Panels.MapPanel;

import Engine.GameObjects.Tiles.Coordinate;
import Engine.GameObjects.Tiles.Tile;
import Engine.Logic.MainData;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;

public class LocalMapPanel extends MapPanel {

    public LocalMapPanel() {
        super();
    }

    //<editor-fold desc="Selected Tile">
    private Coordinate selectedTile = new Coordinate(0, 0);

    public Coordinate getSelectedTile() {
        return selectedTile;
    }
    //</editor-fold>

    //<editor-fold desc="First Position">
    private boolean firstPosSelected = false;

    public void setFirstPosSelected(boolean next) {
        firstPosSelected = next;
    }

    private Coordinate firstPos;

    public Coordinate getFirstPos() {
        return firstPos;
    }

    public void setFirstPos(Coordinate firstPos) {
        this.firstPos = firstPos;
    }
    //</editor-fold>

    //<editor-fold desc="Second Position">
    private boolean secondPosSelected = false;

    public void setSecondPosSelected(boolean next) {
        secondPosSelected = next;
    }

    private Coordinate secondPos;

    public Coordinate getSecondPos() {
        return secondPos;
    }

    public void setSecondPos(Coordinate secondPos) {
        this.secondPos = secondPos;
    }
    //</editor-fold>

    //<editor-fold desc="Selection Enabled">
    private boolean selectionEnabled = true;

    public void setSelection(boolean selection) {
        this.selectionEnabled = selection;
    }
    //</editor-fold>

    private List<Tile> selectedTiles;

    public void setSelectedTiles(List<Tile> selectedTiles) {
        this.selectedTiles = selectedTiles;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setFont(MainData.mainData.getFontManager().getGameFont());
        HashSet<Tile> selectedTiles = new HashSet<>();
        if (firstPosSelected && !secondPosSelected) {
            selectedTiles = MainData.mainData.getMap().getTile(selectedTile).tilesBetweenPoints(MainData.mainData.getMap().getTile(firstPos));
        } else if (secondPosSelected) {
            selectedTiles = MainData.mainData.getMap().getTile(firstPos).tilesBetweenPoints(MainData.mainData.getMap().getTile(secondPos));
        } else if (!selectionEnabled) {
            selectedTiles.addAll(this.selectedTiles);
        }

        Tile selectedTileTile = MainData.mainData.getMap().getTile(selectedTile);

        for (int x = 0; x < MainData.mainData.getMap().getXMax(); x++) {
            for (int y = 0; y < MainData.mainData.getMap().getYMax(); y++) {
                Tile tile = MainData.mainData.getMap().getTile(x, y);
                if (selectionEnabled) { //TODO: Clean up
                    if(firstPosSelected) {
                        if(selectedTiles.contains(tile)) {
                            graphics.drawImage(tile.getOccupierIcon(), x * MainData.mainData.getFontManager().getFontWidth(), y * MainData.mainData.getFontManager().getFontHeight(), MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight(), Color.red, null);
                        } else {
                            graphics.drawImage(tile.getOccupierIcon(), x * MainData.mainData.getFontManager().getFontWidth(), y * MainData.mainData.getFontManager().getFontHeight(), MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight(), Color.black, null);
                        }
                    } else {
                        if(tile.equals(selectedTileTile)) {
                            graphics.drawImage(tile.getOccupierIcon(), x * MainData.mainData.getFontManager().getFontWidth(), y * MainData.mainData.getFontManager().getFontHeight(), MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight(), Color.red, null);
                        } else {
                            graphics.drawImage(tile.getOccupierIcon(), x * MainData.mainData.getFontManager().getFontWidth(), y * MainData.mainData.getFontManager().getFontHeight(), MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight(), Color.black, null);
                        }
                    }
                } else {
                    if(selectedTiles.contains(tile)) {
                        graphics.drawImage(tile.getOccupierIcon(), x * MainData.mainData.getFontManager().getFontWidth(), y * MainData.mainData.getFontManager().getFontHeight(), MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight(), Color.red, null);
                    } else {
                        graphics.drawImage(tile.getOccupierIcon(), x * MainData.mainData.getFontManager().getFontWidth(), y * MainData.mainData.getFontManager().getFontHeight(), MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight(), Color.black, null);
                    }
                }
            }
        }
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch (key) {
            case KeyEvent.VK_UP:
                upKey();
                return true;

            case KeyEvent.VK_DOWN:
                downKey();
                return true;

            case KeyEvent.VK_LEFT:
                leftKey();
                return true;

            case KeyEvent.VK_RIGHT:
                rightKey();
                return true;
        }

        return false;
    }

    private void upKey() {
        selectedTile.addY(-1);
        if (selectedTile.getY() < 0) selectedTile.setY(MainData.mainData.getMap().getYMax());
    }

    private void downKey() {
        selectedTile.addY(1);
        if (selectedTile.getY() > MainData.mainData.getMap().getYMax()) selectedTile.setY(0);
    }

    private void leftKey() {
        selectedTile.addX(-1);
        if (selectedTile.getX() < 0) selectedTile.setX(MainData.mainData.getMap().getXMax());
    }

    private void rightKey() {
        selectedTile.addX(1);
        if(selectedTile.getX() > MainData.mainData.getMap().getXMax()) selectedTile.setX(0);
    }
}
