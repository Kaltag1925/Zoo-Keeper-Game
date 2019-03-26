package Engine.GameObjects.Entities.Moveable.People;

import CustomMisc.*;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import Engine.GameObjects.Tiles.Tile;
import Engine.Logic.AnimalSpecies;
import Engine.Logic.Assignments.Actions.ActionFactory;
import Engine.Logic.Assignments.Assignment;
import Engine.Logic.Assignments.Task;
import Engine.Logic.GameObjectHolder;
import Engine.Logic.MainData;
import XML.FileLocations;
import XML.StorageClasses.StorageXML;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class Visitor extends Person {

    private static final String XML_NODE_NAME = "Visitor";

    private ArrayList<String> favoriteAnimals = new ArrayList<>();
    private static final String FAVORITE_ANIMALS_NODE_NAME = "FavoriteAnimals";

    public static Image icon = null;
    static {
        try {
            icon = ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "visitor.png"));
        } catch (IOException e) {
        }
    }

    private GameObjectHolder<AnimalReal> animalsSeen = new GameObjectHolder<>();
    private GameObjectHolder<AnimalReal> animalsNotSeen = new GameObjectHolder<>();

    private static HashMap<String, Integer> animalsInZooAndFrequency;

    public Visitor(Tile location, String gender, String firstName, String lastName, Integer age, Double money) {
        super(location, gender, firstName, lastName, age, money);

        favoriteAnimals = generateFavoriteAnimals();
    }

    public Visitor(Tile location, Image icon) {
        super(location, icon);
        visitOrLeave();
    }

    private void visitOrLeave() {
        final Random random = MainData.mainData.getRandom();
        if ((random.nextDouble() < .7 || animalsSeen.isEmpty()) && !animalsNotSeen.isEmpty()) {
            visit();
        } else {
            leave();
        }
    }

    private void visit() {
        final List<AnimalReal> animals = new ArrayList<>(animalsNotSeen);
        final Random random = MainData.mainData.getRandom();
        AnimalReal animalToVisit = null;
        do {
            animals.remove(animalToVisit);
            if (animals.isEmpty()) {
                break;
            }
            animalToVisit = animals.get(random.nextInt(animals.size()));
        } while (animalToVisit.getExhibit().getViewingArea() == null);

        if (animalToVisit == null) {
            leave();
        }

        setCurrentAssignment(new Assignment("Visit Animal", Assignment.LOWEST_PRIORITY));
        final Task task = new Task();
        setCurrentTask(task);
        final ActionFactory actionFactory = new ActionFactory();

        final List<Tile> tiles = animalToVisit.getExhibit().getViewingArea().getTiles();
        final Tile tile = tiles.get(random.nextInt(tiles.size()));
        task.addAction(actionFactory.moveTo(this, tile));
        task.addAction(actionFactory.waitInPlace(random.nextInt(10) + 30));
        final AnimalReal finalAnimalToVisit = animalToVisit;
        task.addAction(actionFactory.run(() -> animalsSeen.add(finalAnimalToVisit)));
        task.addAction(actionFactory.run(() -> animalsNotSeen.remove(finalAnimalToVisit)));
        task.addAction(actionFactory.run(this::visitOrLeave));
        MainData.mainData.getTaskManager().addTask(task);
    }

    private void leave() {
        setCurrentAssignment(new Assignment("Leave Zoo", Assignment.LOWEST_PRIORITY));
        final Task task = new Task();
        setCurrentTask(task);
        final ActionFactory actionFactory = new ActionFactory();

        task.addAction(actionFactory.moveTo(this, MainData.mainData.getPlayerZoo().getEntrance()));
        task.addAction(actionFactory.run(this::deleteObject));
        MainData.mainData.getTaskManager().addTask(task);
    }

    private ArrayList<String> generateFavoriteAnimals() {
        int probability = 999;
        CopyOnWriteArrayList<Tree<StringCopy>> animalsList = new CopyOnWriteArrayList<>();

        while (RandomUtils.isBetweenDualInclusive(MainData.mainData.getRandom().nextInt(1000), 0, probability)) {
            probability /= 2;
            Tree<StringCopy> nextAnimal = AnimalSpecies.relationTree.getChildList().get(MainData.mainData.getRandom().nextInt(AnimalSpecies.relationTree.getChildList().size()));
            if(!isAnimalAlreadyInList(nextAnimal, animalsList)) {
                animalsList.add(nextAnimal);
            }
        }

        ArrayList<String> returnAnimals = new ArrayList<>();
        for (Tree<StringCopy> string : animalsList) {
            returnAnimals.add(string.getHead().getInfo());
        }

        return returnAnimals;
    }

    private boolean isAnimalAlreadyInList(Tree<StringCopy> nextAnimal, CopyOnWriteArrayList<Tree<StringCopy>> animalsList) {
        if (animalsList.size() != 0) {

            for (Tree<StringCopy> name : animalsList) {
                Vector<Tree<StringCopy>> childList = name.getChildList();
                Tree<StringCopy> parent = name.getParent();

                if(parent != null) {
                    if(parent.getHead().equals(nextAnimal.getHead())) {
                        return true;
                    }

                    if(parent.getParent() != null) {
                        if(parent.getParent().getHead().equals(nextAnimal.getHead())) {
                            return true;
                        }
                    }
                }

                if(DataStructureUtils.checkArray(childList, nextAnimal)) {
                    return true;
                }

                if(nextAnimal.getHead().equals(nextAnimal)) {
                    return true;
                }
            }
        }

        return false;
    }

    public GameObjectHolder<AnimalReal> getAnimalsSeen() {
        return animalsSeen;
    }

    public GameObjectHolder<AnimalReal> getAnimalsNotSeen() {
        return animalsNotSeen;
    }

    public void setAnimalsNotSeen(GameObjectHolder<AnimalReal> animalsNotSeen) {
        this.animalsNotSeen = new GameObjectHolder<>();
        this.animalsNotSeen.addAll(animalsNotSeen);
    }

    public void deleteObject() {
        super.deleteObject();
    }

    private Visitor(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);
    }

