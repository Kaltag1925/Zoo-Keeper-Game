package XML.StorageClasses;

import CustomMisc.Tree;

public abstract class AStorableObject implements IStorableObject {

    public static final String NODE_NAME = "nodeName";

    protected AStorableObject(Tree<StorableForm> storageTree) {
        fromStorage(storageTree);
    }

    protected AStorableObject() {
    }

    public Tree<StorableForm> toStorage() {
        StorableForm attributes = new StorableForm();
        Tree<StorableForm> storageTree = new Tree<>(attributes);

        fillStorageTree(storageTree);

        return storageTree;
    }

    protected abstract void fillStorageTree(Tree<StorableForm> storageTree);

    public abstract void fromStorage(Tree<StorableForm> storageTree);
}