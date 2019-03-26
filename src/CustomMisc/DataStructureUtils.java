package CustomMisc;

import javafx.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class DataStructureUtils {

    public static void ensureSize(ArrayList<?> list, int size) {
        list.ensureCapacity(size);
        while (list.size() < size) {
            list.add(null);
        }
    }


    public static boolean checkSet(Set<?> list, Object object){
        for (Object items : list) {
            if (items.equals(object)) {
                return true;
            }
        }

        return false;
    }

    public static boolean checkArray(Collection<?> list, Object object){
        for (Object items : list) {
            if (items.equals(object)) {
                return true;
            }
        }

        return false;
    }

    public static <T, U> T findObjectWithPropertyWithMethod(Collection<T> list, U property, Method method, Object... args) {
        for (T t : list) {
            try {
                if (method.invoke(t, args).equals(property)) {
                    return t;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static <T, U> List<T> findAllObjectsWithPropertyWithMethod(Collection<T> list, U property, Method method, Object... args) {
        List<T> returnList = new ArrayList<>();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            try {
                if (method.invoke(t, args).equals(property)) {
                    returnList.add(t);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return returnList;
    }

    public static <T> Map.Entry<Class, T> findEntryFromClass(Map<Class, T> map, Class key) {
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<Class, T> entry = (Map.Entry<Class, T>) iterator.next();
            if ((entry.getKey()).isAssignableFrom(key)) {
                return entry;
            }
        }

        return null;
    }

    public static <T> T findObjectWithClass(Collection<T> collection, Class... keys) {
        Iterator<T> iterator = collection.iterator();
        while(iterator.hasNext()) {
            T next = iterator.next();
            Class nextClass = next.getClass();
            for (Class clazz : keys) {
                if(clazz.isAssignableFrom(nextClass)) {
                    return next;
                }
            }
        }

        return null;
    }

    public static <T, U> List<U> findAllObjectWithClass(Collection<T> collection, Class<U> key) {
        List<U> list = new ArrayList<>();
        Iterator<T> iterator = collection.iterator();
        while(iterator.hasNext()) {
            T next = iterator.next();
            Class nextClass = next.getClass();
            if(key.isAssignableFrom(nextClass)) {
                list.add((U) next);
            }
        }

        return list;
    }

    public static <T> Map.Entry<Pair<Class, Class>, T> findEntryFromClass(Map<Pair<Class, Class>, T> map, Pair<Class, Class> key) {
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<Pair<Class, Class>, T> entry = (Map.Entry<Pair<Class, Class>, T>) iterator.next();
            if (entry.getKey().getValue().isAssignableFrom(key.getValue()) && entry.getKey().getKey().equals(key.getKey())) {
                return entry;
            }
        }

        return null;
    }

    //TODO: Implement Pair Cloner
}
