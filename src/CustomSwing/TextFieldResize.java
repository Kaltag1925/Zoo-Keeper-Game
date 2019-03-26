package CustomSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextFieldResize extends JTextField implements ComponentListener, ActionListener {

    private double textScale;
    public static Font font = LabelResize.font;
    private static int delay = 200;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static JFrame topParent;
    private Timer waitingTimer;

    public TextFieldResize(int columns, double textScale){
        super(columns);
        this.textScale = textScale;
        TextFieldResize h = this;
        addComponentListener(this);
        this.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                char ch = e.getKeyChar();
                if(!Character.isLetter(ch)){
                    h.setText(h.getText().substring(0, h.getText().length() - 1));
                }
            }
        });

        setFont(font);
    }

    //<editor-fold desc="Get Methods">
    public JFrame getTopParent(){
        return topParent;
    }
    //</editor-fold>

    //<editor-fold desc="Set Methods">
    public void setTopParent() {
        topParent = (JFrame) SwingUtilities.getWindowAncestor(this);
    }
    //</editor-fold>

    //<editor-fold desc="Interaction Methods">
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.waitingTimer) {
            waitingTimer.stop();
            waitingTimer = null;
            try {
                resizeText();
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
            setFont(font.deriveFont((float) bigNumber));
        } else {
            double bigNumber = topParent.getWidth() / screenSize.getWidth() * textScale * 100;
            setFont(font.deriveFont((float) bigNumber));
        }
    }

    //</editor-fold>
}
