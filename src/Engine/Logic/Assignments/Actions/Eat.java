package Engine.Logic.Assignments.Actions;

import Engine.GameObjects.Entities.Items.AnimalFoodReal;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;

public class Eat implements Action {

    AnimalReal actor;
    AnimalFoodReal food;

    public Eat(AnimalReal actor, AnimalFoodReal food) {
        this.actor = actor;
        this.food = food;
    }

    public boolean update() {
        actor.eat(food);
        return true;
    }
}
