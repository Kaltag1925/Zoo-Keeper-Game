package Engine.Logic.Assignments.Actions;

public class Run implements Action {

    private Runnable run;

    public Run(Runnable run) {
        this.run = run;
    }

    public boolean update() {
        run.run();
        return true;
    }
}
