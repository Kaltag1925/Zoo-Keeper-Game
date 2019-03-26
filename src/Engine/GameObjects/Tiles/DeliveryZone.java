package Engine.GameObjects.Tiles;

import Engine.Logic.Delivery;
import Engine.Logic.MainData;
import UserInterface.Panels.ControlPanel.ListObjectsPanel;
import UserInterface.Panels.ControlPanel.Listed;
import UserInterface.Panels.ControlPanel.ObjectPanel;
import UserInterface.Panels.DescriptionPanel.DescriptionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class DeliveryZone extends Zone {

    public DeliveryZone(String name, Tile start, Tile end) {
        super(name, end, start);
    }

    @Override
    public ControlPanelFormat getControlPanelControls() {
        ControlPanelFormat controls = new ControlPanelFormat();
        controls.add(new ControlPanelObject("l", "List Incoming Deliveries", listDeliveriesMethod, KeyEvent.VK_L));
        return controls;
    }


    private Method listDeliveriesMethod;
    {
        try {
            listDeliveriesMethod = DeliveryZone.class.getDeclaredMethod("listDeliveries");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void listDeliveries() {
        final Listed.ListedList deliveryList = new Listed.ListedList(MainData.mainData.getPlayerZoo().getDeliveries());
        final Object cur = this;

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ListObjectsPanel(deliveryList, "delivery", selectDeliveryMethod, cur, MainData.mainData.getMapPanelManager().getLocalMapPanel());
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Method selectDeliveryMethod;
    {
        try {
            selectDeliveryMethod = DeliveryZone.class.getDeclaredMethod("selectDelivery", Delivery.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Delivery delivery;

    public void selectDelivery(Delivery delivery) {
        this.delivery = delivery;
        openDelivery();
    }

    private void openDelivery() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ObjectPanel(delivery).open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintToLargeDescriptionPanel(DescriptionPanel panel, Graphics g) {
        final int fontWidth = panel.getFontWidth();
        final int fontHeight = panel.getFontHeight();

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(getName(), fontWidth, fontHeight);

        g.setColor(Color.LIGHT_GRAY);
        final String dimensionsString = "Dimensions: ";
        g.drawString(dimensionsString, fontWidth, fontHeight * 2);
        g.setColor(Color.GREEN);
        g.drawString(getDimensions(X) + "x" + getDimensions(Y), fontWidth * (dimensionsString.length() + 1), fontHeight * 2);

        g.setColor(Color.LIGHT_GRAY);
        final String deliveriesString = "Incoming Deliveries: ";
        final List<Delivery> deliveryList = MainData.mainData.getPlayerZoo().getDeliveries();
        g.drawString(deliveriesString, fontWidth, fontHeight * 3);
        g.setColor(Color.GREEN);
        g.drawString(String.valueOf(deliveryList.size()), fontWidth * (deliveriesString.length() + 1), fontHeight * 3);
    }
}
