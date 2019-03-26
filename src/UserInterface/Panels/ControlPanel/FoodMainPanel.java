package UserInterface.Panels.ControlPanel;

import Engine.GameObjects.Companies.Zoo;
import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Entities.Items.AnimalFoodReal;
import Engine.GameObjects.Tiles.DeliveryZone;
import Engine.Logic.Delivery;
import Engine.Logic.MainData;
import Engine.Logic.NotificationManager;
import Engine.Logic.Ticks.Tick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;

public class FoodMainPanel extends ControlPanel {

    public FoodMainPanel() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.drawString("ESC", getFontWidth(), getFontHeight() * (getMaxY() - 1));
        g.drawString("l", getFontWidth(), getFontHeight());
        g.drawString("b", getFontWidth(), getFontHeight() * 2);

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(": Back", getFontWidth() * 4, getFontHeight() * (getMaxY() - 1));
        g.drawString(": List Foodaz", getFontWidth() * 2, getFontHeight());
        g.drawString(": Buy Food", getFontWidth() * 2, getFontHeight() * 2);
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch (key) {
            case KeyEvent.VK_ESCAPE:
                backKey();
                return true;

            case KeyEvent.VK_L:
                listFoodKey();
                return true;

            case KeyEvent.VK_B:
                buyFoodKey();
                return true;
        }

        return false;
    }

    //<editor-fold desc="List Food">
    private AnimalFoodReal foodSelectedToDisplay;

    private void listFoodKey() {
        final Listed.ListedList food = new Listed.ListedList(MainData.mainData.getPlayerZoo().getAnimalFood());
        final Object cur = this;

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ListObjectsPanel(food, "food", selectFoodMethod, cur, MainData.mainData.getMapPanelManager().getLocalMapPanel()).open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Method selectFoodMethod;
    {
        try {
            selectFoodMethod = FoodMainPanel.class.getDeclaredMethod("selectFood", AnimalFoodReal.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void selectFood(AnimalFoodReal foodSelectedToDisplay) {
        this.foodSelectedToDisplay = foodSelectedToDisplay;
        openSelectedFood();
    }

    private void openSelectedFood() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ObjectPanel(foodSelectedToDisplay).open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Buy Food">
    private Method setBuyingFoodMethod;

    {
        try {
            setBuyingFoodMethod = FoodMainPanel.class.getDeclaredMethod("setBuyingFood", AnimalFoodReal.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Method setDeliveryZoneMethod;

    {
        try {
            setDeliveryZoneMethod = FoodMainPanel.class.getDeclaredMethod("setDeliveryZone", DeliveryZone.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private int trimToSize;

    private void buyFoodKey() {
        trimToSize = MainData.mainData.getControlPanelManager().getDescriptionBreadCrumbs().size();
        final Listed.ListedList deliveryZones = new Listed.ListedList(MainData.mainData.getPlayerZoo().getDeliveryZones());
        final Object cur = this;

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ListObjectsPanel(deliveryZones, "delivery zone", setDeliveryZoneMethod, cur, MainData.mainData.getMapPanelManager().getCurrentMapPanel()).open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //TODO: Allow to complete in any order?

    private DeliveryZone deliveryZone;

    public void setDeliveryZone(DeliveryZone deliveryZone) {
        this.deliveryZone = deliveryZone;
        setBuyingFood(new AnimalFoodReal(AnimalFoodReal.icon, 10000));
    }

    private AnimalFoodReal foodBuying;

    private Method setBuyAmountMethod;
    {
        try {
            setBuyAmountMethod = FoodMainPanel.class.getDeclaredMethod("setBuyAmount", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setBuyingFood(AnimalFoodReal foodBuying) {
        this.foodBuying = foodBuying;

        final Object cur = this;

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new InputPanel(cur, setBuyAmountMethod, "amount to buy", NumberFormat.getInstance(), InputPanel.getNumbersOnlyInstance()).open(); //TODO: make a panel that allows to input text and make different allowed character sets. Use a JTextBox or whatever
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private double buyAmount;

    public void setBuyAmount(String buyAmount) {
        this.buyAmount = Double.parseDouble(buyAmount);
        buyFood();
    }
    private void buyFood() {
        MainData.mainData.getControlPanelManager().trimBreadCrumbs(trimToSize);
        final Zoo playerZoo = MainData.mainData.getPlayerZoo();
        final double money = playerZoo.getMoney();
        foodBuying = foodBuying.split(buyAmount);
        Tick.removeFromUpdateList(foodBuying);
        Entity.entities.remove(foodBuying);
        final double price = foodBuying.getPrice() * foodBuying.getAmount();
        if (money >= price) {
            playerZoo.getDeliveries().add(new Delivery("Animal Delivery", 100, 1, foodBuying, deliveryZone));
            playerZoo.addMoney(-price);
        } else {
            NotificationManager manager = MainData.mainData.getNotificationManager();
            manager.addNotification(manager.getInsufficientFunds(), price - money, "buy this animal");
        }
    }
    //</editor-fold>
}