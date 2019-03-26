package UserInterface.Panels.ControlPanel;

import Engine.Logic.Delivery;
import Engine.Logic.MainData;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DeliveriesPanel extends ControlPanel {

    private ArrayList<Delivery> deliveries = new ArrayList<>();
    private int selectedDelivery = -1;

    public DeliveriesPanel() {
        super();
    }

    @Override
    public void open() {
        super.open();

        deliveries.addAll(MainData.mainData.getPlayerZoo().getDeliveries());
    }

    @Override
    public void close() {
        super.close();

        selectedDelivery = -1;
        deliveries.clear();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.drawString("ESC", getFontWidth(), getFontHeight() * (getMaxY() - 1));

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(": Back", MainData.mainData.getFontManager().getFontWidth() * 4, MainData.mainData.getFontManager().getFontHeight() * (getMaxY() - 1));

        for (int i = 3; i < deliveries.size() + 3; i++) {
            Delivery delivery = deliveries.get(i - 3);

            g.setColor(Color.LIGHT_GRAY);
            String name = delivery.getName() + ": ";
            g.drawString(name, getFontWidth(), getFontHeight() * i);

            g.setColor(Color.GREEN);
            String distanceRemaining = null;
            if(delivery.getDistanceLeft() <= 0) {
                distanceRemaining = "DELIVERED";
            } else {
                distanceRemaining = String.valueOf(NumberFormat.getIntegerInstance().format(delivery.getDistanceLeft()));
            }
            int nameLength = name.length() + 1;
            g.drawString(distanceRemaining, getFontWidth() * nameLength, getFontHeight() * i);
        }

        if(selectedDelivery != -1) selectString(deliveries.get(selectedDelivery).getName(), g, 4 + selectedDelivery);
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch (key) {
            case KeyEvent.VK_ESCAPE:
                backKey();
                return true;

            case KeyEvent.VK_OPEN_BRACKET:
                upKey();
                return true;

            case KeyEvent.VK_CLOSE_BRACKET:
                downKey();
                return true;
        }

        return false;
    }

    private void upKey() {
        selectedDelivery++;
        if(selectedDelivery >= deliveries.size()) {
            selectedDelivery = 0;
        }
    }

    private void downKey() {
        selectedDelivery--;
        if(selectedDelivery < 0) {
            selectedDelivery = deliveries.size() - 1;
        }
    }
}