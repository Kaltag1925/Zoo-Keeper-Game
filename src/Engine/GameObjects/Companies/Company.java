package Engine.GameObjects.Companies;

import CustomMisc.Tree;
import Engine.GameObjects.GameObject;
import Engine.GameObjects.MapSpot;
import XML.StorageClasses.ObjectID;

import java.util.Vector;

public class Company extends GameObject implements HasMoney {

    private static final String XML_NODE_NAME = "Company";

    private String name;
    private double money;
    private MapSpot location;

    public static Vector<Company> companies = new Vector<>();

    public Company(String name, Double money, MapSpot location) {
        super();
        this.name = name;
        this.money = money;
        this.location = location;

        companies.add(this);
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public MapSpot getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void addMoney(double money) {
        this.money += money;
    }

    public void subtractMoney(double money) {
        this.money -= money;
    }

    public void setLocation(MapSpot location) {
        this.location = location;
    }

    public Company(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);
        companies.add(this);
    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();

        name = head.get("name");
        money = Double.parseDouble(head.get("money"));
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        super.fillStorageTree(storageTree);
        StorableForm head = storageTree.getHead();
        head.put("nodeName", XML_NODE_NAME);

        head.put("name", name);
        head.put("money", String.valueOf(money));
        head.put("location", String.valueOf(ObjectID.getObjectID(location)));
    }

    public void assignReferences(Tree<StorableForm> storageTree) {
        super.assignReferences(storageTree);
        StorableForm head = storageTree.getHead();

        ObjectID.getObject(Integer.parseInt(head.get("location")));
    }
}
