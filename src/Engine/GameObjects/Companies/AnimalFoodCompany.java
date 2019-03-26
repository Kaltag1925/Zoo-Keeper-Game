package Engine.GameObjects.Companies;

import CustomMisc.RandomUtils;
import CustomMisc.SaveBuilder;
import CustomMisc.Tree;
import Engine.GameObjects.Entities.Items.AnimalFoodReal;
import Engine.GameObjects.Entities.Items.AnimalFoodType;
import Engine.Logic.MainData;
import XML.StorageClasses.AStorableObject;
import XML.FileLocations;
import XML.StorageClasses.StorageXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

//TODO: create Company Class

public class AnimalFoodCompany extends AStorableObject {

    private String name;
    private Integer id;
    private double distanceFromZoo;
    private ArrayList<String> specializedFoods;
    private HashMap<Integer, AnimalFoodReal> foods = new HashMap<>();
    private double maxSpoilage;
    private HashMap<String, HashMap<String, Double>> foodInformation = new HashMap<>();
    //TODO: Change maxFoodStored based off of demand, only once other consumers are made
    //TODO: Change foodCriticalLevel based off of consumption rate
    private int deliveryRoadSpeed = 70;
    private double deliveryProcessDays = 1d;

    private final static int maxDistance = 3001;

    public static HashMap<Integer, AnimalFoodCompany> companies = new HashMap<>();

