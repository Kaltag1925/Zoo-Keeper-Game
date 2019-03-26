package Engine.Logic;

import CustomMisc.Tree;
import Engine.GameObjects.Companies.Zoo;
import Engine.GameObjects.Entities.Moveable.People.VisitorManager;
import Engine.GameObjects.GameMap;
import Engine.Logic.Assignments.Actions.ActionFactory;
import Engine.Logic.Assignments.AssignmentManager;
import Engine.Logic.Assignments.TaskManager;
import Engine.Logic.Ticks.Tick;
import UserInterface.FontManager;
import UserInterface.GameMain;
import UserInterface.MainFrame;
import UserInterface.Panels.ControlPanel.ControlPanelManager;
import UserInterface.Panels.DescriptionPanel.DescriptionPanelManager;
import UserInterface.Panels.MapPanel.MapPanelManager;
import XML.StorageClasses.AStorableObject;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MainData extends AStorableObject {

    public static void main(String[] args) {
        new MainData();
    }

    private static final String CURRENT_SEED = "currentSeed";
    private static final String FIRST_SEED = "firstSeed";

    private Random random;
    private long randomFirstSeed;

    public Random getRandom() {
        return random;
    }


    //<editor-fold desc="Player Zoo">
    private Zoo playerZoo;

    public Zoo getPlayerZoo() {
        return playerZoo;
    }
    //</editor-fold>


    //<editor-fold desc="Assignment Manager">
    private AssignmentManager assignmentManager;

    public AssignmentManager getAssignmentManager() {
        return assignmentManager;
    }
    //</editor-fold>


    //<editor-fold desc="Task Manager">
    private TaskManager taskManager;

    public TaskManager getTaskManager() {
        return taskManager;
    }
    //</editor-fold>


    //<editor-fold desc="Action Factory">
    private ActionFactory actionFactory;

    public ActionFactory getActionFactory() {
        return actionFactory;
    }
    //</editor-fold>


    //<editor-fold desc="Time">
    private GameTime gameTime;

    public GameTime getGameTime() {
        return gameTime;
    }
    //</editor-fold>


    //<editor-fold desc="Visitor Manager">
    private VisitorManager visitorManager;

    public VisitorManager getVisitorManager() {
        return visitorManager;
    }
    //</editor-fold>


    //<editor-fold desc="Frame">
    private MainFrame mainFrame;

    public MainFrame getMainFrame() {
        return mainFrame;
    }
    //</editor-fold>


    //<editor-fold desc="Input Manager">
    private InputManager inputManager;

    public InputManager getInputManager() {
        return inputManager;
    }
    //</editor-fold>


    //<editor-fold desc="Game Map">
    private GameMap map;

    public GameMap getMap() {
        return map;
    }
    //</editor-fold>


    //<editor-fold desc="Game Main">
    private GameMain gameMain;

    public GameMain getGameMain() {
        return gameMain;
    }
    //</editor-fold>


    //<editor-fold desc="Notification Manager">
    private NotificationManager notificationManager;

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }
    //</editor-fold>


    //<editor-fold desc="Control Panel Manager">
    private ControlPanelManager controlPanelManager;

    public ControlPanelManager getControlPanelManager() {
        return controlPanelManager;
    }
    //</editor-fold>


    //<editor-fold desc="Font Manager">
    private FontManager fontManager;

    public FontManager getFontManager() {
        return fontManager;
    }
    //</editor-fold>


    //<editor-fold desc="Description Panel Manager">
    private DescriptionPanelManager descriptionPanelManager;

    public DescriptionPanelManager getDescriptionPanelManager() {
        return descriptionPanelManager;
    }
    //</editor-fold>


    //<editor-fold desc="Map Panel Manager">
    private MapPanelManager mapPanelManager;

    public MapPanelManager getMapPanelManager() {
        return mapPanelManager;
    }
    //</editor-fold>


    public static MainData mainData;

    private void createStuff() {
        fontManager = new FontManager();
        assignmentManager = new AssignmentManager();
        taskManager = new TaskManager();
        actionFactory = new ActionFactory();
        gameTime = new GameTime();
        //gameTime.getCalendar().scheduleEvent(5, new Job(new Assignment(), 5)::go);
        visitorManager = new VisitorManager();
        notificationManager = new NotificationManager();
        mainFrame = new MainFrame();
        inputManager = new InputManager();
        controlPanelManager = new ControlPanelManager();
        descriptionPanelManager = new DescriptionPanelManager();
        mapPanelManager = new MapPanelManager();
        mainFrame.addKeyListener(inputManager);
    }

    public MainData() {
        super();
        mainData = this;
        random = new Random();
        try
        {
            Field field = Random.class.getDeclaredField("seed");
            field.setAccessible(true);
            AtomicLong scrambledSeed = (AtomicLong) field.get(random);
            randomFirstSeed = scrambledSeed.get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        map = new GameMap(20, 20);
        playerZoo = new Zoo("Zoo", 10000d, null, new AnimalHolder(new GameObjectHolder<>()), new GameObjectHolder<>(), new GameObjectHolder<>(), map.getTile(9, 19));

        createStuff();

        gameMain = new GameMain();

        gameMain.load();

        Tick.switchRunning();


    }

    public MainData(long seed) {
        super();
        mainData = this;
        random = new Random(seed);
        randomFirstSeed = seed;
        createStuff();
    }

    public MainData(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);
    }

    public void fillStorageTree(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();
        head.put(NODE_NAME, "MainData");

        long currentSeed = 0;
        try
        {
            Field field = Random.class.getDeclaredField("seed");
            field.setAccessible(true);
            AtomicLong scrambledSeed = (AtomicLong) field.get(random);
            currentSeed = scrambledSeed.get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        head.put(CURRENT_SEED, String.valueOf(currentSeed));
        head.put(FIRST_SEED, String.valueOf(randomFirstSeed));
    }

    public void fromStorage(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();
        random = new Random(Long.parseLong(head.get(CURRENT_SEED)));
        randomFirstSeed = Long.parseLong(head.get(FIRST_SEED));
    }
}
