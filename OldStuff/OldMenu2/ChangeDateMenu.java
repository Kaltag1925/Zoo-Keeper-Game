package Menu;

import CustomSwing.LabelResize;
import Engine.Day;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ChangeDateMenu extends Menu implements IMenuEntry {
    private ArrayList<LabelResize> panelDisplay = new ArrayList<>();
    private JPanel[][] selectionComponents = new JPanel[8][6];
    private LabelResize numberOfDays, plusSign, minusSign, enterLabel, escLabel;

    private int days;
    private int numDays;

    public ChangeDateMenu(String name, JPanel displayHere, Menu parentMenu){
        super(name, displayHere, parentMenu);

        setPanelMain((JPanel) getDisplayHere());

        days = 0;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 6; j++){
                selectionComponents[i][j] = new JPanel(new GridBagLayout());
                selectionComponents[i][j].setOpaque(false);
//                panelSelections[i][j].setBackground(new Color(i *10, j * 10, i * j));
                selectionComponents[i][j].setPreferredSize(new Dimension(10, 10));
            }
        }
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;

        numberOfDays = new LabelResize("<html><font color=" + selectedHex + ">Skip </font><font color=" + selectCharacterColor + ">No</font><font color=" + selectedHex + "> Days</font>", SwingConstants.CENTER, .5, new double[] {0, 0, 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER}, "Skip ", " Days to ");
        c.gridx = 1;
        c.gridy = 1;
        panelDisplay.add(numberOfDays);

        plusSign = new LabelResize("<html><font color=" + selectCharacterColor + ">+: </font><font color=" + selectedHex + ">Add</font>", SwingConstants.RIGHT, .5, new double[] {0, 0, 0, 50, 0, 0, 1, 1, GridBagConstraints.CENTER}, "+");
        selectionComponents[3][2].add(plusSign);

        minusSign = new LabelResize("<html><font color=" + selectCharacterColor + ">-: </font><font color=" + selectedHex + ">Subtract</font>", SwingConstants.LEFT, .5, new double[] {0, 50, 0, 0, 0, 0, 1, 1, GridBagConstraints.CENTER}, "-");
        selectionComponents[3][3].add(minusSign);

        enterLabel = new LabelResize("<html><font color=" + selectCharacterColor + ">ENTER: </font><font color=" + selectedHex + ">Enter</font>", SwingConstants.LEFT, .5, new double[] {0, 50, 0, 0, 0, 0, 1, 1, GridBagConstraints.CENTER}, "ENTER");
        selectionComponents[4][3].add(enterLabel);

        escLabel = new LabelResize("<html><font color=" + selectCharacterColor + ">ESC: </font><font color=" + selectedHex + ">Cancel</font>", SwingConstants.RIGHT, .5, new double[] {0, 0, 0, 50, 0, 0, 1, 1, GridBagConstraints.CENTER}, "ESC");
        selectionComponents[4][2].add(escLabel);

        EnterAction enter = new EnterAction();
        displayHere.getActionMap().put("doEnterActionChangeDateMenu", enter);

        PlusAction plus = new PlusAction();
        displayHere.getActionMap().put("doPlusActionChangeDateMenu", plus);

        MinusAction minus = new MinusAction();
        displayHere.getActionMap().put("doMinusActionChangeDateMenu", minus);

        EscapeAction esc = new EscapeAction();
        displayHere.getActionMap().put("doEscActionChangeDateMenu", esc);

        ArrayList<Pair<KeyStroke, String>> inputMap = new ArrayList<>();
        inputMap.add(new Pair<>(enterButton, "doEnterActionChangeDateMenu"));
        inputMap.add(new Pair<>(plusButton, "doPlusActionChangeDateMenu"));
        inputMap.add(new Pair<>(plusButtonNP, "doPlusActionChangeDateMenu"));
        inputMap.add(new Pair<>(minusButton, "doMinusActionChangeDateMenu"));
        inputMap.add(new Pair<>(escButton, "doEscActionChangeDateMenu"));
        setInputMapKeysValues(inputMap);
    }

    public void showMenu(){
        GameMainMenu parent = (GameMainMenu) menus.get("Game MainData Menu");
        Container display = parent.getPanelDisplay();
        display.removeAll();
        for (LabelResize label : panelDisplay){
            display.add(label);
        }

        Container selections = parent.getPanelSelections();
        selections.removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 6; j++){
                c.gridx = j;
                c.gridy = i;
                selections.add(selectionComponents[i][j], c);
            }
        }

        days = 0;

//        numberOfDays.setTopParent();
//        plusSign.setTopParent();
//        minusSign.setTopParent();
//        enterLabel.setTopParent();
//        escLabel.setTopParent();

        setInputMaps((JPanel) getDisplayHere());
        frame.getContentPane().validate();
        frame.getContentPane().repaint(100);
    }

    @Override
    public void back(){
        GameMainMenu parent = (GameMainMenu) menus.get("Game MainData Menu");
        parent.getPanelDisplay().removeAll();
        parent.getPanelSelections().removeAll();
        super.back();
    }

    private class PlusAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            days++;
            numDays++;
            numberOfDays.setText("<html><font color=" + selectedHex + ">Skip </font><font color=" + selectCharacterColor + ">" + numDays + "</font><font color=" + selectedHex + "> Days To </font><font color=" + selectCharacterColor + ">" + Day.hypotheticalAdvanceDay(days) + "</font>");
        }
    }

    private class MinusAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            if(days - 1 > 0){
                days--;
                numDays--;
                numberOfDays.setText("<html><font color=" + selectedHex + ">Skip </font><font color=" + selectCharacterColor + ">" + numDays + "</font><font color=" + selectedHex + "> Days To </font><font color=" + selectCharacterColor + ">" + Day.hypotheticalAdvanceDay(days) + "</font>");
            } else if (days - 1 == 0){
                days--;
                numDays--;
                numberOfDays.setText("<html><font color=" + selectedHex + ">Skip </font><font color=" + selectCharacterColor + ">No</font><font color=" + selectedHex + "> Days</font>");
            }
        }
    }

    private class EnterAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            if(days != 0){
                Day.advanceDay(days);
                ((GameMainMenu) menus.get("Game MainData Menu")).getDate().setText(Day.getDateLong());
                back();
            } else {
                back();
            }
        }
    }

    private class EscapeAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            back();
        }
    }
}
