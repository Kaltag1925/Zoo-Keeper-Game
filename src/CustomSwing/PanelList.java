package CustomSwing;

import javax.swing.*;
import java.awt.*;

public class PanelList extends JPanel{

    private LayoutManager layoutManager;
    private int displayHere;

    public PanelList(LayoutManager layoutManager, int displayHere){
        super(layoutManager);
        this.displayHere = displayHere;
    }

    public int getDisplayHere() {
        return displayHere;
    }
}