//    private boolean isVisiting(){
//        double chance = BASE_CHANCE_TO_VISIT;
//        int numOfFavoriteAnimals = 0;
//        for (String animal : favoriteAnimals) {
//            if (DataStructureUtils.checkSet(animalsInZooAndFrequency.keySet(), animal)) {
//                numOfFavoriteAnimals += animalsInZooAndFrequency.get(animal);
//            }
//        }
//
//        for (int i = 0; i < numOfFavoriteAnimals; i++) {
//            chance *= FAVORITE_ANIMAL_MODIFIER;
//        }
//
//        return RandomUtils.isBetweenDualInclusive(MainData.mainData.getRandom().nextDouble(), chance, 1);
//    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        super.fillStorageTree(storageTree);

        Tree<StorableForm> favoriteAnimalsTree = SaveBuilder.build(favoriteAnimals, FAVORITE_ANIMALS_NODE_NAME);
        storageTree.addTreeChild(favoriteAnimalsTree, false);
    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {
        Tree<StorableForm> favoriteAnimalsTree = storageTree.findFirstTreeFromMethod(FAVORITE_ANIMALS_NODE_NAME, StorableForm.method);
        favoriteAnimals = SaveBuilder.read(favoriteAnimalsTree);
    }

    @Override
    public void assignReferences(Tree<StorableForm> storageTree) {
        super.assignReferences(storageTree);
    }

//    public static void loadVisitors(){
//        new StorageVisitor().makeVisible();
//        dayListener = new VisitorDayListener();
//    }
//
//    public static void saveVisitors(){
//        new StorageVisitor().save();
//    }

    private static class StorageVisitor extends StorageXML {

        public StorageVisitor() {
        }

        public void load() {
            CopyOnWriteArrayList<Tree<StorableForm>> data = super.load(FileLocations.visitors);
            for (Tree<StorableForm> dataTree : data) {
                for (Tree<StorableForm> currentVisitorTree : dataTree.getChildren().values()) {
                    if (currentVisitorTree.getHead().getNodeName().equals("Person")) {
                        new Visitor(currentVisitorTree);
                    }

//                  if (visitorTypeTree.getHead().getNodeName().equals("CurrentVisitors")) {
//                      for (Tree<StorableForm, String> currentVisitorTree : visitorTypeTree.getChildren().values()) {
//                          if (currentVisitorTree.getHead().getNodeName().equals("Person")) {
//                              new Visitor(currentVisitorTree);
//                          }
//                      }
//                  }
//
//                  if (visitorTypeTree.getHead().getNodeName().equals("PlanToVisitAgain")) {
//                      for (Tree<StorableForm, String> planToVisitTree : visitorTypeTree.getChildren().values()) {
//                          if (planToVisitTree.getHead().getNodeName().equals("Person")) {
//                              new Visitor(planToVisitTree);
//                          }
//                      }
//                  }
                }
            }
        }

//        public void save() {
//            Vector<Tree<StorableForm>> data = new Vector<>();
//            if (visitedTheDay.size() != 0) {
//                for (Visitor visitor : visitedTheDay.values()) {
//                    data.add(visitor.createStorageObject());
//                }
//            }
//
//            if (planToVisitAgain.size() != 0) {
//                for (Visitor visitor : planToVisitAgain.values()) {
//                    data.add(visitor.createStorageObject());
//                }
//            }
//
//            super.save(FileLocations.visitors, data);
//        }
//    }
//
//    private static class VisitorDayListener implements IDayListener {
//        VisitorDayListener() {
//            Event.addDayListener(this, Integer.MAX_VALUE);
//        }
//
//        public void newDay() {
//            createVisitors();
//        }
    }
}
