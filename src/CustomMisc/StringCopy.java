package CustomMisc;

import XML.StorageClasses.AStorableObject;

public class StringCopy extends AStorableObject implements ICreateCopy, Cloneable {

    private String info;

    public StringCopy(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public void fromStorage(Tree<StorableForm> storedDataTree) {
        StorableForm storedData = storedDataTree.getHead();
        info = storedData.get("info");
    }

    public Tree<StorableForm> createStorageObject() {
        StorableForm attributes = new StorableForm();
        Tree<StorableForm> stringTree = new Tree<>(attributes);
        attributes.put("nodeName", "StringCopy");

        attributes.put("info", info);

        return stringTree;
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {

    }

    @Override
    public ICreateCopy createCopy() {
        return new StringCopy(info);
    }

    @Override
    public Object clone() {
        return new StringCopy(info);
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

        StringCopy part = (StringCopy) obj;

        if (!(getInfo().equals(part.getInfo()))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString(){
        return info;
    }
}
