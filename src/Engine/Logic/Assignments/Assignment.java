package Engine.Logic.Assignments;

import Engine.Logic.MainData;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Assignment {

    public static final int HIGHEST_PRIORITY = 0;
    public static final int LOWEST_PRIORITY = Integer.MAX_VALUE - 1;

    Scheduler scheduler;
    Action action;
    CleanUp cleanUp;

    String name;
    int priority;

    public Assignment(String name, File scheduler, File action, File cleanUp, int priority) {
        this.name = name;
        this.scheduler = new Scheduler(scheduler);
        this.action = new Action(action);
        this.cleanUp = new CleanUp(cleanUp);
        this.priority = priority;

        MainData.mainData.getAssignmentManager().addAssignment(this);
    }

    /**
     * Creates a temporary Assignment. Used by objects creating tasks within the java files. Used for
     * the purpose of priority.
     * @param name
     * @param priority
     */
    public Assignment(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }


    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public void end(Task task) {

        int actionsDone = task.end();
        //TODO: Add clean up method for when tasks are ended prematurely maybe have a number for how far into a task the thing got
    }

    class JSToJava {
        File file;
        String dataString = "";

        public JSToJava(File file) {
            this.file = file;
            List<String> data = null;
            try {
                data = Files.readAllLines(Paths.get(file.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            StringBuilder builder = new StringBuilder();
            for (String line : data) {
                builder.append(line);
            }
            dataString = builder.toString();
        }

        public void run(Context cx, Scriptable scope, String type) {
            cx.evaluateString(scope, dataString, name + type, 1, null);
        }
    }

    class Scheduler extends JSToJava {
        Scheduler(File file) {
            super(file);
        }
    }

    class Action extends JSToJava {
        Action(File file) {
            super(file);
        }
    }

    class CleanUp extends JSToJava {
        CleanUp(File file) {
            super(file);
        }
    }
}
