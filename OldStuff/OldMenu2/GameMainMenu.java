package Menu;

import CustomSwing.JPanelWithSelectable;
import CustomSwing.LabelResize;
import CustomSwing.PanelResize;
import Engine.Day;
import Engine.GameObjects.GameMap;
import Engine.GameObjects.Tiles.Coordinate;
import Engine.Logic.Ticks.IRenderable;
import Engine.Logic.Ticks.Tick;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GameMainMenu extends Menu implements IMenuEntry, IRenderable {

    private LabelResize nextDay, animals, moneyAmount, zooName, date;
    private ArrayList<LabelResize> displayComponents = new ArrayList<>();
    private JPanel[][] selectionComponents = new JPanel[8][6];
    private PanelResize panelMain, panelInfo, panelSelections, panelDisplay;
    static GameMap map = new GameMap(20, 20);
    static JPanelWithSelectable[][] mapTiles;
    private Coordinate selectedTile = new Coordinate(0, 0); //Use this to search for the tile to interact with?

    public GameMainMenu(String name, Container displayHere, Menu parentMenu){
        super(name, displayHere, parentMenu);

        Tick.addToRenderList(this);

        setPanelMain(panelMain);

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 6; j++){
                selectionComponents[i][j] = new JPanel(new GridBagLayout());
//                selectionComponents[i][j].setOpaque(false);
                selectionComponents[i][j].setBackground(new Color(i *10, j * 10, i * j));
                selectionComponents[i][j].setPreferredSize(new Dimension(100, 10));
            }
        }

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;

        //<editor-fold desc="Panels">
        panelMain = new PanelResize(new GridBagLayout(), null);
        panelMain.setBackground(Color.GRAY);

        panelInfo = new PanelResize(new GridLayout(1, 3), null, "Info");
        panelInfo.setBackground(Color.GRAY);

        panelSelections = new PanelResize(new GridBagLayout(), new double[] {0, 25, 25, 25, 0, 2, .25, GridBagConstraints.CENTER}, "Selections");
        panelSelections.setBackground(Color.BLACK);

        panelDisplay = new PanelResize(new GridBagLayout(), new double[] {0, 25, 25, 25, 0, 1, .75, GridBagConstraints.CENTER}, "Display");
        panelDisplay.setBackground(Color.BLACK);
        //</editor-fold>

        //<editor-fold desc="Selection Components">
        nextDay = new LabelResize("<html><font color=" + selectCharacterColor + ">n: </font><font color=" + selectedHex + ">Progress Time</font>", SwingConstants.LEFT, .5, new double[] {0, 0, 0, 0, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START}, "n");
        selectionComponents[0][0].add(nextDay);

        animals = new LabelResize("<html><font color=" + selectCharacterColor + ">a: </font><font color=" + selectedHex + ">Animals</font>", SwingConstants.LEFT, .5, new double[] {0, 0, 0, 0, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START}, "a");
        selectionComponents[1][0].add(animals);
        //</editor-fold>

        //<editor-fold desc="Info Components">
        moneyAmount = new LabelResize(Double.toString(10d), SwingConstants.CENTER, .5, null, null);
        moneyAmount.setVerticalAlignment(SwingConstants.TOP);
        panelInfo.add(moneyAmount);

        zooName = new LabelResize("Yes", SwingConstants.CENTER, .5, null, null);
        zooName.setVerticalAlignment(SwingConstants.TOP);
        panelInfo.add(zooName);

        date = new LabelResize(Day.getDateLong(), SwingConstants.CENTER, .5, null, null);
        date.setVerticalAlignment(SwingConstants.TOP);
        panelInfo.add(date);
        //</editor-fold>

        // Add Everything

        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0;
        c.insets = new Insets(0, 0, 0, 0);
        panelMain.add(panelInfo, c);

        c.weighty = .75;
        c.gridy = 1;
        c.insets = new Insets(0, 25, 25, 25);
        panelMain.add(panelDisplay, c);

        c.insets = new Insets(0, 25, 25, 25);
        c.weighty = .25;
        c.gridy = 2;
        panelMain.add(panelSelections, c);

        NLowerAction nLowerAction = new NLowerAction();
        panelMain.getActionMap().put("doNLowerGameMainMenu", nLowerAction);

        ALowerAction aLowerAction = new ALowerAction();
        panelMain.getActionMap().put("doALowerActionGameMainMenu", aLowerAction);

        EscapeAction esc = new EscapeAction();
        panelMain.getActionMap().put("doEscapeActionGameMainMenu", esc);

        UpAction upAction = new UpAction();
        panelMain.getActionMap().put("doUpActionGameMainMenu", upAction);

        DownAction downAction = new DownAction();
        panelMain.getActionMap().put("doDownActionGameMainMenu", downAction);

        LeftAction leftAction = new LeftAction();
        panelMain.getActionMap().put("doLeftActionGameMainMenu", leftAction);

        RightAction rightAction = new RightAction();
        panelMain.getActionMap().put("doRightActionGameMainMenu", rightAction);

        EnterAction enterAction = new EnterAction();
        panelMain.getActionMap().put("doEnterActionGameMainMenu", enterAction);

        ArrayList<Pair<KeyStroke, String>> inputMap = new ArrayList<>();
        inputMap.add(new Pair<>(nLower, "doNLowerGameMainMenu"));
        inputMap.add(new Pair<>(aLower, "doALowerActionGameMainMenu"));
        inputMap.add(new Pair<>(escButton, "doEscapeActionGameMainMenu"));

        inputMap.add(new Pair<>(upButton, "doUpActionGameMainMenu"));
        inputMap.add(new Pair<>(downButton, "doDownActionGameMainMenu"));
        inputMap.add(new Pair<>(leftButton, "doLeftActionGameMainMenu"));
        inputMap.add(new Pair<>(rightButton, "doRightActionGameMainMenu"));

        inputMap.add(new Pair<>(enterButton, "doEnterActionGameMainMenu"));
        setInputMapKeysValues(inputMap);
    }

    public void showMenu(){
        frame.getContentPane().removeAll();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;



        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 6; j++){
                c.gridx = j;
                c.gridy = i;
                panelSelections.add(selectionComponents[i][j], c);
            }
        }

        c.anchor = GridBagConstraints.CENTER;

        getDisplayHere().add(panelMain, c);
