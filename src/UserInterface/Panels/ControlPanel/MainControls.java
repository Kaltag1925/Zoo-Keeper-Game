package UserInterface.Panels.ControlPanel;

import Engine.Logic.MainData;
import Engine.Logic.Ticks.IHasKeyBinds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

public class MainControls extends ControlPanel implements IHasKeyBinds {

    public MainControls() {
        super();
        setBackground(Color.BLACK);
        setAttachedViewPanel(MainData.mainData.getMapPanelManager().getLocalMapPanel());
    }

    private static final int CHAR_INDICATOR_LENGTH = 2;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(MainData.mainData.getFontManager().getGameFont());

        g.setColor(Color.GREEN);
        g.drawString("z", MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight());
        g.drawString("a", MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight() * 2);
        g.drawString("e", MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight() * 3);
        g.drawString("b", MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight() * 4);
        g.drawString("d", MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight() * 5);

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(": Zones", MainData.mainData.getFontManager().getFontWidth() * CHAR_INDICATOR_LENGTH, MainData.mainData.getFontManager().getFontHeight());
        g.drawString(": Animals", MainData.mainData.getFontManager().getFontWidth() * CHAR_INDICATOR_LENGTH, MainData.mainData.getFontManager().getFontHeight() * 2);
        g.drawString(": Employees", MainData.mainData.getFontManager().getFontWidth() * CHAR_INDICATOR_LENGTH, MainData.mainData.getFontManager().getFontHeight() * 3);
        g.drawString(": Buildings", MainData.mainData.getFontManager().getFontWidth() * CHAR_INDICATOR_LENGTH, MainData.mainData.getFontManager().getFontHeight() * 4);
        g.drawString(": Deliveries", MainData.mainData.getFontManager().getFontWidth() * CHAR_INDICATOR_LENGTH, MainData.mainData.getFontManager().getFontHeight() * 5);

    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch(key) {
            case KeyEvent.VK_Z:
                zoneKey();
                return true;

            case KeyEvent.VK_E:
                employeeKey();
                return true;

            case KeyEvent.VK_A:
                animalKey();
                return true;

            case KeyEvent.VK_B:
                buildingKey();
                return true;

            case KeyEvent.VK_D:
                deliveriesKey();
                return true;

            case KeyEvent.VK_F:
                foodKey();
                return true;
        }

        return false;
    }

    private void zoneKey() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ZoneMainPanel().open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void employeeKey() {

    }

    private void animalKey() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new AnimalMainPanel().open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void buildingKey() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new BuildingPanel().open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void deliveriesKey() {
        Listed.ListedList deliveries = new Listed.ListedList(MainData.mainData.getPlayerZoo().getDeliveries());
        Object cur = this;

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ListObjectsPanel(deliveries, "delivery", null, cur, MainData.mainData.getMapPanelManager().getCurrentMapPanel()).open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void foodKey() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new FoodMainPanel().open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "MainControls";
    }
}
