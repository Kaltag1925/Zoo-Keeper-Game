package Engine.Logic;

import Engine.GameObjects.Tiles.DeliveryZone;
import Engine.GameObjects.Tiles.Tile;
import Engine.Logic.Ticks.IUpdateable;
import Engine.Logic.Ticks.Tick;
import UserInterface.Panels.ControlPanel.Listed;
import UserInterface.Panels.DescriptionPanel.DescriptionPanel;

import java.awt.*;
import java.util.List;

public class Delivery implements IUpdateable, Listed {

    private String name;
    private double distanceLeft;
    private double speed;
    private GameObjectHolder<Deliverable> delivering = new GameObjectHolder<>();
    private DeliveryZone zone;

    public Delivery(String name, double totalDistance, double speed, Deliverable delivering, DeliveryZone zone) {
        this.name = name;
        this.distanceLeft = totalDistance;
        this.speed = speed;
        this.delivering.add(delivering);
        this.zone = zone;

        Tick.addToUpdateList(this);
    }

    @Override
    public void update() {
        distanceLeft -= speed;
        if (distanceLeft <= 0) {
            for (Deliverable deliverable : delivering) {
                deliverable.deliver(zone);
            }
            Tick.removeFromUpdateList(this);
            NotificationManager notificationManager = MainData.mainData.getNotificationManager();
            notificationManager.addNotification(notificationManager.getDeliveryArrived(), name);
        }
    }

    public String getName() {
        return name;
    }

    public double getDistanceLeft() {
        return distanceLeft;
    }

    //<editor-fold desc="Listed">
    @Override
    public String getListName() {
        return name;
    }

    @Override
    public List<Tile> getTilesForList() {
       return null;
    }

    @Override
    public boolean hiddenOnMap() {
        return false;
    }

    @Override
    public DescriptorsFormat getShortDescriptors() {
        DescriptorsFormat descriptors = new DescriptorsFormat();

        descriptors.put("Name", name);
        descriptors.put("Distance", String.valueOf(distanceLeft));

        return descriptors;
    }

    @Override
    public ControlPanelFormat getControlPanelControls() {
        ControlPanelFormat controlPanelObjects = new ControlPanelFormat();
        return controlPanelObjects;
    }

//    //<editor-fold desc="Assign Exhibit">
//    private int trimToSizeAfterAssignExhibit;
//
//    private Method setExhibit;
//    {
//        try {
//            setExhibit = AnimalReal.class.getDeclaredMethod("exhibitToAssignAnimal", AnimalExhibit.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void assignToExhibitKey() {
//        ArrayList<Listed> exhibits = new ArrayList<>(MainData.mainData.getPlayerZoo().getAnimalExhibits());
//        Object cur = this;
//        trimToSizeAfterAssignExhibit = MainData.mainData.getControlPanelManager().getDescriptionBreadCrumbs().size();
//
//        try {
//            SwingUtilities.invokeAndWait(new Runnable() {
//                @Override
//                public void run() {
//                    new ListObjectsPanel(exhibits, "exhibit", null, setExhibit, cur, MainData.mainData.getMapPanelManager().getLocalMapPanel()).open();
//                }
//            });
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private AnimalExhibit setToExhibit;
//
//    public void exhibitToAssignAnimal(AnimalExhibit setToExhibit) {
//        this.setToExhibit = setToExhibit;
//
////        try {
////            SwingUtilities.invokeAndWait(new Runnable() {
////                @Override
////                public void run() {
////                    MainData.mainData.getDescriptionPanelManager().getAnimalDescriptionPanel().open(animal);;
////                }
////            });
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        } catch (InvocationTargetException e) {
////            e.printStackTrace();
////        }
//
//        setAnimalExhibit();
//    }
//
//    private void setAnimalExhibit() {
//        MainData.mainData.getControlPanelManager().trimBreadCrumbs(trimToSizeAfterAssignExhibit);
//        setExhibit(exhibit);
//        if (!exhibit.getTilesForList().contains(getLocation())) {
//            MainData.mainData.getPlayerZoo().addHaulingJob(this, exhibit);
//        }
//    }
    //</editor-fold>

    @Override
    public void paintToLargeDescriptionPanel(DescriptionPanel panel, Graphics g) {
        int fontWidth = panel.getFontWidth();
        int fontHeight = panel.getFontHeight();

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(name, fontWidth, fontHeight);

        g.setColor(Color.LIGHT_GRAY);
        String hungerString = "Distance: ";
        g.drawString(hungerString, fontWidth, fontHeight * 2);
        g.setColor(Color.GREEN);
        if(distanceLeft <= 0) {
            g.drawString("DELIVERED", fontWidth * (hungerString.length() + 1), fontHeight * 2);
        } else {
            g.drawString(String.valueOf(distanceLeft), fontWidth * (hungerString.length() + 1), fontHeight * 2);
        }
    }
    //</editor-fold>
}
