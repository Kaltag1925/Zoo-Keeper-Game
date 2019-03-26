package Engine.Logic;

import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import Engine.Logic.Ticks.IUpdateable;
import Engine.Logic.Ticks.Tick;

import java.util.HashMap;
import java.util.Iterator;

public class AnimalHolder implements IUpdateable {

    private GameObjectHolder<AnimalReal> allAnimals;

    public static final int HUNGRY = 0;

    private HashMap<Integer, GameObjectHolder<AnimalReal>> animalNeeds;

    public AnimalHolder(GameObjectHolder<AnimalReal> allAnimals) {
        this.allAnimals = allAnimals;

        animalNeeds = new HashMap<>();
        animalNeeds.put(HUNGRY, new GameObjectHolder<>());

        Tick.addToUpdateList(this);
    }

    public GameObjectHolder<AnimalReal> getAllAnimals() {
        return allAnimals;
    }

    public void addAnimal(AnimalReal animal) {
        allAnimals.add(animal);
    }

    public GameObjectHolder<AnimalReal> getHungry() {
        return animalNeeds.get(HUNGRY);
    }

    public void update() {
        Iterator<AnimalReal> iterator = allAnimals.iterator();
        while (iterator.hasNext()) {
            AnimalReal animal = iterator.next();
            if(animal.isHungry()) {
                if (!animal.getNeedStatus(HUNGRY)) {
                    getHungry().add(animal);
                }
            }
        }
    }
}
