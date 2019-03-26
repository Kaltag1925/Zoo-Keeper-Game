package CustomMisc;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public interface IDayListener {

    void newDay();

    class Event {
        private static TreeMap<Integer, Vector<IDayListener>> listeners = new TreeMap<>();

        public static void addDayListener(IDayListener object, int priority) {
            if (listeners.get(priority) != null) {
                listeners.get(priority).add(object);
            } else {
                Vector<IDayListener> list = new Vector<>();
                list.add(object);
                listeners.put(priority, list);
            }
        }

        public static void newDayEvent(int days) {
            for (int i = 0; i < days; i++) {
                Iterator iterator = listeners.entrySet().iterator();

                while(iterator.hasNext()) {
                    Map.Entry<Integer, Vector<IDayListener>> mapEntry = (Map.Entry)iterator.next();
                    for (IDayListener listener : mapEntry.getValue()) {
                        listener.newDay();
                    }
                }
            }
        }
    }
}