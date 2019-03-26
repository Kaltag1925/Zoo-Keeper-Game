package Engine.Logic.Assignments.Actions;

import Engine.GameObjects.Entities.Moveable.Moveable;
import Engine.Logic.Assignments.AssignmentManager;

public class EndAssignment implements Action {

    private Moveable actor;

    EndAssignment(Moveable actor) {
        this.actor = actor;
    }

    public boolean update() {
        actor.setCurrentTask(null);
        actor.setCurrentAssignment(AssignmentManager.IDLE_ASSIGNMENT);
        return true;
    }
}
