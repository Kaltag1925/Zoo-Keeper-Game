package Engine.Logic;

import CustomMisc.Tree;
import Engine.GameObjects.Entities.Moveable.People.Person;
import XML.FileLocations;
import XML.StorageClasses.AStorableObject;
import XML.StorageClasses.StorageXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PeopleName extends AStorableObject {
    private String name;
    private CopyOnWriteArrayList<CopyOnWriteArrayList<String>> conditions = new CopyOnWriteArrayList<>();

    public static HashMap<String, PeopleName> firstNames = new HashMap<>();
    public static HashMap<String, PeopleName> lastNames = new HashMap<>();

    private PeopleName(String name) {
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

    public static HashMap<String, ArrayList<String>> createName(Person person) {
        HashMap<String, ArrayList<String>> returnMap = new HashMap<>();

        ArrayList<String> possibleFirstNames = new ArrayList<>();
        returnMap.put("firstNames", possibleFirstNames);
        for (PeopleName name : firstNames.values()) {
            if(name.getConditions() != null) {
                CopyOnWriteArrayList<CopyOnWriteArrayList<String>> conditionsChecker = new CopyOnWriteArrayList<>(name.getConditions());
                ArrayList<String> personDescriptors = new ArrayList<>(person.getNameCheckers());

                for (CopyOnWriteArrayList<String> list : conditionsChecker) {
                    if(containsOneOfThese(list, personDescriptors)) {
                        conditionsChecker.remove(list);
                    }
                }

                if(conditionsChecker.size() == 0) {
                    possibleFirstNames.add(name.getName());
                }
            } else {
                possibleFirstNames.add(name.getName());
            }
        }

        ArrayList<String> possibleLastNames = new ArrayList<>();
        returnMap.put("lastNames", possibleLastNames);
        for (PeopleName name : lastNames.values()) {
            if(name.getConditions() != null) {
                CopyOnWriteArrayList<CopyOnWriteArrayList<String>> conditionsChecker = new CopyOnWriteArrayList<>(name.getConditions());
                ArrayList<String> personDescriptors = new ArrayList<>(person.getNameCheckers());

                for (CopyOnWriteArrayList<String> list : conditionsChecker) {
                    if(containsOneOfThese(list, personDescriptors)) {
                        conditionsChecker.remove(list);
                    }
                }

                if(conditionsChecker.size() == 0) {
                    possibleLastNames.add(name.getName());
                }
            } else {
                possibleLastNames.add(name.getName());
            }
        }

        return returnMap;
    }

    public static ArrayList<String> possibleNames(Person person, int position) {
        if (position == 0) {
            ArrayList<String> possibleNamesList = new ArrayList<>();
            for (PeopleName name : firstNames.values()) {
                if(name.getConditions() != null) {
                    CopyOnWriteArrayList<CopyOnWriteArrayList<String>> conditionsChecker = new CopyOnWriteArrayList<>(name.getConditions());
                    ArrayList<String> personDescriptors = new ArrayList<>(person.getNameCheckers());

                    for (CopyOnWriteArrayList<String> list : conditionsChecker) {
                        if(containsOneOfThese(list, personDescriptors)) {
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
        } else {
            ArrayList<String> possibleNamesList = new ArrayList<>();
            for (PeopleName name : lastNames.values()) {
                if(name.getConditions() != null) {
                    CopyOnWriteArrayList<CopyOnWriteArrayList<String>> conditionsChecker = new CopyOnWriteArrayList<>(name.getConditions());
                    ArrayList<String> personDescriptors = new ArrayList<>(person.getNameCheckers());

                    for (CopyOnWriteArrayList<String> list : conditionsChecker) {
                        if(containsOneOfThese(list, personDescriptors)) {
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
    }

    private static boolean containsOneOfThese(CopyOnWriteArrayList<String> list, ArrayList<String> checkingForIn) {
        for (String string : list){
            if (checkingForIn.contains(string)) {
                return true;
            }
        }

        return false;
    }

    //<editor-fold desc="Save and Load">
    private PeopleName(Tree<StorableForm> storageTree, HashMap<String, PeopleName> list) {
        fromStorage(storageTree);
        list.put(name, this);
    }


    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree){

    }

    @Override
    public void fromStorage(Tree<StorableForm> storedDataTree) {
        StorableForm storedData = storedDataTree.getHead();
        name = storedData.get("name");

        String conditionsLoadForm = storedData.get("conditions");
        if(!conditionsLoadForm.equals("")){
            while (conditionsLoadForm.contains(",")) {
                CopyOnWriteArrayList<String> tempConditionsStorage = new CopyOnWriteArrayList<>();
                if (conditionsLoadForm.charAt(0) == '(') {
                    int closingParenthesis = conditionsLoadForm.indexOf(')');
                    String oneOfThese = conditionsLoadForm.substring(1, closingParenthesis);
                    while (oneOfThese.contains(",")) {
                        int indexEnd = oneOfThese.indexOf(',');
                        String aCondition = oneOfThese.substring(0, indexEnd);
                        tempConditionsStorage.add(aCondition);
                        oneOfThese = oneOfThese.substring(indexEnd + 1, oneOfThese.length());
                    }
                    conditionsLoadForm = conditionsLoadForm.substring(closingParenthesis + 1, conditionsLoadForm.length());
                } else {
                    int indexEnd = conditionsLoadForm.indexOf(',');
                    String aCondition = conditionsLoadForm.substring(0, indexEnd);
                    tempConditionsStorage.add(aCondition);
                    conditionsLoadForm = conditionsLoadForm.substring(indexEnd + 1, conditionsLoadForm.length());
                }

                conditions.add(tempConditionsStorage);
            }
        } else {
            conditions = null;
        }

    }

    public String toString(){
        return getName();
    }

    public static void load() {
        new StoragePeopleName().load();
    }

    private static class StoragePeopleName extends StorageXML {
        public StoragePeopleName(){

        }

        public void load() {
            CopyOnWriteArrayList<Tree<StorableForm>> data = super.load(FileLocations.peopleNames);
            for (Tree<StorableForm> dataTree : data) {
                for (Tree<StorableForm> nameTree : dataTree.getChildren().values()) {
                    if (nameTree.getHead().getNodeName().equals("FirstName")) {
                        for (Tree<StorableForm> specificNameTree : nameTree.getChildren().values()) {
                            if (specificNameTree.getHead().getNodeName().equals("Name")) {
                                new PeopleName(specificNameTree, firstNames);
                            }
                        }
                    } else if (nameTree.getHead().getNodeName().equals("LastName")) {
                        for (Tree<StorableForm> specificNameTree : nameTree.getChildren().values()) {
                            if (specificNameTree.getHead().getNodeName().equals("Name")) {
                                new PeopleName(specificNameTree, lastNames);
                            }
                        }
                    }
                }
            }
        }
    }
    //</editor-fold>
}