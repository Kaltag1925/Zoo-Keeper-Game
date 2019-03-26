package Engine.Logic;

import CustomMisc.Tree;
import Engine.Logic.Ticks.IUpdateable;
import Engine.Logic.Ticks.Tick;
import XML.StorageClasses.AStorableObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

@Deprecated
public class Time extends AStorableObject implements IUpdateable{

   /* i advise using the java calendar: https://www.youtube.com/watch?v=-5wpm-gesOY
    YouTube
            Computerphile
    The Problem with Time & Timezones - Computerphile

    well... unless you want to go the df way and just have a simplyfied thing
    either way, timer iirc is designed to work with the system time, not the game time, not sure that's what you want
    but you need to have a game timekeeper anyway, and things subscribing to it changing, so you could also add a treemap<long,vector<timedEvent>> to that to deal with the stuff happening; repeating tasks just have a single entry in there, and reschedule themselves after completion
    in general, there's 2 approaches to time in games: event based and steadily progressing. i favour the first myself...
    so, in steady progression, each tick you update everything that can be updated. each animal gets a new hunger value and the like
    in event based, you store the initial hunger value, the hunger progression per time, and you schedule an event for when something interesting is going to happen with this hunger - like the animal going for food, or dropping dead from starvation, or passing the point of no return for that where it's too weak to eat on its own but not dead yet
    and if something needs to display the current hunger state, it can calculate the value from the initial value and time, the progression, and the current time
            (or you only display abstract data, and have events for when it changes from "well fed" to "peckish" and the like)*/

    private static final String YEAR_XML = "year";
    private static final String MONTH_XML = "month";
    private static final String DAY_OF_YEAR_XML = "dayOfYear";
    private static final String DAY_OF_MONTH_XML = "dayOfMonth";
    private static final String DAY_OF_WEEK_XML = "dayOfWeek";
    private static final String HOUR_XML = "hour";
    private static final String MINUTE_XML = "minute";
    private static final String SECOND_XML = "second";
    private static final String TIME_SCALE_XML = "timeScale";
    private static final String XML_NODE_NAME = "Time";


