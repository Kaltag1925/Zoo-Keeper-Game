package OldMenu.Navigation.Menus.Game;

import CustomSwing.LabelResize;
import OldMenu.Menus.Game.ChangeDateMenu;
import OldMenu.Navigation.Navigator;

import javax.swing.*;
import java.awt.*;

public class ChangeDateMenuNavigator extends Navigator {

    public void action() {
        JFrame pane = (JFrame) SwingUtilities.getWindowAncestor(Menu.menus.get(Menu.menuCurrent));
        //JLayeredPane main = GameMainMenu.panelMain;
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(40, 25, 25, 25);

        //main.remove(0);

        try {
            Menu.selectables.get(Menu.currentSelected).setForeground(Menu.notSelected);
        } catch (NullPointerException e) {

        }

        Menu.selectables = null;
        Menu.keyLabels = ChangeDateMenu.localKeyLabels;

        try {
           /* main.add(Menu.menus.get(3), c);
            main.moveToFront(Menu.menus.get(3));
            Menu.menuCurrent = 3;*/

            ChangeDateMenu.getNumberLabel().setAmount(1);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            //ChangeDateMenu.createGameMainMenu(main);
        }

        ChangeDateMenu.regainFocus();
        pane.getContentPane().validate();
        pane.getContentPane().repaint(100);
    }

    public void action(LabelResize label){

    }

    public void action(LabelResize label, boolean add, double changeAmount){

    }
}
