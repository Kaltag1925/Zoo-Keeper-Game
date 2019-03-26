package Menu;

import CustomSwing.LabelResize;
import CustomSwing.LabelResizeWithNumber;
import CustomSwing.Lists.AnimalBuyList;
import CustomSwing.PanelResize;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

public class BuyAnimalMenu extends Menu implements IMenuEntry {
    public static ArrayList<AnimalBuyList> list = new ArrayList<>();
    public static int selectedList;

    //private String[] listOfMammalTypes = new String[]{"Bear", "Coyote", "Dingo", "Hyena", "Leopard", "Lion", "Panther", "Tiger", "Wolf", "Deer", "Monkey", "Gorilla", "Armadillo"};

    private JPanel[][] displayComponents = new JPanel[1][6];
    private JPanel[][] selectionComponents = new JPanel[8][6];
    //TODO: Resolve Issue With Text Cutting Off

    public BuyAnimalMenu(String name, PanelResize displayHere, Menu parentMenu) {
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
                displayComponents[i][j] = new JPanel(new BorderLayout());
                displayComponents[i][j].setOpaque(false);
//                displayComponents[i][j].setBackground(new Color(i * 100, j * 40, (int) (i * j * 1.2 + 2 * i)));
                displayComponents[i][j].setPreferredSize(new Dimension(100, 10));
            }
        }

        EnterAction enter = new EnterAction();
        displayHere.getActionMap().put("doEnterActionBuyAnimalMenu", enter);

        LeftAction left = new LeftAction();
        displayHere.getActionMap().put("doLeftActionBuyAnimalMenu", left);

        RightAction right = new RightAction();
        displayHere.getActionMap().put("doRightActionBuyAnimalMenu", right);

        DownAction down = new DownAction();
        displayHere.getActionMap().put("doDownActionBuyAnimalMenu", down);

        UpAction up = new UpAction();
        displayHere.getActionMap().put("doUpActionBuyAnimalMenu", up);

        EscapeAction esc = new EscapeAction();
        displayHere.getActionMap().put("doEscActionBuyAnimalMenu", esc);

        ArrayList<Pair<KeyStroke, String>> inputMap = new ArrayList<>();
        inputMap.add(new Pair<>(enterButton, "doEnterActionBuyAnimalMenu"));
        inputMap.add(new Pair<>(leftButton, "doLeftActionBuyAnimalMenu"));
        inputMap.add(new Pair<>(leftButtonNP, "doLeftActionBuyAnimalMenu"));
        inputMap.add(new Pair<>(rightButton, "doRightActionBuyAnimalMenu"));
        inputMap.add(new Pair<>(rightButtonNP, "doRightActionBuyAnimalMenu"));
        inputMap.add(new Pair<>(downButton, "doDownActionBuyAnimalMenu"));
        inputMap.add(new Pair<>(downButtonNP, "doDownActionBuyAnimalMenu"));
        inputMap.add(new Pair<>(upButton, "doUpActionBuyAnimalMenu"));
        inputMap.add(new Pair<>(upButtonNP, "doUpActionBuyAnimalMenu"));
        inputMap.add(new Pair<>(escButton, "doEscActionBuyAnimalMenu"));
        setInputMapKeysValues(inputMap);
    }

    public void refresh(){
        frame.getContentPane().validate();
        frame.getContentPane().repaint(1000);
    }

    public void showMenu() {
        super.showMenu();
        list.clear();
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
                displayComponents[i][j].removeAll();
            }
        }
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 6; j++) {
                c.gridx = j;
                c.gridy = i;
                display.add(displayComponents[i][j], c);
            }
        }

        selectedList = 0;

        createFirstList(displayComponents[0][0]);

        setInputMaps((JPanel) getDisplayHere());

        frame.getContentPane().validate();
        frame.getContentPane().repaint(1000);
    }

    private static void createFirstList(JPanel display){
        ArrayList<String> nextList = new ArrayList<>();
//        for (AnimalCompany company : AnimalCompany.animalCompanies.values()){
//            Collection<AnimalReal> animals = company.getAnimals().values();
////            for (AnimalReal ani : animals) {
////                if(!nextList.contains(ani.getAnimalClass())) {
////                    nextList.add(ani.getAnimalClass());
////                }
////            }
//        }

        Collections.sort(nextList);


        AnimalBuyList animalType = new AnimalBuyList("Animal Type", nextList, display, BorderLayout.NORTH, SwingConstants.LEFT, list);

        for (LabelResize label : animalType.getLabelList()){
//            label.setTopParent();
        }

        animalType.getLabelList().get(0).setForeground(Menu.selected);

        animalType.preview();
    }

    public void back(){
        GameMainMenu menu = (GameMainMenu) menus.get("Game MainData Menu");
        menu.getPanelDisplay().removeAll();
        menu.getPanelSelections().removeAll();
        list.removeAll(list);
        super.back();
    }

    private class LeftAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {

        }
    }

    private class RightAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {

        }
    }

    private class DownAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    list.get(selectedList).down();
                    if (selectedList <= 2) {
                        list.get(selectedList).preview();
                    }
                    if (list.get(selectedList).getCurrentLabel() instanceof LabelResizeWithNumber) {
                        System.out.println(list.get(selectedList).getCurrentLabel().getText() + " " + ((LabelResizeWithNumber) list.get(selectedList).getCurrentLabel()).getInteger());
                    }
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
                    if (selectedList <= 2) {
                        list.get(selectedList).preview();
                    }
                }
            });
        }
    }

    private class EnterAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    list.get(selectedList).enter();
                    if (selectedList + 1 <= 3) {
                        selectedList++;
                        list.get(selectedList).getLabelList().get(0).setForeground(selected);
                        list.get(selectedList).preview();

                        frame.getContentPane().validate();
                        frame.getContentPane().repaint(1000);
                    } else {
                        removeInputMaps((JPanel) getDisplayHere());
                        int nextAnimalID = ((LabelResizeWithNumber) list.get(selectedList).getLabelList().get(list.get(selectedList).getSelectedLabel())).getInteger();
//                        BuyAnimalInfo buyAnimalInfo = new BuyAnimalInfo("Buy Animal Info", (PanelResize) getDisplayHere(), menus.get("Buy Animals Menu"), AnimalReal.getAllAnimals().get(nextAnimalID), list.get(selectedList).getHierarchy());
//                        buyAnimalInfo.showMenu();
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
                        list.remove(selectedList);
                        selectedList--;
                        displayComponents[0][selectedList + 1].removeAll();
                        list.get(selectedList).preview();
                    }

                    frame.getContentPane().validate();
                    frame.getContentPane().repaint(1000);
                }
            });
        }
    }
}


