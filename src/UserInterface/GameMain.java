package UserInterface;

import Engine.GameObjects.Entities.Moveable.People.Employee;
import Engine.GameObjects.GameMap;
import Engine.Logic.Assignments.Assignment;
import Engine.Logic.Assignments.AssignmentManager;
import Engine.Logic.MainData;
import Engine.Logic.NotificationManager;
import Engine.Logic.Ticks.IHasKeyBinds;
import Engine.Logic.Ticks.IRenderable;
import Engine.Logic.Ticks.Tick;
import UserInterface.Panels.ControlPanel.ControlPanelManager;
import UserInterface.Panels.ControlPanel.MainControls;
import UserInterface.Panels.MapPanel.MapPanelManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;

public class GameMain extends JPanel implements IRenderable, IHasKeyBinds {

    int maxX;
    int maxY;

    public GameMain() {
        super(new GridBagLayout());

        Tick.addToRenderList(this);
        Tick.addToInFocus(this, Tick.LAST_BINDER);

        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(MainData.mainData.getFontManager().getGameFont());


        JFrame frame = MainData.mainData.getMainFrame();
        //Create and set up the window.
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        File schIdle = new File(".." + File.separator + "Tasks" + File.separator + "Idle" + File.separator + "schedule.js");
        File idle = new File(".." + File.separator + "Tasks" + File.separator + "Idle" + File.separator + "action.js");
        File idleCleanUp = new File(".." + File.separator + "Tasks" + File.separator + "Idle" + File.separator + "cleanUp.js");
        AssignmentManager.IDLE_ASSIGNMENT = new Assignment("idle", schIdle, idle, idleCleanUp, Assignment.LOWEST_PRIORITY);

        GameMap map = MainData.mainData.getMap();

        MainData.mainData.getPlayerZoo().getEmployees().add(new Employee(map.getTile(5, 5)));
        MainData.mainData.getPlayerZoo().getEmployees().add(new Employee(map.getTile(6, 6)));
//        MainData.mainData.getPlayerZoo().getAnimals().addAnimal(new AnimalReal(map.getTile(0, 6), 'A'));
//        MainData.mainData.getPlayerZoo().getAnimals().addAnimal(new AnimalReal(map.getTile(0, 10), 'A'));
//        MainData.mainData.getPlayerZoo().getAnimals().addAnimal(new AnimalReal(map.getTile(10, 0), 'A'));
//        MainData.mainData.getPlayerZoo().getAnimalFood().add(new AnimalFoodReal(MainData.mainData.getMap().getTile(15, 12), AnimalFoodReal.icon, 100));

//        File schFeeding = new File(".." + File.separator + "Tasks" + File.separator + "Feeding" + File.separator + "schedule.js");
//        File feed = new File(".." + File.separator + "Tasks" + File.separator + "Feeding" + File.separator + "action.js");
//        File feedCleanUp = new File(".." + File.separator + "Tasks" + File.separator + "Feeding" + File.separator + "cleanUp.js");
//        new Assignment("feedAnimal", schFeeding , feed, feedCleanUp, Assignment.HIGHEST_PRIORITY + 1);

        File schVisit = new File(".." + File.separator + "Tasks" + File.separator + "VisitingAnimals" + File.separator + "schedule.js");
        File visit = new File(".." + File.separator + "Tasks" + File.separator + "VisitingAnimals" + File.separator + "action.js");
        File visitCleanUp = new File(".." + File.separator + "Tasks" + File.separator + "VisitingAnimals" + File.separator + "cleanUp.js");
        new Assignment("visitAnimals" , schVisit, visit, visitCleanUp, 1);

        File schLeave = new File(".." + File.separator + "Tasks" + File.separator + "VisitorLeaveZoo" + File.separator + "schedule.js");
        File leave = new File(".." + File.separator + "Tasks" + File.separator + "VisitorLeaveZoo" + File.separator + "action.js");
        File leaveCleanUp = new File(".." + File.separator + "Tasks" + File.separator + "VisitorLeaveZoo" + File.separator + "cleanUp.js");
        new Assignment("visitorLeaveZoo" , schLeave, leave, leaveCleanUp, Assignment.HIGHEST_PRIORITY);

        File schHaul = new File(".." + File.separator + "Tasks" + File.separator + "MoveToZone" + File.separator + "schedule.js");
        File haul = new File(".." + File.separator + "Tasks" + File.separator + "MoveToZone" + File.separator + "action.js");
        File haulCleanUp = new File(".." + File.separator + "Tasks" + File.separator + "MoveToZone" + File.separator + "cleanUp.js");
        new Assignment("moveToZone" , schHaul, haul, haulCleanUp, Assignment.HIGHEST_PRIORITY);
    }

