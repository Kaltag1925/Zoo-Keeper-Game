package Engine.Logic.Assignments.Actions.Collections;

import Engine.Logic.Assignments.Actions.Action;

import java.util.Collection;

public class ClearCollection<T> implements Action {

    private Collection<T> collection;

    public ClearCollection(Collection<T> collection) {
        this.collection = collection;
    }

    public boolean update() {
        collection.clear();
        return true;
    }
}
