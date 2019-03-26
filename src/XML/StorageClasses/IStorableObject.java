package XML.StorageClasses;

import CustomMisc.ICreateCopy;
import CustomMisc.Tree;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Rhiwaow on 29/03/2017.
 */

public interface IStorableObject {

    class StorableForm extends HashMap<String, String > implements ICreateCopy{
        public static Method method;

        static {
            try {
                method = StorableForm.class.getDeclaredMethod("getNodeName");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        public boolean test(Object compare){
            return false;
        }

        @Override
        public ICreateCopy createCopy() {
            return this;
        }

        public String getNodeName() {
            return get("nodeName");
        }
    }



    Tree<StorableForm> toStorage();

    void fromStorage( Tree<StorableForm> storedData );
}