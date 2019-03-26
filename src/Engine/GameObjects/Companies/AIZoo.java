package Engine.GameObjects.Companies;

import CustomMisc.Tree;
import Engine.GameObjects.Entities.Items.AnimalFoodReal;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import Engine.GameObjects.Entities.Moveable.People.Employee;
import Engine.GameObjects.MapSpot;
import Engine.Logic.AnimalHolder;
import Engine.Logic.AnimalSpecies;
import Engine.Logic.GameObjectHolder;
import Engine.Logic.MainData;
import XML.FileLocations;
import XML.StorageClasses.StorageXML;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import static Engine.GameObjects.Entities.Items.AnimalFoodType.foods;

public class AIZoo extends Zoo {

    private static final String XML_NODE_NAME = "AIZoo";

    public static GameObjectHolder<AIZoo> aiZoos = new GameObjectHolder<>();

    //<editor-fold desc="Initialization">
    public AIZoo(String name, Double money, MapSpot location, AnimalHolder animals, GameObjectHolder<AnimalFoodReal> animalFood, GameObjectHolder<Employee> employees) {
        super(name, money, location, animals, animalFood, employees, null);

        if (animals == null) {
            createAnimals();
        }

        if (animalFood == null) {
            createFoods();
        }

        aiZoos.add(this);
    }

    //<editor-fold desc="Creation">
    private String createName() {
        return "Zoo";
    }

    private double createMoney() {
        return MainData.mainData.getRandom().nextGaussian() * 1000000 + 5000000;
    }

    private void createAnimals() {
        AnimalHolder animals = new AnimalHolder(new GameObjectHolder<>());

        int numOfAnimals = (int) MainData.mainData.getRandom().nextGaussian() * 5 + 10;
        if (numOfAnimals < 5) {
            numOfAnimals = 5;
        }

        ArrayList<String> species = new ArrayList<>(AnimalSpecies.animals.keySet());

        for (int i = 0; i < numOfAnimals; i++) {
            String animalSpecies = species.get(MainData.mainData.getRandom().nextInt(species.size()));
            animals.addAnimal(new AnimalReal(null, "generatedAnimal", (double) MainData.mainData.getRandom().nextInt(1000), null, animalSpecies, this));
        }

        setAnimals(animals);
    }

    private void createFoods() {
        GameObjectHolder<AnimalFoodReal> returnFoods = new GameObjectHolder<>();

        double amountOfFoodNeeded = 0d;

        for (AnimalReal animal : getAnimals().getAllAnimals()) {
            amountOfFoodNeeded += animal.getSpecies().getHungerRate() * 12;
        }

        ArrayList<String> foodList = new ArrayList<>(foods.keySet());
        String foodType = foodList.get(MainData.mainData.getRandom().nextInt(foodList.size()));

        returnFoods.add(new AnimalFoodReal(null, amountOfFoodNeeded, foodType, this, MainData.mainData.getRandom().nextGaussian() / 40 + .25));

        setAnimalFood(returnFoods);
    }

    //</editor-fold>
    //</editor-fold>
    public static void createAnimalZoos(double mean, double standardDeviation, int min, int max) throws CloneNotSupportedException {
        int numberOfZoos = (int) (MainData.mainData.getRandom().nextGaussian() * standardDeviation + mean);
        if (numberOfZoos < min) {
            numberOfZoos = min;
        } else if (numberOfZoos > max) {
            numberOfZoos = max;
        }

        for (int i = 0; i < numberOfZoos; i++) {
            new AIZoo(null, null, null, null, null, null);
        }
    }

    //<editor-fold desc="Save and Load">
    public AIZoo(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();
        head.put("nodeName", XML_NODE_NAME);
    }

    public void fromStorage(Tree<StorableForm> storageTree) {
        StorableForm storedData = storageTree.getHead();

        Method method = null;
        try {
            method = StorableForm.class.getDeclaredMethod("getNodeName");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void loadAnimalZoos() throws CloneNotSupportedException {
        new AnimalZooStorage().load();
    }

    public static void saveAnimalZoos() {
        new AnimalZooStorage().save();
    }

    private static class AnimalZooStorage extends StorageXML {
        AnimalZooStorage() {

        }

        public void load() {
            CopyOnWriteArrayList<Tree<StorableForm>> data = super.load(FileLocations.animalZoos);
            for (Tree<StorableForm> dataTree : data){
                for (Tree<StorableForm> zooTree : dataTree.getChildren().values()) {
                    if (zooTree.getHead().getNodeName().equals(XML_NODE_NAME)) {
                        new AIZoo(zooTree);
                    }
                }
            }
        }

        public void save() {
            Vector<Tree<StorableForm>> data = new Vector<>();
            for (AIZoo zoo : aiZoos) {
                data.add(zoo.toStorage());
            }
            super.save(FileLocations.animalZoos, data);
        }
    }
    //</editor-fold>
}
