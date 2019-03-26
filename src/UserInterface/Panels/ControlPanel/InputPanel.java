package UserInterface.Panels.ControlPanel;

import Engine.Logic.MainData;
import UserInterface.FontManager;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Format;
import java.util.HashMap;

public class InputPanel extends ControlPanel implements ComponentListener, WindowStateListener {

    final private Object objectToExecuteOn;
    final private Method executeAfterAccept;
    final private String whatEntering;

    private JTextField textField;

    public InputPanel(Object objectToExecuteOn, Method executeAfterAccept, String whatEntering, Format format, DocumentFilter filter) {
        super();
        this.objectToExecuteOn = objectToExecuteOn;
        this.executeAfterAccept = executeAfterAccept;
        this.whatEntering = whatEntering;

        setLayout(null);
        textField = new JTextField();
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.LIGHT_GRAY);
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(filter);
        add(textField);
    }

    private void doShowing() {
        textField.requestFocus();

        final FontManager fontManager = MainData.mainData.getFontManager();
        final HashMap<Integer, Integer> panelFontStats = fontManager.getFontInfoForPanel(this);
        final int maxX = panelFontStats.get(FontManager.X);
        final int fontWidth = fontManager.getFontWidth();
        final int fontHeight = fontManager.getFontHeight();
        textField.setBounds(fontWidth, fontHeight * 3, fontWidth * maxX, fontHeight);
        textField.setFont(MainData.mainData.getFontManager().getGameFont());

        final JFrame frame = MainData.mainData.getMainFrame();
        frame.addComponentListener(this);
        frame.addWindowStateListener(this);
    }

    public static NumbersOnly getNumbersOnlyInstance() {
        return new NumbersOnly();
    }

    static class NumbersOnly extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
            System.out.println(1);
            for (char c : str.toCharArray()) {
                if (Character.isDigit(c)) super.insertString(fb, offs, String.valueOf(c), a);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
            System.out.println(1);
            for (char c : str.toCharArray()) {
                if (Character.isDigit(c)) super.replace(fb, offs, length, String.valueOf(c), a);
            }
        }
    }

    @Override
    public void open() {
        super.open();
        doShowing();
        textField.addKeyListener(MainData.mainData.getInputManager());
    }

    @Override
    public void makeVisible() {
        super.makeVisible();
        doShowing();
    }

    @Override
    public void close() {
        super.close();
        JFrame frame = MainData.mainData.getMainFrame();
        frame.requestFocus();
        frame.removeComponentListener(this);
        frame.removeWindowStateListener(this);
    }

    @Override
    public void makeHidden() {
        super.makeHidden();
        JFrame frame = MainData.mainData.getMainFrame();
        frame.requestFocus();
        frame.removeComponentListener(this);
        frame.removeWindowStateListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.drawString("ESC", getFontWidth(), getFontHeight() * (getMaxY() - 1));

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(": Back", getFontWidth() * 4, getFontHeight() * (getMaxY() - 1));
        g.drawString("Enter " + whatEntering, getFontWidth(), getFontHeight());
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch (key) {
            case KeyEvent.VK_ESCAPE:
                backKey();
                return true;

            case KeyEvent.VK_ENTER:
                selectKey();
                return true;
        }

        return false;
    }

    private void selectKey() {
        final String input = textField.getText();

        try {
            executeAfterAccept.invoke(objectToExecuteOn, input);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void setTextFieldNumbers() {
        textField.setBounds(getFontWidth(), getFontHeight() * 3, getFontWidth() * getMaxX(), getFontHeight());
        textField.setFont(MainData.mainData.getFontManager().getGameFont());
    }

    @Override
    public void componentResized(ComponentEvent e) {
        setTextFieldNumbers();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        setTextFieldNumbers();
    }
}