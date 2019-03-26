package Engine.Logic.Assignments.Actions;

import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Entities.Items.Item;

public class PickUp implements Action {

    private Entity actor;
    private Entity equip;
    private Double amount;

    public PickUp(Entity actor, Entity equip, double amount) {
        this.actor = actor;
        this.equip = equip;
        this.amount = amount;
    }


    public PickUp(Entity actor, Entity equip) {
        this.actor = actor;
        this.equip = equip;
    }


    public boolean update() {
        if (amount != null) {
            Item item =  (Item) equip;
            if (amount < item.getAmount()) {
                actor.pickUp(item.split(amount));
            } else {
                actor.pickUp(item);
            }
        } else {
            actor.pickUp(equip);
        }

        return true;
    }
}
