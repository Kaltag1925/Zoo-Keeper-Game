package CustomSwing;

import CustomMisc.IResizeMaxViewListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

/*

 Size Array

 0 - Top
 1 - Left
 2 - Bottom
 3 - Right
 4 - Grid X
 5 - Grid Y

*/
@Deprecated
public class LabelResize extends JLabel implements ComponentListener, ActionListener {

    private double textScale;
    public static Font font;
    private static int delay = 200;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static JFrame topParent;
    private static GridBagConstraints c = new GridBagConstraints();
    private static Insets inset = new Insets(0, 0, 0, 0);
    private double[] sizeParameters;
    private Timer waitingTimer;

    private String selectString;

    private String firstText;
    private String lastText;

    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Px437_IBM_VGA9.ttf"));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    //<editor-fold desc="Initiation">
    public LabelResize(String text, int horizontalAlignment, double textScale, double[] sizeParameters, String selectString) {
        super(text, horizontalAlignment);
        this.textScale = textScale;
        this.sizeParameters = sizeParameters;
        this.selectString = selectString;
        addComponentListener(this);
        setFont(font);
    }

    public LabelResize(String text, int horizontalAlignment, double textScale, double[] sizeParameters, String firstText, String lastText) {
        super(text, horizontalAlignment);
        this.textScale = textScale;
        this.sizeParameters = sizeParameters;
        this.firstText = firstText;
        this.lastText = lastText;
        addComponentListener(this);
        setFont(font);
}

    public LabelResize(String name, String text, int horizontalAlignment, double textScale) {
        super(text, horizontalAlignment);
        setName(name);
        this.textScale = textScale;
        addComponentListener(this);
        setFont(font);
    }

    public LabelResize(String text, int horizontalAlignment, double textScale) {
        super(text, horizontalAlignment);
        this.textScale = textScale;
        addComponentListener(this);
        setFont(font);
        topParent = new JFrame();
    }

    //</editor-fold>

    //<editor-fold desc="Get Methods">
    public String getSelectString(){
        return selectString;
    }

    public String getFirstText(){
        return firstText;
    }

    public String getLastText(){
        return lastText;
    }

    public double[] getSizeParameters(){
        return sizeParameters;
    }

    public JFrame getTopParent(){
        return topParent;
    }
    //</editor-fold>

    //<editor-fold desc="Set Methods">
    public void setTopParent() {
        topParent = (JFrame) SwingUtilities.getWindowAncestor(this);
    }

    //<editor-fold desc="Interaction Methods">
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.waitingTimer) {
            waitingTimer.stop();
            waitingTimer = null;
            try {
                resizeText();
                resizeInsets(sizeParameters, getParent());
                IResizeMaxViewListener.Resize.event();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    public void componentResized(ComponentEvent e) {
        if (waitingTimer == null) {
            waitingTimer = new Timer(delay, this);
            waitingTimer.start();
        } else {
            waitingTimer.restart();
        }
    }

    public void componentMoved(ComponentEvent ce) {

    }

    public void componentShown(ComponentEvent ce) {

    }

    public void componentHidden(ComponentEvent ce) {

    }

    public void resizeText() {
        if (topParent.getHeight() < topParent.getWidth()) {
            double bigNumber = topParent.getHeight() / screenSize.getHeight() * textScale * 100;
            setFont(getFont().deriveFont((float) bigNumber));
        } else {
            double bigNumber = topParent.getWidth() / screenSize.getWidth() * textScale * 100;
            setFont(getFont().deriveFont((float) bigNumber));
        }
    }

    public void resizeInsets(double[] list, Container container){
        if(list != null) {
            try {
                double[] insetList = new double[4];

                for (int i = 0; i < 4; i++) {
                    if (i % 2 == 0) {
                        insetList[i] = (topParent.getHeight() / screenSize.getHeight() * list[i]);
                    } else {
                        insetList[i] = (topParent.getWidth() / screenSize.getWidth() * list[i]);
                    }
                }

                inset.set((int) (insetList[0]), (int) (insetList[1]), (int) (insetList[2]), (int) (insetList[3]));
                c.insets = inset;
                c.gridx = (int) list[4];
                c.gridy = (int) list[5];
                c.weightx = list[6];
                c.weighty = list[7];
                c.anchor = (int) list[8];
                c.fill = GridBagConstraints.BOTH;
                container.remove(this);
                container.add(this, c);
            } catch (NullPointerException e) {

            }
        }
    }
    //</editor-fold>
}
