package CustomSwing;

import javax.swing.*;
import java.awt.*;

public class ScrollPaneResize extends JScrollPane {

    private Component view;
    private int old = 0;

    public ScrollPaneResize(Component view){
        super(view);
    }

    public void setOld(int i){
        old = i;
    }

    public int getOld(){
        return old;
    }
}
