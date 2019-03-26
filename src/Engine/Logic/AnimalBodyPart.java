package Engine.Logic;

import CustomMisc.ICreateCopy;
import CustomMisc.Tree;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import XML.StorageClasses.AStorableObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimalBodyPart extends AStorableObject implements ICreateCopy {

    public static final int PART_POSITION = 0;
    public static final int PART_ATTACHMENT = 1;
    public static final int PART_TEMPLATE = 2;

    private String name;
    private AnimalBodyPart parent;
    private ArrayList<AnimalBodyPart> children = new ArrayList<>();
    private AnimalReal animal;
    private HashMap<String, String> tags = new HashMap<>();
    private HashMap<Integer, Object> tagsNew = new HashMap<>(); //TODO: Implement this
    private String tagsString;

    //<editor-fold desc="Initialization">
    private AnimalBodyPart() {

    }

    public AnimalBodyPart(String name, AnimalBodyPart parent, AnimalReal animal) {
        this.name = name;

        if(parent != null) {
            this.parent = parent;
        } else {
            animal.setBaseBodyPart(this);
        }

        this.animal = animal;
    }

    @Deprecated
    public AnimalBodyPart(StorableForm storedData, AnimalBodyPartTemplate template) {
        name = storedData.get("name");
        if (storedData.get("parent") != null) {
            parent = template.getTemplate().findFirstKeyFromMethod(storedData.get("parent"), AnimalBodyPart::getName);
            parent.addChild(this);
        }

        String tagsLoadForm = storedData.get("tags");
        tagsString = storedData.get("tags");
        while (tagsLoadForm.contains(",")) {
            while (tagsLoadForm.contains(",")) {
                int indexEnd = tagsLoadForm.indexOf(',');
                String tag = tagsLoadForm.substring(0, indexEnd);
                if (tag.contains(":")) {
                    int tagColon = tag.indexOf(':');
                    String tagPrefix = tag.substring(0, tagColon);
                    if (tagPrefix.equals("position")) {
                        tags.put("position", tag.substring(tagColon + 1, tag.length()));
                    }
                    if (tagPrefix.equals("template")) {
                        tags.put("template", tag.substring(tagColon + 1, tag.length()));
                    }
                }

                tagsLoadForm = tagsLoadForm.substring(indexEnd + 1, tagsLoadForm.length());
            }
        }
    }

    @Deprecated
    public AnimalBodyPart(StorableForm storedData, Tree<AnimalBodyPart> partTree) {
        name = storedData.get("name");
        if (storedData.get("parent") != null) {
            parent = partTree.findFirstKeyFromMethod(storedData.get("parent"), AnimalBodyPart::getName);
            parent.addChild(this);
        }

        String tagsLoadForm = storedData.get("tags");
        tagsString = storedData.get("tags");
        while (tagsLoadForm.contains(",")) {
            while (tagsLoadForm.contains(",")) {
                int indexEnd = tagsLoadForm.indexOf(',');
                String tag = tagsLoadForm.substring(0, indexEnd);
                if (tag.contains(":")) {
                    int tagColon = tag.indexOf(':');
                    String tagPrefix = tag.substring(0, tagColon);
                    if (tagPrefix.equals("position")) {
                        tags.put("position", tag.substring(tagColon + 1, tag.length()));
                    }
                    if (tagPrefix.equals("template")) {
                        tags.put("template", tag.substring(tagColon + 1, tag.length()));
                    }
                }

                tagsLoadForm = tagsLoadForm.substring(indexEnd + 1, tagsLoadForm.length());
            }
        }
    }

    @Deprecated
    public AnimalBodyPart(StorableForm storedData, boolean forTemplate) {
        name = storedData.get("name");

        if (storedData.get("tags") != null) {
            String tagsLoadForm = storedData.get("tags");
            tagsString = storedData.get("tags");
            while (tagsLoadForm.contains(",")) {
                while (tagsLoadForm.contains(",")) {
                    int indexEnd = tagsLoadForm.indexOf(',');
                    String tag = tagsLoadForm.substring(0, indexEnd);
                    if(tag.contains(":")) {
                        int tagColon = tag.indexOf(':');
                        String tagPrefix = tag.substring(0, tagColon);
                        if(tagPrefix.equals("position")) {
                            tags.put("position", tag.substring(tagColon + 1, tag.length()));
                        }
                        if(tagPrefix.equals("template")) {
                            tags.put("template", tag.substring(tagColon + 1, tag.length()));
                        }
                    }

                    tagsLoadForm = tagsLoadForm.substring(indexEnd + 1, tagsLoadForm.length());
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Get Methods">
    public String getName(){
        return name;
    }

    public AnimalBodyPart getParent() {
        return parent;
    }

    public String getTagsString(){
        return tagsString;
    }

    public HashMap<String, String> getTags() {
        return tags;
    }
    //</editor-fold>

//    public AnimalBodyPart getChild(String name){
//        String child;
//        ArrayList<AnimalBodyPart> childrenOfThis = new ArrayList<>();
//
//        for (AnimalBodyPart part : animal.getBodyPartMap().()){
//            if (part.getParent().equals(this)) {
//                childrenOfThis.add(this);
//            }
//        }
//        if (childrenOfThis.size() != 0){
//            for (AnimalBodyPart part : childrenOfThis){
//                if (part.getChild(name) != null){
//                    return part.getChild(name);
//                }
//            }
//        }
//
//        return null;
//    }

    public void setName(String nextName){
        name = nextName;
    }

    public void setParent(AnimalBodyPart parent){
        this.parent = parent;
    }

    public void addChild(AnimalBodyPart child) {
        children.add(child);
        child.parent = this;
    }

    //<editor-fold desc="Save and Load">
    private AnimalBodyPart(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);
    }

    private AnimalBodyPart(Tree<StorableForm> storableForm, AnimalBodyPart parent) {
        fromStorage(storableForm);
        if (parent != null) {
            this.parent = parent;
            parent.addChild(this);
        }
    }

    public static Tree<AnimalBodyPart> createBodyFromStorage(Tree<StorableForm> bodyStorage, AnimalBodyPart parent) {
        AnimalBodyPart main = new AnimalBodyPart(bodyStorage);
        Tree<AnimalBodyPart> bodyPartMap = new Tree<>(main);

        if (main.getTags().get("template") != null) {
            for (Tree<AnimalBodyPart> template : AnimalBodyPartTemplate.getTemplates().get(main.getTags().get("template")).getTemplate().getChildren().values()) {
                Tree<AnimalBodyPart> templateCopy = template.createCopy();
                bodyPartMap.addTreeChild(templateCopy, true);
                goThroughBodyPartsParent(main, bodyPartMap);

                if(main.getTags().get("position") != null) {
                    for (Tree<AnimalBodyPart> children : bodyPartMap.getChildren().values()) {
                        goThroughBodyPartsPosition(main, children);
                    }
                }
            }
        }

        if (bodyStorage.hasChildren()) {
            for (Tree<StorableForm> childTree : bodyStorage.getChildren().values()) {
                if (childTree.getHead().getNodeName().equals("BodyPart")) {
                    bodyPartMap.addTreeChild(loadThroughTree(childTree, main), true);
                }
            }
        }
        return bodyPartMap;
    }

    private static void goThroughBodyPartsPosition(AnimalBodyPart part, Tree<AnimalBodyPart> currentTree) {
        currentTree.getHead().setName(part.getTags().get("position") + " " + currentTree.getHead().getName());
        if (currentTree.getChildren().size() != 0) {
            for (Tree<AnimalBodyPart> nextBodyTree : currentTree.getChildren().values()) {
                goThroughBodyPartsPosition(part, nextBodyTree);
            }
        }
    }

    private static void goThroughBodyPartsParent(AnimalBodyPart previousPart, Tree<AnimalBodyPart> currentTree) {
        previousPart.addChild(currentTree.getHead());
        if (currentTree.getChildren().size() != 0) {
            for (Tree<AnimalBodyPart> nextBodyTree : currentTree.getChildren().values()) {
                goThroughBodyPartsParent(currentTree.getHead(), nextBodyTree);
            }
        }
    }

    private static Tree<AnimalBodyPart> loadThroughTree(Tree<StorableForm> currentTree, AnimalBodyPart lastBodyPart) {
        AnimalBodyPart main = new AnimalBodyPart(currentTree, lastBodyPart);
        Tree<AnimalBodyPart> returnTree = new Tree<>(main);

        if (main.getTags().get("template") != null) {
            for (Tree<AnimalBodyPart> template : AnimalBodyPartTemplate.getTemplates().get(main.getTags().get("template")).getTemplate().getChildren().values()) {
                Tree<AnimalBodyPart> templateCopy = template.createCopy();
                returnTree.addTreeChild(templateCopy, true);
                goThroughBodyPartsParent(main, returnTree);

                if(main.getTags().get("position") != null) {
                    for (Tree<AnimalBodyPart> children : returnTree.getChildren().values()) {
                        goThroughBodyPartsPosition(main, children);
                    }
                }
            }
        }


        if (currentTree.hasChildren()) {
            for (Tree<StorableForm> childTree : currentTree.getChildren().values()) {
                if (childTree.getHead().getNodeName().equals("BodyPart")) {
                    returnTree.addTreeChild(loadThroughTree(childTree, main), true);
                }
            }
        }

        return returnTree;
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();
        head.put("nodeName", "BodyPart");
        head.put("name", String.valueOf(name));

        for (AnimalBodyPart childBodyPart : children) {
            Tree<StorableForm> childTree = new Tree<>(new StorableForm());
            storageTree.addTreeChild(childTree, false);
            childBodyPart.fillStorageTree(childTree);
        }

        head.put("tags", tagsString);
    }

    @Override
    public void fromStorage(Tree<StorableForm> storedDataTree) {
        StorableForm storedData = storedDataTree.getHead();
        name = storedData.get("name");

        if (storedData.get("tags") != null) {
            String tagsLoadForm = storedData.get("tags");
            tagsString = storedData.get("tags");
            while (tagsLoadForm.contains(",")) {
                while (tagsLoadForm.contains(",")) {
                    int indexEnd = tagsLoadForm.indexOf(',');
                    String tag = tagsLoadForm.substring(0, indexEnd);
                    if(tag.contains(":")) {
                        int tagColon = tag.indexOf(':');
                        String tagPrefix = tag.substring(0, tagColon);
                        if(tagPrefix.equals("position")) {
                            tags.put("position", tag.substring(tagColon + 1, tag.length()));
                        }
                        if(tagPrefix.equals("template")) {
                            tags.put("template", tag.substring(tagColon + 1, tag.length()));
                        }
                    }

                    tagsLoadForm = tagsLoadForm.substring(indexEnd + 1, tagsLoadForm.length());
                }
            }
        }
    }
    //</editor-fold>

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        AnimalBodyPart part = (AnimalBodyPart) obj;

        if (!(getName().equals(part.getName()))) {
            return false;
        }

        return true;
    }

    @Override
    public ICreateCopy createCopy() {
        AnimalBodyPart copy = new AnimalBodyPart();
        copy.name = name;
        copy.tagsString = tagsString;
        copy.tags.putAll(tags);

        return copy;
    }
}
