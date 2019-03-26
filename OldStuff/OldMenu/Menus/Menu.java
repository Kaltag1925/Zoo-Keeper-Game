package OldMenu.Menus;

import CustomSwing.LabelResize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Menu {

    //<editor-fold desc="KeyBinds">
    public static KeyStroke upButton = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
    public static KeyStroke upButtonNP = KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, 0);
    public static KeyStroke downButton = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
    public static KeyStroke downButtonNP = KeyStroke.getKeyStroke(KeyEvent.VK_KP_DOWN, 0);
    public static KeyStroke rightButton = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
    public static KeyStroke rightButtonNP = KeyStroke.getKeyStroke(KeyEvent.VK_KP_RIGHT, 0);
    public static KeyStroke leftButton = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
    public static KeyStroke leftButtonNP = KeyStroke.getKeyStroke(KeyEvent.VK_KP_LEFT, 0);
    public static KeyStroke enterButton = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
    public static KeyStroke escButton = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

    public static KeyStroke plusButton = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0);
    public static KeyStroke plusButtonNP = KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0);
    public static KeyStroke minusButton = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0);

    public static KeyStroke nLower= KeyStroke.getKeyStroke(KeyEvent.VK_N, 0);


    //</editor-fold>

    public static Color notSelected = new Color(150, 150, 150);
    public static Color selected = Color.WHITE;
    public static ArrayList<Component> menus = new ArrayList<Component>();
    public static ArrayList<LabelResize> selectables = new ArrayList<LabelResize>();
    public static ArrayList<LabelResize> keyLabels = new ArrayList<LabelResize>();
    public static ArrayList<LabelResize> moneyAmounts = new ArrayList<LabelResize>();
    public static ArrayList<LabelResize> dates = new ArrayList<LabelResize>();
    public static String versionNumber = "Version id1*10^-99";
    public static String creditsText = "Created by Kaltag";
    public static String selectCharacterColor = "#5f9b3f";
    public static String notSelectedHex = "#969696";
    public static String selectedHex = "#ffffff";
    public static int menuCurrent = 0;
    public static int currentSelected = 0;

    //<editor-fold desc="KeyBindActions">
    public static class UpAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            LabelResize current = selectables.get(currentSelected);

            if(current.getOriginalText() != null){
                String selectionChar = current.getSelectString();
                String original = current.getOriginalText();

                current.setText("<html><font color=" + selectCharacterColor + ">" + selectionChar + ": </font><font color=" + notSelectedHex + ">" + original + "</font>");
            } else {
                selectables.get(currentSelected).setForeground(notSelected);
            }

            if(currentSelected - 1 < 0){
                currentSelected = selectables.size() - 1;
            }
            else{
                currentSelected--;
            }

            LabelResize next = selectables.get(currentSelected);

            if(next.getOriginalText() != null){
                String selectionChar = next.getSelectString();
                String original = next.getOriginalText();

                next.setText("<html><font color=" + selectCharacterColor + ">" + selectionChar + ": </font><font color=" + selectedHex + ">" + original + "</font>");
            } else {
                selectables.get(currentSelected).setForeground(selected);
            }
        }
    }

    public static class DownAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            LabelResize current = selectables.get(currentSelected);

            if(current.getOriginalText() != null){
                String selectionChar = current.getSelectString();
                String original = current.getOriginalText();

                current.setText("<html><font color=" + selectCharacterColor + ">" + selectionChar + ": </font><font color=" + notSelectedHex + ">" + original + "</font>");
            } else {
                selectables.get(currentSelected).setForeground(notSelected);
            }

            if(currentSelected + 1 > selectables.size() - 1){
                currentSelected = 0;
            }
            else{
                currentSelected++;
            }

            LabelResize next = selectables.get(currentSelected);

            if(next.getOriginalText() != null){
                String selectionChar = next.getSelectString();
                String original = next.getOriginalText();

                next.setText("<html><font color=" + selectCharacterColor + ">" + selectionChar + ": </font><font color=" + selectedHex + ">" + original + "</font>");
            } else {
                selectables.get(currentSelected).setForeground(selected);
            }
        }
    }

    public static class RightAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            LabelResize current = selectables.get(currentSelected);

            if(current.getOriginalText() != null){
                String selectionChar = current.getSelectString();
                String original = current.getOriginalText();

                current.setText("<html><font color=" + selectCharacterColor + ">" + selectionChar + ": </font><font color=" + notSelectedHex + ">" + original + "</font>");
            } else {
                selectables.get(currentSelected).setForeground(notSelected);
            }

            if(currentSelected - 1 < 0){
                currentSelected = selectables.size() - 1;
            }
            else{
                currentSelected--;
            }

            LabelResize next = selectables.get(currentSelected);

            if(next.getOriginalText() != null){
                String selectionChar = next.getSelectString();
                String original = next.getOriginalText();

                next.setText("<html><font color=" + selectCharacterColor + ">" + selectionChar + ": </font><font color=" + selectedHex + ">" + original + "</font>");
            } else {
                selectables.get(currentSelected).setForeground(selected);
            }
        }
    }

    public static class LeftAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            LabelResize current = selectables.get(currentSelected);

            if(current.getOriginalText() != null){
                String selectionChar = current.getSelectString();
                String original = current.getOriginalText();

                current.setText("<html><font color=" + selectCharacterColor + ">" + selectionChar + ": </font><font color=" + notSelectedHex + ">" + original + "</font>");
            } else {
                selectables.get(currentSelected).setForeground(notSelected);
            }

            if(currentSelected + 1 > selectables.size() - 1){
                currentSelected = 0;
            }
            else{
                currentSelected++;
            }

            LabelResize next = selectables.get(currentSelected);

            if(next.getOriginalText() != null){
                String selectionChar = next.getSelectString();
                String original = next.getOriginalText();

                next.setText("<html><font color=" + selectCharacterColor + ">" + selectionChar + ": </font><font color=" + selectedHex + ">" + original + "</font>");
            } else {
                selectables.get(currentSelected).setForeground(selected);
            }
        }
    }

    public static class EnterAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            if(selectables != null){
                LabelResize current = selectables.get(currentSelected);
                if(current.getChangeLabel() != null){
                    current.getAction().action(current.getChangeLabel());
                } else {
                    current.getAction().action();
                }
            } else if(keyLabels != null){
                for (LabelResize label : keyLabels) {
                    if (label.getSelectString().equals("ENTER")) {
                        if(label.getChangeLabel() != null){
                            label.getAction().action(label.getChangeLabel());
                        } else {
                            label.getAction().action();
                        }
                    }
                }
            }
        }
    }

    public static class EscapeAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            for (LabelResize label : keyLabels) {
                if (label.getSelectString().equals("ESC")) {
                    label.getAction().action();
                }
            }
        }
    }

    public static class NLowerAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            for (LabelResize label : keyLabels) {
                if (label.getSelectString().equals("n")) {
                    label.getAction().action();
                }
            }
        }
    }

    public static class PlusAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            System.out.println("1");
            for (LabelResize label : keyLabels) {
                System.out.println("2");
                if (label.getSelectString().equals("+")) {
                    System.out.println("3");
                    label.getAction().action(label.getChangeLabel(), label.getAdd(), label.getChangeAmount());
                }
            }
        }
    }

    public static class MinusAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            for (LabelResize label : keyLabels) {
                if (label.getSelectString().equals("-")) {
                    label.getAction().action(label.getChangeLabel(), label.getAdd(), label.getChangeAmount());
                }
            }
        }
    }
    //</editor-fold>
}
