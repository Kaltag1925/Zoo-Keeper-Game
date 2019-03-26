package Engine.GameObjects.Entities.Items;

import CustomMisc.IDayListener;
import CustomMisc.Tree;
import Engine.GameObjects.Companies.IAnimalFoodOwner;
import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import Engine.GameObjects.Tiles.DeliveryZone;
import Engine.GameObjects.Tiles.Tile;
import Engine.Logic.Deliverable;
import Engine.Logic.MainData;
import Engine.Logic.Ticks.Tick;
import UserInterface.Panels.ControlPanel.Listed;
import UserInterface.Panels.DescriptionPanel.DescriptionPanel;
import XML.StorageClasses.ObjectID;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimalFoodReal extends Item implements IDayListener, Listed, Deliverable {

    private static final String XML_NODE_NAME = "AnimalFood";

    private AnimalFoodType type;
    private IAnimalFoodOwner owner; //TODO: make this a global thing for items?
    private double spoiledPercent;
    private double temperature = 32d; //TODO: Will temperature be provided by the tile? Since Closed in areas will have the same temperature make tile groups
    private boolean itemInUse = false;

    public static Image icon = null;
    static {
        try {
            icon = ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "food.png"));
        } catch (IOException e) {
        }
    }

    private static final double REFRIGERATION_SPOILAGE_MULTIPLIER = 0.6;
    private static final double FREEZING_SPOILAGE_MULTIPLIER = 0.35;

    @Override
    public AnimalFoodReal split(double amount) {
        this.addAmount(-amount);
        final AnimalFoodReal food = new AnimalFoodReal(getLocation(), getIcon(), amount);
        MainData.mainData.getPlayerZoo().getAnimalFood().add(food);
        return food;
    }

    public AnimalFoodReal(Image icon, double amount) {
        super(null, icon, amount);
        Tick.removeFromUpdateList(this);
        Entity.entities.remove(this);
    }

    public void use(Entity actor, Double amount) {
        AnimalReal animal = (AnimalReal) actor;
        animal.setHunger(animal.getHunger() + amount);
        addAmount(-amount);
    }

    //<editor-fold desc="Deliverable">
    @Override
    public void deliver(DeliveryZone zone) {
        List<Tile> tiles = zone.getTiles();
        setLocation(tiles.get(MainData.mainData.getRandom().nextInt(tiles.size())));
        Entity.entities.add(this);
        Tick.addToUpdateList(this);
        MainData.mainData.getPlayerZoo().getAnimalFood().add(this);
    }
    //</editor-fold>

    //<editor-fold desc="Initialization">
    public AnimalFoodReal(Tile location, Double amount, String type, IAnimalFoodOwner owner, Double spoiledPercent) {
        super(location, null, amount);
        this.type = AnimalFoodType.foods.get(type);
        this.owner = owner;

        if (spoiledPercent == null) {
            this.spoiledPercent = createSpoiledPercent();
        } else {
            this.spoiledPercent = spoiledPercent;
        }

        IDayListener.Event.addDayListener(this, 0);
    }

    //<editor-fold desc="Creation Methods">
    private double createSpoiledPercent() {
        double max = owner.getMaxSpoilage();
        return MainData.mainData.getRandom().nextDouble() % max;
    }
    //</editor-fold>
    //</editor-fold>


    //<editor-fold desc="Listed">
    @Override
    public String getListName() {
        return "Food";
    }

    @Override
    public boolean hiddenOnMap() {
        return false;
    }

    @Override
    public ControlPanelFormat getControlPanelControls() {
        ControlPanelFormat controls = new ControlPanelFormat();
        return controls;
    }

    @Override
    public DescriptorsFormat getShortDescriptors() {
        DescriptorsFormat descriptors = new DescriptorsFormat();
        descriptors.put("Food Type", "Meat");
        descriptors.put("Temperature", String.valueOf(temperature));
        descriptors.put("Amount", String.valueOf(getAmount()));
        return descriptors;
    }

    @Override
    public void paintToLargeDescriptionPanel(DescriptionPanel panel, Graphics g) {

    }

    @Override
    public List<Tile> getTilesForList() {
        List<Tile> list = new ArrayList<>();
        list.add(getLocation());
        return list;
    }
    //</editor-fold>

    public AnimalFoodReal(Tile location, Image icon, double amount) {
        super(location, icon, amount);
    }

    //<editor-fold desc="Get Methods">
    public AnimalFoodType getType() {
        return type;
    }

    public String getTypeName() {
        return type.getName();
    }

    public IAnimalFoodOwner getOwner() {
        return owner;
    }

    public double getSpoiledPercent() {
        return spoiledPercent;
    }

    public boolean isSpoiled() {
        return spoiledPercent >= 1;
    }

    public boolean getItemInUse() {
        return itemInUse;
    }
    //</editor-fold>

    //<editor-fold desc="Set Methods">
    public void switchItemInUse() {
        itemInUse = !itemInUse;
    }

    public double subtractForFeeding(double subtract) {
        double notFeed = getAmount() - subtract;
        double actuallyFed = subtract;
        if (!(notFeed >= 0)) {
            actuallyFed = subtract + notFeed;
            deleteObject();
        } else {
            subtractAmount(subtract);
        }

        return actuallyFed;
    }
    //</editor-fold>

