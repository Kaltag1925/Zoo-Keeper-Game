package Engine.GameObjects.Entities.Moveable;

import CustomMisc.DataStructureUtils;
import CustomMisc.Tree;
import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Tiles.Tile;
import Engine.Logic.Assignments.Assignable;
import Engine.Logic.Assignments.Assignment;
import Engine.Logic.Assignments.AssignmentManager;
import Engine.Logic.Assignments.Task;

import java.awt.*;

public class Moveable extends Entity implements Assignable {

    private static final String XML_NODE_NAME = "Moveable";

    private static char icon;
    private double speed = .2;
    private Assignment currentAssignment;
    private Task currentTask;

    //<editor-fold desc="Can Move">
    private boolean canMove = true;

    public boolean canMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
    //</editor-fold>

    public Moveable() {

    }

    public Moveable(Tile location, Image icon) {
        super(location, icon, Tile.HIGHEST_PRIORITY, true);
        currentAssignment = AssignmentManager.IDLE_ASSIGNMENT;
    }

    public double getSpeed() {
        return speed;
    }

    public Moveable(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);
        currentAssignment = AssignmentManager.IDLE_ASSIGNMENT;
    }

    @Override
    public Assignment getCurrentAssignment() {
        return currentAssignment;
    }

    @Override
    public void setCurrentAssignment(Assignment assignment) {
        if (currentAssignment != null) {
            if (currentTask != null) {
                currentAssignment.end(currentTask);
            }
        }
        currentAssignment = assignment;
    }

    @Override
    public Task getCurrentTask() {
        return currentTask;
    }

    @Override
    public void setCurrentTask(Task task) {
        currentTask = task;
    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {
    }

    public Entity locate(Class entityType, double amount) {
        return DataStructureUtils.findObjectWithClass(Entity.entities, entityType);
    }

    @Override
    public void assignReferences(Tree<StorableForm> storageTree) {
        super.assignReferences(storageTree);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        super.fillStorageTree(storageTree);
        StorableForm head = storageTree.getHead();
        head.put("nodeName", XML_NODE_NAME);
    }
}