    //<editor-fold desc="Initialization">
    public AnimalFoodCompany(String name, Double distanceFromZoo, boolean createFood) {
        while(isRunning){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isRunning = true;
        createID();
        isRunning = false;

        companies.put(id, this);

        this.name = name;

        if (distanceFromZoo == null) {
            this.distanceFromZoo = createDistance();
        } else {
            this.distanceFromZoo = distanceFromZoo;
        }

        specializedFoods = createSpecializedFoods();

        maxSpoilage = createMaxSpoilage();

        if (createFood) {
            createStartingFood();
        }
    }

    //<editor-fold desc="Creation Methods">
    //<editor-fold desc="Identification">
    private volatile static boolean isRunning = false;
    private volatile static int nextAnimalFoodCompanyID;

    public static void setNextAnimalFoodCompanyID(int id) {
        nextAnimalFoodCompanyID = id;
    }

    public static int getNextAnimalFoodCompanyID() {
        return nextAnimalFoodCompanyID;
    }

    private synchronized void createID() {
        id = nextAnimalFoodCompanyID;
        nextAnimalFoodCompanyID++;
    }
    //</editor-fold>

    private Double createDistance(){
        int probability = maxDistance - 1;
        Double distance = 0.0;
        while (RandomUtils.isBetweenDualInclusive(MainData.mainData.getRandom().nextInt(maxDistance), 0, probability)){
            probability--;
            distance++;
        }
        return distance;
    }

    //<editor-fold desc="Food">
    private ArrayList<String> createSpecializedFoods() {
        ArrayList<String> returnFoods = new ArrayList<>();
        long numOfFoods = Math.round(MainData.mainData.getRandom().nextGaussian() + 3);
        if (numOfFoods < 1) {
            numOfFoods = 1;
        } else if (numOfFoods > 6) {
            numOfFoods = 6;
        }

        Vector<String> foodTypes = new Vector<>(AnimalFoodType.foods.keySet());

        for (long i = 0; i < numOfFoods; i++); {
            int nextRandom = MainData.mainData.getRandom().nextInt(foodTypes.size());
            String next = foodTypes.get(nextRandom);
            returnFoods.add(next);

            HashMap<String, Double> nextInformation = new HashMap<String, Double>();
            nextInformation.put("maxFoodStored", createMaxFoodStored());
            nextInformation.put("foodSourceDistance", createDistance());
            nextInformation.put("foodCriticalLevel", nextInformation.get("maxFoodStored") / 2);
            foodInformation.put(next, nextInformation);
            foodTypes.remove(next);
        }

        return returnFoods;
    }

    private double createMaxFoodStored() {
        return MainData.mainData.getRandom().nextInt(50) * 50;
    }

    private void createStartingFood() {
        for (int i = 0; i < 10; i++) {
            if (RandomUtils.isBetweenDualInclusive(MainData.mainData.getRandom().nextDouble(), 0, 0.5)) {
                double spoilage = MainData.mainData.getRandom().nextDouble() % maxSpoilage;
                double amount = MainData.mainData.getRandom().nextInt(50);
                String foodType;
                if(specializedFoods.size() != 1) {
                    foodType = specializedFoods.get(MainData.mainData.getRandom().nextInt(specializedFoods.size()));
                } else {
                    foodType = specializedFoods.get(0);
                }

//                AnimalFoodReal food = new AnimalFoodReal(foodType, id, name, amount, foods, null, true);
            }
        }
    }
    //</editor-fold>


    private double createMaxSpoilage() {
        return MainData.mainData.getRandom().nextGaussian() / 40 + .25;
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Get Methods">
    public String getName() {
        return name;
    }

    public Integer getID() {
        return id;
    }

    public double getDistanceFromZoo() {
        return distanceFromZoo;
    }

    public ArrayList<String> getSpecializedFoods() {
        return specializedFoods;
    }

    public HashMap<Integer, AnimalFoodReal> getFoods() {
        return foods;
    }

    public double getMaxSpoilage() {
        return maxSpoilage;
    }

    public static HashMap<Integer, AnimalFoodCompany> getCompanies() {
        return companies;
    }
    //</editor-fold>

    //<editor-fold desc="Save and Load">
    private AnimalFoodCompany(Tree<StorableForm> storageTree) {
        fromStorage(storageTree);
        companies.put(id, this);
    }

    public void fillStorageTree(Tree<StorableForm> storageTree) {
//        attributes.put("name", name);
//        attributes.put("id", String.valueOf(id));
//        attributes.put("distanceFromZoo", String.valueOf(distanceFromZoo));
//
//        StringBuilder specializedFoodsSaveForm = new StringBuilder();
//        for (String food : specializedFoods) {
//            specializedFoodsSaveForm.append(food);
//            specializedFoodsSaveForm.append(",");
//        }
//        attributes.put("specializedFoods", specializedFoodsSaveForm.toString());
//
//        attributes.put("maxSpoilage", String.valueOf(maxSpoilage));
    }

    public Tree<StorableForm> createStorageObject() {
        StorableForm attributes = new StorableForm();
        attributes.put("nodeName", "Company");
        Tree<StorableForm> animalFoodCompanyTree = new Tree<>(attributes);

        attributes.put("name", name);
        attributes.put("id", String.valueOf(id));
        attributes.put("distanceFromZoo", String.valueOf(distanceFromZoo));

        Tree<StorableForm> specializedFoodsTree = SaveBuilder.build(specializedFoods, "SpecializedFoods");
        animalFoodCompanyTree.addTreeChild(specializedFoodsTree, true);

        attributes.put("maxSpoilage", String.valueOf(maxSpoilage));

        StorableForm foodsHead = new StorableForm();
        foodsHead.put("nodeName", "Foods");
        Tree<StorableForm> foodsHeadTree = new Tree<>(foodsHead);
        for (AnimalFoodReal food : foods.values()) {
//            foodsHeadTree.addTreeChild(food.createStorageObject(), true);
        }
        animalFoodCompanyTree.addTreeChild(foodsHeadTree, true);

        return animalFoodCompanyTree;
    }

    public void fromStorage(Tree<StorableForm> storedDataTree) {
        StorableForm storedData = storedDataTree.getHead();
        name = storedData.get("name");
        id = Integer.parseInt(storedData.get("id"));
        distanceFromZoo = Double.parseDouble(storedData.get("distanceFromZoo"));

        Tree<StorableForm> specializedFoodsTree = storedDataTree.findFirstTreeFromMethod("SpecializedFoods", StorableForm::getNodeName);
        specializedFoods = SaveBuilder.read(specializedFoodsTree);

        maxSpoilage = Double.parseDouble(storedData.get("maxSpoilage"));
    }

    public static void createAnimalFoodCompanies(int max) {
        for (int i = 0; i < max; i++) {
            if (RandomUtils.isBetweenDualInclusive(MainData.mainData.getRandom().nextDouble(), 0, 0.2)) {
                new AnimalFoodCompany("Food Company" + i, null, true);
            }
        }
    }

    public static void loadAnimalFoodCompanies() {
        new AnimalFoodCompanyStorage().load();
    }

    public static void saveAnimalFoodCompanies() {
        new AnimalFoodCompanyStorage().save();
    }

    private static class AnimalFoodCompanyStorage extends StorageXML {
        AnimalFoodCompanyStorage() {

        }

        public void load(){
            CopyOnWriteArrayList<Tree<StorableForm> > data = super.load(FileLocations.animalFoodCompanies);
            for (Tree<StorableForm>  dataTree : data){
                for (Tree<StorableForm> foodCompanyTree : dataTree.getChildren().values()) {
                    if (foodCompanyTree.getHead().getNodeName().equals("Company")) {
                        new AnimalFoodCompany(foodCompanyTree);
                    }
                }
            }
        }

        public void save() {
            Vector<Tree<StorableForm>> data = new Vector<>();
            for (AnimalFoodCompany animalFoodCompany : companies.values()) {
                data.add(animalFoodCompany.createStorageObject());
            }
            super.save(FileLocations.animalFoodCompanies, data);
        }
    }
    //</editor-fold>
}
