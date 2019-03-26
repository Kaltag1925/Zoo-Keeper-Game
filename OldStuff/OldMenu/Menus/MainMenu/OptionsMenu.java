package OldMenu.Menus.MainMenu;

import CustomMisc.ExtraArrayList;
import CustomSwing.LabelResize;
import OldMenu.Navigation.Menus.MainMenu.MainMenuNavigator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OptionsMenu extends Menu {

    private static LabelResize title, back, version, credits;
    private static JPanel panelMain;
    public static int lastSelected = 0;
    public static ArrayList<LabelResize> localSelectables = new ArrayList<LabelResize>();
    private static LayoutManager layoutStyle = new BorderLayout();

    public static void createOptionsMenu(Container pane) {
        pane.setLayout(layoutStyle);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;

        panelMain = new JPanel(new GridBagLayout());
        panelMain.setBackground(Color.BLACK);

        // Selection Components

        back = new LabelResize("Back", SwingConstants.CENTER, 1, new MainMenuNavigator(), null, null, null, "Back");
        back.setForeground(notSelected);
        localSelectables.add(back);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(back, c);

        // Info Components

        version = new LabelResize(versionNumber, SwingConstants.CENTER, .5, null, null, null, null, versionNumber);
        version.setForeground(Color.GREEN);
        c.gridx = 2;
        c.gridy = 2;
        panelMain.add(version, c);


        credits = new LabelResize(creditsText, SwingConstants.CENTER, .5, null, null, null, null, creditsText);
        credits.setForeground(Color.GREEN);
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        panelMain.add(credits, c);

        pane.add(panelMain, BorderLayout.CENTER);
        back.setTopParent();
        version.setTopParent();
        credits.setTopParent();


        ExtraArrayList.ensureSize(menus, 1);
        menus.add(1, panelMain);
        Menu.menuCurrent = 1;

        // Key Binds

        panelMain.requestFocus();

        UpAction up = new UpAction();
        panelMain.getInputMap().put(upButton, "doUpAction");
        panelMain.getInputMap().put(upButtonNP, "doUpAction");
        panelMain.getActionMap().put("doUpAction", up);

        DownAction down = new DownAction();
        panelMain.getInputMap().put(downButton, "doDownAction");
        panelMain.getInputMap().put(downButtonNP, "doDownAction");
        panelMain.getActionMap().put("doDownAction", down);

        EnterAction enter = new EnterAction();
        panelMain.getInputMap().put(enterButton, "doEnterAction");
        panelMain.getActionMap().put("doEnterAction", enter);

        selectables = localSelectables;
    }

    public static void main(String[] args) {
        //Create and set up the window.
        JFrame frame = new JFrame("Zoo Keeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        createOptionsMenu(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static JPanel getPanelMain() {
        return panelMain;
    }

    public static void regainFocus() {
        panelMain.requestFocus();
    }
}