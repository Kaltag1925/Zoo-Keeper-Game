package OldMenu.Navigation.Menus.MainMenu;

import CustomSwing.LabelResize;
import OldMenu.Menus.MainMenu.OptionsMenu;
import OldMenu.Navigation.Navigator;

import javax.swing.*;
import java.awt.*;

public class OptionsMenuNavigator extends Navigator {

	public void action() {
		JFrame pane = (JFrame) SwingUtilities.getWindowAncestor( Menu.menus.get( Menu.menuCurrent ) );
		pane.getContentPane().removeAll();

		try {
			Menu.selectables.get(Menu.currentSelected).setForeground(Menu.notSelected);
		} catch (NullPointerException e) {

		}

		Menu.keyLabels = null;
		Menu.selectables = OptionsMenu.localSelectables;

		try {
			pane.getContentPane().add( Menu.menus.get( 1 ), BorderLayout.CENTER );

			OptionsMenu.regainFocus();
			Menu.menuCurrent = 1;
		}catch( IndexOutOfBoundsException | NullPointerException e ) {
			OptionsMenu.createOptionsMenu( pane.getContentPane() );
		}

		pane.getContentPane().validate();
		pane.getContentPane().repaint( 100 );

		Menu.currentSelected = 0;
		OptionsMenu.localSelectables.get( 0 ).setForeground( Menu.selected );
	}

    public void action(LabelResize label){

    }

	public void action(LabelResize label, boolean add,double changeAmount){

	}
}
