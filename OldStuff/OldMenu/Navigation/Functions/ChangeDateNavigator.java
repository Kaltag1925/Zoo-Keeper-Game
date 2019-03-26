package OldMenu.Navigation.Functions;

import CustomSwing.LabelResize;
import Engine.Day;
import OldMenu.Navigation.Menus.Game.GameMainMenuNavigator;
import OldMenu.Navigation.Navigator;

public class ChangeDateNavigator extends Navigator {
    public void action(){

    }

    public void action(LabelResize label) {
        Day.advanceDay((int) label.getAmount());
        GameMainMenuNavigator next = new GameMainMenuNavigator();
        next.action();
    }

    public void action(LabelResize label, boolean add, double changeAmount){

    }
}
