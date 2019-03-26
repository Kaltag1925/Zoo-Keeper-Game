package Engine.Logic.Assignments;

import Engine.GameObjects.Tiles.AnimalExhibit;
import Engine.Logic.GameTime;
import Engine.Logic.MainData;

import java.io.File;

public class Job {

    private Assignment assignment;
    private int frequency;

    public Job(Assignment assignment, int frequency) {
        this.assignment = assignment;
        this.frequency = frequency;
    }

    public void go() {
        System.out.println("you went!11!!!1!!!");
        MainData.mainData.getAssignmentManager().addAssignment(assignment);
        GameTime gameTime = MainData.mainData.getGameTime();
        gameTime.getCalendar().scheduleEvent(frequency + gameTime.getCalendar().getTick(), this::go);
    }

    public class Feed extends Job {
        private AnimalExhibit exhibit;
        private int amount;

        public Feed(AnimalExhibit exhibit, int frequency, int amount) {
            super(new Assignment("feedAnimal",
                    new File(".." + File.separator + "Tasks" + File.separator + "Feeding" + File.separator + "schedule.js"),
                    new File(".." + File.separator + "Tasks" + File.separator + "Feeding" + File.separator + "action.js"),
                    new File(".." + File.separator + "Tasks" + File.separator + "Feeding" + File.separator + "cleanUp.js"),
                    Assignment.HIGHEST_PRIORITY + 1),

                    frequency);

            this.exhibit = exhibit;
            this.amount = amount;
        }
    }

//    public class Haul extends Job {
//        private Tile destination;
//        private Entity hauling;
//
//        public Haul(Tile destination)
//    }
}
