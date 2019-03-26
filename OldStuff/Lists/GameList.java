package CustomSwing.Lists;

import CustomMisc.IResizeMaxViewListener;
import CustomSwing.LabelResize;
import CustomSwing.LabelResizeWithNumber;
import CustomSwing.ScrollPaneResize;
import Menu.Menu;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static CustomMisc.IResizeMaxViewListener.Resize.addResizeMaxViewListener;


public class GameList implements IResizeMaxViewListener {

    private int selectedLabel;
    private int relativeSelectedLabel;
    private String name;
    private ArrayList<String> list;
    private ArrayList<LabelResize> labelList = new ArrayList<>();
    private JPanel panel;
    private ScrollPaneResize scroller;
    private JPanel displayHere;
    private Object constraints;
    private ArrayList<Pair<String, Integer>> hierarchy = new ArrayList<>();
    private TreeMap<Integer, String> iDList = new TreeMap<>();
    private TreeMap<String, Pair<Integer, Integer>> pairedIDList = new TreeMap<>();

    private int maxView = 28;
    private final int minView = 0;
    private int scrollAmount = 10;

    public GameList(String name, ArrayList<String> list, JPanel displayHere, Object constraints, int alignment){
        selectedLabel = 0;
        relativeSelectedLabel = 0;
        this.name = name;
        this.list = list;
        this.displayHere = displayHere;
        this.constraints = constraints;

        addResizeMaxViewListener(this);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 0;
        for (int i = 0; i < list.size(); i++){
            LabelResize type = new LabelResize(list.get(i), alignment, Menu.textSize, null, null);
            type.setVerticalAlignment(SwingConstants.TOP);
            type.setForeground(Menu.notSelected);
            c.gridy = i;
            getLabelList().add(type);
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
        displayHere.add(scroller, constraints);

        for (LabelResize label : getLabelList()){
//            label.setTopParent();
        }
    }

    public GameList(String name, ArrayList<String> list, JPanel displayHere, Object constraints, int alignment, Pair<String, Integer> lastNameAndNumber){
        selectedLabel = 0;
        relativeSelectedLabel = 0;
        this.name = name;
        this.list = list;
        this.displayHere = displayHere;
        this.constraints = constraints;
        hierarchy.add(lastNameAndNumber);

        addResizeMaxViewListener(this);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 0;
        for (int i = 0; i < list.size(); i++){
            LabelResize type = new LabelResize(list.get(i), alignment, Menu.textSize, null, null);
            type.setVerticalAlignment(SwingConstants.TOP);
            type.setForeground(Menu.notSelected);
            c.gridy = i;
            getLabelList().add(type);
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
        displayHere.add(scroller, constraints);

        for (LabelResize label : getLabelList()){
//            label.setTopParent();
        }
    }


    public GameList(String name, ArrayList<String> list, JPanel displayHere, Object constraints, int alignment, Pair<String, Integer> lastNameAndNumber, GameList lastList){
        selectedLabel = 0;
        relativeSelectedLabel = 0;
        this.name = name;
        this.list = list;
        this.displayHere = displayHere;
        this.constraints = constraints;
        if (lastList.getHierarchy().size() != 0) {
            hierarchy.addAll(lastList.getHierarchy());
        }
        hierarchy.add(lastNameAndNumber);

        addResizeMaxViewListener(this);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 0;
        for (int i = 0; i < list.size(); i++){
            LabelResize type = new LabelResize(list.get(i), alignment, Menu.textSize, null, null);
            type.setVerticalAlignment(SwingConstants.TOP);
            type.setForeground(Menu.notSelected);
            c.gridy = i;
            getLabelList().add(type);
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
        displayHere.add(scroller, constraints);

        for (LabelResize label : getLabelList()){
//            label.setTopParent();
        }
    }

    public GameList(String name, ArrayList<?> hasIDList, JPanel displayHere, Object constraints, int alignment, boolean forErasure){
        selectedLabel = 0;
        relativeSelectedLabel = 0;
        this.name = name;
//        this.hasIDList = hasIDList;
        this.displayHere = displayHere;
        this.constraints = constraints;


        addResizeMaxViewListener(this);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 0;
        for (int i = 0; i < hasIDList.size(); i++){
//            LabelResize type = new LabelResize(hasIDList.get(i).getName(), alignment, Menu.textSize, null, null);
//            type.setVerticalAlignment(SwingConstants.TOP);
//            type.setForeground(Menu.notSelected);
//            c.gridy = i;
//            getLabelList().add(type);
//            panel.add(type, c);
//            try {
//                type.setTopParent();
//            } catch (NullPointerException ex){
//                ex.printStackTrace();
//            }
        }

        ScrollPaneResize scroller = new ScrollPaneResize(panel);
        setScroller(scroller);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        displayHere.add(scroller, constraints);

        for (LabelResize label : getLabelList()){
//            label.setTopParent();
        }
    }

    public GameList(String name, TreeMap<Integer, String> iDList, JPanel displayHere, Object constraints, int alignment){
        selectedLabel = 0;
        relativeSelectedLabel = 0;
        this.name = name;
        this.displayHere = displayHere;
        this.constraints = constraints;
        this.iDList = iDList;

        addResizeMaxViewListener(this);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 0;

        Iterator iterator = iDList.entrySet().iterator();
        int i = 0;

        while(iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry)iterator.next();
            LabelResizeWithNumber type = new LabelResizeWithNumber((String) mapEntry.getValue(), alignment, Menu.textSize, (Integer) mapEntry.getKey());
            type.setVerticalAlignment(SwingConstants.TOP);
            type.setForeground(Menu.notSelected);
            c.gridy = i;
            i++;
            getLabelList().add(type);
            panel.add(type, c);
            try {
//                type.setTopParent();
            } catch (NullPointerException ex){
                ex.printStackTrace();
            }
        }

        ScrollPaneResize scroller = new ScrollPaneResize(panel);
        setScroller(scroller);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        displayHere.add(scroller, constraints);

        for (LabelResize label : getLabelList()){
//            label.setTopParent();
        }
    }

    public GameList(String name, TreeMap<Integer, String> iDList, JPanel displayHere, Object constraints, int alignment, Pair<String, Integer> lastNameAndNumber, GameList lastList){
        selectedLabel = 0;
        relativeSelectedLabel = 0;
        this.name = name;
        this.displayHere = displayHere;
        this.constraints = constraints;
        this.iDList = iDList;
        if (lastList.getHierarchy().size() != 0) {
            hierarchy.addAll(lastList.getHierarchy());
        }
        hierarchy.add(lastNameAndNumber);

        addResizeMaxViewListener(this);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 0;

        Iterator iterator = iDList.entrySet().iterator();
        int i = 0;

        while(iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry)iterator.next();
            LabelResizeWithNumber type = new LabelResizeWithNumber((String) mapEntry.getValue(), alignment, Menu.textSize, (Integer) mapEntry.getKey());
            type.setVerticalAlignment(SwingConstants.TOP);
            type.setForeground(Menu.notSelected);
            c.gridy = i;
            i++;
            getLabelList().add(type);
            panel.add(type, c);
            try {
//                type.setTopParent();
            } catch (NullPointerException ex){
                ex.printStackTrace();
            }
        }

        ScrollPaneResize scroller = new ScrollPaneResize(panel);
        setScroller(scroller);
        scroller.setBorder(BorderFactory.createEmptyBorder());
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        displayHere.add(scroller, constraints);

        for (LabelResize label : getLabelList()){
//            label.setTopParent();
        }
    }

    //<editor-fold desc="Get Methods">
    public int getSelectedLabel() {
        return selectedLabel;
    }

    public int getRelativeSelectedLabel() {
        return relativeSelectedLabel;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public ArrayList<LabelResize> getLabelList() {
        return labelList;
    }

    public JPanel getPanel() {
        return panel;
    }

    public ScrollPaneResize getScroller() {
        return scroller;
    }

    public JPanel getDisplayHere() {
        return displayHere;
    }

    public LabelResize getCurrentLabel(){
        return labelList.get(selectedLabel);
    }

    public TreeMap<Integer, String> getIDList(){
        return iDList;
    }

    public TreeMap<String, Pair<Integer, Integer>> getPairedIDList() {
        return pairedIDList;
    }

    public ArrayList<Pair<String, Integer>> getHierarchy() {
        return hierarchy;
    }

    //</editor-fold>

    //<editor-fold desc="Set Methods">
    public void setSelectedLabel(int selectedLabel) {
        this.selectedLabel = selectedLabel;
    }

    public void setRelativeSelectedLabel(int relativeSelectedLabel) {
        this.relativeSelectedLabel = relativeSelectedLabel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void setLabelList(ArrayList<LabelResize> labelList) {
        this.labelList = labelList;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void setScroller(ScrollPaneResize scroller) {
        this.scroller = scroller;
    }
    //</editor-fold>

    public void down(){
        if(selectedLabel < labelList.size() - 1){
            if(relativeSelectedLabel < maxView - 1) {
                LabelResize current = (LabelResize) labelList.get(selectedLabel);
                current.setForeground(Menu.notSelected);
                selectedLabel++;
                relativeSelectedLabel++;
                LabelResize next = (LabelResize) labelList.get(selectedLabel);
                next.setForeground(Menu.selected);
                scroller.setOld(selectedLabel);
            } else {
                LabelResize current = (LabelResize) labelList.get(selectedLabel);
                current.setForeground(Menu.notSelected);
                JViewport viewPort = scroller.getViewport();
                viewPort.setViewPosition(new Point(0, (int) (viewPort.getViewPosition().getY() + scrollAmount)));
                selectedLabel++;
                LabelResize next = (LabelResize) labelList.get(selectedLabel);
                next.setForeground(Menu.selected);
                scroller.setOld(selectedLabel);
            }
        } else {
            LabelResize current = (LabelResize) labelList.get(selectedLabel);
            current.setForeground(Menu.notSelected);
            JViewport viewPort = scroller.getViewport();
            viewPort.setViewPosition(new Point(0, 0));
            selectedLabel = 0;
            relativeSelectedLabel = 0;
            LabelResize next = (LabelResize) labelList.get(selectedLabel);
            next.setForeground(Menu.selected);
            scroller.setOld(0);
        }
    }

    public void up(){
        if(selectedLabel > 0){
            if(relativeSelectedLabel > minView) {
                LabelResize current = (LabelResize) labelList.get(selectedLabel);
                current.setForeground(Menu.notSelected);
                selectedLabel--;
                relativeSelectedLabel--;
                LabelResize next = (LabelResize) labelList.get(selectedLabel);
                next.setForeground(Menu.selected);
                scroller.setOld(selectedLabel);
            } else {
                LabelResize current = (LabelResize) labelList.get(selectedLabel);
                current.setForeground(Menu.notSelected);
                JViewport viewPort = scroller.getViewport();
                viewPort.setViewPosition(new Point(0, (int) (viewPort.getViewPosition().getY() - scrollAmount)));
                selectedLabel--;
                LabelResize next = (LabelResize) labelList.get(selectedLabel);
                next.setForeground(Menu.selected);
                scroller.setOld(selectedLabel);
            }
        } else {
            LabelResize current = (LabelResize) labelList.get(selectedLabel);
            current.setForeground(Menu.notSelected);
            JViewport viewPort = scroller.getViewport();
            if(list.size() > maxView) {
                viewPort.setViewPosition(new Point(0, (int) ((list.size() - maxView) * scrollAmount)));
            }
            selectedLabel = labelList.size() - 1;
            relativeSelectedLabel = maxView - 1;
            LabelResize next = (LabelResize) labelList.get(selectedLabel);
            next.setForeground(Menu.selected);
            scroller.setOld(labelList.size() - 1);
        }
    }

    @Override
    public void resize(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                scroller.getVerticalScrollBar().setValue(0);
                FontMetrics metrics = labelList.get(0).getFontMetrics(labelList.get(0).getFont());
                double displayHeight = displayHere.getHeight();
                double maxDescent = metrics.getMaxDescent();
                double maxAscent = metrics.getMaxAscent();
                double newMax = Math.round((displayHeight / (maxDescent + maxAscent)) - 0.5);
                int oldMax = maxView;
                maxView = (int) newMax;
                scrollAmount = (int) (maxAscent + maxDescent);

                LabelResize current = (LabelResize) labelList.get(selectedLabel);
                Point pos = SwingUtilities.convertPoint(current.getParent(), current.getLocation(), displayHere);

                relativeSelectedLabel = (int) (pos.getY() / scrollAmount);

                while (relativeSelectedLabel >= maxView){
                    scroller.getVerticalScrollBar().setValue(scroller.getVerticalScrollBar().getValue() + scrollAmount);
                    relativeSelectedLabel--;
                }

                int y;
                if(maxView < labelList.size()) {
                    y = (int) displayHeight;
                } else {
                    y = labelList.size() * scrollAmount;
                }
                scroller.setSize(new Dimension(displayHere.getWidth(), y));
            }
        });
    }
}
