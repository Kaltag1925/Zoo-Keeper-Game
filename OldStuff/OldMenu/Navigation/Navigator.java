package OldMenu.Navigation;

/*

 0 - Main Menu
 1 - Play
 2 - Options

*/

import CustomSwing.LabelResize;

public abstract class Navigator {

    public abstract void action();

    public abstract void action(LabelResize label);

    public abstract void action(LabelResize label, boolean add, double changeAmount);

}
