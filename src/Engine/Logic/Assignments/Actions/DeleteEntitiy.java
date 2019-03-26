package Engine.Logic.Assignments.Actions;

import Engine.GameObjects.Entities.Entity;

public class DeleteEntitiy implements Action {

    private Entity entity;

    DeleteEntitiy(Entity entity) {
        this.entity = entity;
    }

    public boolean update() {
        entity.deleteObject();
        return true;
    }
}
