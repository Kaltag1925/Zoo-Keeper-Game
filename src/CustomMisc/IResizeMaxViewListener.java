package CustomMisc;

import java.util.ArrayList;
import java.util.List;

public interface IResizeMaxViewListener {

    void resize();

    class Resize {
        private static List<IResizeMaxViewListener> listeners = new ArrayList<IResizeMaxViewListener>();

        public static void addResizeMaxViewListener(IResizeMaxViewListener object) {
            listeners.add(object);
        }

        public static void event() {
            for (IResizeMaxViewListener listener : listeners) {
                listener.resize();
            }
        }
    }
}
