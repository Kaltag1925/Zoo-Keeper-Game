package Engine.Logic.Assignments.Actions;

import Engine.Logic.MainData;

import java.util.HashMap;

public class CleanUp implements Action {

    private HashMap<String, Object> args;

    CleanUp(HashMap<String, Object> args) {
        this.args = args;
    }

    public boolean update() {
        MainData.mainData.getAssignmentManager().addToCleanUp(args);
        return true;
    }
}
