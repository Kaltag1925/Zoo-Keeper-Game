package Engine.Logic.Assignments.Actions;

import Engine.GameObjects.Entities.Entity;
import Engine.GameObjects.Entities.Items.Item;

public class UseItem implements Action{
    private Entity actor;
    private Item item;
    private Double amount;

    public UseItem(Entity actor, Item item, Double amount) {
        this.actor = actor;
        this.item = item;
        this.amount = amount;
    }

    public UseItem(Entity actor, Item item) {
        this.actor = actor;
        this.item = item;
        this.amount = 1d;
    }


    public boolean update() {
        item.use(actor, amount);

        return true;
    }
}
