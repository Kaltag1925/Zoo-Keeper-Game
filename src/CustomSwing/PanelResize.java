package CustomSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class PanelResize extends JPanel implements ComponentListener, ActionListener{

    private JFrame topParent;
    private LayoutManager layout;
    private static int delay = 200;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static GridBagConstraints c = new GridBagConstraints();
    private static Insets inset = new Insets(0, 0, 0, 0);
    private double[] sizeParameters;
    private Timer waitingTimer;

    private String name;

    public PanelResize(LayoutManager layout, double[] sizeParameters){
        super(layout);
        this.sizeParameters = sizeParameters;
        addComponentListener(this);
    }

    public PanelResize(LayoutManager layout, double[] sizeParameters, String name){
        super(layout);
        this.sizeParameters = sizeParameters;
        setName(name);
        addComponentListener(this);
    }

    public PanelResize(LayoutManager layout){
        super(layout);
        addComponentListener(this);
    }

    public JFrame getTopParent() {
        return topParent;
    }

    public Component getChild(String name){
        Component[] children = this.getComponents();
        for(Component child : children){
            System.out.println(child.getName());
            if(child.getName().equals(name)){
                return child;
            }
        }

        return null;
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == this.waitingTimer) {
            waitingTimer.stop();
            waitingTimer = null;
            resizeInsets(sizeParameters, getParent());
        }
    }

    public void componentResized(ComponentEvent e) {
        if(sizeParameters != null) {
            if(waitingTimer == null) {
                waitingTimer = new Timer(delay, this);
                waitingTimer.start();
            } else {
                waitingTimer.restart();
            }
        }
    }

    public void componentMoved(ComponentEvent ce) {

    }

    public void componentShown(ComponentEvent ce) {

    }

    public void componentHidden(ComponentEvent ce) {

    }

    public void setTopParent() {
        topParent = (JFrame) SwingUtilities.getWindowAncestor(this);
    }

    private void resizeInsets(double[] list, Container container){
        try {
            double[] insetList = new double[4];

            for (int i = 0; i < 4; i++){
                if (i % 2 == 0) {
                    insetList[i] = (topParent.getHeight() / screenSize.getHeight() * list[i]);
                } else {
                    insetList[i] = (topParent.getWidth() / screenSize.getWidth() * list[i]);
                }
            }

            inset.set((int)(insetList[0]), (int)(insetList[1]), (int)(insetList[2]), (int)(insetList[3]));
            c.insets = inset;
            c.gridx = (int) list[4];
            c.gridy = (int) list[5];
            c.weightx = 1;
            c.weighty = list[6];
            c.anchor = (int) list[7];
            c.fill = GridBagConstraints.BOTH;
            container.remove(this);
            container.add(this, c);

        } catch (NullPointerException e) {

        }
    }

}