    //Months
    public static final int JANUARY = 0;
    public static final int FEBRUARY = 1;
    public static final int MARCH = 2;
    public static final int APRIL = 3;
    public static final int MAY = 4;
    public static final int JUNE = 5;
    public static final int JULY = 6;
    public static final int AUGUST = 7;
    public static final int SEPTEMBER = 8;
    public static final int OCTOBER = 9;
    public static final int NOVEMBER = 10;
    public static final int DECEMBER = 11;
    public static final int[] MONTH_MAX_DAYS = new int[]
            {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final String[] MONTH_NAMES = new String[]
            {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    //Time
    public static final int SECOND = 0;
    public static final int MINUTE = 1;
    public static final int HOUR = 2;
    public static final int DAY_OF_MONTH = 3;
    public static final int DAY_OF_WEEK = 4;
    public static final int DAY_OF_YEAR = 5;
    public static final int MONTH = 6;
    public static final int YEAR = 7;
    public static final int[] TIME_MIN_VALUES = new int[]
            {0, 0, 0, 1, 1, 1, 1, 0};
    public static final int[] TIME_LEAST_MAX_VALUES = new int[]
            {59, 59, 23, 28, 7, 365, 12, Integer.MAX_VALUE};
    public static final int[] TIME_GREATEST_MAX_VALUES = new int[]
            {59, 59, 23, 31, 7, 365, 12, Integer.MAX_VALUE}; //TODO: Add Leap Year? Week of Year?

    public void add(int part, int amount) { //TODO: Replace with divide and then a round down
        switch(part) {
            //<editor-fold desc="Second">
            case SECOND:
                second += amount;
                if (second > TIME_GREATEST_MAX_VALUES[SECOND]) {
                    int addToNext = Math.floorDiv(second, TIME_GREATEST_MAX_VALUES[SECOND]);
                    second -= addToNext * TIME_GREATEST_MAX_VALUES[SECOND];
                    add(MINUTE, addToNext);
                }
                break;
            //</editor-fold>

            //<editor-fold desc="Minute">
            case MINUTE:
                minute += amount;
                if (minute > TIME_GREATEST_MAX_VALUES[MINUTE]) {
                    int addToNext = Math.floorDiv(minute, TIME_GREATEST_MAX_VALUES[MINUTE]);
                    minute -= addToNext * TIME_GREATEST_MAX_VALUES[MINUTE];
                    add(HOUR, addToNext);
                }
                break;
            //</editor-fold>

            //<editor-fold desc="Hour">
            case HOUR:
                hourOfDay += amount;
                if (hourOfDay > TIME_GREATEST_MAX_VALUES[HOUR]) {
                    int addToNext = Math.floorDiv(hourOfDay, TIME_GREATEST_MAX_VALUES[HOUR]);
                    hourOfDay -= addToNext * TIME_GREATEST_MAX_VALUES[HOUR];
                    add(DAY_OF_MONTH, addToNext);
                }
                break;
            //</editor-fold>

            //<editor-fold desc="Days">
            case DAY_OF_MONTH:
                for (int i = 0; i < amount; i++) {
                    newDay();
                }

                dayOfMonth += amount;
                if (dayOfMonth > MONTH_MAX_DAYS[month]) {
                    while (dayOfMonth > MONTH_MAX_DAYS[month]) {
                        dayOfMonth -= MONTH_MAX_DAYS[month];
                        add(MONTH, 1);
                    }

                }

                dayOfWeek += amount;
                if (dayOfWeek > TIME_GREATEST_MAX_VALUES[DAY_OF_WEEK]) {
                    while (dayOfWeek > TIME_GREATEST_MAX_VALUES[DAY_OF_WEEK]) {
                        dayOfWeek -= TIME_GREATEST_MAX_VALUES[DAY_OF_WEEK];
                    }
                }

                dayOfYear += amount;
                if (dayOfYear > TIME_GREATEST_MAX_VALUES[DAY_OF_YEAR]) {
                    while (dayOfYear > TIME_GREATEST_MAX_VALUES[DAY_OF_YEAR]) {
                        dayOfYear -= TIME_GREATEST_MAX_VALUES[DAY_OF_YEAR];
                    }
                }

                break;

            case DAY_OF_WEEK:
                for (int i = 0; i < amount; i++) {
                    newDay();
                }

                dayOfMonth += amount;
                if (dayOfMonth > MONTH_MAX_DAYS[month]) {
                    while (dayOfMonth > MONTH_MAX_DAYS[month]) {
                        dayOfMonth -= MONTH_MAX_DAYS[month];
                        add(MONTH, 1);
                    }

                }

                dayOfWeek += amount;
                if (dayOfWeek > TIME_GREATEST_MAX_VALUES[DAY_OF_WEEK]) {
                    while (dayOfWeek > TIME_GREATEST_MAX_VALUES[DAY_OF_WEEK]) {
                        dayOfWeek -= TIME_GREATEST_MAX_VALUES[DAY_OF_WEEK];
                    }
                }

                dayOfYear += amount;
                if (dayOfYear > TIME_GREATEST_MAX_VALUES[DAY_OF_YEAR]) {
                    while (dayOfYear > TIME_GREATEST_MAX_VALUES[DAY_OF_YEAR]) {
                        dayOfYear -= TIME_GREATEST_MAX_VALUES[DAY_OF_YEAR];
                    }
                }

                break;

            case DAY_OF_YEAR:
                for (int i = 0; i < amount; i++) {
                    newDay();
                }

                dayOfMonth += amount;
                if (dayOfMonth > MONTH_MAX_DAYS[month]) {
                    while (dayOfMonth > MONTH_MAX_DAYS[month]) {
                        dayOfMonth -= MONTH_MAX_DAYS[month];
                        add(MONTH, 1);
                    }

                }

                dayOfWeek += amount;
                if (dayOfWeek > TIME_GREATEST_MAX_VALUES[DAY_OF_WEEK]) {
                    while (dayOfWeek > TIME_GREATEST_MAX_VALUES[DAY_OF_WEEK]) {
                        dayOfWeek -= TIME_GREATEST_MAX_VALUES[DAY_OF_WEEK];
                    }
                }

                dayOfYear += amount;
                if (dayOfYear > TIME_GREATEST_MAX_VALUES[DAY_OF_YEAR]) {
                    while (dayOfYear > TIME_GREATEST_MAX_VALUES[DAY_OF_YEAR]) {
                        dayOfYear -= TIME_GREATEST_MAX_VALUES[DAY_OF_YEAR];
                    }
                }

                break;
            //</editor-fold>

            //<editor-fold desc="Month">
            case MONTH:
                month += amount;
                if (month > TIME_GREATEST_MAX_VALUES[MONTH]) {
                    int addToNext = Math.floorDiv(month, TIME_GREATEST_MAX_VALUES[MONTH]);
                    month -= addToNext * TIME_GREATEST_MAX_VALUES[MONTH];
                    add(YEAR, addToNext);
                }

                break;
            //</editor-fold>

            case YEAR:
                year += amount;

                break;
        }
    }

    //Weekdays
    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final String[] WEEKDAY_NAMES = new String[]
            {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};


    private int second;
    private int minute;
    private int hourOfDay;
    private int dayOfWeek;
    private int dayOfMonth;
    private int dayOfYear;
    private int month;
    private int year;

    private int timeScale = 240; //Seconds per real world second
    private int tickTimeScale; //Seconds per update
    public int getTickTimeScale() {
        return tickTimeScale;
    }

    public Time(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second, int timeScale) {
        this.second = second;
        this.minute = minute;
        this.hourOfDay = hourOfDay;
        dayOfWeek = 0;
        this.timeScale = timeScale;
        tickTimeScale = timeScale / Tick.getMaxUpdatesBeforeRender() / Tick.getGameHertz();
        Tick.addToUpdateList(this);
    }

    public Time() {
        second = 0;
        minute = 0;
        hourOfDay = 0;
        dayOfWeek = 1;
        dayOfMonth = 1;
        dayOfYear = 1;
        month = 1;
        year = 1970;
        //timeScale = 8640;
        tickTimeScale = timeScale / Tick.getMaxUpdatesBeforeRender() / Tick.getGameHertz();
        Tick.addToUpdateList(this);
    }

    //<editor-fold desc="Day Listener">
    public interface DayListener {
        void newDay();
    }

    static ArrayList<DayListener> dayListeners = new ArrayList<>();

    public static void addDayListener(DayListener listener) {
        dayListeners.add(listener);
    }

    private void newDay() {
        Iterator<DayListener> iterator = dayListeners.iterator();
        while (iterator.hasNext()) {
            DayListener next = iterator.next();
            next.newDay();
        }
    }
    //</editor-fold>

    public String shortDate() {
        NumberFormat nm = NumberFormat.getInstance();
        nm.setMinimumIntegerDigits(2);
        return nm.format(month) + "/" + nm.format(dayOfMonth) + "/" + year + " " + nm.format(hourOfDay) + ":" + nm.format(minute);
    }

    @Override
    public void update() {
        add(SECOND, tickTimeScale);
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();
        head.put(NODE_NAME, XML_NODE_NAME);

        head.put(YEAR_XML, String.valueOf(year));
        head.put(MONTH_XML, String.valueOf(month));
        head.put(DAY_OF_YEAR_XML, String.valueOf(dayOfYear));
        head.put(DAY_OF_MONTH_XML, String.valueOf(dayOfMonth));
        head.put(DAY_OF_WEEK_XML, String.valueOf(dayOfWeek));
        head.put(HOUR_XML, String.valueOf(hourOfDay));
        head.put(MINUTE_XML, String.valueOf(minute));
        head.put(SECOND_XML, String.valueOf(second));
        head.put(TIME_SCALE_XML, String.valueOf(timeScale));

    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();

        year = Integer.parseInt(head.get(YEAR_XML));
        month = Integer.parseInt(head.get(MONTH_XML));
        dayOfYear = Integer.parseInt(head.get(DAY_OF_YEAR_XML));
        dayOfMonth = Integer.parseInt(head.get(DAY_OF_MONTH_XML));
        dayOfWeek = Integer.parseInt(head.get(DAY_OF_WEEK_XML));
        hourOfDay = Integer.parseInt(head.get(HOUR_XML));
        minute = Integer.parseInt(head.get(MINUTE_XML));
        second = Integer.parseInt(head.get(SECOND_XML));

        timeScale = Integer.parseInt(head.get(TIME_SCALE_XML));
    }

    public Time(Tree<StorableForm> storageTree) {
        fromStorage(storageTree);

        tickTimeScale = timeScale / Tick.getGameHertz();
        Tick.addToUpdateList(this);
    }
}
//