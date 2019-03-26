package Engine.GameObjects.Entities.Moveable.People;

import Engine.GameObjects.Companies.Zoo;
import Engine.GameObjects.Entities.Moveable.Animal.AnimalReal;
import Engine.Logic.GameObjectHolder;
import Engine.Logic.MainData;
import Engine.Logic.Ticks.IUpdateable;
import Engine.Logic.Ticks.Tick;

public class VisitorManager implements IUpdateable {

    public static final double BASE_CHANCE_TO_VISIT = .08;
    public static final double FAVORITE_ANIMAL_MODIFIER = 1.01;

    public VisitorManager() {
        Tick.addToUpdateList(this);
    }

    public void createVisitors() {
        Zoo playerZoo = MainData.mainData.getPlayerZoo();
        int tries = (int) Math.ceil(Math.abs(MainData.mainData.getRandom().nextGaussian()));
        double probability = (1 - BASE_CHANCE_TO_VISIT) / Math.pow(FAVORITE_ANIMAL_MODIFIER, playerZoo.getAnimals().getAllAnimals().size());
        for (int i = 0; i < tries; i++) {
            if (MainData.mainData.getRandom().nextDouble() > probability) {
                final Visitor visitor = new Visitor(playerZoo.getEntrance(), Visitor.icon);
                playerZoo.getVisitors().add(visitor);
                playerZoo.addMoney(playerZoo.getAdmissionPrice());
                final GameObjectHolder<AnimalReal> animals = new GameObjectHolder<>();
                animals.addAll(playerZoo.getViewableAnimals());
                visitor.setAnimalsNotSeen(animals);
            }
        }
    }

    public void createVisitor() {
        Zoo playerZoo = MainData.mainData.getPlayerZoo();
        Visitor visitor = new Visitor(playerZoo.getEntrance(), Visitor.icon);
        playerZoo.getVisitors().add(visitor);
        playerZoo.addMoney(playerZoo.getAdmissionPrice());
        visitor.setAnimalsNotSeen(playerZoo.getAnimals().getAllAnimals());
    }

    public void update() {
        createVisitors();
    }
}
