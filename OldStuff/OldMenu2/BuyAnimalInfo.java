package Menu;

import CustomMisc.IResizeMaxViewListener;
import CustomSwing.LabelResize;
import CustomSwing.ScrollPaneResize;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class BuyAnimalInfo extends Menu implements IMenuEntry, IResizeMaxViewListener{

    private JPanel[][] selectionComponents = new JPanel[8][6];
    private LabelResize buyAnimal, escapeLabel;
    private ArrayList<LabelResize> labelResizes = new ArrayList<>();
    private AnimalReal animal;
    private ScrollPaneResize scroller;
    private JPanel panel, panelMain;
    private ArrayList<Pair<String, Integer>> hierarchy;
    private ArrayList<LabelResize> labelList = new ArrayList<>();

    private int maxView;
    private int scrollAmount;

    public BuyAnimalInfo(String name, JPanel displayHere, Menu parentMenu, AnimalReal animal, ArrayList<Pair<String, Integer>> hierarchy){
        super(name, displayHere, parentMenu);

        IResizeMaxViewListener.Resize.addResizeMaxViewListener(this);

        setPanelMain((JPanel) getDisplayHere());

        this.animal = animal;
        this.hierarchy = hierarchy;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 6; j++){
                selectionComponents[i][j] = new JPanel(new GridBagLayout());
                selectionComponents[i][j].setOpaque(false);
//                selectionComponents[i][j].setBackground(new Color(i *10, j * 10, i * j));
                selectionComponents[i][j].setPreferredSize(new Dimension(10, 10));
            }
        }

        buyAnimal = new LabelResize("<html><font color=" + selectCharacterColor + ">b: </font><font color=" + selectedHex + ">Buy Animal</font>", SwingConstants.LEFT, controlLabelTextSize, new double[]{0, 0, 0, 0, 0, 0, 1, 1, GridBagConstraints.CENTER}, "b");
        selectionComponents[3][2].add(buyAnimal);
        labelResizes.add(buyAnimal);

        escapeLabel = new LabelResize("<html><font color=" + selectCharacterColor + ">ESC: </font><font color=" + selectedHex + ">Back</font>", SwingConstants.LEFT, controlLabelTextSize, new double[]{0, 0, 0, 0, 0, 0, 1, 1, GridBagConstraints.CENTER}, "ESC");
        selectionComponents[3][3].add(escapeLabel);
        labelResizes.add(escapeLabel);

        //<editor-fold desc="Scroll Panel">
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 0;

        TreeMap<String, Object> list = animal.getDisplayAttributes();
        Iterator iterator = list.entrySet().iterator();
        int i = 0;

        while(iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry)iterator.next();
            LabelResize type = new LabelResize(mapEntry.getKey() + ": " + mapEntry.getValue() , SwingConstants.LEFT, Menu.textSize);
            type.setVerticalAlignment(SwingConstants.TOP);
            type.setForeground(Menu.notSelected);
            c.gridy = i;
            i++;
            labelList.add(type);
            panel.add(type, c);
            try {
//                type.setTopParent();
            } catch (NullPointerException ex){
                ex.printStackTrace();
            }
        }

        scroller = new ScrollPaneResize(panel);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelMain = new JPanel(new BorderLayout());
        panelMain.setBackground(Color.BLACK);
        panelMain.add(scroller, BorderLayout.NORTH);
        //</editor-fold>

        EscapeAction esc = new EscapeAction();
        ((JPanel) getDisplayHere()).getActionMap().put("doEscActionBuyAnimalInfo", esc);

        BLowerAction bLowerAction = new BLowerAction();
        ((JPanel) getDisplayHere()).getActionMap().put("doBLowerActionBuyAnimalInfo", bLowerAction);

        ArrayList<Pair<KeyStroke, String>> inputMap = new ArrayList<>();
        inputMap.add(new Pair<>(escButton, "doEscActionBuyAnimalInfo"));
        inputMap.add(new Pair<>(bLower, "doBLowerActionBuyAnimalInfo"));

        setInputMapKeysValues(inputMap);

    }

    @Override
    public void resize(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                scroller.getVerticalScrollBar().setValue(0);
                FontMetrics metrics = labelList.get(0).getFontMetrics(labelList.get(0).getFont());
                double displayHeight = panelMain.getHeight();
                double maxDescent = metrics.getMaxDescent();
                double maxAscent = metrics.getMaxAscent();
                double newMax = Math.round((displayHeight / (maxDescent + maxAscent)) - 0.5);
                int oldMax = maxView;
                maxView = (int) newMax;
                scrollAmount = (int) (maxAscent + maxDescent);

                int y;
                if(maxView < labelList.size()) {
                    y = (int) displayHeight;
                } else {
                    y = labelList.size() * scrollAmount;
                }
                scroller.setSize(new Dimension(panelMain.getWidth(), y));
            }
        });
    }

    public void showMenu(){
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;

        GameMainMenu parent = (GameMainMenu) menus.get("Game MainData Menu");
        Container display = parent.getPanelDisplay();
        display.removeAll();
        display.add(panelMain, c);

        Container selections = parent.getPanelSelections();
        selections.removeAll();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 6; j++){
                c.gridx = j;
                c.gridy = i;
                selections.add(selectionComponents[i][j], c);
            }
        }

        setInputMaps((JPanel) getDisplayHere());

        for (LabelResize label : labelResizes) {
//            label.setTopParent();
        }

        for (LabelResize label : labelList){
//            label.setTopParent();
        }

        frame.getContentPane().validate();
        frame.getContentPane().repaint(100);
    }

    public void back() {
        GameMainMenu parent = (GameMainMenu) menus.get("Game MainData Menu");
        parent.getPanelDisplay().removeAll();
        parent.getPanelSelections().removeAll();
        super.back();
        if(hierarchy != null) {
            for (Pair<String, Integer> nameAndNumber : hierarchy) {
                int index = -1;
                if(BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList).getLabelList().get(nameAndNumber.getValue()).getText().equals(nameAndNumber.getKey())) {
                    BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList).getLabelList().get(nameAndNumber.getValue()).setForeground(selected);
                    BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList).setSelectedLabel(nameAndNumber.getValue());
                    BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList).preview();
                } else {
                    for (int i = 0; i < BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList).getLabelList().size(); i++) {
                        if(BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList).getLabelList().get(i).getText().equals(nameAndNumber.getKey())) {
                            index = i;
                            break;
                        }
                    }
                    if(index != -1) {
                        BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList).getLabelList().get(index).setForeground(selected);
                        BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList).setSelectedLabel(index);
                        BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList).preview();
                    }
                }

                BuyAnimalMenu.selectedList++;
            }
        }
        BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList).getLabelList().get(0).setForeground(selected);
        BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList).preview();
        frame.getContentPane().validate();
        frame.getContentPane().repaint(1000);
    }

    private class EscapeAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    back();
                }
            });
        }
    }

    private class BLowerAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            animal.buy();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    back();
                }
            });
        }
    }
}
