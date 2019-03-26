package Engine.Logic.Assignments.Actions.Collections;

import Engine.Logic.Assignments.Actions.Action;

import java.util.Collection;

public class AddToCollection<T> implements Action {

    private Collection<T> collection;
    private T what;

    public AddToCollection(Collection<T> collection, T what) {
        this.collection = collection;
        this.what = what;
    }

    public boolean update() {
        collection.add(what);
        return true;
    }
}