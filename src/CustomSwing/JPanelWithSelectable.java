package CustomSwing;

import UserInterface.ISelectable;

import javax.swing.*;

public class JPanelWithSelectable extends JPanel implements ISelectable{

    private ISelectable selectable;

    public JPanelWithSelectable(ISelectable selectable) {
        super();
        this.selectable = selectable;
    }

    public void selected() {
        selectable.selected();
    }
}
