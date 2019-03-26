package Engine.GameObjects.Entities.Items;

import CustomMisc.Tree;
import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Tiles.Tile;

import java.awt.*;

public abstract class Item extends Entity {

    private final static String XML_NODE_NAME = "Item";

    private double price;

    public double getPrice() {
        return price;
    }

    private double amount;

    public Item(Tile location, Image icon, Double amount) {
        super(location, icon, Tile.LOWEST_PRIORITY, true);

        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public abstract Item split(double amount);

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void subtractAmount(double subtract) {
        amount -= subtract;
    }

    public void addAmount(double add) {
        amount += add;
    }

    public abstract void use(Entity actor, Double amount);

    public Item(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);
    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();

        amount = Double.parseDouble(head.get("amount"));
    }

    @Override
    public void assignReferences(Tree<StorableForm> storageTree) {
        super.assignReferences(storageTree);
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        super.fillStorageTree(storageTree);
        StorableForm head = storageTree.getHead();
        head.put("nodeName", XML_NODE_NAME);

        head.put("amount", String.valueOf(amount));
    }
}