//        nextDay.setTopParent();
//        animals.setTopParent();
//
//
//        moneyAmount.setTopParent();
//        zooName.setTopParent();
//        date.setTopParent();

        panelInfo.setTopParent();
        panelDisplay.setTopParent();
        panelSelections.setTopParent();
        panelMain.setTopParent();
        panelMain.requestFocus();

        setInputMaps(panelMain);

        frame.getContentPane().validate();
        frame.getContentPane().repaint();
    }

    public void back(){

    }

    public PanelResize getPanelInfo() {
        return panelInfo;
    }

    public LabelResize getDate() {
        return date;
    }

    public LabelResize getMoneyAmount() {
        return moneyAmount;
    }

    public LabelResize getZooName() {
        return zooName;
    }

    public PanelResize getPanelDisplay() {
        return panelDisplay;
    }

    public PanelResize getPanelSelections() {
        return panelSelections;
    }

    private class ALowerAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            removeInputMaps(panelMain);
            AnimalMainMenu animalMainMenu = new AnimalMainMenu("Animal MainData Menu", panelMain, menus.get(getName()));
            animalMainMenu.showMenu();
        }
    }

    private class NLowerAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            removeInputMaps(panelMain);
            ChangeDateMenu changeDateMenu = new ChangeDateMenu("Change Date Menu", panelMain, menus.get(getName()));
            changeDateMenu.showMenu();
        }
    }

    private class EscapeAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            removeInputMaps(panelMain);
            GameEscapeMenu gameEscapeMenu = new GameEscapeMenu("Game Escape Menu", frame, menus.get(getName()));
            gameEscapeMenu.showMenu();
        }
    }

    private class UpAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            selectedTile.addY(-1);
            if (selectedTile.getY() < 0) selectedTile.setY(mapTiles[0].length);
        }
    }

    private class DownAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            selectedTile.addY(1);
            if (selectedTile.getY() > mapTiles[0].length) selectedTile.setY(0);
        }
    }

    private class LeftAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            selectedTile.addX(-1);
            if (selectedTile.getX() < 0) selectedTile.setX(mapTiles.length);
        }
    }

    private class RightAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            selectedTile.addX(1);
            if (selectedTile.getX() > mapTiles.length) selectedTile.setX(0);
        }
    }

    private class EnterAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            JPanelWithSelectable selected = mapTiles[selectedTile.getX()][selectedTile.getY()];
            selected.selected();
        }
    }

    @Override
    public void render() {
        System.out.println(1);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mapTiles = map.toDisplay();

                GridBagConstraints c = new GridBagConstraints();
                for (int x = 0; x < mapTiles.length; x++) {
                    c.gridx = x;
                    for (int y = 0; y < mapTiles[x].length; y++) {
                        c.gridy = y;
                        panelDisplay.add(mapTiles[x][y], c);
                    }
                }
                JPanelWithSelectable selected = mapTiles[selectedTile.getX()][selectedTile.getY()];
                selected.setBackground(Color.RED);
                selected.setForeground(Color.WHITE);

                frame.getContentPane().validate();
                frame.getContentPane().repaint();
            }
        });
    }
}
