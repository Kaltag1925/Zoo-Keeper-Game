package Menu;

import CustomSwing.LabelResize;
import CustomSwing.PanelResize;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AnimalMainMenu extends Menu implements IMenuEntry {

    private ArrayList<LabelResize> displayComponents = new ArrayList<>();
    private JPanel[][] selectionComponents = new JPanel[8][6];

    private LabelResize buyAnimal, viewAnimals;

    public AnimalMainMenu(String name, PanelResize displayHere, Menu parentMenu) {
        super(name, displayHere, parentMenu);

        setPanelMain((JPanel) getDisplayHere());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                selectionComponents[i][j] = new JPanel(new GridBagLayout());
//                selectionComponents[i][j].setOpaque(false);
                selectionComponents[i][j].setBackground(new Color(i * 10, j * 10, i * j));
                selectionComponents[i][j].setPreferredSize(new Dimension(100, 10));
            }
        }

        buyAnimal = new LabelResize("<html><font color=" + selectCharacterColor + ">b: </font><font color=" + selectedHex + ">Buy Animal</font>", SwingConstants.LEFT, .5, new double[]{0, 0, 0, 0, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START}, "b");
        selectionComponents[0][0].add(buyAnimal);

        viewAnimals = new LabelResize("<html><font color=" + selectCharacterColor + ">v: </font><font color=" + selectedHex + ">View Animals</font>", SwingConstants.LEFT, .5, new double[] {0, 0, 0, 0, 0, 0, 1, 1, GridBagConstraints.FIRST_LINE_START}, "v");
        selectionComponents[1][0].add(viewAnimals);

        BLowerAction bLowerAction = new BLowerAction();
        displayHere.getActionMap().put("doBLowerActionAnimalMainMenu", bLowerAction);

        VLowerAction vLowerAction = new VLowerAction();
        displayHere.getActionMap().put("doVLowerActionAnimalMainMenu", vLowerAction);

        EscapeAction escape = new EscapeAction();
        displayHere.getActionMap().put("doEscapeActionAnimalMainMenu", escape);

        ArrayList<Pair<KeyStroke, String>> inputMap = new ArrayList<>();
        inputMap.add(new Pair<>(bLower, "doBLowerActionAnimalMainMenu"));
        inputMap.add(new Pair<>(vLower, "doVLowerActionAnimalMainMenu"));
        inputMap.add(new Pair<>(escButton, "doEscapeActionAnimalMainMenu"));
        setInputMapKeysValues(inputMap);
    }

    public void showMenu(){
        PanelResize displayHere = (PanelResize) getDisplayHere();
        Container display = (Container) displayHere.getChild("Display");
        display.removeAll();
        for (LabelResize label : displayComponents){
            display.add(label);
        }

        Container selections = (Container) displayHere.getChild("Selections");
        selections.removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 6; j++){
                c.gridx = j;
                c.gridy = i;
                selections.add(selectionComponents[i][j], c);
            }
        }

//        buyAnimal.setTopParent();
//        viewAnimals.setTopParent();

        setInputMaps((JPanel) getDisplayHere());

        frame.getContentPane().validate();
        frame.getContentPane().repaint(100);
    }

    private class BLowerAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            PanelResize displayHere = (PanelResize) getDisplayHere();
            removeInputMaps((JPanel) getDisplayHere());
            BuyAnimalMenu buyAnimalMenu = new BuyAnimalMenu("Buy Animals Menu", displayHere, menus.get(getName()));
            buyAnimalMenu.showMenu();
        }
    }

    private class VLowerAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            PanelResize displayHere = (PanelResize) getDisplayHere();
            removeInputMaps((JPanel) getDisplayHere());
            ViewAnimalMenu viewAnimalMenu = new ViewAnimalMenu("View Animals Menu", displayHere, menus.get(getName()));
        }
    }

    private class EscapeAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            back();
        }
    }
}
