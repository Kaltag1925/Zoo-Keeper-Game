package Engine.GameObjects.Companies;

import CustomMisc.RandomUtils;
import CustomMisc.SaveBuilder;
import CustomMisc.Tree;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import Engine.GameObjects.MapSpot;
import Engine.Logic.AnimalHolder;
import Engine.Logic.AnimalSpecies;
import Engine.Logic.GameObjectHolder;
import Engine.Logic.MainData;
import XML.FileLocations;
import XML.StorageClasses.StorageXML;

import java.lang.reflect.Method;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimalCompany extends Company implements IAnimalOwner {

    private AnimalHolder animals = new AnimalHolder(new GameObjectHolder<>());
    private Vector<String> animalsSpecialized = new Vector<>();

    public static Vector<AnimalCompany> animalCompanies = new Vector<>();

    //<editor-fold desc="Initialization">
    public AnimalCompany(String name, Double money, MapSpot location, Vector<String> animalsSpecialized) {
        super(name, money, location);

        if (animalsSpecialized == null){
            this.animalsSpecialized = createAnimalsSpecialized();
        } else {
            this.animalsSpecialized = animalsSpecialized;
        }

        for (int i = 0; i < 40; i++){
            int randomInt = MainData.mainData.getRandom().nextInt(this.animalsSpecialized.size());
            String species = this.animalsSpecialized.get(randomInt);

            randomInt = MainData.mainData.getRandom().nextInt(10);
            if (RandomUtils.isBetweenDualInclusive(randomInt, 0, 2)) {
                animals.addAnimal(new AnimalReal(null, "generatedAnimal", (double) MainData.mainData.getRandom().nextInt(1000), null, species, this));
            }
        }

        animalCompanies.add(this);
    }

    //<editor-fold desc="Creation">

    public static void createAnimalCompanies(int max) {
        int probability = max - 1;

        while (RandomUtils.isBetweenDualInclusive(MainData.mainData.getRandom().nextInt(max), 0 , probability)) {
            probability--;
            new AnimalCompany("Company" + probability, null, null, null);
        }
    }

    public static Vector<String> createAnimalsSpecialized() {
        Vector<String> allAnimalSpecies = new Vector<>(AnimalSpecies.animals.keySet());
        Vector<String> animalsSpecializedSpecies = new Vector<>();

        int numberOfAnimalsSpecialized = (int) MainData.mainData.getRandom().nextGaussian() + 3;
        if (numberOfAnimalsSpecialized < 1) {
            numberOfAnimalsSpecialized = 1;
        } else if (numberOfAnimalsSpecialized > allAnimalSpecies.size()) {
            numberOfAnimalsSpecialized = allAnimalSpecies.size();
        }

        for(int i = 0; i < numberOfAnimalsSpecialized; i++){
            int speciesNumber = MainData.mainData.getRandom().nextInt(allAnimalSpecies.size());
            animalsSpecializedSpecies.add(allAnimalSpecies.get(speciesNumber));
            allAnimalSpecies.remove(speciesNumber);
        }

        return animalsSpecializedSpecies;
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Get Methods">
    public AnimalHolder getAnimals(){
        return animals;
    }
    //</editor-fold>

    public void addAnimalToCompany(AnimalReal animal){
        animals.addAnimal(animal);
    }

    //<editor-fold desc="Save and Load">
    public AnimalCompany(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);

        Tree<StorableForm> aniamlsTree = storageTree.findFirstTreeFromMethod("Animals", StorableForm::getNodeName);
        for (Tree<StorableForm> specificAnimalTree : aniamlsTree.getChildren().values()) {
            if (specificAnimalTree.getHead().getNodeName().equals("Animals")) {
                AnimalReal animal = new AnimalReal(specificAnimalTree);
                animal.setOwner(this);
            }
        }

        animalCompanies.add(this);
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        super.fillStorageTree(storageTree);
        StorableForm head = storageTree.getHead();
        head.put("nodeName", "AnimalCompany");

        Tree<StorableForm> animalSpecializedTree = SaveBuilder.build(animalsSpecialized, "AnimalsSpecialized");
        storageTree.addTreeChild(animalSpecializedTree, false);

        Tree<StorableForm> animalsTree = SaveBuilder.build(animals, "Animals");
        storageTree.addTreeChild(animalsTree, false);
    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {
        StorableForm storedData = storageTree.getHead();

        Method method = null;
        try {
            method = StorableForm.class.getDeclaredMethod("getNodeName");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Tree<StorableForm> animalsSpecializedTree = storageTree.findFirstTreeFromMethod("AnimalsSpecialized", method);
        animalsSpecialized = SaveBuilder.read(animalsSpecializedTree);

        Tree<StorableForm> animalsTree = storageTree.findFirstTreeFromMethod("Animals", method);
        animalsSpecialized = SaveBuilder.read(animalsSpecializedTree);
    }

    @Override
    public void assignReferences(Tree<StorableForm> storageTree) {
        super.assignReferences(storageTree);
    }

    public static void loadAnimalCompanies(){
        new StorageAnimalCompanies().load();
    }

    public static void saveAnimalCompanies(){
        new StorageAnimalCompanies().save();
    }

    private static class StorageAnimalCompanies extends StorageXML {

        public StorageAnimalCompanies() {
        }

        public void load(){
            CopyOnWriteArrayList<Tree<StorableForm>> data = super.load(FileLocations.animalCompanies);
            for (Tree<StorableForm> dataTree : data){
                for (Tree<StorableForm> animalCompanyTree : dataTree.getChildren().values()) {
                    if (animalCompanyTree.getHead().getNodeName().equals("Company")) {
                        new AnimalCompany(animalCompanyTree);
                    }
                }
            }
        }

        public void save() {
            Vector<Tree<StorableForm>> data = new Vector<>();
            for (AnimalCompany animalCompany : animalCompanies) {
                data.add(animalCompany.toStorage());
            }
            super.save(FileLocations.animalCompanies, data);
        }
    }
    //</editor-fold>
}
