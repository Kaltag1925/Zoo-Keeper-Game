package UserInterface.Panels.DescriptionPanel;

import Engine.Logic.MainData;
import UserInterface.FontManager;

import java.awt.*;

public class DescriptionPanelManager {

    //<editor-fold desc="Current Description Panel">
    private DescriptionPanel currentShortDescriptionPanel;

    public DescriptionPanel getCurrentShortDescriptionPanel() {
        return currentShortDescriptionPanel;
    }

    public void setCurrentShortDescriptionPanel(DescriptionPanel currentShortDescriptionPanel) {
        this.currentShortDescriptionPanel = currentShortDescriptionPanel;
    }

    private DescriptionPanel currentLongDescriptionPanel;

    public DescriptionPanel getCurrentLongDescriptionPanel() {
        return currentLongDescriptionPanel;
    }

    public void setCurrentLongDescriptionPanel(DescriptionPanel currentLongDescriptionPanel) {
        this.currentLongDescriptionPanel = currentLongDescriptionPanel;
    }
    //</editor-fold>

//    //<editor-fold desc="Object Description Panel">
//    private ObjectShortDescriptionPanel objectDescriptionPanel;
//
//    public ObjectShortDescriptionPanel getObjectDescriptionPanel() {
//        return objectDescriptionPanel;
//    }
//    //</editor-fold>
//
//    //<editor-fold desc="Animal Description Panel">
//    private AnimalDescriptionPanel animalDescriptionPanel;
//
//    public AnimalDescriptionPanel getAnimalDescriptionPanel() {
//        return animalDescriptionPanel;
//    }
//    //</editor-fold>

    //<editor-fold desc="Constraints">
    private static GridBagConstraints shortDescriptionConstraints = new GridBagConstraints();
    static {
        shortDescriptionConstraints.fill = GridBagConstraints.BOTH;
        shortDescriptionConstraints.weightx = 2;
        shortDescriptionConstraints.weighty = 2; //TODO: Weight needs to be dynamic. Class that extends GBC?
        shortDescriptionConstraints.gridx = 1;
        shortDescriptionConstraints.gridy = 1;
        FontManager fontManager = MainData.mainData.getFontManager();
        shortDescriptionConstraints.insets = new Insets(0, 0, fontManager.getFontHeight(), fontManager.getFontWidth());
    }

    public static GridBagConstraints getShortDescriptionConstraints() {
        return shortDescriptionConstraints;
    }

    private static GridBagConstraints longDescriptionConstraints = new GridBagConstraints();
    static {
        longDescriptionConstraints.fill = GridBagConstraints.BOTH;
        longDescriptionConstraints.weightx = 8;
        longDescriptionConstraints.weighty = 1;
        longDescriptionConstraints.gridy = 0;
        longDescriptionConstraints.gridx = 0;
        FontManager fontManager = MainData.mainData.getFontManager();
        longDescriptionConstraints.insets = new Insets(fontManager.getFontHeight(), fontManager.getFontWidth(), fontManager.getFontHeight(), fontManager.getFontWidth());
    }

    public static GridBagConstraints getLongDescriptionConstraints() {
        return longDescriptionConstraints;
    }
    //</editor-fold>

    public DescriptionPanelManager() {
//        objectDescriptionPanel = new ObjectShortDescriptionPanel();
//        animalDescriptionPanel = new AnimalDescriptionPanel();
    }
}
