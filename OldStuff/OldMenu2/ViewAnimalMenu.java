package Menu;

import CustomSwing.LabelResize;
import CustomSwing.Lists.AnimalViewList;
import CustomSwing.PanelResize;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

public class ViewAnimalMenu extends Menu {
    public static ArrayList<AnimalViewList> list = new ArrayList<>();
    public static int selectedList;

    private PanelResize[][] displayComponents = new PanelResize[1][6];
    private JPanel[][] selectionComponents = new JPanel[8][6];

    public ViewAnimalMenu(String name, PanelResize displayHere, Menu parentMenu) {
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

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 6; j++) {
                displayComponents[i][j] = new PanelResize(new BorderLayout());
//                displayComponents[i][j].setOpaque(false);
                displayComponents[i][j].setBackground(new Color(i * 100, j * 40, (int) (i * j * 1.2 + 2 * i)));
                displayComponents[i][j].setPreferredSize(new Dimension(100, 10));
            }
        }

        UpAction up = new UpAction();
        displayHere.getActionMap().put("doUpActionViewAnimalMenu", up);

        DownAction down = new DownAction();
        displayHere.getActionMap().put("doDownActionViewAnimalMenu", down);

        EnterAction enter = new EnterAction();
        displayHere.getActionMap().put("doEnterActionViewAnimalMenu", enter);

        EscapeAction escape = new EscapeAction();
        displayHere.getActionMap().put("doEscapeActionViewAnimalMenu", escape);

        ArrayList<Pair<KeyStroke, String>> inputMap = new ArrayList<>();
        inputMap.add(new Pair<>(upButton, "doUpActionViewAnimalMenu"));
        inputMap.add(new Pair<>(upButtonNP, "doUpActionViewAnimalMenu"));
        inputMap.add(new Pair<>(downButton, "doDownActionViewAnimalMenu"));
        inputMap.add(new Pair<>(downButtonNP, "doDownActionViewAnimalMenu"));
        inputMap.add(new Pair<>(enterButton, "doEnterActionViewAnimalMenu"));
        inputMap.add(new Pair<>(escButton, "doEscapeActionViewAnimalMenu"));

        setInputMapKeysValues(inputMap);
    }

    public void showMenu() {
        super.showMenu();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PanelResize displayHere = (PanelResize) getDisplayHere();
                Container selections = (Container) displayHere.getChild("Selections");
                selections.removeAll();
                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.BOTH;
                c.weighty = 1;
                c.weightx = 1;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 6; j++) {
                        c.gridx = j;
                        c.gridy = i;
                        selections.add(selectionComponents[i][j], c);
                    }
                }

                Container display = (Container) displayHere.getChild("Display");
                display.removeAll();
                for (int i = 0; i < 1; i++) {
                    for (int j = 0; j < 6; j++) {
                        c.gridx = j;
                        c.gridy = i;
                        display.add(displayComponents[i][j], c);
                    }
                }

                createFirstList(displayComponents[0][0]);
                selectedList = 0;

                setInputMaps((JPanel) getDisplayHere());

                frame.getContentPane().validate();
                frame.getContentPane().repaint(1000);
            }
        });
    }

    private static void createFirstList(PanelResize display) {
//        Collection<AnimalReal> animals = AnimalReal.getAnimalsOwned().values();
        ArrayList<String> nextList = new ArrayList<>();
//        for (AnimalReal ani : animals) {
//            if(!nextList.contains(ani.getAnimalClass())) {
//                nextList.add(ani.getAnimalClass());
//            }
//        }

        if(!nextList.isEmpty()) {
            if (display.getChild("No Animals Warning") != null) {
                display.removeAll();
            }
            Collections.sort(nextList);

            AnimalViewList animalType = new AnimalViewList("Animal Type", nextList, display, BorderLayout.NORTH, SwingConstants.LEFT, list);

            for (LabelResize label : animalType.getLabelList()){
//                label.setTopParent();
            }
        } else {
            if (display.getChild("No Animals Warning") == null) {
                LabelResize noAnimalsWarning = new LabelResize("No Animals Warning", "No Animals", SwingConstants.LEFT, .5);
                noAnimalsWarning.setVerticalAlignment(SwingConstants.TOP);
                noAnimalsWarning.setForeground(selected);
                display.add(noAnimalsWarning);
//                noAnimalsWarning.setTopParent();
            }
        }
    }

    @Override
    public void back(){
        list.removeAll(list);
        removeInputMaps((JPanel) getDisplayHere());
        super.back();
    }

    private class DownAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    list.get(selectedList).down();
                    list.get(selectedList).preview();
                }
            });
        }
    }

    private class UpAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    list.get(selectedList).up();
                    list.get(selectedList).preview();
                }
            });
        }
    }

    private class EnterAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (selectedList + 1 <= 6) {
                        list.get(selectedList).enter();
                        selectedList++;
                        list.get(selectedList).getLabelList().get(0).setForeground(Menu.selected);
                        list.get(selectedList).preview();

                        frame.getContentPane().validate();
                        frame.getContentPane().repaint(1000);
                    }
                }
            });
        }
    }

    private class EscapeAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (selectedList - 1 == -1){
                        back();
                    } else {
                        selectedList--;
                        displayComponents[0][selectedList + 1].removeAll();
                        list.get(selectedList).enter();
                    }

                    frame.getContentPane().validate();
                    frame.getContentPane().repaint(1000);
                }
            });
        }
    }
}
