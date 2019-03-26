package CustomSwing.Lists;

import CustomMisc.IResizeMaxViewListener;
import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class AnimalBuyList extends GameList implements IResizeMaxViewListener {

    private static Integer animalID;

    private ArrayList<AnimalBuyList> listList = new ArrayList<>();

    public AnimalBuyList(String name, ArrayList<String> list, JPanel displayHere, Object constraints, int alignment, ArrayList<AnimalBuyList> listList) {
        super(name, list, displayHere, constraints, alignment);
        this.listList = listList;

        AnimalBuyList holding = this;

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

    public AnimalBuyList(String name, ArrayList<String> list, JPanel displayHere, Object constraints, int alignment, ArrayList<AnimalBuyList> listList, Pair<String, Integer> lastNameAndInteger) {
        super(name, list, displayHere, constraints, alignment, lastNameAndInteger);
        this.listList = listList;

        AnimalBuyList holding = this;

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

    public AnimalBuyList(String name, ArrayList<String> list, JPanel displayHere, Object constraints, int alignment, ArrayList<AnimalBuyList> listList, Pair<String, Integer> lastNameAndInteger, GameList lastList) {
        super(name, list, displayHere, constraints, alignment, lastNameAndInteger, lastList);
        this.listList = listList;

        AnimalBuyList holding = this;

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

    public AnimalBuyList(String name, TreeMap<Integer, String> iDList, JPanel displayHere, Object constraints, int alignment, ArrayList<AnimalBuyList> listList, Pair<String, Integer> lastNameAndNumber, GameList lastList) {
        super(name, iDList, displayHere, constraints, alignment, lastNameAndNumber, lastList);
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

    public void preview(){
//        if (BuyAnimalMenu.selectedList == 0){
//            if (getListList().size() >= 2) {
//                getListList().remove(1);
//            }
//
//            String nextName = getLabelList().get(getSelectedLabel()).getText();
//            ArrayList<String> nextList = new ArrayList<>();
//            for (AnimalCompany company : AnimalCompany.animalCompanies.values()){
//                Collection<AnimalReal> animals = company.getAnimals().values();
//                for (AnimalReal ani : animals) {
//                    if (ani.getAnimalClass().equals(nextName)) {
//                        if(!nextList.contains(ani.getFamily())) {
//                            nextList.add(ani.getFamily());
//                        }
//                    }
//                }
//            }
//
//            Collections.sort(nextList);
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[1];
//            nextDisplay.removeAll();
//
//            AnimalBuyList nextScroll = new AnimalBuyList("Family", nextList, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, BuyAnimalMenu.list, new Pair(nextName, getSelectedLabel()));
//
//            for (LabelResize label : nextScroll.getLabelList()){
//                label.setTopParent();
//            }
//
//        } else if (BuyAnimalMenu.selectedList == 1) {
//            if (getListList().size() >= 3) {
//                getListList().remove(2);
//            }
//            String nextName = getLabelList().get(getSelectedLabel()).getText();
//            ArrayList<String> nextList = new ArrayList<>();
//            for (AnimalCompany company : AnimalCompany.animalCompanies.values()){
//                Collection<AnimalReal> animals = company.getAnimals().values();
//                for (AnimalReal ani : animals) {
//                    if (ani.getFamily().equals(nextName)) {
//                        if(!nextList.contains(ani.getSpeciesName())) {
//                            nextList.add(ani.getSpeciesName());
//                        }
//                    }
//                }
//            }
//
//            Collections.sort(nextList);
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[2];
//            nextDisplay.removeAll();
//
//            AnimalBuyList nextScroll = new AnimalBuyList("Species", nextList, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, getListList(), new Pair(nextName, getSelectedLabel()), BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList));
//
//            for (LabelResize label : nextScroll.getLabelList()){
//                label.setTopParent();
//            }
//
//        } else if (BuyAnimalMenu.selectedList == 2) {
//            if (getListList().size() >= 4) {
//                getListList().remove(3);
//            }
//            String nextName = getLabelList().get(getSelectedLabel()).getText();
//            TreeMap<Integer, String> animalNamesAndIDs = new TreeMap<>();
//
//            for (AnimalCompany company : AnimalCompany.animalCompanies.values()) {
//                for (AnimalReal ani : company.getAnimals().values()) {
//                    if(ani.getSpeciesName().equals(nextName)) {
//                        animalNamesAndIDs.put(ani.getID(), ani.getName());
//                    }
//                }
//            }
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[3];
//            nextDisplay.removeAll();
//
//            AnimalBuyList nextScroll = new AnimalBuyList("NamedAnimals", animalNamesAndIDs, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, getListList(), new Pair(nextName, getSelectedLabel()), BuyAnimalMenu.list.get(BuyAnimalMenu.selectedList));
//
//            for (LabelResize label : nextScroll.getLabelList()){
//                label.setTopParent();
//            }
//        }
    }


    //TODO: Remove enter() method
    public void enter() {
//        if (BuyAnimalMenu.selectedList == 0) {
//            ArrayList<String> nextList = new ArrayList<>();
//            for (AnimalCompany company : AnimalCompany.animalCompanies.values()){
//                Collection<AnimalReal> animals = company.getAnimals().values();
//                for (AnimalReal ani : animals) {
//                    if(!nextList.contains(ani.getAnimalClass())) {
//                        nextList.add(ani.getAnimalClass());
//                    }
//                }
//            }
//
//            Collections.sort(nextList);
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[0];
//
//            AnimalBuyList animalType = new AnimalBuyList("Animal Type", nextList, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, getListList());
//
//            for (LabelResize label : animalType.getLabelList()){
//                label.setTopParent();
//            }
//        } else if (BuyAnimalMenu.selectedList == 1){
//            String nextName = getLabelList().get(getSelectedLabel()).getText();
//            ArrayList<String> nextList = new ArrayList<>();
//            for (AnimalCompany company : AnimalCompany.animalCompanies.values()){
//                Collection<AnimalReal> animals = company.getAnimals().values();
//                for (AnimalReal ani : animals) {
//                    if (ani.getAnimalClass().equals(nextName)) {
//                        if(!nextList.contains(ani.getFamily())) {
//                            nextList.add(ani.getFamily());
//                        }
//                    }
//                }
//            }
//
//            Collections.sort(nextList);
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[1];
//            AnimalBuyList nextScroll = new AnimalBuyList("Family", nextList, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, BuyAnimalMenu.list);
//
//            BuyAnimalMenu.selectedList++;
//
//            for (LabelResize label : nextScroll.getLabelList()){
//                label.setTopParent();
//            }
//        } else if (BuyAnimalMenu.selectedList == 2) {
//            String nextName = getLabelList().get(getSelectedLabel()).getText();
//            ArrayList<String> nextList = new ArrayList<>();
//            for (AnimalCompany company : AnimalCompany.animalCompanies.values()){
//                Collection<AnimalReal> animals = company.getAnimals().values();
//                for (AnimalReal ani : animals) {
//                    if (ani.getFamily().equals(nextName)) {
//                        if(!nextList.contains(ani.getSpeciesName())) {
//                            nextList.add(ani.getSpeciesName());
//                        }
//                    }
//                }
//            }
//
//            Collections.sort(nextList);
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[2];
//            AnimalBuyList nextScroll = new AnimalBuyList("Species", nextList, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, getListList());
//
//
//            for (LabelResize label : nextScroll.getLabelList()){
//                label.setTopParent();
//            }
//        } else if (BuyAnimalMenu.selectedList == 3) {
//            String nextName = getLabelList().get(getSelectedLabel()).getText();
//            TreeMap<String, Integer> animalNamesAndIDs = new TreeMap<>();
//
//            for (AnimalCompany company : AnimalCompany.animalCompanies.values()) {
//                for (AnimalReal ani : company.getAnimals().values()) {
//                    if(ani.getSpeciesName().equals(nextName)) {
//                        animalNamesAndIDs.put(ani.getName(), ani.getID());
//                    }
//                }
//            }
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[3];
//            AnimalBuyList nextScroll = new AnimalBuyList("NamedAnimals", animalNamesAndIDs, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, getListList());
//
//            for (LabelResize label : nextScroll.getLabelList()){
//                label.setTopParent();
//            }
//        } else if (BuyAnimalMenu.selectedList == 4) {
//            LabelResizeWithNumber nextID = (LabelResizeWithNumber) getLabelList().get(getSelectedLabel());
//            animalID = nextID.getInteger();
//            AnimalReal currentAnimal = AnimalReal.getAllAnimals().get(nextID.getInteger());
//            ArrayList<String> nextList = new ArrayList<>();
//
//            Iterator iterator = currentAnimal.getDisplayAttributes().entrySet().iterator();
//
//            while(iterator.hasNext()) {
//                Map.Entry mapEntry = (Map.Entry)iterator.next();
//                nextList.add(mapEntry.getKey() + ": " + mapEntry.getValue());
//            }
//
//            nextList.add("Buy");
//
//            JPanel nextDisplay = (JPanel) getDisplayHere().getParent().getComponents()[5];
//            AnimalBuyList nextScroll = new AnimalBuyList("AnimalInfo", nextList, nextDisplay, BorderLayout.NORTH, SwingConstants.LEFT, getListList());
//
//            for (LabelResize label : nextScroll.getLabelList()){
//                label.setTopParent();
//            }
//        } else if (BuyAnimalMenu.selectedList == 5){
//            String nextName = getLabelList().get(getSelectedLabel()).getText();
//
//            if (nextName.equals("Buy")) {
//                AnimalReal.getAllAnimals().get(animalID).buy();
//                BuyAnimalMenu.selectedList = BuyAnimalMenu.selectedList - 2;
//            }
//
//        }
    }

    public ArrayList<AnimalBuyList> getListList(){
        return listList;
    }

}
