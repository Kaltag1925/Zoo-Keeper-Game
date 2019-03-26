package Engine.Logic;

import CustomMisc.*;
import XML.FileLocations;
import XML.StorageClasses.AStorableObject;
import XML.StorageClasses.StorageXML;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/**
 * Created by Rhiwaow on 27/02/2017.
 */
public class AnimalSpecies extends AStorableObject implements ICreateCopy {

    private String animalSpecies;
    private String diet;
    private double lifeExpectancyFemale;
    private double lifeExpectancyMale;
    private double basePrice;
    private Tree<AnimalBodyPart> bodyPartMap;
    private double hungerRate;
    private String allergies;
    private double speed = 5;

    private Tree<StorableForm> tempBodyPartMap;

    private HashMap<String, ArrayList<String>> coatColors;
    private HashMap<String, ArrayList<String>> eyeColors;
    private HashMap<String, Integer> colorInfo;

    private CopyOnWriteArrayList<CoatColorGroup> coatColorGroups; //FIXME: All elements are null

    public static HashMap<String, AnimalSpecies> animals = new HashMap<>();

    public static Tree<StringCopy> relationTree = new Tree<>(new StringCopy("Animals"));

    @Override
    public ICreateCopy createCopy() {
        AnimalSpecies copy = new AnimalSpecies();

        copy.animalSpecies = animalSpecies;
        copy.diet = diet;
        copy.lifeExpectancyFemale = lifeExpectancyFemale;
        copy.lifeExpectancyMale = lifeExpectancyMale;
        copy.basePrice = basePrice;
        copy.bodyPartMap = bodyPartMap.createCopy();
        copy.hungerRate = hungerRate;
        copy.allergies = allergies;
        copy.tempBodyPartMap = tempBodyPartMap.createCopy();
        copy.coatColors = DataStructureCloner.clone(coatColors);
        copy.eyeColors = DataStructureCloner.clone(eyeColors);
        copy.colorInfo = DataStructureCloner.clone(colorInfo);

        return copy;
    }

    private AnimalSpecies() {

    }

    private AnimalSpecies(String animalSpecies) {
        this.animalSpecies = animalSpecies;
    }

    //<editor-fold desc="Get Methods">
    public String getAnimalSpecies() {
        return animalSpecies;
    }

    public String getDiet() {
        return diet;
    }

    public double getLifeExpectancyFemale() {
        return lifeExpectancyFemale;
    }

