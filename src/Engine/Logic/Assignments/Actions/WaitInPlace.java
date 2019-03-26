package Engine.Logic.Assignments.Actions;

public class WaitInPlace implements Action {

    public WaitInPlace(int ticksToWait) {
        this.ticksLeft = ticksToWait;
    }

    private int ticksLeft;

    public boolean update() {
        if (ticksLeft > 0) {
            ticksLeft--;
            return false;
        } else {
            return true;
        }
    }

}
