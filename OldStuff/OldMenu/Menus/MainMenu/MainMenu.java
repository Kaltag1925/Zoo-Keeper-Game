package OldMenu.Menus.MainMenu;

import CustomSwing.LabelResize;
import Engine.Day;
import OldMenu.Navigation.Functions.ExitNavigator;
import OldMenu.Navigation.Menus.Game.GameMainMenuNavigator;
import OldMenu.Navigation.Menus.MainMenu.OptionsMenuNavigator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MainMenu extends Menu {

    private static LabelResize title, play, options, exit, version, credits;
    private static JPanel panelMain, panelTitle, panelSelections, panelInfo;
    public static int lastSelected = 0;
    public static ArrayList<LabelResize> localSelectables = new ArrayList<LabelResize>();
    private static LayoutManager layoutStyle = new BorderLayout();


    public static void createMainMenu(Container pane) {
        pane.setLayout(layoutStyle);
        GridBagConstraints c = new GridBagConstraints();

        panelMain = new JPanel(new GridBagLayout());
        panelMain.setBackground(Color.BLACK);
        panelMain.setPreferredSize(new Dimension(400, 300));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;

        // Title Components

        title = new LabelResize("Zoo Keeper", SwingConstants.CENTER, 1, null, null, null, null, "Zoo Keeper");
        title.setForeground(Color.ORANGE);
        c.gridx = 1;
        c.gridy = 0;
        panelMain.add(title, c);

        // Selection Components

        play = new LabelResize("Play", SwingConstants.CENTER, 1, new GameMainMenuNavigator(), new double[] {0, 0, 200, 0, 1, 1, GridBagConstraints.CENTER}, null, null, "Play");
        play.setForeground(selected);
        localSelectables.add(play);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(play, c);

        options = new LabelResize("Options", SwingConstants.CENTER, 1, new OptionsMenuNavigator(), null, null, null, "Options");
        options.setForeground(notSelected);
        localSelectables.add(options);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(options, c);

        exit = new LabelResize("Exit", SwingConstants.CENTER, 1,  new ExitNavigator(), new double[] {200, 0, 0, 0, 1, 1, GridBagConstraints.CENTER}, null, null, "Exit");
        exit.setForeground(notSelected);
        localSelectables.add(exit);
        c.gridx = 1;
        c.gridy = 1;
        panelMain.add(exit, c);

        // Info Components

        version = new LabelResize(versionNumber, SwingConstants.CENTER, .5, null, null, null, null, versionNumber);
        version.setForeground(Color.GREEN);
        c.gridx = 2;
        c.gridy = 2;
        panelMain.add(version, c);


        credits = new LabelResize(creditsText, SwingConstants.CENTER, .5, null, null, null, null, creditsText);
        credits.setForeground(Color.GREEN);
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        panelMain.add(credits, c);

        // Add Everything

        pane.add(panelMain, BorderLayout.CENTER);
        title.setTopParent();
        play.setTopParent();
        options.setTopParent();
        exit.setTopParent();
        version.setTopParent();
        credits.setTopParent();

        // Key Binds

        panelMain.requestFocus();

        UpAction up = new UpAction();
        panelMain.getInputMap().put(upButton, "doUpAction");
        panelMain.getInputMap().put(upButtonNP, "doUpAction");
        panelMain.getActionMap().put("doUpAction", up);

        DownAction down = new DownAction();
        panelMain.getInputMap().put(downButton, "doDownAction");
        panelMain.getInputMap().put(downButtonNP, "doDownAction");
        panelMain.getActionMap().put("doDownAction", down);

        EnterAction enter = new EnterAction();
        panelMain.getInputMap().put(enterButton, "doEnterAction");
        panelMain.getActionMap().put("doEnterAction", enter);

        selectables = localSelectables;
        menus.add(0, panelMain);
        Menu.menuCurrent = 0;
    }

    public static void main(String[] args) {

        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(LabelResize.font);

        //Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        createMainMenu(frame.getContentPane());

        //Display the window.
        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();
        frame.setVisible(true);

        System.out.println(Day.getDate());
        System.out.println(Day.getDateLong());
        Day.advanceDay(1000);
        System.out.println(Day.getDate());
        System.out.println(Day.getDateLong());
    }

    public static JPanel getPanelMain(){
        return panelMain;
    }

    public static void regainFocus(){
        panelMain.requestFocus();
    }
}
