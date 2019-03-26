package Engine.GameObjects;

import CustomMisc.Tree;
import Engine.Logic.Ticks.IUpdateable;
import Engine.Logic.Ticks.Tick;
import XML.StorageClasses.AStorableObject;

public class GameObject extends AStorableObject implements IUpdateable{

    boolean deleting = false;

    public GameObject() {
        Tick.addToUpdateList(this);
    }

    @Override
    public void update() {

    }

    public boolean isDeleting() {
        return deleting;
    }

    public GameObject(Tree<StorableForm> storageTree) {
        fromStorage(storageTree);
    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {

    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {

    }

    public void assignReferences(Tree<StorableForm> storageTree) {

    }

    public void deleteObject() {
        Tick.removeFromUpdateList(this);
        deleting = true;
    }
}
