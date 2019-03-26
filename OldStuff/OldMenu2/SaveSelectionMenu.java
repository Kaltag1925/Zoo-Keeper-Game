package Menu;

import CustomSwing.LabelResize;
import CustomSwing.Lists.SaveSelectionList;
import CustomSwing.TextFieldResize;
import XML.CreateSaveFolder;
import XML.FileLocations;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

public class SaveSelectionMenu extends Menu implements IMenuEntry {
    private ArrayList<SaveSelectionList> list = new ArrayList<>();
    private int selectedList;

    private TextFieldResize newSaveName;
    private JPanel panelMain, listHolder;
    private LabelResize info;
    SaveSelectionList saveSelectionList;

    private boolean creatingNewSave;

    public SaveSelectionMenu(String name, Container displayHere, Menu parentMenu){

        super(name, displayHere, parentMenu);

        setPanelMain(panelMain);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1;
        c.gridy = 0;
        c.gridx = 0;

        panelMain = new JPanel(new GridBagLayout());
        panelMain.setBackground(Color.BLACK);

        info = new LabelResize("Select Save", SwingConstants.CENTER, .75, null, null);
        info.setForeground(selected);
        c.weighty = .20;
        panelMain.add(info, c);

        listHolder = new JPanel(new BorderLayout());
        c.anchor = GridBagConstraints.PAGE_START;
        c.weighty = .80;
        c.gridy = 1;
        panelMain.add(listHolder, c);


        //<editor-fold desc="Selection Components">
        //</editor-fold>

        UpAction up = new UpAction();
        panelMain.getActionMap().put("doUpActionSaveSelectionMenu", up);

        DownAction down = new DownAction();
        panelMain.getActionMap().put("doDownActionSaveSelectionMenu", down);

        EnterAction enter = new EnterAction();
        panelMain.getActionMap().put("doEnterActionSaveSelectionMenu", enter);

        EscapeAction escape = new EscapeAction();
        panelMain.getActionMap().put("doEscapeActionSaveSelectionMenu", escape);

        ArrayList<Pair<KeyStroke, String>> inputMap = new ArrayList<>();
        inputMap.add(new Pair<>(upButton, "doUpActionSaveSelectionMenu"));
        inputMap.add(new Pair<>(upButtonNP, "doUpActionSaveSelectionMenu"));
        inputMap.add(new Pair<>(downButton, "doDownActionSaveSelectionMenu"));
        inputMap.add(new Pair<>(downButtonNP, "doDownActionSaveSelectionMenu"));
        inputMap.add(new Pair<>(enterButton, "doEnterActionSaveSelectionMenu"));
        inputMap.add(new Pair<>(escButton, "doEscapeActionSaveSelectionMenu"));

        setInputMapKeysValues(inputMap);
        //</editor-fold>
    }

    public void back(){
        frame.getContentPane().removeAll();
        list.removeAll(list);
        super.back();
    }

    public void showMenu(){
        frame.getContentPane().removeAll();

        File[] fileHolding = FileLocations.saveLocation.listFiles();
        ArrayList<String> files = new ArrayList<>();
        files.add("Create New Save");

        for (File f : fileHolding){
            files.add(f.getName());
        }

        saveSelectionList = new SaveSelectionList("Saves", files, listHolder, BorderLayout.NORTH, SwingConstants.CENTER, list);
        saveSelectionList.getLabelList().get(0).setForeground(Menu.selected);

        creatingNewSave = false;

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 1;
        c.weightx = 1;
        c.gridy = 0;

        getDisplayHere().add(panelMain, c);

        for (LabelResize label : saveSelectionList.getLabelList()){
//            label.setTopParent();
        }

        setInputMaps(panelMain);
        panelMain.requestFocus();

        frame.getContentPane().validate();
        frame.getContentPane().repaint(100);

    }

    private class UpAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            list.get(selectedList).up();
        }
    }

    private class DownAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            list.get(selectedList).down();
        }
    }

    private class EnterAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            if (!creatingNewSave) {
                String watDo = null;
                try {
                    watDo = list.get(selectedList).enter();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                switch (watDo) {
                    case "Loading file":
                        list.removeAll(list);
                        GameMainMenu gameMainMenu = new GameMainMenu("Game MainData Menu", frame, menus.get(getName()));
                        gameMainMenu.showMenu();
                        break;
                    case "Creating new file":
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                listHolder.removeAll();
                                list.removeAll(list);

                                GridBagConstraints c = new GridBagConstraints();
                                c.anchor = GridBagConstraints.CENTER;

                                newSaveName = new TextFieldResize(20, .5);
                                listHolder.setLayout(new GridBagLayout());
                                listHolder.add(newSaveName, c);
                                newSaveName.setTopParent();

                                EnterAction enter = new EnterAction();
                                newSaveName.getInputMap().put(enterButton, "doEnterActionSaveSelectionMenuCreate");
                                newSaveName.getActionMap().put("doEnterActionSaveSelectionMenuCreate", enter);

                                frame.getContentPane().validate();
                                frame.getContentPane().repaint(100);
                                newSaveName.requestFocus();
                            }
                        });

                        creatingNewSave = true;
                        break;
                    case "File not found":
                        break;
                }
            } else {
                CreateSaveFolder.createSave(newSaveName.getText());
                newSaveName = null;
                removeInputMaps(panelMain);
                GameMainMenu gameMainMenu = new GameMainMenu("Game MainData Menu", frame, menus.get(getName()));
                gameMainMenu.showMenu();
            }
        }
    }

    private class EscapeAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae){
            back();
        }
    }
}
