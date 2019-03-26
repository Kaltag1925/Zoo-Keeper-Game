package Engine.Logic;

import Engine.Logic.Ticks.IRenderable;
import Engine.Logic.Ticks.Tick;

import java.text.MessageFormat;
import java.util.LinkedList;

public class NotificationManager implements IRenderable {


    //TODO: Make these create new Builders
    //<editor-fold desc="Need Zone Notification">
    private NotificationBuilder needZone;

    public NotificationBuilder getNeedZone() {
        return needZone;
    }
    //</editor-fold>

    //<editor-fold desc="Insufficient Funds Notification">
    private NotificationBuilder insufficientFunds;

    public NotificationBuilder getInsufficientFunds() {
        return insufficientFunds;
    }
    //</editor-fold>

    //<editor-fold desc="Object Bought Notification">
    private NotificationBuilder objectBought;

    public NotificationBuilder getObjectBought() {
        return objectBought;
    }
    //</editor-fold>

    //<editor-fold desc="Delivery Arrived Notification">
    private NotificationBuilder deliveryArrived;

    public NotificationBuilder getDeliveryArrived() {
        return deliveryArrived;
    }
    //</editor-fold>

    LinkedList<Notification> notificationList = new LinkedList<>();
    static final int TIME_BETWEEN_DISPLAYS = 5 * Tick.getGameHertz();
    int timeTillNextDisplay = 0;
    public Notification currentNotification;

    public NotificationManager() {
        Tick.addToRenderList(this);

        MessageFormat needZoneMF = new MessageFormat("Need {0} new {1}.");
        needZone = new NotificationBuilder(needZoneMF);

        MessageFormat insufFundsMF = new MessageFormat("Need {0, number, currency} more to {1}."); //TODO: Add choice format
        insufficientFunds = new NotificationBuilder(insufFundsMF);

        MessageFormat objBoughtMF = new MessageFormat("Bought {0} x{1}.");
        objectBought = new NotificationBuilder(objBoughtMF);

        MessageFormat deliArrivedMF = new MessageFormat("{0} has arrived.");
        deliveryArrived = new NotificationBuilder(deliArrivedMF);
    }

    public void addNotification(NotificationBuilder builder, Object... args) {
        notificationList.add(builder.build(args));
    }

    @Override
    public void render() {
        if (currentNotification == null) {
            if(notificationList.size() != 0) {
                currentNotification = notificationList.poll();
                timeTillNextDisplay = TIME_BETWEEN_DISPLAYS;
            }
        } else if (timeTillNextDisplay == 0) {
            if(notificationList.size() != 0) {
                currentNotification = notificationList.poll();
                timeTillNextDisplay = TIME_BETWEEN_DISPLAYS;
            } else {
                currentNotification = null;
            }
        } else {
            timeTillNextDisplay--;
        }

    }



    class NotificationBuilder {

        MessageFormat format;

        NotificationBuilder(MessageFormat format) {
            this.format = format;
        }

        Notification build(Object... args) {
            return new Notification(format.format(args));
        }
    }

    public class Notification {

        public String text;

        public Notification(String text) {
            this.text = text;
        }

        public String toString() {
            return text;
        }
    }
}