    public double getLifeExpectancyMale() {
        return lifeExpectancyMale;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public HashMap<String, ArrayList<String>> getCoatColors() {
        return coatColors;
    }

    public HashMap<String, ArrayList<String>> getEyeColors() {
        return eyeColors;
    }

    public HashMap<String, Integer> getColorInfo() {
        return colorInfo;
    }

    public Tree<AnimalBodyPart> getBodyPartMap() {
        return bodyPartMap;
    }

    public double getHungerRate() {
        return hungerRate;
    }

    public double getSpeed() {
        return speed;
    }
    //</editor-fold>

    //<editor-fold desc="Set Methods">
    public void setAnimalSpecies(String animalSpecies) {
        this.animalSpecies = animalSpecies;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    private void setBodyPartMap(Tree<AnimalBodyPart> newMap) {
        bodyPartMap = newMap;
    }

    public CopyOnWriteArrayList<CoatColorGroup> getCoatColorGroups() {
        return coatColorGroups;
    }

    public ArrayList<String> pickColors() {
        HashMap<CoatColorGroup, Integer> picksLeft = new HashMap<>();
        Vector<CoatColorGroup> groups = new Vector<>();
        for (int i = 0; i < coatColorGroups.size(); i++) {
            CoatColorGroup cur = coatColorGroups.get(i);
            groups.add(cur);
            picksLeft.put(cur, cur.getPick());
        }

        ArrayList<String> colors = new ArrayList<>();
        double p = 1;

        Iterator<CoatColorGroup> iterator = groups.iterator();
        while (iterator.hasNext()) {
            CoatColorGroup group = iterator.next();
            if (colors.size() < colorInfo.get("maxCoatColors")) {
                if(group.getRequired()) {
                    boolean repeat = true;
                    while (repeat) {
                        colors.add(group.pickColor());
                        if(colors.size() >= colorInfo.get("minCoatColors")) {
                            p /= 1.6;
                        }
                        picksLeft.replace(group, picksLeft.get(group) - 1);
                        if(picksLeft.get(group) == 0) {
                            iterator.remove();
                            repeat = false;
                        }
                    }
                }
            } else {
                break;
            }
        }

        while (colors.size() < colorInfo.get("maxCoatColors") && RandomUtils.isBetweenLowerInclusive(MainData.mainData.getRandom().nextDouble(), 0, p)) {
            int totalWeight = 0;
            for (CoatColorGroup group : groups) {
                totalWeight += group.getWeight();
            }

            int random = MainData.mainData.getRandom().nextInt(totalWeight);
            int cur = 0;
            for (CoatColorGroup group : groups) {
                if (RandomUtils.isBetweenLowerInclusive(random, cur, cur + group.getWeight())) {
                    colors.add(group.pickColor());
                    if(colors.size() >= colorInfo.get("minCoatColors")) {
                        p /= 1.6;
                    }
                    picksLeft.replace(group, picksLeft.get(group) - 1);
                    if(picksLeft.get(group) == 0) {
                        groups.remove(group);
                    }
                    break;
                } else {
                    cur += group.getWeight();
                }
            }
        }

        return colors;
    }
    //</editor-fold>

    //<editor-fold desc="Save and Load">
    @Override
    protected void fillStorageTree(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();
        head.put("nodeName", "Animal");

        head.put("type", animalSpecies);
        head.put("diet", diet);

        head.put("lifeExpectancyFemale", String.valueOf(lifeExpectancyFemale));
        head.put("lifeExpectancyMale", String.valueOf(lifeExpectancyMale));
        head.put("basePrice", String.valueOf(basePrice));
        head.put("hungerRate", String.valueOf(hungerRate));
        if (allergies != null) {
            head.put("allergies", allergies);
        }

        Tree<StorableForm> colorInfoTree = null;
        try {
            colorInfoTree = runAndReturnIfNotNull(colorInfoTree, SaveBuilder.class.getDeclaredMethod("build", Object.class, String.class), new Object[] {colorInfo, "ColorInfo"});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Reimplement method build(T data, String nodeName)");
        }
        storageTree.addTreeChild(colorInfoTree, true);

        Tree<StorableForm> eyeColorsTree = null;
        try {
            eyeColorsTree = runAndReturnIfNotNull(eyeColorsTree, SaveBuilder.class.getDeclaredMethod("build", Object.class, String.class), new Object[] {eyeColors, "EyeColors"});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Reimplement method build(T data, String nodeName)");
        }
        storageTree.addTreeChild(eyeColorsTree, true);

        Tree<StorableForm> coatColorGroupsTree = null;
        try {
            coatColorGroupsTree = runAndReturnIfNotNull(coatColorGroupsTree, SaveBuilder.class.getDeclaredMethod("build", Object.class, String.class), new Object[] {coatColorGroups, "CoatColors"});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Reimplement method build(T data, String nodeName)");
        }
        storageTree.addTreeChild(coatColorGroupsTree, true);

//        Tree<StorableForm> bodyTree = bodyPartMap.getHead().createStorageObject();
//        speciesTree.addTreeChild(tempBodyPartMap, true);
    }

    private <T> T changeIfNotNull(Class<T> clazz, T original, Object set) {
        if (set != null) {
            return GenericConverter.convert(set, clazz);
        }

        return original;
    }

    private <T> T runAndReturnIfNotNull(T original, Method method, Object... args) {
        for (Object o : args) {
            if (o == null) {
                return original;
            }
        }


        try {
            return (T) method.invoke(null, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return original;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return original;
        }
    }

    private <T> T runAndReturnIfNotNull(T original, T set, Method method, Object... args) {
        for (Object o : args) {
            if (o == null) {
                return original;
            }
        }

        if (set == null) {
            return original;
        }

        try {
            return (T) method.invoke(set, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return original;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return original;
        }
    }

    @Override
    public void fromStorage(Tree<StorableForm> storedDataTree) {
        StorableForm storedData = storedDataTree.getHead();
        animalSpecies = changeIfNotNull(String.class, animalSpecies, storedData.get("type"));
        diet = changeIfNotNull(String.class, diet, storedData.get("diet"));
        lifeExpectancyFemale = changeIfNotNull(Double.class, lifeExpectancyFemale, storedData.get("lifeExpectancyFemale"));
        lifeExpectancyMale = changeIfNotNull(Double.class, lifeExpectancyMale, storedData.get("lifeExpectancyMale"));
        basePrice = changeIfNotNull(Double.class, basePrice, storedData.get("basePrice"));
        hungerRate = changeIfNotNull(Double.class, hungerRate, storedData.get("hungerRate"));
        allergies = changeIfNotNull(String.class, allergies, storedData.get("tags"));

        Tree<StorableForm> colorInfoTree = storedDataTree.findFirstTreeFromMethod("ColorInfo", StorableForm::getNodeName);
        colorInfo = changeIfNotNull(HashMap.class, colorInfo, colorInfoTree);

        Tree<StorableForm> coatColorGroupsTree = storedDataTree.findFirstTreeFromMethod("CoatColors", StorableForm::getNodeName);
        coatColorGroups = changeIfNotNull(CopyOnWriteArrayList.class, coatColorGroups, coatColorGroupsTree);

        Tree<StorableForm> eyeColorsTree = storedDataTree.findFirstTreeFromMethod("EyeColors", StorableForm::getNodeName); //FIXME: It's finding taking the wolf's eyecolors for all mammals, need to define that it only takes the direct children
        eyeColors = changeIfNotNull(HashMap.class, eyeColors, eyeColorsTree);

        try {
            Tree<StorableForm> newTempBodyPartMap = runAndReturnIfNotNull(tempBodyPartMap, tempBodyPartMap, Tree.class.getDeclaredMethod("findFirstTreeFromMethod", Object.class, Method.class, Object[].class), "BodyPart", StorableForm.class.getDeclaredMethod("getNodeName"));
            tempBodyPartMap = runAndReturnIfNotNull(tempBodyPartMap, newTempBodyPartMap, Tree.class.getDeclaredMethod("createCopy"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            bodyPartMap = runAndReturnIfNotNull(bodyPartMap, AnimalBodyPart.class.getDeclaredMethod("createBodyFromStorage", Tree.class), new Object[] {storedDataTree.findFirstTreeFromMethod("BodyPart", StorableForm::getNodeName)});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Reimplement method createBodyFromStorage(Tree<StorableForm> dataTree>)");
        }
    }

    public static AnimalSpecies getAnimalSpecies(String animalSpecies) {
        return animals.get(animalSpecies);
    }

    public static void loadAnimalSpecies() {
        new StorageAnimal().load();
    }

    private <T> T runAndReturnIfNotNull(T original, T set, Function<T, T> method) {
        if (set != null) {
            return method.apply(set);
        } else {
            return original;
        }
    }

    private <T> T setIfNotNull(T original, T set) {
        if (set != null) {
            return set;
        } else {
            return original;
        }
    }

    private ICreateCopy copyAndSetIfNotNull(ICreateCopy original, ICreateCopy set) {
        if (set != null) {
            return set.createCopy();
        } else {
            return original;
        }
    }

    private AnimalSpecies(Tree<StorableForm> storageTree, AnimalSpecies parent) {
        if (parent != null) {
            animalSpecies = setIfNotNull(animalSpecies, parent.animalSpecies);
            diet = setIfNotNull(diet, parent.diet);
            lifeExpectancyFemale = setIfNotNull(lifeExpectancyFemale, parent.lifeExpectancyFemale);
            lifeExpectancyMale = setIfNotNull(lifeExpectancyMale, parent.lifeExpectancyMale);
            basePrice = setIfNotNull(basePrice, parent.basePrice);
            bodyPartMap = (Tree<AnimalBodyPart>) copyAndSetIfNotNull((ICreateCopy) bodyPartMap, (ICreateCopy) parent.bodyPartMap);
            hungerRate = setIfNotNull(hungerRate, parent.hungerRate);
            allergies = setIfNotNull(allergies, parent.allergies);
            tempBodyPartMap = (Tree<StorableForm>) copyAndSetIfNotNull((ICreateCopy) tempBodyPartMap, (ICreateCopy) parent.tempBodyPartMap);
            coatColors = runAndReturnIfNotNull(coatColors, parent.coatColors, DataStructureCloner::clone);
            colorInfo = runAndReturnIfNotNull(colorInfo, parent.colorInfo, DataStructureCloner::clone);
            eyeColors = runAndReturnIfNotNull(eyeColors, parent.eyeColors, DataStructureCloner::clone);
        }

        fromStorage(storageTree);

        ArrayList<Tree<StorableForm>> parents = storageTree.getParents();
        ArrayList<StringCopy> checkingParents = new ArrayList<>();
        for (int i = parents.size() - 2; i > -1; i--) {
            checkingParents.add(new StringCopy(parents.get(i).getHead().get("type")));
        }
        checkingParents.add(new StringCopy(animalSpecies));

        relationTree.addTreeChild(Tree.arrayListToTree(checkingParents, 0), true);
        animals.put(animalSpecies, this);
    }

    private static class StorageAnimal extends StorageXML {
        public StorageAnimal() {

        }

        public void load() {
            CopyOnWriteArrayList<Tree<StorableForm>> data = super.load(FileLocations.animalDefs);
            for (Tree<StorableForm> dataTree : data) {
                for (Tree<StorableForm> animalTree : dataTree.getChildren().values()) {
                    loadThroughTree(animalTree, null);
                }
            }
        }

        private void loadThroughTree(Tree<StorableForm> currentTree, AnimalSpecies previous) {
            if(currentTree.getHead().getNodeName().equals("Animal")) {
                AnimalSpecies species = new AnimalSpecies(currentTree, previous);
                for (Tree<StorableForm> child : currentTree.getChildren().values()) {
                    loadThroughTree(child, species);
                }
            }
        }

//        public void save() {
//            Vector<Tree<StorableForm>> data = new Vector<>();
//            for (Tree<StringCopy> spec : relationTree.getChildren().values()) {
//                data.add(saveThroughTree(spec));
//            }
//            super.save(FileLocations.temporary, data);
//        }
//
//        private Tree<StorableForm> saveThroughTree(Tree<StringCopy> currentTree) {
//            Tree<StorableForm> returnTree;
//
//            if (currentTree.hasChildren()) {
//                returnTree = currentTree.getHead().createStorageObject();
//                returnTree.getHead().remove("info");
//                returnTree.getHead().put("type", currentTree.getHead().getInfo());
//                returnTree.getHead().replace("nodeName", "Animal");
//
//                for (Tree<StringCopy> child : currentTree.getChildren().values()) {
//                    returnTree.addTreeChild(saveThroughTree(child), true);
//                }
//            } else {
//                returnTree = animals.get(currentTree.getHead().getInfo()).createStorageObject();
//            }
//
//            return returnTree;
//        }
    }
    //</editor-fold>
}