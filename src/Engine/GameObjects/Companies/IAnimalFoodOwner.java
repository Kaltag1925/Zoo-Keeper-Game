package Engine.GameObjects.Companies;

import Engine.GameObjects.Entities.Items.AnimalFoodReal;
import Engine.Logic.GameObjectHolder;

public interface IAnimalFoodOwner {

    GameObjectHolder<AnimalFoodReal> getAnimalFood();

    double getMaxSpoilage();
}
