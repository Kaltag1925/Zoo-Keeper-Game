package XML;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public interface ISaveAndLoadListener {

    interface ILoadListener {

        void load();

        class Event {
            private static TreeMap<Integer, Vector<ILoadListener>> listeners = new TreeMap<>();

            public static void addLoadLister(ILoadListener object, int priority) {
                if(listeners.get(priority) != null) {
                    listeners.get(priority).add(object);
                } else {
                    Vector<ILoadListener> list = new Vector<>();
                    list.add(object);
                    listeners.put(priority, list);
                }
            }

            public static void loadEvent() {
                Iterator iterator = listeners.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<Integer, Vector<ILoadListener>> mapEntry = (Map.Entry) iterator.next();
                    for (ILoadListener listener : mapEntry.getValue()) {
                        listener.load();
                    }
                }
            }
        }
    }

    interface ISaveListener {

        void save();

        class Event {
            private static TreeMap<Integer, Vector<ISaveListener>> listeners = new TreeMap<>();

            public static void addLoadLister(ISaveListener object, int priority) {
                if(listeners.get(priority) != null) {
                    listeners.get(priority).add(object);
                } else {
                    Vector<ISaveListener> list = new Vector<>();
                    list.add(object);
                    listeners.put(priority, list);
                }
            }

            public static void loadEvent() {
                Iterator iterator = listeners.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<Integer, Vector<ISaveListener>> mapEntry = (Map.Entry) iterator.next();
                    for (ISaveListener listener : mapEntry.getValue()) {
                        listener.save();
                    }
                }
            }
        }
    }

    interface ICreateListener {

        void create();

        class Event {
            private static TreeMap<Integer, Vector<ICreateListener>> listeners = new TreeMap<>();

            public static void addLoadLister(ICreateListener object, int priority) {
                if(listeners.get(priority) != null) {
                    listeners.get(priority).add(object);
                } else {
                    Vector<ICreateListener> list = new Vector<>();
                    list.add(object);
                    listeners.put(priority, list);
                }
            }

            public static void loadEvent() {
                Iterator iterator = listeners.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<Integer, Vector<ICreateListener>> mapEntry = (Map.Entry) iterator.next();
                    for (ICreateListener listener : mapEntry.getValue()) {
                        listener.create();
                    }
                }
            }
        }
    }
}
