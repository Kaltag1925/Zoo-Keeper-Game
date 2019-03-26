package Engine.Logic.Assignments.Actions;

import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Entities.Items.Item;

public class Drop implements Action {
    private Entity actor;
    private Entity equip;
    private Double amount;

    public Drop(Entity actor, Entity equip, double amount) {
        this.actor = actor;
        this.equip = equip;
        this.amount = amount;
    }

    public Drop(Entity actor, Entity equip) {
        this.actor = actor;
        this.equip = equip;
    }


    public boolean update() {
        if (amount != null) {
            Item item = (Item) equip;
            if (amount < item.getAmount()) {
                actor.drop(item.split(amount));
            } else {
                actor.drop(item);
            }
        } else {
            actor.drop(equip);
        }

        return true;
    }
}
