package CustomSwing.Lists;

import javax.swing.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class AnimalViewList extends GameList {
    private ArrayList<AnimalViewList> listList = new ArrayList<>();

    public AnimalViewList(String name, ArrayList<String> list, JPanel displayHere, Object constraints, int alignment, ArrayList<AnimalViewList> listList) {
        super(name, list, displayHere, constraints, alignment);
        this.listList = listList;
        listList.add(this);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(displayHere);
                    frame.getContentPane().validate();
                    frame.getContentPane().repaint(1000);
                } catch (NullPointerException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public AnimalViewList(String name, ArrayList<?> list, JPanel displayHere, Object constraints, int alignment, ArrayList<AnimalViewList> listList, boolean forErasure) {
        super(name, list, displayHere, constraints, alignment, true);
        this.listList = listList;
        listList.add(this);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(displayHere);
                    frame.getContentPane().validate();
                    frame.getContentPane().repaint(1000);
                } catch (NullPointerException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public AnimalViewList(String name, TreeMap<Integer, String> iDList, JPanel displayHere, Object constraints, int alignment, ArrayList<AnimalViewList> listList) {
        super(name, iDList, displayHere, constraints, alignment);
        this.listList = listList;
        listList.add(this);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(displayHere);
                    frame.getContentPane().validate();
                    frame.getContentPane().repaint(1000);
                } catch (NullPointerException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public void preview() {
//        if (ViewAnimalMenu.selectedList == 0) {
//            if (getListList().size() >= 2) {
//                getListList().remove(1);
//            }
//            String nextName = getLabelList().get(getSelectedLabel()).getText();
//            Collection<AnimalReal> animals = AnimalReal.animalsOwned.values();
//            ArrayList<String> nextList = new ArrayList<>();
//
//            for (AnimalReal ani : animals) {
//                if(ani.getAnimalClass().equals(nextName)) {
//                    if(!nextList.contains(ani.getFamily())) {
//                        nextList.add(ani.getFamily());
//                    }
//                }
//            }
//
//            Collections.sort(nextList);
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[1];
//            nextDisplay.removeAll();
//
//            AnimalViewList nextScroll = new AnimalViewList("Family", nextList, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, getListList());
//        } else if (ViewAnimalMenu.selectedList == 1) {
//            if (getListList().size() >= 3) {
//                getListList().remove(2);
//            }
//            String nextName = getLabelList().get(getSelectedLabel()).getText();
//            Collection<AnimalReal> animals = AnimalReal.animalsOwned.values();
//            ArrayList<String> nextList = new ArrayList<>();
//
//            for (AnimalReal ani : animals) {
//                if(ani.getFamily().equals(nextName)) {
//                    if(!nextList.contains(ani.getSpeciesName())) {
//                        nextList.add(ani.getSpeciesName());
//                    }
//                }
//            }
//
//            Collections.sort(nextList);
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[2];
//            nextDisplay.removeAll();
//
//            AnimalViewList nextScroll = new AnimalViewList("Species", nextList, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, getListList());
//        } else if (ViewAnimalMenu.selectedList == 2) {
//            if (getListList().size() >= 4) {
//                getListList().remove(3);
//            }
//            String nextName = getLabelList().get(getSelectedLabel()).getText();
//            TreeMap<Integer, String> animalNamesAndIDs = new TreeMap<>();
//
//            for (AnimalReal ani : AnimalReal.animalsOwned.values()) {
//                if(ani.getSpeciesName().equals(nextName)) {
//                    animalNamesAndIDs.put(ani.getID(), ani.getName());
//                }
//            }
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[3];
//            nextDisplay.removeAll();
//
//            AnimalViewList nextScroll = new AnimalViewList("NamedAnimals", animalNamesAndIDs, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, getListList());
//        } else if (ViewAnimalMenu.selectedList == 3) {
//            if (getListList().size() >= 5) {
//                getListList().remove(4);
//            }
//            LabelResizeWithNumber nextID = (LabelResizeWithNumber) getLabelList().get(getSelectedLabel());
//            AnimalReal currentAnimal = AnimalReal.getAnimalsOwned().get(nextID.getInteger());
//            ArrayList<String> nextList = new ArrayList<>();
//
//            Iterator iterator = currentAnimal.getDisplayAttributes().entrySet().iterator();
//
//            while(iterator.hasNext()) {
//                Map.Entry mapEntry = (Map.Entry)iterator.next();
//                nextList.add(mapEntry.getKey() + ": " + mapEntry.getValue());
//            }
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[5];
//            nextDisplay.removeAll();
//
//            AnimalViewList nextScroll = new AnimalViewList("AnimalInfo", nextList, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, getListList());
//        }
    }

    public void enter(){

        //Get the labelSelected
        //Get the displayPanel
        //displayPanel.add(subMenus[labelSelected], BorderLayout.North)
    }

    public ArrayList<AnimalViewList> getListList(){
        return listList;
    }
}
