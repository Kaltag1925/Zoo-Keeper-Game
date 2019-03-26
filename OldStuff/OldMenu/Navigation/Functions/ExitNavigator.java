package OldMenu.Navigation.Functions;

import CustomSwing.LabelResize;
import OldMenu.Navigation.Navigator;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class ExitNavigator extends Navigator {

    public void action(){
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Menu.menus.get(Menu.menuCurrent));
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public void action(LabelResize label){

    }

    public void action(LabelResize label, boolean add, double changeAmount){

    }
}
