package Engine.Logic.Assignments.Actions;

import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Entities.Items.AnimalFoodReal;
import Engine.GameObjects.Entities.Items.Item;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import Engine.GameObjects.Entities.Moveable.Moveable;
import Engine.GameObjects.Tiles.Tile;
import Engine.Logic.Assignments.Actions.Collections.AddToCollection;
import Engine.Logic.Assignments.Actions.Collections.ClearCollection;
import Engine.Logic.Assignments.Actions.Collections.RemoveFromCollection;

import java.util.Collection;
import java.util.HashMap;

public class ActionFactory {

    public ActionFactory() {

    }

    //<editor-fold desc="Eat">
    public Action eat(AnimalReal animal, AnimalFoodReal food) {
        return new Eat(animal, food);
    }
    //</editor-fold>

    //<editor-fold desc="Pick Up">
    public Action pickUp(Entity actor, Entity equip, double amount) {
        return new PickUp(actor, equip, amount);
    }

    public Action pickUp(Entity actor, Entity equip) {
        return new PickUp(actor, equip);
    }
    //</editor-fold>

    //<editor-fold desc="Drop">
    public Action drop(Entity actor, Entity equip, double amount) {
        return new Drop(actor, equip, amount);
    }

    public Action drop(Entity actor, Entity equip) {
        return new Drop(actor, equip);
    }
    //</editor-fold>

    //<editor-fold desc="Move To">
    public Action moveTo(Moveable actor, Tile where) {
        return new MoveTo(actor, where);
    }
    //</editor-fold>

    //<editor-fold desc="Wait In Place">
    public Action waitInPlace(int ticksToWait) {
        return new WaitInPlace(ticksToWait);
    }
    //</editor-fold>

    //<editor-fold desc="Delete Entity">
    public Action deleteEntity(Entity entity) {
        return new DeleteEntitiy(entity);
    }
    //</editor-fold>

    //<editor-fold desc="Clean Up">
    public Action cleanUp(HashMap<String, Object> args) {
        return new CleanUp(args);
    }
    //</editor-fold>

    //<editor-fold desc="Modify Collection">
    public <T> Action addToCollection(Collection<T> collection, T add) {
        return new AddToCollection<>(collection, add);
    }

    public <T> Action removeFromCollection(Collection<T> collection, T remove) {
        return new RemoveFromCollection<>(collection, remove);
    }

    public <T> Action clearCollection(Collection<T> collection) {
        return new ClearCollection<>(collection);
    }
    //</editor-fold>

    public Action useItem(Entity actor, Item item, Double amount) {
        return new UseItem(actor, item, amount);
    }

    public Action endAssignment(Moveable actor) {
        return new EndAssignment(actor);
    }

    public Action run(Runnable run) {
        return new Run(run);
    }
}
