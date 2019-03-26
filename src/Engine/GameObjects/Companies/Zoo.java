package Engine.GameObjects.Companies;

import CustomMisc.StringCopy;
import CustomMisc.Tree;
import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Entities.Items.AnimalFoodReal;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import Engine.GameObjects.Entities.Moveable.People.Employee;
import Engine.GameObjects.Entities.Moveable.People.Visitor;
import Engine.GameObjects.MapSpot;
import Engine.GameObjects.Tiles.*;
import Engine.Logic.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Zoo extends Company implements IAnimalOwner, IAnimalFoodOwner, Time.DayListener {

    private static final String XML_NODE_NAME = "Zoo";

    private AnimalHolder animals;
    private GameObjectHolder<AnimalFoodReal> animalFood;
    private GameObjectHolder<Employee> employees;
    private GameObjectHolder<Visitor> visitors;
    private GameObjectHolder<AnimalExhibit> animalExhibits = new GameObjectHolder<>();
    private GameObjectHolder<DeliveryZone> deliveryZones = new GameObjectHolder<>();

    //<editor-fold desc="ViewingZone">
    private GameObjectHolder<ViewingZone> viewingZones = new GameObjectHolder<>();

    public GameObjectHolder<ViewingZone> getViewingZones() {
        return viewingZones;
    }
    //</editor-fold>

    private GameObjectHolder<Delivery> deliveries = new GameObjectHolder<>();

    private Tile entrance;

    private double admissionPrice = 10.25;


    public Zoo(String name, Double money, MapSpot location, AnimalHolder animals, GameObjectHolder<AnimalFoodReal> animalFood, GameObjectHolder<Employee> employees, Tile entrance) {
        super(name, money, location);

        this.animals = animals;
        this.animalFood = animalFood;
        this.employees = employees;
        this.entrance = entrance;
        visitors = new GameObjectHolder<>();

        Time.addDayListener(this);
    }

    @Override
    public AnimalHolder getAnimals() {
        return animals;
    }

    public List<AnimalReal> getViewableAnimals() {
        List<AnimalReal> animals = new ArrayList<>();

        List<AnimalExhibit> exhibits = new ArrayList<>(animalExhibits);
        for (AnimalExhibit exhibit : exhibits) {
            if (exhibit.getViewingArea() != null) {
                animals.addAll(exhibit.getAnimals());
            }
        }

        return animals;
    }

    public GameObjectHolder<AnimalFoodReal> getAnimalFood() {
        return animalFood;
    }

    public GameObjectHolder<Employee> getEmployees() {
        return employees;
    }

    public Tile getEntrance() {
        return entrance;
    }

    public GameObjectHolder<Visitor> getVisitors() {
        return visitors;
    }

    public double getAdmissionPrice() {
        return admissionPrice;
    }

    public GameObjectHolder<AnimalExhibit> getAnimalExhibits() {
        return animalExhibits;
    }

    public GameObjectHolder<DeliveryZone> getDeliveryZones() {
        return deliveryZones;
    }

    public GameObjectHolder<Delivery> getDeliveries() {
        return deliveries;
    }

    //<editor-fold desc="Hauling Orders">
    private ArrayList<HaulingOrder> haulingJobs = new ArrayList<>();

    public ArrayList<HaulingOrder> getHaulingJobs() {
        return haulingJobs;
    }

    public void addHaulingJob(Entity haul, Zone destination, double amount) {
        haulingJobs.add(new HaulingOrder(haul, destination, amount));
    }

    public void addHaulingJob(Entity haul, Zone destination) {
        haulingJobs.add(new HaulingOrder(haul, destination, null));
    }

    public class HaulingOrder {
        private Entity haul;
        private Zone destination;
        private Double amount;

        public HaulingOrder(Entity haul, Zone destination, Double amount) {
            this.haul = haul;
            this.destination = destination;
            this.amount = amount;
        }

        public Entity getHaul() {
            return haul;
        }

        public Zone getDestination() {
            return destination;
        }

        public Double getAmount() {
            return amount;
        }
    }
    //</editor-fold>

    ///////

    public void setAnimals(AnimalHolder animals) {
        this.animals = animals;
    }

    public void setAnimalFood(GameObjectHolder<AnimalFoodReal> animalFood) {
        this.animalFood = animalFood;
    }

    public void setEmployees(GameObjectHolder<Employee> employees) {
        this.employees = employees;
    }

    public void setEntrance(Tile entrance) {
        this.entrance = entrance;
    }

    public void setVisitors(GameObjectHolder<Visitor> visitors) {
        this.visitors = visitors;
    }

    public void setAdmissionPrice(double admissionPrice) {
        this.admissionPrice = admissionPrice;
    }

    public void addZone(Zone zone) {
        Class<? extends Zone> zoneClass = zone.getClass();

        if (AnimalExhibit.class.isAssignableFrom(zoneClass)) {
            animalExhibits.add((AnimalExhibit) zone);
        } else if (DeliveryZone.class.isAssignableFrom(zoneClass)) {
            deliveryZones.add((DeliveryZone) zone);
        } else if (ViewingZone.class.isAssignableFrom(zoneClass)) {
            viewingZones.add((ViewingZone) zone);
        }
    }

    @Override
    public double getMaxSpoilage() {
        return 0;
    }

    public void buyAnimal(AnimalReal animal) throws InsufficientFundsException {
        if (getMoney() < animal.getPrice()) {
            throw new InsufficientFundsException( this, animal.getPrice() - getMoney());
        }

        addMoney(-animal.getPrice());
        animals.addAnimal(animal);
    }

    public class InsufficientFundsException extends Exception
    {
        public InsufficientFundsException(HasMoney thrower, double amount) {
            super(thrower + "has insufficient funds, needs " + amount);
        }
    }

    public HashMap<String, Integer> getAnimalSpeciesListAndFrequency() {
        HashMap<String, Integer> returnMap = new HashMap<>();

        for (AnimalReal animal : animals.getAllAnimals()) {
            ArrayList<String> animalTypes = new ArrayList<>();
            Tree<StringCopy> base = AnimalSpecies.relationTree.findFirstTreeFromMethod(animal.getSpeciesName(), StringCopy::getInfo);
            ArrayList<Tree<StringCopy>> parents = base.getParents();
            for (Tree<StringCopy> parent : parents) {
                animalTypes.add(parent.getHead().getInfo());
            }

            for (String type : animalTypes) {
                if (returnMap.containsKey(type)) {
                    returnMap.replace(type, returnMap.get(type) + 1);
                } else {
                    returnMap.put(type, 1);
                }
            }
        }

        return returnMap;
    }

    public void newDay() {
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee next = iterator.next();
            addMoney(-next.getSalary());
        }
    }

    public Zoo(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);
    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        super.fillStorageTree(storageTree);
        StorableForm head = storageTree.getHead();
        head.put("nodeName", XML_NODE_NAME);

        StorableForm animalsHead = new StorableForm();
        animalsHead.put("nodeName", "Animals");
        Tree<StorableForm> animalsTree = new Tree<>(animalsHead);
        for (AnimalReal animal : animals.getAllAnimals()) {
            animalsTree.addTreeChild(animal.toStorage(), false);
        }
        storageTree.addTreeChild(animalsTree, false);

        StorableForm foodHead = new StorableForm();
        animalsHead.put("nodeName", "Foods");
        Tree<StorableForm> foodsTree = new Tree<>(animalsHead);
        for (AnimalFoodReal food : animalFood) {
            foodsTree.addTreeChild(food.toStorage(), false);
        }
        storageTree.addTreeChild(foodsTree, false);
    }

    public void assignReferences(Tree<StorableForm> storageTree) {
        super.assignReferences(storageTree);
        StorableForm head = storageTree.getHead();
    }
}
