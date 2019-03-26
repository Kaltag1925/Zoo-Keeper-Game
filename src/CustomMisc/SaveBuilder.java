package CustomMisc;

import XML.StorageClasses.AStorableObject;
import XML.StorageClasses.IStorableObject;
import javafx.util.Pair;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SaveBuilder {

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

    public SaveBuilder() {

    }

    //<editor-fold desc="Saving">
    public static <U> Tree<IStorableObject.StorableForm> build(U obj, String nodeName) {
        IStorableObject.StorableForm head = new IStorableObject.StorableForm();
        head.put("nodeName", nodeName);
        Tree<IStorableObject.StorableForm> returnTree = new Tree<>(head);

        if(Map.class.isAssignableFrom(obj.getClass())) {
            Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> data = mapSaveBuilder((Map) obj, nodeName);
            head.put("dataSaved", data.getKey());
            returnTree.addTreeCollection(data.getValue());
        } else if(Pair.class.isAssignableFrom(obj.getClass())) {
            Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> data = pairSaveBuilder((Pair) obj, nodeName);
            head.put("dataSaved", data.getKey());
            returnTree.addTreeCollection(data.getValue());
        } else if(Collection.class.isAssignableFrom(obj.getClass())) {
            Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> data = collectionSaveBuilder((Collection) obj, nodeName);
            head.put("dataSaved", data.getKey());
            returnTree.addTreeCollection(data.getValue());
        } else {
            throw new UnsupportedOperationException("Could not find builder for " + obj.getClass());
        }

        return returnTree;
    }

    //<editor-fold desc="Builders">
    private static <T, U> Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> mapSaveBuilder(Map<T, U> map, String nodeName) {
        ArrayList<Tree<IStorableObject.StorableForm>> dataArray = new ArrayList<>();


        Map.Entry<T, U> entry = map.entrySet().iterator().next();
        Class<T> keyClass = (Class<T>) entry.getKey().getClass();
        Class<U> valueClass = (Class<U>) entry.getValue().getClass();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(map.getClass().getName());
        stringBuilder.append(":");
        stringBuilder.append(keyClass.getName());
        stringBuilder.append(",");
        stringBuilder.append(valueClass.getName());

        Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> returnPair = new Pair<>(stringBuilder.toString(), dataArray);

        if(Map.class.isAssignableFrom(valueClass)) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entrySec = (Map.Entry) iterator.next();
                T key = (T) entrySec.getKey();
                Map<?, ?> value = (Map<?, ?>) entrySec.getValue();

                if(value != null && value.size() != 0) {
                    IStorableObject.StorableForm data = new IStorableObject.StorableForm();
                    data.put("nodeName", nodeName);
                    data.put("key", key.toString());
                    Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

                    Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> mapData = mapSaveBuilder(value, nodeName);
                    data.put("dataSaved", mapData.getKey());
                    tree.addTreeCollection(mapData.getValue());

                    dataArray.add(tree);
                }
            }
        } else if(Pair.class.isAssignableFrom(valueClass)) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entrySec = (Map.Entry) iterator.next();
                T key = (T) entrySec.getKey();
                Pair<?, ?> value = (Pair<?, ?>) entrySec.getValue();

                if(value != null && value.getValue() != null && value.getKey() != null) {
                    IStorableObject.StorableForm data = new IStorableObject.StorableForm();
                    data.put("nodeName", nodeName);
                    data.put("key", key.toString());
                    Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

                    Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> pairData = pairSaveBuilder(value, nodeName);
                    data.put("dataSaved", pairData.getKey());
                    tree.addTreeCollection(pairData.getValue());

                    dataArray.add(tree);
                }
            }
        } else if(Collection.class.isAssignableFrom(valueClass)) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entrySec = (Map.Entry) iterator.next();
                T key = (T) entrySec.getKey();
                Collection<?> value = (Collection<?>) entrySec.getValue();

                if(value != null && value.size() != 0) {
                    IStorableObject.StorableForm data = new IStorableObject.StorableForm();
                    data.put("nodeName", nodeName);
                    data.put("key", key.toString());
                    Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

                    Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> collectionData = collectionSaveBuilder(value, nodeName);
                    data.put("dataSaved", collectionData.getKey());
                    tree.addTreeCollection(collectionData.getValue());

                    dataArray.add(tree);
                }
            }
        } else if(AStorableObject.class.isAssignableFrom(valueClass)) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entrySec = (Map.Entry) iterator.next();
                T key = (T) entrySec.getKey();
                U value = (U) entrySec.getValue();

                if(value != null) {
                    IStorableObject.StorableForm data = new IStorableObject.StorableForm();
                    data.put("nodeName", nodeName);
                    data.put("key", key.toString());
                    Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

                    tree.addTreeChild(((AStorableObject) value).toStorage(), false);

                    dataArray.add(tree);
                }
            }
        } else {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entrySec = (Map.Entry) iterator.next();
                T key = (T) entrySec.getKey();
                U value = (U) entrySec.getValue();

                if(value != null) {
                    IStorableObject.StorableForm data = new IStorableObject.StorableForm();
                    data.put("nodeName", nodeName);
                    data.put("key", key.toString());
                    data.put("value", value.toString());
                    Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

                    dataArray.add(tree);
                }
            }
        }

        return returnPair;
    }

    private static <T, U> Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> pairSaveBuilder(Pair<T, U> pair, String nodeName) {
        ArrayList<Tree<IStorableObject.StorableForm>> dataArray = new ArrayList<>();

        Class<T> keyClass = (Class<T>) pair.getKey().getClass();
        Class<U> valueClass = (Class<U>) pair.getValue().getClass();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(pair.getClass().getName());
        stringBuilder.append(":");
        stringBuilder.append(keyClass.getName());
        stringBuilder.append(",");
        stringBuilder.append(valueClass.getName());

        Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> returnPair = new Pair<>(stringBuilder.toString(), dataArray);

        if(Map.class.isAssignableFrom(valueClass)) {
            IStorableObject.StorableForm data = new IStorableObject.StorableForm();
            data.put("nodeName", nodeName);
            data.put("key", pair.getKey().toString());
            Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

            Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> mapData = mapSaveBuilder((Map<?, ?>) pair.getValue(), nodeName);
            data.put("dataSaved", mapData.getKey());
            tree.addTreeCollection(mapData.getValue());

            dataArray.add(tree);
        } else if(Pair.class.isAssignableFrom(valueClass)) {
            IStorableObject.StorableForm data = new IStorableObject.StorableForm();
            data.put("nodeName", nodeName);
            data.put("key", pair.getKey().toString());
            Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

            Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> pairData = pairSaveBuilder((Pair<?, ?>) pair.getValue(), nodeName);
            data.put("dataSaved", pairData.getKey());
            tree.addTreeCollection(pairData.getValue());

            dataArray.add(tree);
        } else if(Collection.class.isAssignableFrom(valueClass)) {
            IStorableObject.StorableForm data = new IStorableObject.StorableForm();
            data.put("nodeName", nodeName);
            data.put("key", pair.getKey().toString());
            Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

            Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> pairData = collectionSaveBuilder((Collection<?>) pair.getValue(), nodeName);
            data.put("dataSaved", pairData.getKey());
            tree.addTreeCollection(pairData.getValue());
        } else if(AStorableObject.class.isAssignableFrom(valueClass)){
            IStorableObject.StorableForm data = new IStorableObject.StorableForm();
            data.put("nodeName", nodeName);
            data.put("key", pair.getKey().toString());
            Tree<IStorableObject.StorableForm> tree = new Tree<>(data);
            tree.addTreeChild(((AStorableObject) pair.getValue()).toStorage(), false);
            dataArray.add(tree);
        } else {
            IStorableObject.StorableForm data = new IStorableObject.StorableForm();
            data.put("nodeName", nodeName);
            data.put("key", pair.getKey().toString());
            data.put("value", pair.getValue().toString());
            Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

            dataArray.add(tree);
        }

        return returnPair;
    }

    private static <T> Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> collectionSaveBuilder(Collection<T> collection, String nodeName) {
        ArrayList<Tree<IStorableObject.StorableForm>> dataArray = new ArrayList<>();

        Class<T> valueClass = (Class<T>) collection.iterator().next().getClass();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(collection.getClass().getName());
        stringBuilder.append(":");
        stringBuilder.append(valueClass.getName());

        Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> returnPair = new Pair<>(stringBuilder.toString(), dataArray);

        if(Map.class.isAssignableFrom(valueClass)) {
            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                Map<?, ?> value = (Map<?, ?>) iterator.next();

                if(value != null && value.size() != 0) {
                    IStorableObject.StorableForm data = new IStorableObject.StorableForm();
                    data.put("nodeName", nodeName);
                    Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

                    Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> mapData = mapSaveBuilder(value, nodeName);
                    data.put("dataSaved", mapData.getKey());
                    tree.addTreeCollection(mapData.getValue());

                    dataArray.add(tree);
                }
            }
        } else if(Pair.class.isAssignableFrom(valueClass)) {
            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                Pair<?, ?> value = (Pair<?, ?>) iterator.next();

                if(value != null && value.getValue() != null && value.getKey() != null) {
                    IStorableObject.StorableForm data = new IStorableObject.StorableForm();
                    data.put("nodeName", nodeName);
                    Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

                    Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> pairData = pairSaveBuilder(value, nodeName);
                    data.put("dataSaved", pairData.getKey());
                    tree.addTreeCollection(pairData.getValue());

                    dataArray.add(tree);
                }
            }
        } else if(Collection.class.isAssignableFrom(valueClass)) {
            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                Collection<?> value = (Collection<?>) iterator.next();

                if(value != null && value.size() != 0) {
                    IStorableObject.StorableForm data = new IStorableObject.StorableForm();
                    data.put("nodeName", nodeName);
                    Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

                    Pair<String, ArrayList<Tree<IStorableObject.StorableForm>>> collectionData = collectionSaveBuilder(value, nodeName);
                    data.put("dataSaved", collectionData.getKey());
                    tree.addTreeCollection(collectionData.getValue());

                    dataArray.add(tree);
                }
            }
        } else if(AStorableObject.class.isAssignableFrom(valueClass)) {
            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                AStorableObject value = (AStorableObject) iterator.next();

                if(value != null) {
                    Tree<IStorableObject.StorableForm> tree = value.toStorage();
                    IStorableObject.StorableForm data = tree.getHead();
                    data.put("nodeName", nodeName);

                    dataArray.add(tree);
                }
            }
        } else {
            Iterator iterator = collection.iterator();
            while (iterator.hasNext()) {
                T value = (T) iterator.next();

                if(value != null) {
                    IStorableObject.StorableForm data = new IStorableObject.StorableForm();
                    data.put("nodeName", nodeName);
                    Tree<IStorableObject.StorableForm> tree = new Tree<>(data);

                    data.put("value", value.toString());

                    dataArray.add(tree);
                }
            }
        }

        return returnPair;
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Loading">
    public static <T> T read(Tree<IStorableObject.StorableForm> dataTree) {
        String[] carrierAndStored = dataTree.getHead().get("dataSaved").split(":");
        String nodeName = dataTree.getHead().getNodeName();

        Class carrierClass = null;
        try {
            carrierClass = Class.forName(carrierAndStored[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Object returnData = null;
        try {
            returnData = carrierClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(Map.class.isAssignableFrom(carrierClass)) {
            Map map = readMap(dataTree, nodeName);
            returnData = map;
        } else if(Pair.class.isAssignableFrom(carrierClass)) {
            Pair pair = readPair(dataTree, nodeName);
            returnData = pair;
        } else if(Collection.class.isAssignableFrom(carrierClass)) {
            Collection collection = readCollection(dataTree, nodeName);
            returnData = collection;
        } else {
            throw new UnsupportedOperationException("Could not find reader for " + dataTree);
        }

        return (T) returnData;
    }


    //<editor-fold desc="Readers">
    private static Map readMap(Tree<IStorableObject.StorableForm> dataTree, String nodeName) {
        String[] carrierAndStored = dataTree.getHead().get("dataSaved").split((":"));
        String[] stored = carrierAndStored[1].split(",");

        Class carrierClass = null;
        Class keyClass = null;
        Class valueClass = null;
        try {
            carrierClass = Class.forName(carrierAndStored[0]);
            keyClass = Class.forName(stored[0]);
            valueClass = Class.forName(stored[1]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Map map = null;
        try {
            map = (Map) carrierClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Vector<Tree<IStorableObject.StorableForm>> actualTrees = new Vector<>();
        for (Tree<IStorableObject.StorableForm> tree : dataTree.getChildList()) {
            if(tree.getHead().getNodeName().equals(nodeName)) {
                actualTrees.add(tree);
            }
        }

        if(Map.class.isAssignableFrom(valueClass)) {
            for (Tree<IStorableObject.StorableForm> tree : actualTrees) {
                map.put(GenericConverter.convert(tree.getHead().get("key"), keyClass), readMap(tree, nodeName));
            }
        } else if(Pair.class.isAssignableFrom(valueClass)) {
            for (Tree<IStorableObject.StorableForm> tree : actualTrees) {
                map.put(GenericConverter.convert(tree.getHead().get("key"), keyClass), readPair(tree, nodeName));
            }
        } else if(Collection.class.isAssignableFrom(valueClass)) {
            for (Tree<IStorableObject.StorableForm> tree : actualTrees) {
                map.put(GenericConverter.convert(tree.getHead().get("key"), keyClass), readCollection(tree, nodeName));
            }
        } else if(AStorableObject.class.isAssignableFrom(valueClass)) {
            for (Tree<IStorableObject.StorableForm> tree : actualTrees) {
                Constructor constructor = null;
                try {
                    constructor = valueClass.getConstructor(Tree.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    map.put(GenericConverter.convert(tree.getHead().get("key"), keyClass), constructor.newInstance(tree));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (Tree<IStorableObject.StorableForm> tree : actualTrees) {
                map.put(GenericConverter.convert(tree.getHead().get("key"), keyClass), GenericConverter.convert(tree.getHead().get("value"), valueClass));
            }
        }

        return map;
    }

    private static Pair readPair(Tree<IStorableObject.StorableForm> dataTree, String nodeName) {
        String[] carrierAndStored = dataTree.getHead().get("dataSaved").split((":"));
        String[] stored = carrierAndStored[1].split(",");

        Class carrierClass = null;
        Class keyClass = null;
        Class valueClass = null;
        try {
            carrierClass = Class.forName(carrierAndStored[0]);
            keyClass = Class.forName(stored[0]);
            valueClass = Class.forName(stored[1]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Pair pair = null;

        Tree<IStorableObject.StorableForm> actualTree = null;
        for (Tree<IStorableObject.StorableForm> tree : dataTree.getChildList()) {
            if(tree.getHead().getNodeName().equals(nodeName)) {
                actualTree = tree;
            }
        }

        if(Map.class.isAssignableFrom(valueClass)) {
            Constructor constructor = null;
            try {
                constructor = carrierClass.getConstructor(Object.class, Object.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                pair = (Pair) constructor.newInstance(GenericConverter.convert(actualTree.getHead().get("key"), keyClass), readMap(actualTree, nodeName));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else if(Pair.class.isAssignableFrom(valueClass)) {
            Constructor constructor = null;
            try {
                constructor = carrierClass.getConstructor(Object.class, Object.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                pair = (Pair) constructor.newInstance(GenericConverter.convert(actualTree.getHead().get("key"), keyClass), readPair(actualTree, nodeName));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else if(Collection.class.isAssignableFrom(valueClass)) {
            Constructor constructor = null;
            try {
                constructor = Pair.class.getConstructor(Object.class, Object.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                pair = (Pair) constructor.newInstance(GenericConverter.convert(actualTree.getHead().get("key"), keyClass), readCollection(actualTree, nodeName));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else if(AStorableObject.class.isAssignableFrom(valueClass)) {
            Constructor constructor = null;
            try {
                constructor = Pair.class.getConstructor(Object.class, Object.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            Constructor valueConstructor = null;
            try {
                valueConstructor = valueClass.getConstructor(Tree.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                pair = (Pair) constructor.newInstance(GenericConverter.convert(actualTree.getHead().get("key"), keyClass), valueConstructor.newInstance(actualTree));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            Constructor constructor = null;
            try {
                constructor = carrierClass.getConstructor(Object.class, Object.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                pair = (Pair) constructor.newInstance(GenericConverter.convert(actualTree.getHead().get("key"), keyClass), GenericConverter.convert(actualTree.getHead().get("value"), valueClass));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return pair;
    }

    private static Collection readCollection(Tree<IStorableObject.StorableForm> dataTree, String nodeName) {
        String[] carrierAndStored = dataTree.getHead().get("dataSaved").split((":"));

        Class carrierClass = null;
        Class valueClass = null;
        try {
            carrierClass = Class.forName(carrierAndStored[0]);
            valueClass = Class.forName(carrierAndStored[1]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Collection collection = null;
        try {
            collection = (Collection) carrierClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Vector<Tree<IStorableObject.StorableForm>> actualTrees = new Vector<>();
        for (Tree<IStorableObject.StorableForm> tree : dataTree.getChildList()) {
            if(tree.getHead().getNodeName().equals(nodeName)) {
                actualTrees.add(tree);
            }
        }

        if(Map.class.isAssignableFrom(valueClass)) {
            for (Tree<IStorableObject.StorableForm> tree : actualTrees) {
                collection.add(readMap(tree, nodeName));
            }
        } else if(Pair.class.isAssignableFrom(valueClass)) {
            for (Tree<IStorableObject.StorableForm> tree : actualTrees) {
                collection.add(readPair(tree, nodeName));
            }
        } else if(Collection.class.isAssignableFrom(valueClass)) {
            for (Tree<IStorableObject.StorableForm> tree : actualTrees) {
                collection.add(readCollection(tree, nodeName));
            }
        } else if(AStorableObject.class.isAssignableFrom(valueClass)) {
            for (Tree<IStorableObject.StorableForm> tree : actualTrees) {
                Constructor constructor = null;
                try {
                    constructor = valueClass.getConstructor(Tree.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    collection.add(constructor.newInstance(tree));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (DataStructureUtils.checkArray(primativeClasses, valueClass)) {
                for (Tree<IStorableObject.StorableForm> tree : actualTrees) {
                    collection.add(GenericConverter.convert(tree.getHead().get("value"), valueClass));
                }
            } else {
                for (Tree<IStorableObject.StorableForm> tree : actualTrees) {
                    collection.add(GenericConverter.convert(tree, valueClass));
                }
            }
        }

        return collection;
    }
    //</editor-fold>
    //</editor-fold>
}