    public void load() {
        JFrame frame = MainData.mainData.getMainFrame();

        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        //Set up the content pane.
        frame.add(this, c);
        setBackground(Color.GRAY);

        MapPanelManager mapManager = MainData.mainData.getMapPanelManager();
        mapManager.getLocalMapPanel().open();


        ControlPanelManager descManager = MainData.mainData.getControlPanelManager();
        new MainControls().open();

        //Display the window.
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocus();

        frame.getContentPane().validate();
        frame.getContentPane().repaint(100);
    }

    public static void main(String[] args) {
        new GameMain();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setFont(MainData.mainData.getFontManager().getGameFont());
        graphics.setColor(Color.WHITE);

        HashMap<Integer, Integer> panelFontStats = MainData.mainData.getFontManager().getFontInfoForPanel(this);
        maxX = panelFontStats.get(FontManager.X);
        maxY = panelFontStats.get(FontManager.Y);

        String moneyString = NumberFormat.getCurrencyInstance().format(MainData.mainData.getPlayerZoo().getMoney());
        graphics.drawString(moneyString, MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight());

        String zooName = MainData.mainData.getPlayerZoo().getName();
        int halfOfName = zooName.length() / 2;
        int halfOfScreen = maxX / 2;
        int xCharAnchor = halfOfScreen - halfOfName;
        graphics.drawString(zooName, xCharAnchor * MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight());

        Date date = MainData.mainData.getGameTime().getCalendar().getTime();
        int dateLength = date.toString().length();
        int endOfScreen = maxX;
        xCharAnchor = endOfScreen - dateLength - 1;
        graphics.drawString(date.toString(), xCharAnchor * MainData.mainData.getFontManager().getFontWidth(), MainData.mainData.getFontManager().getFontHeight());

        NotificationManager.Notification currentNotification = MainData.mainData.getNotificationManager().currentNotification;
        if (currentNotification != null) {
            int screenMiddle = maxX / 2;
            int stringMiddle = currentNotification.text.length() / 2;
            int xAnchor = screenMiddle - stringMiddle;

            graphics.drawString(currentNotification.text, MainData.mainData.getFontManager().getFontWidth() * xAnchor, MainData.mainData.getFontManager().getFontHeight() * maxY);
        }
    }

    @Override
    public void render() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    repaint(100);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkKeyBinds(int key) {
        switch(key) {
//            case KeyEvent.VK_E:
//                eKey();
//                return true;
//
//            case KeyEvent.VK_V:
//                vKey();
//                return true;
//
//            case KeyEvent.VK_D:
//                dKey();
//                return true;
//
//            case KeyEvent.VK_W:
//                wKey();
//                return true;
//
//            case KeyEvent.VK_P:
//                pKey();
//                return true;
        }

        return false;
    }

//
//    private void aKey() {
//        AnimalReal animal = new AnimalReal(MainData.mainData.getMap().getTile(mapPanel.selectedTile), AnimalReal.icon);
//        try {
//            MainData.mainData.getPlayerZoo().buyAnimal(animal);
//        } catch (Zoo.InsufficientFundsException ex) {
//            animal.deleteObject();
//        }
//    }
//
//    private void eKey() {
//        MainData.mainData.getPlayerZoo().getEmployees().add(new Employee(MainData.mainData.getMap().getTile(mapPanel.selectedTile), Employee.icon));
//    }
}