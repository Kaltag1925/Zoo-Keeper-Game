package CustomSwing.Lists;

import XML.CreateSaveFolder;

import javax.swing.*;
import java.util.ArrayList;

public class SaveSelectionList extends GameList {

    private ArrayList<SaveSelectionList> listList = new ArrayList<>();

    public SaveSelectionList(String name, ArrayList<String> list, JPanel displayHere, Object constraints, int alignment, ArrayList<SaveSelectionList> listList) {
        super(name, list, displayHere, constraints, alignment);
        this.listList = listList;

        SaveSelectionList holding = this;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    listList.add(holding);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(displayHere);
                    frame.getContentPane().validate();
                    frame.getContentPane().repaint(1000);
                } catch (NullPointerException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public String enter() throws CloneNotSupportedException {
        String nextName = getLabelList().get(getSelectedLabel()).getText();
        if (!nextName.equals("Create New Save")) {
            if(CreateSaveFolder.loadSave(nextName)) {
                return "Loading file";
            } else {
                return "File not found";
            }
        }

        return "Creating new file";
    }
}
