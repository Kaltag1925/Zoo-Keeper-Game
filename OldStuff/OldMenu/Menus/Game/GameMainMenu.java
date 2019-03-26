package OldMenu.Menus.Game;

import CustomMisc.ExtraArrayList;
import CustomSwing.LabelResize;
import CustomSwing.PanelResize;
import Engine.Day;
import OldMenu.Navigation.Menus.Game.ChangeDateMenuNavigator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameMainMenu extends Menu {

    public static JPanel panelMain;
    public static PanelResize panelInfo;
    public static LabelResize moneyAmount, zooName, date;

    private static LabelResize nextDay;
    private static PanelResize panelStuff, panelSelections, panelDisplay;
    public static int lastSelected = 0;
    public static ArrayList<LabelResize> localKeyLabels = new ArrayList<LabelResize>();

    private static LayoutManager layoutStyle = new BorderLayout();

    public static void createGameMainMenu(Container pane) {
        pane.setLayout(layoutStyle);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;

        // Panels

        panelMain = new JPanel();
        panelMain.setLayout(new GridBagLayout());
        panelMain.setBackground(Color.GRAY);

        panelInfo = new PanelResize(new GridBagLayout(), null, false);
        panelInfo.setBackground(Color.GRAY);

        panelSelections = new PanelResize(new GridBagLayout(), null, false);
        panelSelections.setBackground(Color.BLACK);

        panelDisplay = new PanelResize(new GridBagLayout(), null, false);
        panelDisplay.setBackground(Color.BLACK);

        // Selection Components

        nextDay = new LabelResize("<html><font color=" + selectCharacterColor + ">n: </font><font color=" + selectedHex + ">Progress Time</font>", SwingConstants.LEFT, .5, new ChangeDateMenuNavigator(), null, "n", "Progress Time", "n: Progress Time");
        nextDay.setVerticalAlignment(SwingConstants.TOP);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        localKeyLabels.add(nextDay);
        panelSelections.add(nextDay, c);

        // Info Components

        moneyAmount = new LabelResize("$100", SwingConstants.CENTER, .5, null, null, null, null, "$100");
        moneyAmount.setVerticalAlignment(SwingConstants.TOP);
        moneyAmount.setPreferredSize(new Dimension(100, 40));
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        panelInfo.add(moneyAmount, c);

        zooName = new LabelResize("Zoo", SwingConstants.CENTER, .5, null, null, null, null, "Zoo");
        zooName.setVerticalAlignment(SwingConstants.TOP);
        zooName.setPreferredSize(new Dimension(100, 40));
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.PAGE_START;
        panelInfo.add(zooName, c);

        date = new LabelResize(Day.getDateLong(), SwingConstants.CENTER, .5, null, null, null, null, Day.getDateLong());
        date.setVerticalAlignment(SwingConstants.TOP);
        date.setPreferredSize(new Dimension(100, 40));
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        panelInfo.add(date, c);
        dates.add(date);

        // Add Everything

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.weighty = 0;
        c.insets = new Insets(0, 0, 0, 0);
        panelMain.add(panelInfo, c);

        c.weighty = 1;
        c.gridy = 1;
        c.insets = new Insets(25, 25, 25, 25);
        panelMain.add(panelDisplay, c);

        c.insets = new Insets(0, 25, 25, 25);
        c.weighty = .35;
        c.gridy = 2;
        panelMain.add(panelSelections, c);

        pane.add(panelMain, BorderLayout.CENTER);

        nextDay.setTopParent();
        moneyAmount.setTopParent();
        zooName.setTopParent();
        date.setTopParent();

        panelInfo.setTopParent();
        panelDisplay.setTopParent();
        panelSelections.setTopParent();
//        panelStuff.setTopParent();

        ExtraArrayList.ensureSize(menus, 2);
        menus.add(2, panelStuff);
        Menu.menuCurrent = 2;

        // Key Binds

        panelSelections.requestFocus();

        NLowerAction nLowerAction = new NLowerAction();
        panelMain.getInputMap().put(nLower, "doNLower");
        panelMain.getActionMap().put("doNLower", nLowerAction);

        keyLabels = localKeyLabels;
    }

/*    public static JLayeredPane getPanelMain() {
        return panelMain;
    }*/

    public static void regainFocus() {
        panelStuff.requestFocus();
    }
}
