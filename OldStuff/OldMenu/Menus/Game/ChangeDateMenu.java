package OldMenu.Menus.Game;

import CustomMisc.ExtraArrayList;
import CustomSwing.LabelResize;
import CustomSwing.PanelResize;
import OldMenu.Navigation.Functions.ChangeDateNavigator;
import OldMenu.Navigation.Functions.ChangeIntInLabel;
import OldMenu.Navigation.Menus.Game.GameMainMenuNavigator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChangeDateMenu extends Menu {

    private static LabelResize numberOfDays, plusSign, minusSign, enterLabel, escLabel;
    private static PanelResize panelMain, panelSelections, panelDisplay;
    public static int lastSelected = 0;
    public static ArrayList<LabelResize> localKeyLabels = new ArrayList<LabelResize>();
    private static LayoutManager layoutStyle = new BorderLayout();

    public static void createGameMainMenu(JLayeredPane pane) {
        //pane.setLayout(layoutStyle);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;

        panelMain = new PanelResize(new GridBagLayout(), new double[] {40, 25, 25, 25, 0, 0, GridBagConstraints.CENTER}, true);
        panelMain.setBackground(Color.GRAY);

        panelSelections = new PanelResize(new GridBagLayout(), null, false);
        panelSelections.setBackground(Color.BLACK);

        panelDisplay = new PanelResize(new GridBagLayout(), new double[] {0, 0, 25, 0, 0, 0, GridBagConstraints.CENTER}, false);
        panelDisplay.setBackground(Color.BLACK);


        // Selection Components

        numberOfDays = new LabelResize("<html><font color=" + selectedHex + ">Skip</font><font color=" + selectCharacterColor + "> 1 </font><font color=" + selectedHex + ">Days</font>" , SwingConstants.CENTER, .5, null, new double[] {0, 0, 0, 0, 1, 1, GridBagConstraints.CENTER}, "Skip ", " Days", 1, 1, Integer.MAX_VALUE, false, "Skip 0 Days");
        c.gridx = 1;
        c.gridy = 1;
        panelDisplay.add(numberOfDays);

        plusSign = new LabelResize("<html><font color=" + selectCharacterColor + ">+: </font><font color=" + selectedHex + ">Add</font>", SwingConstants.CENTER, .5, new ChangeIntInLabel(), new double[] {200, 300, 0, 0, 1, 1, GridBagConstraints.CENTER}, "+", "Add", true, numberOfDays, 1, "+: Add");
        localKeyLabels.add(plusSign);
        panelSelections.add(plusSign);

        minusSign = new LabelResize("<html><font color=" + selectCharacterColor + ">-: </font><font color=" + selectedHex + ">Subtract</font>", SwingConstants.CENTER, .5, new ChangeIntInLabel(), new double[] {200, 0, 0, 300, 1, 1, GridBagConstraints.CENTER}, "-", "Subtract", false, numberOfDays, 1, "-: Subtract");
        localKeyLabels.add(minusSign);
        panelSelections.add(minusSign);

        enterLabel = new LabelResize("<html><font color=" + selectCharacterColor + ">ENTER: </font><font color=" + selectedHex + ">Enter</font>", SwingConstants.CENTER, .5, new ChangeDateNavigator(), new double[] {400, 0, 0, 300, 1, 1, GridBagConstraints.CENTER}, "ENTER", "Enter", numberOfDays, "ENTER: Enter");
        localKeyLabels.add(enterLabel);
        panelSelections.add(enterLabel);

        escLabel = new LabelResize("<html><font color=" + selectCharacterColor + ">ESC: </font><font color=" + selectedHex + ">Cancel</font>", SwingConstants.CENTER, .5, new GameMainMenuNavigator(), new double[] {400, 300, 0, 0, 1, 1, GridBagConstraints.CENTER}, "ESC", "Cancel", "ESC: Cancel");
        localKeyLabels.add(escLabel);
        panelSelections.add(escLabel);

        // Add
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.gridx = 0;
        c.weighty = .65;
        panelMain.add(panelDisplay, c);

        c.weighty = .35;
        c.gridy = 1;
        panelMain.add(panelSelections, c);

        c.weighty = 1;
        c.gridy = 0;
        pane.add(panelMain, c);
        pane.moveToFront(panelMain);

        numberOfDays.setTopParent();
        plusSign.setTopParent();
        minusSign.setTopParent();
        enterLabel.setTopParent();
        escLabel.setTopParent();

        panelDisplay.setTopParent();
        panelSelections.setTopParent();
        panelMain.setTopParent();

        ExtraArrayList.ensureSize(menus, 3);
        menus.add(3, panelMain);
        Menu.menuCurrent = 3;

        // Key Binds

        panelSelections.requestFocus();

        EnterAction enter = new EnterAction();
        panelMain.getInputMap().put(enterButton, "doEnterAction");
        panelMain.getActionMap().put("doEnterAction", enter);

        PlusAction plus = new PlusAction();
        panelMain.getInputMap().put(plusButton, "doPlusAction");
        panelMain.getInputMap().put(plusButtonNP, "doPlusAction");
        panelMain.getActionMap().put("doPlusAction", plus);

        MinusAction minus = new MinusAction();
        panelMain.getInputMap().put(minusButton, "doMinusAction");
        panelMain.getActionMap().put("doMinusAction", minus);

        EscapeAction esc = new EscapeAction();
        panelMain.getInputMap().put(escButton, "doEscAction");
        panelMain.getActionMap().put("doEscAction", esc);

        keyLabels = localKeyLabels;
    }

    public static JPanel getPanelMain() {
        return panelMain;
    }

    public static LabelResize getNumberLabel(){
        return numberOfDays;
    }

    public static void regainFocus() {
        panelMain.requestFocus();
    }
}
