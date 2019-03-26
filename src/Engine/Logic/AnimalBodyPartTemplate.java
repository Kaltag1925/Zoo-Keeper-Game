package Engine.Logic;

import CustomMisc.Tree;
import XML.StorageClasses.AStorableObject;
import XML.FileLocations;
import XML.StorageClasses.StorageXML;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimalBodyPartTemplate extends AStorableObject {
    private String name;
    private Tree<AnimalBodyPart> template;

    private static HashMap<String, AnimalBodyPartTemplate> templates = new HashMap<>();

    public Tree<AnimalBodyPart> getTemplate() {
        return template;
    }

    public AnimalBodyPart getPartByName(String name){
        return template.findFirstKeyFromMethod(name, AnimalBodyPart::getName);

    }

    private String getName(){
        return name;
    }

    public static HashMap<String, AnimalBodyPartTemplate> getTemplates() {
        return templates;
    }

    private void setTemplate(Tree<AnimalBodyPart> template){
        this.template = template;
    }

    //<editor-fold desc="Save and Load"
    private AnimalBodyPartTemplate(Tree<StorableForm> storageTree){
        fromStorage(storageTree);

        template = AnimalBodyPart.createBodyFromStorage(storageTree, null);

        templates.put(name, this);
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree){

    }

    @Override
    public void fromStorage(Tree<StorableForm> storedDataTree){
        StorableForm storedData = storedDataTree.getHead();
        name = storedData.get("name");
    }

    public static void load(){
        new StorageAnimalBodyTemplates().load();
    }

    private static class StorageAnimalBodyTemplates extends StorageXML {

        public StorageAnimalBodyTemplates() {
        }

        public void load(){
            CopyOnWriteArrayList<Tree<StorableForm>> data = super.load(FileLocations.animalBodyPartTemplates);
            for (Tree<StorableForm> dataTree : data) {
                for (Tree<StorableForm> templateTree : dataTree.getChildren().values()) {
                    if (templateTree.getHead().getNodeName().equals("Template")) {
                        new AnimalBodyPartTemplate(templateTree);
                    }
                }
            }
        }


        public void save() {
        }
    }
    //</editor-fold>
}