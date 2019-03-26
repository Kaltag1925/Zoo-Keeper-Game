package Engine.GameObjects.Entities.Moveable.People;

import Engine.GameObjects.Tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Employee extends Person {

    private static final String XML_NODE_NAME = "Employee";

    private String job;
    private Double salary = 10d;
    private HashMap<String, Double> experience;

    public static Image icon = null;
    static {
        try {
            icon = ImageIO.read(new File(".." + File.separator + "Icons" + File.separator + "employee.png"));
        } catch (IOException e) {
        }
    }

    public static HashMap<Integer, Employee> allEmployees = new HashMap<>();
    public static HashMap<Integer, Employee> ownedEmployees = new HashMap<>();

    public Employee(Tile location, String gender, String firstName, String lastName, Integer age, Double money, String job, Double salary, HashMap<String, Double> experience) {
        super(location, gender, firstName, lastName, age, money);

        if (job == null) {
            //job = createJob();
        } else {
            this.job = job;
        }

        if (experience == null) {
            //experience = createExperience();
        } else {
            this.experience = experience;
        }

        if (salary == null) {
            //salary = createSalary(job, experience);
        } else {
            this.salary = salary;
        }
    }

    public Employee(Tile location) {
        super(location, icon);
    }

    public double getSalary() {
        return salary;
    }
}
