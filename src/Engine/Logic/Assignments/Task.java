package Engine.Logic.Assignments;

import Engine.Logic.Assignments.Actions.Action;

import java.util.LinkedList;
import java.util.List;

public class Task {

    LinkedList<Action> actions;
    Action currentAction;
    int actionsDone = 0;

    public Task() {
        actions = new LinkedList<>();
    }

    public void addAction(Action action) {
        if (actions.isEmpty()) {
            currentAction = action;
        }
        actions.add(action);
    }

    public void addAction(List<Action> actions) {
        if (this.actions.isEmpty()) {
            currentAction = actions.get(0);
        }
        this.actions.addAll(actions);
    }

    public void nextAction() {
        actions.removeFirst();
        if (!actions.isEmpty()) {
            currentAction = actions.getFirst();
        }
    }

    public int end() {
        actions.clear();
        return actionsDone;
    }

    public boolean update() {
        if (!actions.isEmpty()) {
            if (currentAction.update()) {
                actionsDone++;
                nextAction();
            }

            return false;
        } else {
            return true;
        }
    }
}
