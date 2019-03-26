package Menu;

import CustomSwing.LabelResize;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainMenu extends Menu implements IMenuEntry {

    private ArrayList<LabelResize> selectables = new ArrayList<LabelResize>();
    private LabelResize title, play, options, exit, credits, version;
    private JPanel panelMain;
    private int currentSelected = 0;

    public MainMenu(String name, Container displayHere, Menu parentMenu){
        super(name, displayHere, parentMenu);

        setPanelMain(panelMain);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weighty = 1;
        c.weightx = 1;

        panelMain = new JPanel(new GridBagLayout());
        panelMain.setBackground(Color.BLACK);

        c.gridx = 0;
        c.gridy = 0;

        //<editor-fold desc="Components">
        //<editor-fold desc="Title Components">
        title = new LabelResize("Zoo Keeper", SwingConstants.CENTER, 1, null, null);
        title.setForeground(Color.ORANGE);
        c.gridx = 1;
        c.gridy = 0;
        panelMain.add(title, c);
        //</editor-fold>

        //<editor-fold desc="Selection Components">
        play = new LabelResize("Play", SwingConstants.CENTER, .75, new double[] {0, 0, 150, 0, 1, 1, 1, 1, GridBagConstraints.CENTER}, null);
        play.setForeground(selected);
        selectables.add(play);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(play, c);

        options = new LabelResize("Options", SwingConstants.CENTER, .75, null, null);
        options.setForeground(notSelected);
        selectables.add(options);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(options, c);

        exit = new LabelResize("Exit", SwingConstants.CENTER, .75, new double[] {150, 0, 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER}, null);
        exit.setForeground(notSelected);
        selectables.add(exit);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(exit, c);
        //</editor-fold>

        //<editor-fold desc="Info Components">
        version = new LabelResize(versionNumber, SwingConstants.CENTER, .75, new double[] {0, 0, 0, 0, 2, 2, 1, 1, GridBagConstraints.CENTER}, null);
        version.setForeground(Color.GREEN);
        c.gridx = 2;
        c.gridy = 2;
        version.setPreferredSize(new Dimension(100, 100));
        panelMain.add(version, c);

        credits = new LabelResize(creditsText, SwingConstants.CENTER, .75, new double[] {0, 0, 0, 0, 0, 2, 1, 1, GridBagConstraints.CENTER}, null);
        credits.setForeground(Color.GREEN);
        c.gridx = 0;
        c.gridy = 2;
        credits.setPreferredSize(new Dimension(100, 100));
        panelMain.add(credits, c);
        //</editor-fold>

        UpAction up = new UpAction();
        panelMain.getActionMap().put("doUpActionMainMenu", up);

        DownAction down = new DownAction();
        panelMain.getActionMap().put("doDownActionMainMenu", down);

        EnterAction enter = new EnterAction();
        panelMain.getActionMap().put("doEnterActionMainMenu", enter);

        ArrayList<Pair<KeyStroke, String>> inputMap = new ArrayList<>();
        inputMap.add(new Pair<>(upButton, "doUpActionMainMenu"));
        inputMap.add(new Pair<>(upButtonNP, "doUpActionMainMenu"));
        inputMap.add(new Pair<>(downButton, "doDownActionMainMenu"));
        inputMap.add(new Pair<>(downButtonNP, "doDownActionMainMenu"));
        inputMap.add(new Pair<>(enterButton, "doEnterActionMainMenu"));
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
//        title.setTopParent();
//        play.setTopParent();
//        options.setTopParent();
//        exit.setTopParent();
//        version.setTopParent();
//        credits.setTopParent();

        frame.getContentPane().validate();
        frame.getContentPane().repaint(100);

        currentSelected = 0;
        setInputMaps(panelMain);
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
            if(currentSelected == 0){
                removeInputMaps(panelMain);
                SaveSelectionMenu saveSelectionMenu = new SaveSelectionMenu("Save Selection Menu", frame, menus.get(getName()));
                saveSelectionMenu.showMenu();
            }
            if(currentSelected == 2){
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        }
    }
    //</editor-fold>
}
