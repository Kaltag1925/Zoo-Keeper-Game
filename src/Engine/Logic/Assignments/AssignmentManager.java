package Engine.Logic.Assignments;

import Engine.GameObjects.Entities.Items.AnimalFoodReal;
import Engine.Logic.AnimalHolder;
import Engine.Logic.MainData;
import Engine.Logic.Ticks.IUpdateable;
import Engine.Logic.Ticks.Tick;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.util.*;

public class AssignmentManager implements IUpdateable {

    //TODO: Make an animal list class that has methods to make a list of hungry animals
    //TODO: Make a list class that has methods for unoccupied assignables;

    //TODO: change task in settask in cleanups to idle

    private static final String ACTION_FACTORY = "actionFactory";
    private static final String ANIMAL_HUNGRY = "ANIMAL_HUNGRY";
    private static final String ARGUMENTS = "args";
    private static final String ASSIGNMENT = "ASSIGNMENT";
    private static final String ASSIGNMENT_MANAGER = "assignmentManager";
    private static final String CLASS_FOOD = "classFood";
    private static final String JS_UTILITIES = "jsUtilities";
    private static final String PLAYER_ZOO = "playerZoo";
    private static final String RANDOM = "random";
    private static final String TASK = "task";

    public static Assignment IDLE_ASSIGNMENT;

    private Context cx;
    private Scriptable scope;
    private Vector<Assignment> assignments = new Vector<>();

    public AssignmentManager() {
        Tick.addToUpdateList(this);
        cx = Context.enter();
        scope = cx.initStandardObjects();

        Object assignmentManager = Context.javaToJS(this, scope);
        ScriptableObject.putProperty(scope, ASSIGNMENT_MANAGER, assignmentManager);

        Object animalHungryInt = Context.javaToJS(AnimalHolder.HUNGRY, scope);
        ScriptableObject.putProperty(scope, ANIMAL_HUNGRY, animalHungryInt);

        Object assignmentString = Context.javaToJS(ASSIGNMENT, scope);
        ScriptableObject.putProperty(scope, ASSIGNMENT, assignmentString);

        Object foodClass = Context.javaToJS(AnimalFoodReal.class, scope);
        ScriptableObject.putProperty(scope, CLASS_FOOD, foodClass);

        Object jsUtilities = Context.javaToJS(new JSUtil(), scope);
        ScriptableObject.putProperty(scope, JS_UTILITIES, jsUtilities);

        Object playerZoo = Context.javaToJS(MainData.mainData.getPlayerZoo(), scope);
        ScriptableObject.putProperty(scope, PLAYER_ZOO, playerZoo);

        Object random = Context.javaToJS(MainData.mainData.getRandom(), scope);
        ScriptableObject.putProperty(scope, RANDOM, random);

        Object out = Context.javaToJS(System.out, scope);
        ScriptableObject.putProperty(scope, "out", out);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);

        Object assignmentJS = Context.javaToJS(assignment, scope);
        ScriptableObject.putProperty(scope, assignment.getName(), assignmentJS);
    }

    private LinkedList<HashMap<String, Object>> jobsAwaitingBuilding = new LinkedList<>();

    public void schedule(HashMap<String, Object> args) {
       jobsAwaitingBuilding.add(args);
    }


    private LinkedList<HashMap<String, Object>> jobsAwaitingCleanUp = new LinkedList<>();

    public void addToCleanUp(HashMap<String, Object> args) {
        jobsAwaitingCleanUp.add(args);
    }


    public void update() {
        Iterator<Assignment> iterator = assignments.iterator();
        while (iterator.hasNext()) {
            Assignment next = iterator.next();
            next.scheduler.run(cx, scope, "Scheduler");
            iterator.remove();
        }

        Iterator<HashMap<String, Object>> jobSchedulerIterator = jobsAwaitingBuilding.iterator();
        while (jobSchedulerIterator.hasNext()) {
            HashMap<String, Object> args = jobSchedulerIterator.next();

            Scriptable taskScope = cx.initStandardObjects();
            Task task = new Task();
            Assignment assignment = (Assignment) args.get(ASSIGNMENT);

            Object animalHungryInt = Context.javaToJS(AnimalHolder.HUNGRY, taskScope);
            ScriptableObject.putProperty(taskScope, ANIMAL_HUNGRY, animalHungryInt);

            Object actionFactory = Context.javaToJS(MainData.mainData.getActionFactory(), taskScope);
            ScriptableObject.putProperty(taskScope, ACTION_FACTORY, actionFactory);

            Object argsJS = Context.javaToJS(args, taskScope);
            ScriptableObject.putProperty(taskScope, ARGUMENTS, argsJS);

            Object random = Context.javaToJS(MainData.mainData.getRandom(), taskScope);
            ScriptableObject.putProperty(taskScope, RANDOM, random);

            Object taskJS = Context.javaToJS(task, taskScope);
            ScriptableObject.putProperty(taskScope, TASK, taskJS);

            Object out = Context.javaToJS(System.out, taskScope);
            ScriptableObject.putProperty(taskScope, "out", out);

            Iterator<Map.Entry<String, Object>> argsIterator = args.entrySet().iterator();
            while (argsIterator.hasNext()) {
                Map.Entry<String, Object> entry = argsIterator.next();

                Object objectJS = Context.javaToJS(entry.getValue(), taskScope);
                ScriptableObject.putProperty(taskScope, entry.getKey(), objectJS);
            }

            assignment.action.run(cx, taskScope, "Action");

            MainData.mainData.getTaskManager().addTask(task);

            jobSchedulerIterator.remove();
        }

        Iterator<HashMap<String, Object>> jobCleanUpIterator = jobsAwaitingCleanUp.iterator();
        while (jobCleanUpIterator.hasNext()) {
            HashMap<String, Object> args = jobCleanUpIterator.next();

            Scriptable cleanUpScope = cx.initStandardObjects();
            Assignment assignment = (Assignment) args.get(ASSIGNMENT);

            Object animalHungryInt = Context.javaToJS(AnimalHolder.HUNGRY, cleanUpScope);
            ScriptableObject.putProperty(cleanUpScope, ANIMAL_HUNGRY, animalHungryInt);

            Object assignmentManager = Context.javaToJS(this, cleanUpScope);
            ScriptableObject.putProperty(cleanUpScope, ASSIGNMENT_MANAGER, assignmentManager);

            Object random = Context.javaToJS(MainData.mainData.getRandom(), cleanUpScope);
            ScriptableObject.putProperty(cleanUpScope, RANDOM, random);

            Object playerZoo = Context.javaToJS(MainData.mainData.getPlayerZoo(), cleanUpScope);
            ScriptableObject.putProperty(cleanUpScope, PLAYER_ZOO, playerZoo);

            Object out = Context.javaToJS(System.out, cleanUpScope);
            ScriptableObject.putProperty(cleanUpScope, "out", out);

            Iterator<Map.Entry<String, Object>> argsIterator = args.entrySet().iterator();
            while (argsIterator.hasNext()) {
                Map.Entry<String, Object> entry = argsIterator.next();

                Object objectJS = Context.javaToJS(entry.getValue(), cleanUpScope);
                ScriptableObject.putProperty(cleanUpScope, entry.getKey(), objectJS);
            }

            assignment.cleanUp.run(cx, cleanUpScope, "CleanUp");

            jobCleanUpIterator.remove();
        }
    }
}
