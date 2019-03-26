package CustomMisc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class DataStructureCloner {

    protected DataStructureCloner() {

    }

    private static HashMap<Class, Method> cloners = new HashMap<>();
    static {
        Method[] methods = DataStructureCloner.class.getDeclaredMethods();
        for (Method meth : methods) {
            if (meth.getName().toCharArray().length > 5) {
                Class key = meth.getReturnType();
                cloners.put(key, meth);
            }
        }
    }

    private static ArrayList<Class> primativeClasses = new ArrayList<>();
    static {
        primativeClasses.add(Boolean.class);
        primativeClasses.add(Character.class);
        primativeClasses.add(Byte.class);
        primativeClasses.add(Short.class);
        primativeClasses.add(Integer.class);
        primativeClasses.add(Long.class);
        primativeClasses.add(Float.class);
        primativeClasses.add(Double.class);
        primativeClasses.add(Void.class);
        primativeClasses.add(String.class);
    }

    public static <T> T clone(T t) {
        if (t != null) {
            Map.Entry<Class, Method> clonerEntry = DataStructureUtils.findEntryFromClass(cloners, t.getClass());
            if(clonerEntry == null) {
                throw new UnsupportedOperationException("Could not find cloner for " + t.getClass());
            }

            try {
                return (T) clonerEntry.getValue().invoke(DataStructureCloner.class, t);
            } catch (Exception ex) {
                throw new RuntimeException("Could not clone " + t.getClass());
            }
        }

        return null;
    }

    private static <T, U> Map cloneMap(Map<T, U> map) {
        Class storageClass = map.getClass();
        Map.Entry<T, U> first = map.entrySet().iterator().next();
        Class<T> keyClass = (Class<T>) first.getKey().getClass();
        Class<U> valueClass = (Class<U>) first.getValue().getClass();

        Map<T, U> mapCopy = null;
        try {
            mapCopy = map.getClass().cast(storageClass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Map.Entry<Class, Method> clonerEntry = DataStructureUtils.findEntryFromClass(cloners, valueClass);
        if (clonerEntry == null) {
            if (DataStructureUtils.checkArray(primativeClasses, valueClass)) {
                Iterator iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<T, U> entry = (Map.Entry<T, U>) iterator.next();
                    T key = entry.getKey();
                    T keyCopy = key;
                    U value = entry.getValue();
                    U valueCopy = value;

                    mapCopy.put(keyCopy, valueCopy);
                }
            } else {
                Iterator iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<T, U> entry = (Map.Entry<T, U>) iterator.next();
                    T key = entry.getKey();
                    T keyCopy = key;
                    ICreateCopy value = (ICreateCopy) entry.getValue();
                    ICreateCopy valueCopy = value.createCopy();

                    mapCopy.put(keyCopy, (U) valueCopy);
                }
            }
        } else {
            Method cloner = clonerEntry.getValue();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<T, U> entry = (Map.Entry<T, U>) iterator.next();
                T key = entry.getKey();
                T keyCopy = key;
                U value = entry.getValue();
                U valueCopy = null;
                try {
                    valueCopy = (U) cloner.invoke(null, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                mapCopy.put(keyCopy, valueCopy);
            }
        }

        return mapCopy;
    }

    private static <T> Collection cloneCollection(Collection<T> collection) {
        Class storageClass = collection.getClass();
        T first = collection.iterator().next();
        Class<T> valueClass = (Class<T>) first.getClass();

        Collection<T> collectionCopy = null;
        try {
            collectionCopy = collection.getClass().cast(storageClass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Map.Entry<Class, Method> clonerEntry = DataStructureUtils.findEntryFromClass(cloners, valueClass);
        if (clonerEntry == null) {
            if (DataStructureUtils.checkArray(primativeClasses, valueClass)) {
                Iterator iterator = collection.iterator();
                while (iterator.hasNext()) {
                    T value = (T) iterator.next();
                    T valueCopy = value;

                    collectionCopy.add(valueCopy);
                }
            } else {
                Iterator iterator = collection.iterator();
                while (iterator.hasNext()) {
                    ICreateCopy value = (ICreateCopy) iterator.next();
                    ICreateCopy valueCopy = value.createCopy();

                    collectionCopy.add((T) valueCopy);
                }
            }
        } else {
            Method cloner = clonerEntry.getValue();
            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                T value = (T) iterator.next();
                T valueCopy = null;
                try {
                    valueCopy = (T) cloner.invoke(null, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                collectionCopy.add(valueCopy);
            }
        }

        return collectionCopy;
    }
}