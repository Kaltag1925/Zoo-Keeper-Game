package Engine.GameObjects.Entities.Moveable.Animal;

import CustomMisc.DataStructureUtils;
import CustomMisc.IDayListener;
import CustomMisc.Tree;
import Engine.GameObjects.Companies.IAnimalOwner;
import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Entities.Items.AnimalFoodReal;
import Engine.GameObjects.Entities.Moveable.Moveable;
import Engine.GameObjects.Tiles.AnimalExhibit;
import Engine.GameObjects.Tiles.DeliveryZone;
import Engine.GameObjects.Tiles.Tile;
import Engine.Logic.*;
import Engine.Logic.Assignments.Actions.ActionFactory;
import Engine.Logic.Assignments.Assignment;
import Engine.Logic.Assignments.Task;
import Engine.Logic.Ticks.Tick;
import StatusEffectTypes.StatusEffect;
import UserInterface.Panels.ControlPanel.ListObjectsPanel;
import UserInterface.Panels.ControlPanel.Listed;
import UserInterface.Panels.DescriptionPanel.DescriptionPanel;
import XML.FileLocations;
import XML.StorageClasses.StorageXML;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimalReal extends Moveable implements IDayListener, Deliverable, Listed {

    private static final String XML_NODE_NAME = "Animal";

    private Double hunger;
    private Double thirst;
    private Double health;
    private Double age;
    private String name;
    private String gender;
    private ArrayList<StatusEffect> status = new ArrayList<StatusEffect>();
    private Description description;
    private AnimalSpecies species;
    private Double price = 100d;
    private AnimalExhibit exhibit;

    private boolean[] needs = new boolean[]
            {false};

    private IAnimalOwner owner;

    public static Image icon = null;
    static {
        try {
            icon = ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "animal.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, ArrayList<String>> colors = new HashMap<>();
    private Tree<AnimalBodyPart> bodyPartMap;

    private static Vector<AnimalReal> animals = new Vector<>();

    //<editor-fold desc="Creation">
    public AnimalReal(Tile location, String name, Double age, String gender, String species, IAnimalOwner owner) {
        super(location, null);
        this.species = AnimalSpecies.animals.get(species);
        this.owner = owner;

        hunger = 0.00;
        thirst = 0.00;
        health = 100.00;

        if (gender == null) {
            int number = MainData.mainData.getRandom().nextInt(2);
            if (number == 0) {
                this.gender = "male";
            } else {
                this.gender = "female";
            }
        }

        price = (getLifeExpectancy() - age) / getLifeExpectancy() * this.species.getBasePrice();

        createColors();

        if (name.equals("generatedAnimal")) {
            this.name = createName();
        }

        description = new Description();

        bodyPartMap = this.species.getBodyPartMap().createCopy();
        for (Tree<AnimalBodyPart> child : bodyPartMap.getChildren().values()) {
            goThroughBodyPartsParent(bodyPartMap.getHead(), child);
        }

        owner.getAnimals().addAnimal(this);
        animals.add(this);

        IDayListener.Event.addDayListener(this, 1);
    }

    public AnimalReal(Image icon) {
        this(null, icon);
        Tick.removeFromUpdateList(this);
        Entity.entities.remove(this);
    }

    public AnimalReal(Tile location, Image icon) {
        super(location, icon);
        hunger = 75d;
        animals.add(this);
        name = "Animal" + System.currentTimeMillis();
    }

    public boolean isHungry() {
        return hunger < 50;
    }

    private static void goThroughBodyPartsParent(AnimalBodyPart previousPart, Tree<AnimalBodyPart> currentTree) {
        previousPart.addChild(currentTree.getHead());
        if (currentTree.getChildren().size() != 0) {
            for (Tree<AnimalBodyPart> nextBodyTree : currentTree.getChildren().values()) {
                goThroughBodyPartsParent(currentTree.getHead(), nextBodyTree);
            }
        }
    }

    private void createColors(){
        colors.put("coatColors", species.pickColors());

        ArrayList<String> youngEyes = new ArrayList<>();
        youngEyes.add(species.getEyeColors().get("youngEyes").get(MainData.mainData.getRandom().nextInt(species.getEyeColors().get("youngEyes").size())));
        colors.put("youngEyes", youngEyes);

        ArrayList<String> adultEyes = new ArrayList<>();
        adultEyes.add(species.getEyeColors().get("adultEyes").get(MainData.mainData.getRandom().nextInt(species.getEyeColors().get("adultEyes").size())));
        colors.put("adultEyes", adultEyes);
    }

    private class Description {
        private String description = "";

        public Description() {
            int gender;
            if (getGender().equals("male")){
                gender = 0;
            } else {
                gender = 1;
            }

            StringBuilder descriptionBuilder = new StringBuilder();

            double[] genderSubjectFormLimits = {0, 1};
            String[] genderSubjectFormParts = {"he", "she"};
            ChoiceFormat genderSubjectForm = new ChoiceFormat(genderSubjectFormLimits, genderSubjectFormParts);

            double[] genderPossessiveFormLimits = {0, 1};
            String[] genderPossessiveFormParts = {"his", "her"};
            ChoiceFormat genderPossessiveForm = new ChoiceFormat(genderPossessiveFormLimits, genderPossessiveFormParts);

            //<editor-fold desc="Name and Species">
            MessageFormat nameAndSpeciesForm = new MessageFormat("{0} is a {1} {2}.");

            Object[] nameAndSpeciesArguments = {getName(), getGender(), getSpeciesName()};

            descriptionBuilder.append(nameAndSpeciesForm.format(nameAndSpeciesArguments));
            //</editor-fold>

            //<editor-fold desc="Body Colors">
            MessageFormat bodyColorForm = new MessageFormat(" {0} fur is {1}.");
            bodyColorForm.setFormatByArgumentIndex(0, genderPossessiveForm);
//            bodyColorForm.setFormatByArgumentIndex(1, bodyColorNumberForm);

            int numCoatColors = colors.get("coatColors").size();
            ArrayList<String> coatColors = colors.get("coatColors");
            StringBuilder coatBuilder = new StringBuilder();
            if (numCoatColors > 1) {
                if (numCoatColors > 2) {
                    for (int i = 0; i < numCoatColors - 1; i++) {
                        coatBuilder.append(coatColors.get(i));
                        coatBuilder.append(", ");
                    }
                } else {
                    coatBuilder.append(coatColors.get(0));
                    coatBuilder.append(" ");
                }
                coatBuilder.append("and ");
                coatBuilder.append(coatColors.get(numCoatColors - 1));
            } else {
                coatBuilder.append(coatColors.get(0));
            }

            Object[] genderAndCoatColor = {gender, coatBuilder.toString()};
            descriptionBuilder.append(bodyColorForm.format(genderAndCoatColor));
            //</editor-fold>

            MessageFormat eyeColorForm = new MessageFormat(" {0} eyes are {1}.");
            eyeColorForm.setFormatByArgumentIndex(0, genderPossessiveForm);

            int numEyesColors = colors.get("adultEyes").size();
            ArrayList<String> eyeColors = colors.get("adultEyes");
            StringBuilder eyeBuilder = new StringBuilder();
            if (numEyesColors > 1) {
                if (numEyesColors > 2) {
                    for (int i = 0; i < numEyesColors - 1; i++) {
                        eyeBuilder.append(eyeColors.get(i));
                        eyeBuilder.append(", ");
                    }
                } else {
                    eyeBuilder.append(eyeColors.get(0));
                    eyeBuilder.append(" ");
                }
                eyeBuilder.append("and ");
                eyeBuilder.append(eyeColors.get(numEyesColors - 1));
            } else {
                eyeBuilder.append(eyeColors.get(0));
            }

            Object[] genderAndEyeColor = {gender, eyeBuilder.toString()};
            descriptionBuilder.append(eyeColorForm.format(genderAndEyeColor));

        }

        public String toString(){
            return description;
        }
    }

    private String createName(){
        ArrayList<String> possibleNames = AnimalName.possibleNames(this);
        return possibleNames.get(MainData.mainData.getRandom().nextInt(possibleNames.size()));
    }
    //</editor-fold>

    //<editor-fold desc="Get Methods">
    public double getHunger() {
        return hunger;
    }

    public double getThirst() {
        return thirst;
    }

    public double getHeath() {
        return health;
    }

    public double getAge() {
        return age;
    }

    public ArrayList<StatusEffect> getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public String getDiet(){
        return species.getDiet();
    }

    public String getSpeciesName(){
        return species.getAnimalSpecies();
    }

    public AnimalSpecies getSpecies(){
        return species;
    }

    public TreeMap<String, Object> getDisplayAttributes() {
        TreeMap<String, Object> displayAttributes = new TreeMap<>();
        displayAttributes.put("Name", name);
        displayAttributes.put("Age", age);
        displayAttributes.put("Species", species.getAnimalSpecies());
        displayAttributes.put("Health", health);
        displayAttributes.put("Gender", gender);
        displayAttributes.put("Life Expectancy", getLifeExpectancy());
        displayAttributes.put("Price", price);
        displayAttributes.put("Description", description);
        return displayAttributes;
    }

    public Double getLifeExpectancy() {
        switch (gender){
            case "male": return this.species.getLifeExpectancyMale();
            case "female": return this.species.getLifeExpectancyFemale();
        }

        return null;
    }

    public String getGender(){
        return gender;
    }

    public ArrayList<String> getNameCheckers(){
        ArrayList<String> returnList = new ArrayList<>();
//        returnList.add(getAnimalClass().toLowerCase());
//        returnList.add(getFamily().toLowerCase()); TODO: change to new system
        returnList.add(getSpeciesName().toLowerCase());
        returnList.add(getGender());
        returnList.addAll(colors.get("coatColors"));
        return returnList;
    }

    public HashMap<String, ArrayList<String>> getColors() {
        return colors;
    }

    public Tree<AnimalBodyPart> getBodyPartMap() {
        return bodyPartMap;
    }

    public IAnimalOwner getOwner(){
        return owner;
    }

    public static Vector<AnimalReal> getAnimals(){
        return animals;
    }

    public double getPrice() {
        return price;
    }

    public boolean getNeedStatus(int index) {
        return needs[index];
    }

    public AnimalExhibit getExhibit() {
        return exhibit;
    }

    //</editor-fold>

    //<editor-fold desc="Set Methods">
    public void setAge(Double newAge){
        age = newAge;
    }

    public void setOwner(IAnimalOwner owner) {
        this.owner = owner;
    }

    public void setHunger(Double hunger) {
        this.hunger = hunger;
    }

    public void setNeedStatus(int index, boolean value) {
        needs[index] = value;
    }

    public void setExhibit(AnimalExhibit exhibit) {
        this.exhibit = exhibit;
    }

    //</editor-fold>

    //<editor-fold desc="Interaction Methods">
    @Override
    public void newDay() {
        age++;
        price = (getLifeExpectancy() - age) / getLifeExpectancy() * species.getBasePrice();
//        if (!isOwnedByCompany) {
//            hunger -= species.getHungerRate();
//        }
    }



    public void setBaseBodyPart(AnimalBodyPart part){
//        baseBodyPart = part;
    }

    @Override
    public void deliver(DeliveryZone zone) {
        List<Tile> tiles = zone.getTiles();
        setLocation(tiles.get(MainData.mainData.getRandom().nextInt(tiles.size())));
        Entity.entities.add(this);
        Tick.addToUpdateList(this);
        MainData.mainData.getPlayerZoo().getAnimals().addAnimal(this);
        setHungerTimer();
    }

    private void setHungerTimer() {
        GameTime gameTime = MainData.mainData.getGameTime();
        long timeToStarve = (long) (hunger / .5) + gameTime.getCalendar().getTick();
        gameTime.getCalendar().scheduleEvent(timeToStarve, this::starve);
    }

    private void starve() {
        System.out.println(name + " has starved ;(");
    }

    //<editor-fold desc="Listed">
    @Override
    public String getListName() {
        return name;
    }

    @Override
    public List<Tile> getTilesForList() {
        List<Tile> list = new ArrayList<>();
        list.add(getLocation());
        return list;
    }

    @Override
    public boolean hiddenOnMap() {
        return false;
    }

    @Override
    public DescriptorsFormat getShortDescriptors() {
        DescriptorsFormat descriptors = new DescriptorsFormat();

        descriptors.put("Species", "animal");
        descriptors.put("Hunger", String.valueOf(hunger));

        return descriptors;
    }

    @Override
    public ControlPanelFormat getControlPanelControls() {
        ControlPanelFormat controlPanelObjects = new ControlPanelFormat();
        controlPanelObjects.add(new ControlPanelObject("z", "Assign To Exhibit", assignToExhibitMethod, KeyEvent.VK_Z));
        return controlPanelObjects;
    }

    //<editor-fold desc="Assign Exhibit">
    private int trimToSizeAfterAssignExhibit;

    private Method assignToExhibitMethod;
    {
        try {
            assignToExhibitMethod = AnimalReal.class.getDeclaredMethod("assignToExhibitKey");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private Method setExhibit;
    {
        try {
            setExhibit = AnimalReal.class.getDeclaredMethod("exhibitToAssignAnimal", AnimalExhibit.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void assignToExhibitKey() {
        ListedList exhibits = new ListedList(MainData.mainData.getPlayerZoo().getAnimalExhibits());
        Object cur = this;
        trimToSizeAfterAssignExhibit = MainData.mainData.getControlPanelManager().getDescriptionBreadCrumbs().size();

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    new ListObjectsPanel(exhibits, "exhibit", setExhibit, cur, MainData.mainData.getMapPanelManager().getLocalMapPanel()).open();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private AnimalExhibit setToExhibit;

    public void exhibitToAssignAnimal(AnimalExhibit setToExhibit) {
        this.setToExhibit = setToExhibit;

//        try {
//            SwingUtilities.invokeAndWait(new Runnable() {
//                @Override
//                public void run() {
//                    MainData.mainData.getDescriptionPanelManager().getAnimalDescriptionPanel().open(animal);;
//                }
//            });
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

        setAnimalExhibit();
    }

    private void setAnimalExhibit() {
        MainData.mainData.getControlPanelManager().trimBreadCrumbs(trimToSizeAfterAssignExhibit);
        setExhibit(setToExhibit);
        setToExhibit.addAnimal(this);
        if (!setToExhibit.getTilesForList().contains(getLocation())) {
            MainData.mainData.getPlayerZoo().addHaulingJob(this, setToExhibit);
        }
    }
    //</editor-fold>

    @Override
    public void paintToLargeDescriptionPanel(DescriptionPanel panel, Graphics g) {
        int fontWidth = panel.getFontWidth();
        int fontHeight = panel.getFontHeight();

        g.setColor(Color.LIGHT_GRAY);
        g.drawString(name, fontWidth, fontHeight);

        g.setColor(Color.LIGHT_GRAY);
        String hungerString = "Hunger: ";
        g.drawString(hungerString, fontWidth, fontHeight * 2);
        g.setColor(Color.GREEN);
        g.drawString(String.valueOf(hunger), fontWidth * (hungerString.length() + 1), fontHeight * 2);

        g.setColor(Color.LIGHT_GRAY);
        String exhibitString = "Exhibit: ";
        g.drawString(exhibitString, fontWidth, fontHeight * 3);
        String exhibitName;
        if (exhibit == null) {
            g.setColor(Color.RED);
            exhibitName = "None";
        } else {
            g.setColor(Color.GREEN);
            exhibitName = exhibit.getName();
        }
        g.drawString(exhibitName, fontWidth * (exhibitString.length() + 1), fontHeight * 3);
    }
    //</editor-fold>



    public void buy() {
//        ArrayList<AnimalReal> out = new ArrayList<>();
//        for (AnimalCompany company : AnimalCompany.animalCompanies.values()) {
//            out.addAll(company.getAnimals().values());
//        }
//        AnimalCompany.animalCompanies.get(owner).getAnimals().remove(id);
//        ArrayList<AnimalReal> out2 = new ArrayList<>();
//        for (AnimalCompany company : AnimalCompany.animalCompanies.values()) {
//            out2.addAll(company.getAnimals().values());
//        }
//        MainData.basicSaveData.setMoney(MainData.basicSaveData.getMoney() - price);
//        animalsOwned.put(id, this);
    }
    //</editor-fold>

    private static void createTestAnimals() throws CloneNotSupportedException {
        for (int i = 0; i < 15; i++) {
            int randomInt = MainData.mainData.getRandom().nextInt(AnimalSpecies.animals.keySet().size());
            String species = (String) AnimalSpecies.animals.keySet().toArray()[randomInt];

//            AnimalReal newAnimal = new AnimalReal(species, (double) MainData.mainData.getRandom().nextInt(1000), animalsOwned, null, false, 0);
        }
    }
    //<editor-fold desc="Save/Load">
    public AnimalReal(Tree<StorableForm> storageTree) {
        super(storageTree);
//        super(storageTree);
//        fromStorage(storageTree);
//
//        Tree<StorableForm> animalBodyPartTreeTop = storageTree.findFirstTreeFromMethod("BodyPart", StorableForm::getNodeName); //Does not support multiple base body parts
//        bodyPartMap = AnimalBodyPart.createBodyFromStorage(animalBodyPartTreeTop);
//        baseBodyPart = bodyPartMap.getHead();
//
//        description = new Description();
//        animalList.put(id, this);
//        animals.put(id, this);
//        IDayListener.Event.addDayListener(this, 1);
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
//        attributes.put("nodeName", XML_NODE_NAME);
//
//        attributes.put("name", String.valueOf(name));
//        attributes.put("health", String.valueOf(health));
//        attributes.put("thirst", String.valueOf(thirst));
//        attributes.put("hunger", String.valueOf(hunger));
//        attributes.put("age", String.valueOf(age));
//        attributes.put("gender", String.valueOf(gender));
//        attributes.put("price", String.valueOf(price));
//
//        Iterator iterator = colors.entrySet().iterator();
//
//        while(iterator.hasNext()) {
//            Map.Entry mapEntry = (Map.Entry)iterator.next();
//            attributes.put(String.valueOf(mapEntry.getKey()), String.valueOf(mapEntry.getValue()));
//        }
//
//        attributes.put("owner", String.valueOf(owner));
    }

    public void createStorageObject() {

//        StorableForm attributes = new StorableForm();
//        Tree<StorableForm> animalTree = new Tree<>(attributes);
//        attributes.put("nodeName", XML_NODE_NAME);
//
//        attributes.put("name", String.valueOf(name));
//        attributes.put("speciesName", String.valueOf(speciesName));
//        attributes.put("id", String.valueOf(id));
//        attributes.put("health", String.valueOf(health));
//        attributes.put("thirst", String.valueOf(thirst));
//        attributes.put("hunger", String.valueOf(hunger));
//        attributes.put("age", String.valueOf(age));
//        attributes.put("gender", String.valueOf(gender));
//        attributes.put("price", String.valueOf(price));
//        attributes.put("owner", String.valueOf(owner));
//
//        Tree<StorableForm> colorsTree = SaveBuilder.build(colors, "Colors");
//        animalTree.addTreeChild(colorsTree, true);
//
//        animalTree.addTreeChild(bodyPartMap.getHead().createStorageObject(), true);
//
//        return animalTree;
    }

    @Override
    public void fromStorage(Tree<StorableForm> storedDataTree) {
//        StorableForm storedData = storedDataTree.getHead();
//        name = storedData.get("name");
//        speciesName = storedData.get("speciesName");
//        id = Integer.parseInt(storedData.get("id"));
//        health = Double.parseDouble(storedData.get("health"));
//        thirst = Double.parseDouble(storedData.get("thirst"));
//        hunger = Double.parseDouble(storedData.get("hunger"));
//        age = Double.parseDouble(storedData.get("age"));
//        gender = storedData.get("gender");
//        price = Double.parseDouble(storedData.get("price"));
//
//        species = AnimalSpecies.animals.get(speciesName);
//
//        Tree<StorableForm> colorsTree = storedDataTree.findFirstTreeFromMethod("Colors", StorableForm::getNodeName);
//        colors = SaveBuilder.read(colorsTree);
//
//        if (!storedData.get("owner").equals("null")) {
//            owner = Integer.parseInt(storedData.get("owner"));
//        }
    }

    private boolean eating = false;

    @Override
    public void update() {
        super.update();
    }

    private static Method getEntities;
    static {
        try {
            getEntities = Tile.class.getDeclaredMethod("getOccupiers");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void eat() {
        if (exhibit != null) {
            final List<Tile> tiles = getExhibit().getTiles();
            List<AnimalFoodReal> food = new ArrayList<>();
            for (Tile t : tiles) {
                final List<Entity> occupiers = t.getOccupiers();
                food.addAll(DataStructureUtils.findAllObjectWithClass(occupiers, AnimalFoodReal.class));
            }
            if(food.size() != 0 && !eating) {
                eating = true;
                final AnimalFoodReal animalFoodReal = food.get(MainData.mainData.getRandom().nextInt(food.size()));
                setCurrentAssignment(new Assignment("Eat", 1));
                Task task = new Task();
                setCurrentTask(task);
                final ActionFactory actionFactory = new ActionFactory();
                if(!getLocation().equals(animalFoodReal.getLocation())) {
                    task.addAction(actionFactory.moveTo(this, animalFoodReal.getLocation()));
                }
                task.addAction(actionFactory.eat(this, animalFoodReal));
                task.addAction(actionFactory.endAssignment(this));
                task.addAction(actionFactory.run(() -> eating = false));
                MainData.mainData.getTaskManager().addTask(task);
            }
        }
    }

    public void eat(AnimalFoodReal food) {
        final double difference = 100 - hunger;
        hunger += food.subtractForFeeding(difference);
    }

    public static void loadAnimals() throws CloneNotSupportedException {
        new StorageAnimal().load();
        createTestAnimals();
    }

    public static void saveAnimals(){
        new StorageAnimal().save();
    }

    private static class StorageAnimal extends StorageXML {

        public StorageAnimal() {
        }

        public void load(){
            CopyOnWriteArrayList<Tree<StorableForm>> data = super.load(FileLocations.animals);
            for (Tree<StorableForm> dataTree : data) {
                for (Tree<StorableForm> animalTree : dataTree.getChildren().values()) {
                    if (animalTree.getHead().getNodeName().equals("Animal")) {
//                        new AnimalReal(animalTree, animalsOwned);
                    }
                }
            }
        }

        public void save() {
            Vector<Tree<StorableForm>> data = new Vector<>();
//            for (AnimalReal animal : animalsOwned.values()) {
//                data.add(animal.createStorageObject());
//            }
            super.save(FileLocations.animals, data);
        }
    }
    //</editor-fold>
}