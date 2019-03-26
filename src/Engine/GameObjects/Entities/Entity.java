package Engine.GameObjects.Entities;

import CustomMisc.Tree;
import Engine.GameObjects.GameObject;
import Engine.GameObjects.Tiles.*;
import Engine.Logic.GameObjectHolder;
import XML.StorageClasses.ObjectID;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;

public class Entity extends GameObject implements IHasLocation, Zonable {

    public static GameObjectHolder<Entity> entities = new GameObjectHolder<>();

    Tile location;
    Image icon;
    Double movementModifier = 1d;
    Zone zone;
    int displayPriority;
    boolean displayIfCovered;


    //<editor-fold desc="Opening">
    private boolean canOpen = false;

    public boolean getCanOpen() {
        return canOpen;
    }

    public void setCanOpen(boolean canOpen) {
        this.canOpen = canOpen;
    }
    //</editor-fold>

    //<editor-fold desc="Carrying">
    private HashSet<Entity> carrying = new HashSet<>();

    public HashSet<Entity> getCarrying() {
        return carrying;
    }

    public void pickUp(Entity object) {
        carrying.add(object);
    }

    public void drop(Entity object) {
        carrying.remove(object);
    }
    //</editor-fold>

    public void setMovementModifier(double movementModifier) {
        this.movementModifier = movementModifier;
    }

    public Double getMovementModifier() {
        return movementModifier;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public Entity() {

    }

    public Entity(Tile location, Image icon, int displayPriority, boolean displayIfCovered) {
        super();
        this.location = location;
        if (location != null) {
            location.setHasChanged(true);
        }
        this.icon = icon;
        this.displayPriority = displayPriority;
        this.displayIfCovered = displayIfCovered;
        entities.add(this);
    }


    @Override
    public Zone getZone() {
        return zone;
    }

    @Override
    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Tile getLocation() {
        return location;
    }

    public void setLocation(Tile location) {
        if (this.location != null) {
            this.location.setHasChanged(true);
        }
        this.location = location;
        if (location != null) {
            location.setHasChanged(true);
        }
    }

    @Override
    public Coordinate getCoordinate() {
        return location.getCoordinate();
    }

    public Entity(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);
    }

    public int getDisplayPriority() {
        return displayPriority;
    }

    public boolean isDisplayIfCovered() {
        return displayIfCovered;
    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {
    }

    @Override
    public void assignReferences(Tree<StorableForm> storageTree) {
        super.assignReferences(storageTree);
        StorableForm head = storageTree.getHead();

        location = (Tile) ObjectID.getObject(Integer.parseInt(head.get("location")));
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        super.fillStorageTree(storageTree);
        StorableForm head = storageTree.getHead();
        head.put("nodeName", "Entity");

        head.put("location", String.valueOf(ObjectID.getObjectID(location)));
    }

    public void deleteObject() {
        super.deleteObject();
        entities.remove(this);
        setLocation(null);
    }

    @Override
    public void update() {
        //TODO: what if cant carry anymore
        if (carrying.size() != 0) {
            Iterator<Entity> iterator = carrying.iterator();
            while (iterator.hasNext()) {
                iterator.next().setLocation(getLocation());
            }
        }
    }
}
