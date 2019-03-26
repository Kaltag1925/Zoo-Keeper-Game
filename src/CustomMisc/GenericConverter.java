package CustomMisc;

import Engine.Logic.CoatColorGroup;
import XML.StorageClasses.IStorableObject;
import javafx.util.Pair;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GenericConverter {

    private static HashMap<Pair<Class, Class>, Method> converters = new HashMap<>(); //Pair<FROM, TO>

    static {
        Method[] methods = GenericConverter.class.getDeclaredMethods();
        for (Method meth : methods) {
            if (meth.getParameterCount() == 1) {
                converters.put(new Pair<>(meth.getParameterTypes()[0], meth.getReturnType()), meth);
            }
        }
    }

    public static <T> T convert(Object from, Class<T> to) {

        if (from == null) {
            return null;
        }

        if (to.isAssignableFrom(from.getClass())) {
            return to.cast(from);
        }


        Pair converterId = new Pair<>(from.getClass(), to);
        Map.Entry<Pair<Class, Class>, Method> converterEntry = DataStructureUtils.findEntryFromClass(converters, converterId);

        if (converterEntry == null) {
            throw new UnsupportedOperationException("Cannot convert from "
                    + from.getClass().getName() + " to " + to.getName()
                    + ". Requested converter does not exist.");
        }

        // Convert the value.
        try {
            return to.cast(converterEntry.getValue().invoke(null, from));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot convert from "
                    + from.getClass().getName() + " to " + to.getName()
                    + ". Conversion failed with " + e.getMessage(), e); //TODO: Throws at the to.cast
        }
    }

    private static Integer stringToInteger(String convert) {
        return Integer.parseInt(convert);
    }

    private static Double stringToDouble(String convert) {
        return Double.parseDouble(convert);
    }

    private static Map treeToMap(Tree<IStorableObject.StorableForm> convert) {
        return SaveBuilder.read(convert);
    }

    private static Pair treeToPair(Tree<IStorableObject.StorableForm> convert) {
        return SaveBuilder.read(convert);
    }

    private static Collection treeToCollection(Tree<IStorableObject.StorableForm> convert) {
        return SaveBuilder.read(convert);
    }

    private static CoatColorGroup treeToCoatColorGroup(Tree<IStorableObject.StorableForm> convert) {
        return new CoatColorGroup(convert);
    }

    private static Boolean stringToBoolean(String convert) {
        return Boolean.parseBoolean(convert);
    }
}