//    private void deleteObject() {
//        owner.getAnimalFood().remove(this);
//    }

    public void newDay() {
        if (temperature <= 32d) {
            spoiledPercent += type.getSpoilageRate() * FREEZING_SPOILAGE_MULTIPLIER;
        } else if (temperature <= 65d) {
            spoiledPercent += type.getSpoilageRate() * REFRIGERATION_SPOILAGE_MULTIPLIER;
        } else {
            spoiledPercent += type.getSpoilageRate();
        }
    }

    public void combine(AnimalFoodReal food) {
        if (!food.owner.equals(owner)) {
            throw (new IllegalArgumentException(type + " with reference " + this + " is not owned by the same party as " + food.getType() + "with reference " + food));
        }
        if (!food.type.equals(type)) {
            throw (new IllegalArgumentException(type + " with reference " + this +  "does not equal" + food.getType() + " with reference " + food));
        }
        if (food.spoiledPercent != spoiledPercent) {
            throw (new IllegalArgumentException(type + " with reference " + this + " with spoilage of " + spoiledPercent + " does not equal " + food.getType() + " with reference " + food + " with spoilage of " + food.getSpoiledPercent()));
        }

        owner.getAnimalFood().remove(this);
        owner.getAnimalFood().remove(food);
        owner.getAnimalFood().add(new AnimalFoodReal(getLocation(), getAmount() + food.getAmount(), getTypeName(), owner, spoiledPercent));
    }

    //<editor-fold desc="Save and Load">
    public AnimalFoodReal(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        super.fillStorageTree(storageTree);
        StorableForm head = storageTree.getHead();
        head.put("nodeName", XML_NODE_NAME);

        head.put("type", type.getName());
        head.put("owner", String.valueOf(ObjectID.getObjectID(owner)));
        head.put("spoiledPercent", String.valueOf(spoiledPercent));
    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();

        type = AnimalFoodType.foods.get(head.get("type"));
        spoiledPercent = Double.parseDouble(head.get("spoiledPercent"));
    }

    @Override
    public void assignReferences(Tree<StorableForm> storageTree) {
        super.assignReferences(storageTree);
        StorableForm head = storageTree.getHead();


    }

//    private static class FoodRealStorage extends StorageXML {
//        FoodRealStorage() {
//
//        }
//
//        public void makeVisible(){
//            CopyOnWriteArrayList<Tree<StorableForm>> data = super.makeVisible(FileLocations.food);
//            for (Tree<StorableForm> dataTree : data) {
//                for (Tree<StorableForm> foodTree : dataTree.getChildren().values()) {
//                    if (foodTree.getHead().getNodeName().equals("Food")) {
//                        new AnimalFoodReal(foodTree, getOwnedFood());
//                    }
//                }
//            }
//        }
//
//        public void save() {
//            Vector<Tree<StorableForm>> data = new Vector<>();
//            for (AnimalFoodReal foodReal : getOwnedFood().values()) {
//                data.add(foodReal.createStorageObject());
//            }
//            super.save(FileLocations.food, data);
//        }
//    }
    //</editor-fold>
}

