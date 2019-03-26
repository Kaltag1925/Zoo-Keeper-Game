package Engine.Logic;

import CustomMisc.SaveBuilder;
import CustomMisc.Tree;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import XML.StorageClasses.AStorableObject;
import XML.FileLocations;
import XML.StorageClasses.StorageXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Rhiwaow on 27/02/2017.
 */
public class AnimalName extends AStorableObject {

    private String name;
    private CopyOnWriteArrayList<CopyOnWriteArrayList<String>> conditions;

    public static HashMap<String, AnimalName> names = new HashMap<>();

    private AnimalName(String name) {
        this.name = name;

    }

    //<editor-fold desc="Get Methods">
    public String getName() {
        return name;
    }

    public CopyOnWriteArrayList<CopyOnWriteArrayList<String>> getConditions() {
        return conditions;
    }
    //</editor-fold>

    public static ArrayList<String> possibleNames(AnimalReal animal){
        ArrayList<String> possibleNamesList = new ArrayList<>();
        for(AnimalName name : names.values()) {
            if (name.getConditions() != null) {
                CopyOnWriteArrayList<CopyOnWriteArrayList<String>> conditionsChecker = new CopyOnWriteArrayList<>(name.getConditions());
                Vector<String> animalDescriptors = new Vector<>(animal.getNameCheckers());

                for (CopyOnWriteArrayList<String> list : conditionsChecker) {
                    if(containsOneOfThese(list, animalDescriptors)) {
                        conditionsChecker.remove(list);
                    }
                }

                if(conditionsChecker.size() == 0) {
                    possibleNamesList.add(name.getName());
                }
            } else {
                possibleNamesList.add(name.getName());
            }
        }

        return possibleNamesList;
    }

    public static boolean containsOneOfThese(CopyOnWriteArrayList<String> list, Vector<String> checkingForIn) {
        for (String string : list){
            if (checkingForIn.contains(string)) {
                return true;
            }
        }

        return false;
    }

    public String toString(){
        return getName();
    }

    private AnimalName(Tree<StorableForm> storageTree) {
        fromStorage(storageTree);
        names.put(name, this);
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree){
        StorableForm head = storageTree.getHead();
        head.put("nodeName", "Name");

        head.put("name", name);

        if (conditions != null) {
            Tree<StorableForm> conditionsTree = SaveBuilder.build(conditions, "Conditions");
            storageTree.addTreeChild(conditionsTree, false);
        }
    }

    @Override
    public void fromStorage(Tree<StorableForm> storedDataTree) {
        StorableForm storedData = storedDataTree.getHead();
        name = storedData.get("name");

        Tree<StorableForm> conditionsTree = storedDataTree.findFirstTreeFromMethod("Conditions", StorableForm::getNodeName);
        if (conditionsTree != null) {
            conditions = SaveBuilder.read(conditionsTree);
        }
    }

    public static void load() {
        new StorageAnimalNames().load();
    }

    private static class StorageAnimalNames extends StorageXML {
        StorageAnimalNames() {

        }

        public void load() {
            CopyOnWriteArrayList<Tree<StorableForm>> data = super.load(FileLocations.animalNames);
            for (Tree<StorableForm> dataTree : data) {
                for (Tree<StorableForm> nameTree : dataTree.getChildren().values()) {
                    if (nameTree.getHead().getNodeName().equals("Name")) {
                        new AnimalName(nameTree);
                    }
                }
            }
        }
    }
}