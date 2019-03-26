package Engine.Logic.Assignments.Actions;

import CustomMisc.DataStructureUtils;
import Engine.GameObjects.Entities.Door;
import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Entities.Moveable.Moveable;
import Engine.GameObjects.Entities.Openable;
import Engine.GameObjects.Tiles.Tile;
import Engine.Logic.AStarPathFinder;
import Engine.Logic.Pathing;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MoveTo implements Action {

    private Moveable actor;
    Tile moveTo;
    private Pathing.Path path;
    private int pathIndex;
    private double progressToNextTile;

    MoveTo(Moveable actor, Tile moveTo) {
        this.actor = actor;
        this.moveTo = moveTo;
        pathIndex = 0;
    }

    private static Method closeDoor;
    static {
        try {
            closeDoor = Door.class.getDeclaredMethod("close");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private HashMap<Counter, Openable> doorTimers = new HashMap<>();

    public boolean update() {
        if (path == null) {
            path = new AStarPathFinder(actor).getPath(actor.getLocation(), moveTo);
        }
        progressToNextTile += actor.getSpeed();

        while (progressToNextTile >= 1) {
            progressToNextTile--;
            pathIndex++;

            Iterator<Map.Entry<Counter, Openable>> iterator = doorTimers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Counter, Openable> counterAndOpenable = iterator.next();
                Counter counter = counterAndOpenable.getKey();
                counter.subtract(1);
                if (counter.getAmount() <= 0) {
                    iterator.remove();
                    Openable openable = counterAndOpenable.getValue();
                    openable.close();
                }
            }

            Tile tile = path.get(pathIndex);
            if (tile.getMovementFactor() == 0) {
                Entity openable = DataStructureUtils.findObjectWithClass(tile.getOccupiers(), Openable.class);
                if (openable != null) {
                    ((Openable) openable).open();
                    doorTimers.put(new Counter(2), (Openable) openable);
                } else {
                    path = new AStarPathFinder(actor).getPath(actor.getLocation(), moveTo);
                    pathIndex = 0;
                }
            } else {
                actor.setLocation(tile);
            }

            if (pathIndex >= path.size() - 1) {
                return true;
            }

        }

        return false;
    }

    private class Counter {

        private int amount;

        protected Counter(int amount) {
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public void subtract(int amount) {
            this.amount -= amount;
        }
    }
}