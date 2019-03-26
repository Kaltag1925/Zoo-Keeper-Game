package UserInterface.Panels.ControlPanel;

import Engine.Logic.MainData;
import UserInterface.FontManager;
import UserInterface.Panels.GamePanel;

import java.awt.*;
import java.util.LinkedList;

public class ControlPanelManager {
    private LinkedList<ControlPanel> descriptionBreadCrumbs = new LinkedList<>();

    public LinkedList<ControlPanel> getDescriptionBreadCrumbs() {
        return descriptionBreadCrumbs;
    }

    //<editor-fold desc="Current Panels">
    private ControlPanel currentControlPanel;

    public ControlPanel getCurrentControlPanel() {
        return currentControlPanel;
    }

    public void setCurrentControlPanel(ControlPanel currentControlPanel) {
        this.currentControlPanel = currentControlPanel;
    }

    private GamePanel currentViewPanel;

    public GamePanel getCurrentViewPanel() {
        return currentViewPanel;
    }

    public void setCurrentViewPanel(GamePanel currentViewPanel) {
        this.currentViewPanel = currentViewPanel;
    }

    private GamePanel currentShortDescriptionPanel;

    public GamePanel getCurrentShortDescriptionPanel() {
        return currentShortDescriptionPanel;
    }

    public void setCurrentShortDescriptionPanel(GamePanel currentShortDescriptionPanel) {
        this.currentShortDescriptionPanel = currentShortDescriptionPanel;
    }
    //</editor-fold>

//    //<editor-fold desc="Main Controls">
//    private MainControls mainControls;
//
//    public MainControls getMainControls() {
//        return mainControls;
//    }
//    //</editor-fold>
//
//    //<editor-fold desc="Animal Panel">
//    private AnimalMainPanel animalMainPanel;
//
//    public AnimalMainPanel getAnimalMainPanel() {
//        return animalMainPanel;
//    }
//    //</editor-fold>
//
//    //<editor-fold desc="Select Zone Panel">
//    private SelectZonePanel selectZonePanel;
//
//    public SelectZonePanel getSelectZonePanel() {
//        return selectZonePanel;
//    }
//    //</editor-fold>
//
//    //<editor-fold desc="Deliveries Panel">
//    private DeliveriesPanel deliveriesPanel;
//
//    public DeliveriesPanel getDeliveriesPanel() {
//        return deliveriesPanel;
//    }
//    //</editor-fold>
//
//    //<editor-fold desc="Create Zone Panel">
//    private CreateZonePanel createZonePanel;
//
//    public CreateZonePanel getCreateZonePanel() {
//        return createZonePanel;
//    }
//    //</editor-fold>
//
//    //<editor-fold desc="Building Panel">
//    private BuildingPanel buildingPanel;
//
//    public BuildingPanel getBuildingPanel() {
//        return buildingPanel;
//    }
//    //</editor-fold>
//
//    //<editor-fold desc="List Objects Panel">
//    private ListObjectsPanel listObjectsPanel;
//
//    public ListObjectsPanel getListObjectsPanel() {
//        return listObjectsPanel;
//    }
//    //</editor-fold>
//
//    //<editor-fold desc="Animal Panel">
//    private AnimalPanel animalPanel;
//
//    public AnimalPanel getAnimalPanel() {
//        return animalPanel;
//    }
//    //</editor-fold>

    public GridBagConstraints CONTROL_CONSTRAINTS = new GridBagConstraints();

    {
        CONTROL_CONSTRAINTS.fill = GridBagConstraints.BOTH;
        CONTROL_CONSTRAINTS.weightx = 2;
        CONTROL_CONSTRAINTS.weighty = 8;
        CONTROL_CONSTRAINTS.gridx = 1;
        CONTROL_CONSTRAINTS.gridy = 0;
        FontManager fontManager = MainData.mainData.getFontManager();
        CONTROL_CONSTRAINTS.insets = new Insets(fontManager.getFontHeight(), 0, fontManager.getFontHeight(), fontManager.getFontWidth());
    }

    public ControlPanelManager() {
//        mainControls = new MainControls();
//        animalMainPanel = new AnimalMainPanel();
//        selectZonePanel = new SelectZonePanel();
//        deliveriesPanel = new DeliveriesPanel();
//        createZonePanel = new CreateZonePanel();
//        buildingPanel = new BuildingPanel();
//        listObjectsPanel = new ListObjectsPanel();
//        animalPanel = new AnimalPanel();
    }

    public void trimBreadCrumbs(int size) {
        while (descriptionBreadCrumbs.size() > size) {
            descriptionBreadCrumbs.getLast().close();
        }
    }
}