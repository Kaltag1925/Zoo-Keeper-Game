package Engine.GameObjects.Entities.Items;

import CustomMisc.StringCopy;
import CustomMisc.Tree;
import XML.StorageClasses.AStorableObject;
import XML.FileLocations;
import XML.StorageClasses.StorageXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimalFoodType extends AStorableObject {

    private static final String XML_NODE_NAME = "FoodType";

    private String name;
    private double spoilageRate = .15;
    private String diet;

    public static Tree<StringCopy> relationTree;
    public static HashMap<String, AnimalFoodType> foods = new HashMap<>();

    public String getName() {
        return name;
    }

    public double getSpoilageRate() {
        return spoilageRate;
    }

//    public static HashMap<String, AnimalFoodType> getFoods() {
//        return foods;
//    }

    public ArrayList<String> checkParents() {
        ArrayList<String> result = new ArrayList<>();
        result.add(getName());
        checkParentsOfParents(getName(), result);
        return result;
    }

    private void checkParentsOfParents(String name, ArrayList<String> result) {
        Tree<StringCopy> parent = relationTree.findFirstTreeFromMethod(name, StringCopy::getInfo).getParent();
        if (parent != null) {
            result.add(parent.getHead().getInfo());
            checkParentsOfParents(parent.getHead().getInfo(), result);
        }
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        AnimalFoodType part = (AnimalFoodType) obj;

        if (!(getName().equals(part.getName()))) {
            return false;
        }

        if (getSpoilageRate() != part.getSpoilageRate()) {
            return false;
        }

        return true;
    }

    //<editor-fold desc="Save and Load">
    public AnimalFoodType(Tree<StorableForm> storableForm, String diet) {
        fromStorage(storableForm);
        this.diet = diet;
        foods.put(getName(), this);
    }

    public Tree<StorableForm> createStorageObject() {
        return null;
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {

    }

    @Override
    public void fromStorage(Tree<StorableForm> storedDataTree) {
        StorableForm storedData = storedDataTree.getHead();
        name = storedData.get("type");
    }

    public static void loadFoodTypes(){
        new FoodTypeStorage().load();
    }

    private static class FoodTypeStorage extends StorageXML {

        FoodTypeStorage() {

        }

        public void load() {
            CopyOnWriteArrayList<Tree<StorableForm>> tree = super.load(FileLocations.foodTypes);
            for (Tree<StorableForm> dataTree : tree) {
                relationTree = goTroughTree(dataTree, null);
            }
        }

        private Tree<StringCopy> goTroughTree(Tree<StorableForm> tree, String diet) {
            Tree<StringCopy> returnTree;
            StorableForm head = tree.getHead();
            if (head.get("type") != null) {
                returnTree = new Tree<>(new StringCopy(head.get("type")));
                if (head.get("diet") != null) {
                    diet = head.get("diet");
                }
                if (tree.getChildren().size() != 0) {
                    for (Tree<StorableForm> childTree : tree.getChildren().values()) {
                        returnTree.addTreeChild(goTroughTree(childTree, diet), true);
                    }
                } else {
                    new AnimalFoodType(tree, diet);
                }
            } else {
                returnTree = new Tree<>(new StringCopy("Food"));
                for (Tree<StorableForm> childTree : tree.getChildren().values()) {
                    returnTree.addTreeChild(goTroughTree(childTree, diet), true);
                }
            }

            return returnTree;
        }
    }
    //</editor-fold>
}
