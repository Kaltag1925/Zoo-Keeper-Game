package UserInterface;

import Engine.Logic.MainData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MainFrame extends JFrame implements ComponentListener{

    private int minimumWidthForFont = (int) (Toolkit.getDefaultToolkit().getScreenSize().width / 2.5);
    private int minimumHeightForFont = (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 2.5);

    /*
    * make strings of options, if can fit whole string on line draw on that line
    * subtract that from max x
    * repeat
    * if not go to next line
    * if new line too big resize text down
    * have option for making spacers*/

    public MainFrame() {
        super();
        setPreferredSize(new Dimension(minimumWidthForFont, minimumHeightForFont));
        addComponentListener(this);
    }

    @Override
    public void componentShown(ComponentEvent ce) {

    }

    @Override
    public void componentMoved(ComponentEvent ce) {

    }

    @Override
    public void componentHidden(ComponentEvent ce) {

    }

    @Override
    public void componentResized(ComponentEvent ce) {
        FontManager fontManager = MainData.mainData.getFontManager();
        if (getWidth() < getHeight()) {
            if (getWidth() < minimumWidthForFont) {
                float newFontSize = ((float) getWidth() / (float) minimumWidthForFont) * fontManager.getMaxFontSize();
                fontManager.resizeFont(newFontSize);
            }
        } else {
            if (getHeight() < minimumHeightForFont) {
                float newFontSize = ((float) getHeight() / (float) minimumHeightForFont) * fontManager.getMaxFontSize();
                fontManager.resizeFont(newFontSize);
            }
        }
    }
}
