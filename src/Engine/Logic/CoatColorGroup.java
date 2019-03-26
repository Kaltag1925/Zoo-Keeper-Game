package Engine.Logic;

import CustomMisc.GenericConverter;
import CustomMisc.RandomUtils;
import CustomMisc.SaveBuilder;
import CustomMisc.Tree;
import XML.StorageClasses.AStorableObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CoatColorGroup extends AStorableObject {

    private Boolean required = false;
    private Integer pick = 1;
    private Integer weight = 1;

    private HashMap<String, Integer> colors;

    //<editor-fold desc="Get Methods">
    public Boolean getRequired() {
        return required;
    }

    public Integer getPick() {
        return pick;
    }

    public Integer getWeight() {
        return weight;
    }

    public HashMap<String, Integer> getColors() {
        return colors;
    }
    //</editor-fold>

    public String pickColor() {
        int total = 0;
        for (int wgt : colors.values()) {
            total += wgt;
        }
        int random = MainData.mainData.getRandom().nextInt(total++);
        int current = 0;
        Iterator iterator = colors.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iterator.next();
            if (RandomUtils.isBetweenLowerInclusive(random, current, current + entry.getValue())) {
                return entry.getKey();
            } else {
                current += entry.getValue();
            }
        }

        throw new IndexOutOfBoundsException("Reached end of color list without finding suitable color.");
    }

    //<editor-fold desc="Save and Load">
    public CoatColorGroup(Tree<StorableForm> storageTree) {
        fromStorage(storageTree);
    }

    public Tree<StorableForm> createStorageObject(){
        return null;
    }

    private <T> T changeIfNotNull(T original, T set) {
        return set != null ? set : original;
    }

    public void fillStorageTree(Tree<StorableForm> storageTree){

    }


    public void fromStorage(Tree<StorableForm> storageTree) {
        StorableForm coatColorGroup = storageTree.getHead();

        required = changeIfNotNull(required, GenericConverter.convert(coatColorGroup.get("required"), Boolean.class));
        pick = changeIfNotNull(pick, GenericConverter.convert(coatColorGroup.get("pick"), Integer.class));
        weight = changeIfNotNull(weight, GenericConverter.convert(coatColorGroup.get("weight"), Integer.class));

        Tree<StorableForm> colorsTree = storageTree.findFirstTreeFromMethod("Colors", StorableForm::getNodeName);
        colors = SaveBuilder.read(colorsTree);
    }
    //</editor-fold>
}
