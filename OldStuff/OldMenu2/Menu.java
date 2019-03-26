package Menu;

import CustomSwing.LabelResize;
import Engine.Logic.Ticks.Tick;
import XML.FileLocations;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Menu extends JPanel implements IMenuEntry {

    //<editor-fold desc="KeyBinds">
    public static KeyStroke upButton = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
    public static KeyStroke upButtonNP = KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, 0);
    public static KeyStroke downButton = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
    public static KeyStroke downButtonNP = KeyStroke.getKeyStroke(KeyEvent.VK_KP_DOWN, 0);
    public static KeyStroke rightButton = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
    public static KeyStroke rightButtonNP = KeyStroke.getKeyStroke(KeyEvent.VK_KP_RIGHT, 0);
    public static KeyStroke leftButton = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
    public static KeyStroke leftButtonNP = KeyStroke.getKeyStroke(KeyEvent.VK_KP_LEFT, 0);
    public static KeyStroke enterButton = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
    public static KeyStroke escButton = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

    public static KeyStroke plusButton = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0);
    public static KeyStroke plusButtonNP = KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0);
    public static KeyStroke minusButton = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0);

    public static KeyStroke aLower = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);
    public static KeyStroke bLower = KeyStroke.getKeyStroke(KeyEvent.VK_B, 0);
    public static KeyStroke nLower = KeyStroke.getKeyStroke(KeyEvent.VK_N, 0);
    public static KeyStroke vLower = KeyStroke.getKeyStroke(KeyEvent.VK_V, 0);
    //</editor-fold>

    public static Color notSelected = new Color(150, 150, 150);
    public static Color selected = Color.WHITE;
    public static HashMap<String, Menu> menus = new HashMap<>();
    public static String versionNumber = "Version id1*10^-99";
    public static String creditsText = "Created by Kaltag";
    public static String selectCharacterColor = "#5f9b3f";
    public static String notSelectedHex = "#969696";
    public static String selectedHex = "#ffffff";
    public static double textSize = .4;
    public static double controlLabelTextSize = .5;

    public static JFrame frame = new JFrame("Zoo Keeper");

    private int currentSelected;
    private JPanel panelMain;
    private Container displayHere;
    private Menu parentMenu;
    private ArrayList<Pair<KeyStroke, String>> inputMap;

    public Menu(String name, Container displayHere, Menu parentMenu){
        setName(name);
        this.displayHere = displayHere;
        this.parentMenu = parentMenu;

        menus.put(name, this);
    }

    public void back(){
        menus.remove(getName());
        removeInputMaps(panelMain);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                parentMenu.showMenu();
            }
        });
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public int getCurrentSelected(){
        return currentSelected;
    }

    public void setCurrentSelected(int num){
        currentSelected = num;
    }

    public JPanel getPanelMain(){
        return panelMain;
    }

    public void setPanelMain(JPanel panel) {
        panelMain = panel;
    }

    public void setInputMapKeysValues(ArrayList<Pair<KeyStroke, String>> list) {
        inputMap = list;
    }

    public void setInputMaps(JPanel panel) {
        for (Pair<KeyStroke, String> pair : inputMap) {
            panel.getInputMap().put(pair.getKey(), pair.getValue());
        }
    }

    public void removeInputMaps(JPanel panel) {
        for (Pair<KeyStroke, String> pair : inputMap) {
            panel.getInputMap().remove(pair.getKey());
        }
    }

    public Container getDisplayHere(){
        return displayHere;
    }

    public static void main(String[] args) {
        Tick.switchRunning();

        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(LabelResize.font);

        FileLocations.setSaveLocation();

        //Create and set up the window.
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Set up the content pane.
        MainMenu mainMenu = new MainMenu("MainData Menu", frame, null);
        mainMenu.showMenu();

        //Display the window.
        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();
        frame.setVisible(true);

//        AnimalReal bear = new AnimalReal("Polar Bear", "Tom", 2.0);
//        System.out.println(bear.getDiet());
//        AnimalReal wolf = new AnimalReal("Artic Wolf", "Atka", 13.0);
//        System.out.println(wolf.getDiet());
//        AnimalReal deer = new AnimalReal("White-Tailed Deer", "Bambi", 3.0);
//        System.out.println(deer.getDiet());
    }

    public void showMenu(){
    }

//    public void back(){
//        parentMenu.show();
//    }
}