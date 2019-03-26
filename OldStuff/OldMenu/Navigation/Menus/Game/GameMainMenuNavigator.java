package OldMenu.Navigation.Menus.Game;

import CustomSwing.LabelResize;
import OldMenu.Menus.Game.GameMainMenu;
import OldMenu.Navigation.Navigator;

import javax.swing.*;
import java.awt.*;

public class GameMainMenuNavigator extends Navigator {

    public void action() {
        JFrame pane = (JFrame) SwingUtilities.getWindowAncestor(Menu.menus.get(Menu.menuCurrent));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(40, 25, 25, 25);


        try {
            Menu.selectables.get(Menu.currentSelected).setForeground(Menu.notSelected);
        } catch (NullPointerException e) {

        }

        Menu.selectables = null;
        Menu.keyLabels = GameMainMenu.localKeyLabels;

        //try {
/*            JLayeredPane main = GameMainMenu.panelMain;
            main.remove(2);
            main.add(Menu.menus.get(2), c);
            main.moveToFront(Menu.menus.get(2));

            Menu.menuCurrent = 2;*/
        //} catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("2");
            pane.getContentPane().removeAll();
            GameMainMenu.createGameMainMenu(pane.getContentPane());
        //}

        pane.getContentPane().validate();
        pane.getContentPane().repaint(100);
    }

    public void action(LabelResize label){

    }

    public void action(LabelResize label, boolean add, double changeAmount){

    }
}