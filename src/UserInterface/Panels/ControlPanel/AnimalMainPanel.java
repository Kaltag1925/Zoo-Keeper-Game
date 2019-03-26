package UserInterface.Panels.ControlPanel;

import Engine.GameObjects.Companies.Zoo;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import Engine.GameObjects.Tiles.DeliveryZone;
import Engine.Logic.Delivery;
import Engine.Logic.MainData;
import Engine.Logic.NotificationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnimalMainPanel extends ControlPanel {

    public AnimalMainPanel() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        g.drawString("a", getFontWidth(), getFontHeight());
        g.drawString("b", getFontWidth(), getFontHeight() * 2);
        g.drawString("ESC", getFontWidth(), getFontHeight() * (getMaxY() - 1));

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(": All Animals", getFontWidth() * 2, getFontHeight());
        g.drawString(": Buy Animals", getFontWidth() * 2, getFontHeight() * 2);
        g.drawString(": Back", getFontWidth() * 4, getFontHeight() * (getMaxY() - 1));
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch(key) {
            case KeyEvent.VK_ESCAPE :
                backKey();
                return true;

            case KeyEvent.VK_A:
                listAnimalsKey();
                return true;

            case KeyEvent.VK_B:
                buyKey();
                return true;
        }

        return false;
    }

    //<editor-fold desc="Select Animal">
    private Method setSelectedAnimal;

    {
        try {
            setSelectedAnimal = AnimalMainPanel.class.getDeclaredMethod("setAnimal", AnimalReal.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void listAnimalsKey() {
        Listed.ListedList animals = new Listed.ListedList(MainData.mainData.getPlayerZoo().getAnimals().getAllAnimals());
        ControlPanel cur = this;

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                   new ListObjectsPanel(animals, "animal", null, setSelectedAnimal, cur, MainData.mainData.getMapPanelManager().getCurrentMapPanel()).open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private AnimalReal animal;

    public void setAnimal(AnimalReal animal) {
        this.animal = animal;
        openAnimalInfo();
    }

    private void openAnimalInfo() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ObjectPanel(animal).open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Buy Animals">
    private Method setBuyingAnimalMethod;

    {
        try {
            setBuyingAnimalMethod = AnimalMainPanel.class.getDeclaredMethod("setBuyingAnimal", AnimalReal.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Method setDeliveryZoneMethod;

    {
        try {
            setDeliveryZoneMethod = AnimalMainPanel.class.getDeclaredMethod("setDeliveryZone", DeliveryZone.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private int trimToSize;

    private void buyKey() {
        trimToSize = MainData.mainData.getControlPanelManager().getDescriptionBreadCrumbs().size();
        Listed.ListedList deliveryZones = new Listed.ListedList(MainData.mainData.getPlayerZoo().getDeliveryZones());
        ControlPanel cur = this;

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ListObjectsPanel(deliveryZones, "delivery zone", null, setDeliveryZoneMethod, cur, MainData.mainData.getMapPanelManager().getCurrentMapPanel()).open();
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

        setBuyingAnimal(new AnimalReal(AnimalReal.icon));
    }

    private AnimalReal animalBuying;

    public void setBuyingAnimal(AnimalReal animalBuying) {
        this.animalBuying = animalBuying;
        buyAnimal();
    }

    private void buyAnimal() {
        MainData.mainData.getControlPanelManager().trimBreadCrumbs(trimToSize);
        Zoo playerZoo = MainData.mainData.getPlayerZoo();
        double money = playerZoo.getMoney();
        double price = animalBuying.getPrice();
        if (money >= price) {
            playerZoo.getDeliveries().add(new Delivery("Animal Delivery", 50, 1, animalBuying, deliveryZone));
            playerZoo.addMoney(-price);
        } else {
            NotificationManager manager = MainData.mainData.getNotificationManager();
            manager.addNotification(manager.getInsufficientFunds(), price - money, "buy this animal");
        }
    }
    //</editor-fold>
}
