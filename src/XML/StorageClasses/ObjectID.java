package XML.StorageClasses;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ObjectID {

    private static HashMap<Object, Integer> savedObjectsAndIDs = new HashMap<>();

    public static int getObjectID(Object obj) {
        Integer id = savedObjectsAndIDs.get(obj);
        if (id != null) {
            return id;
        } else {
            id = savedObjectsAndIDs.size();
            savedObjectsAndIDs.put(obj, savedObjectsAndIDs.size());
            return id;
        }
    }

    public static void resetSave() {
        savedObjectsAndIDs = new HashMap<>();
    }

    private static HashMap<Integer, Object> loadedIDsAndObjects = new HashMap<>();

    public static void resetLoad() {
        loadedIDsAndObjects = new HashMap<>();
    }

    public static Object getObject(int id) throws NoSuchElementException {
        Object returnValue = loadedIDsAndObjects.get(id);
        if (returnValue != null) {
            return returnValue;
        } else {
            throw new NoSuchElementException("Could not find object for ID: " + id);
        }
    }
}
