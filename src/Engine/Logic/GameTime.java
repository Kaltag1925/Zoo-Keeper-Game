package Engine.Logic;

import Engine.Logic.Ticks.IUpdateable;
import Engine.Logic.Ticks.Tick;

import java.util.*;

public class GameTime implements IUpdateable {

    private final int tickIncrement = 720/30;

    public int getTickMilliIncrement() {
        return tickIncrement * 1000;
    }

    private GameCalendar calendar;

    public GameCalendar getCalendar() {
        return calendar;
    }

    GameTime() {
        calendar = new GameCalendar();
        Tick.addToUpdateList(this);
    }

    public class GameCalendar extends GregorianCalendar {
        private long startTime;
        private long tick;

        public long getTick() {
            return tick;
        }

        GameCalendar() {
            set(1970, Calendar.JANUARY, 1, 0, 0, 0);
            tick = 0;
            eventStorage = new EventStorage();
        }


        void addTick() {
            add(Calendar.SECOND, tickIncrement);
            tick++;
            eventStorage.runEvents();
        }

        private EventStorage eventStorage;

        public EventStorage getEventStorage() {
            return eventStorage;
        }

        public void scheduleEvent(long time, Runnable event) {
            eventStorage.putIfAbsent(time, new ArrayList<>());
            eventStorage.get(time).add(event);
        }

        private class EventStorage extends HashMap<Long, List<Runnable>> {
            void runEvents() {
                Profiler profiler = new Profiler();
                profiler.log();
                List<Runnable> events = get(tick);
                if(events != null) {
                    Iterator<Runnable> iterator = events.iterator();
                    while (iterator.hasNext()) {
                        Runnable next = iterator.next();
                        next.run();
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        calendar.addTick();
    }
}
