package Menu;

import CustomSwing.LabelResize;
import XML.CreateSaveFolder;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GameEscapeMenu extends Menu implements IMenuEntry {
    private ArrayList<LabelResize> selectables = new ArrayList<LabelResize>();
    private LabelResize saveExit, play, options, exit, save;
    private JPanel panelMain;
    private int currentSelected = 0;

    public GameEscapeMenu(String name, Container displayHere, Menu parentMenu){
        super(name, displayHere, parentMenu);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 1;
        c.weightx = 1;

        panelMain = new JPanel(new GridBagLayout());
        panelMain.setBackground(Color.BLACK);
        setPanelMain(panelMain);

        c.gridx = 0;
        c.gridy = 0;

        //<editor-fold desc="Components">
        //<editor-fold desc="Selection Components">
        play = new LabelResize("Resume", SwingConstants.CENTER, textSize, new double[] {0, 0, 300, 0, 1, 1, 1, 1, GridBagConstraints.CENTER}, null);
        play.setForeground(selected);
        selectables.add(play);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(play, c);

        options = new LabelResize("Options", SwingConstants.CENTER, textSize, new double[] {0, 0, 150, 0, 1, 1, 1, 1, GridBagConstraints.CENTER}, null);
        options.setForeground(notSelected);
        selectables.add(options);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(options, c);

        save = new LabelResize("Save & Exit", SwingConstants.CENTER, textSize, new double[] {0, 0, 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER}, null);
        save.setForeground(notSelected);
        selectables.add(save);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(save, c);

        saveExit = new LabelResize("Save & Exit", SwingConstants.CENTER, textSize, new double[] {150, 0, 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER}, null);
        saveExit.setForeground(notSelected);
        selectables.add(saveExit);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(saveExit, c);


        exit = new LabelResize("Exit", SwingConstants.CENTER, textSize, new double[] {300, 0, 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER}, null);
        exit.setForeground(notSelected);
        selectables.add(exit);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(exit, c);
        //</editor-fold>

        UpAction up = new UpAction();
        panelMain.getActionMap().put("doUpActionGameEscapeMenu", up);

        DownAction down = new DownAction();
        panelMain.getActionMap().put("doDownActionGameEscapeMenu", down);

        EnterAction enter = new EnterAction();
        panelMain.getActionMap().put("doEnterActionGameEscapeMenu", enter);

        ArrayList<Pair<KeyStroke, String>> inputMap = new ArrayList<>();
        inputMap.add(new Pair<>(upButton, "doUpActionGameEscapeMenu"));
        inputMap.add(new Pair<>(upButtonNP, "doUpActionGameEscapeMenu"));
        inputMap.add(new Pair<>(downButton, "doDownActionGameEscapeMenu"));
        inputMap.add(new Pair<>(downButtonNP, "doDownActionGameEscapeMenu"));
        inputMap.add(new Pair<>(enterButton, "doEnterActionGameEscapeMenu"));
        setInputMapKeysValues(inputMap);
        //</editor-fold>
    }

    public void showMenu(){
        frame.getContentPane().removeAll();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 1;
        c.weightx = 1;

        getDisplayHere().add(panelMain, c);
//        saveExit.setTopParent();
//        play.setTopParent();
//        options.setTopParent();
//        exit.setTopParent();
//        save.setTopParent();

        frame.getContentPane().validate();
        frame.getContentPane().repaint(1000);

        setInputMaps(panelMain);

        currentSelected = 0;
        panelMain.requestFocus();
    }

    //<editor-fold desc="Keybind Commands">
    private class UpAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            selectables.get(currentSelected).setForeground(notSelected);

            if(currentSelected - 1 < 0){
                currentSelected = selectables.size() - 1;
            }
            else{
                currentSelected--;
            }

            selectables.get(currentSelected).setForeground(selected);
        }
    }

    private class DownAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            selectables.get(currentSelected).setForeground(notSelected);

            if(currentSelected + 1 > selectables.size() - 1){
                currentSelected = 0;
            }
            else{
                currentSelected++;
            }

            selectables.get(currentSelected).setForeground(selected);
        }
    }

    private class EnterAction extends AbstractAction {
        public void actionPerformed(ActionEvent tf){
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelMain);
            if (currentSelected == 0) {
                back();
            } else if (currentSelected == 3) {
                menus.remove(getName());
                removeInputMaps(panelMain);
                CreateSaveFolder.save();
                menus.get("MainData Menu").showMenu();
            } else if (currentSelected == 4) {
                menus.remove(getName());
                removeInputMaps(panelMain);
                menus.get("MainData Menu").showMenu();
            }
        }
    }
    //</editor-fold>
}
