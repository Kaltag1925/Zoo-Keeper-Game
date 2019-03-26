package OldMenu.Navigation.Menus.MainMenu;

import CustomSwing.LabelResize;
import OldMenu.Menus.MainMenu.MainMenu;
import OldMenu.Navigation.Navigator;

import javax.swing.*;
import java.awt.*;

public class MainMenuNavigator extends Navigator {

	public void action() {
		JFrame pane = (JFrame) SwingUtilities.getWindowAncestor( Menu.menus.get( Menu.menuCurrent ) );
		pane.getContentPane().removeAll();

		try {
			Menu.selectables.get(Menu.currentSelected).setForeground(Menu.notSelected);
		} catch (NullPointerException e) {

		}

		Menu.keyLabels = null;
		Menu.selectables = MainMenu.localSelectables;

		try {
			pane.getContentPane().add( Menu.menus.get( 0 ), BorderLayout.CENTER );

			MainMenu.regainFocus();
			Menu.menuCurrent = 0;
		}catch( IndexOutOfBoundsException | NullPointerException e ) {
			MainMenu.createMainMenu( pane.getContentPane() );
		}
		pane.getContentPane().validate();
		pane.getContentPane().repaint( 100 );

		Menu.currentSelected = 0;
		MainMenu.localSelectables.get( 0 ).setForeground( Menu.selected );
	}

	public void action(LabelResize label){

	}

	public void action(LabelResize label, boolean add, double changeAmount){

	}
}