//                    Vector<LabelResize> currentShowedList = showed.get(listSelected);
//                    ArrayList<LabelResize> currentFullList = lists.get(listSelected);
//                    if(relativeLabelSelectedList[listSelected] < 23){
//                        currentShowedList.get(labelSelectedList[listSelected]).setForeground(notSelected);
//                        labelSelectedList[listSelected]++;
//                        relativeLabelSelectedList[listSelected]++;
//                        currentShowedList.get(labelSelectedList[listSelected]).setForeground(selected);
//                    } else {
//                        Container display = (Container) displayHere.getChild("Display");
//                        try {
//                            currentShowedList.get(relativeLabelSelectedList[listSelected]).setForeground(notSelected);
//                            currentShowedList.remove(0);
//                            currentShowedList.add(currentFullList.get(labelSelectedList[listSelected] + 1));
//                            for (int i = 0; i < 24; i++) {
//                                try {
//                                    displayComponents[i][listSelected].removeAll();
//                                    System.out.println("7");
//                                    displayComponents[i - 1][listSelected].add(currentShowedList.get(i));
//                                } catch (NullPointerException x) {
//                                    System.out.println("5");
//                                    break;
//                                } catch (IndexOutOfBoundsException x) {
//                                    System.out.println("6");
//
//                                }
//                            }
//                            display.add(currentShowedList.get(23));
//                            currentShowedList.get(23).setForeground(selected);
//                            labelSelectedList[listSelected]++;
////                    relativeLabelSelectedList[listSelected]++;
//                            System.out.println("1");
//                        } catch (IndexOutOfBoundsException x){
//                            System.out.println("2");
//                            if(currentFullList.size() > 24){
//                                currentShowedList.get(relativeLabelSelectedList[listSelected]).setForeground(notSelected);
//                                for (int i = 0; i < 24; i++) {
//                                    try {
//                                        displayComponents[listSelected][i].removeAll();
//                                        displayComponents[listSelected][i - 1].add(currentFullList.get(i + 1 + (relativeLabelSelectedList[listSelected] - labelSelectedList[listSelected])));
//                                    } catch (NullPointerException ex) {
//                                        System.out.println("3");
//                                        break;
//                                    }
//                                }
//                                currentShowedList.get(0).setForeground(selected);
//                                labelSelectedList[listSelected]++;
//                                relativeLabelSelectedList[listSelected]++;
//                                System.out.println("3");
//                            } else {
//                                System.out.println("4");
//                            }
//                        }
//                    }
//                    JFrame frame = displayHere.getTopParent();
//                    frame.getContentPane().validate();
//                    frame.getContentPane().repaint();
