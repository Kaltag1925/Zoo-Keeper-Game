package Engine.Logic.Assignments;

import Engine.Logic.Ticks.IUpdateable;
import Engine.Logic.Ticks.Tick;

import java.util.Iterator;
import java.util.LinkedList;

public class TaskManager implements IUpdateable {

    LinkedList<Task> tasks;

    public TaskManager() {
        Tick.addToUpdateList(this);
        tasks = new LinkedList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void update() {
        if (!tasks.isEmpty()) {
            Iterator<Task> iterator = tasks.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if(task.update()) {
                    iterator.remove();
                }
            }
        }
    }
}