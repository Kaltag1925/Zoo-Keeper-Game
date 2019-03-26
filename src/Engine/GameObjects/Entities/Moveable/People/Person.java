package Engine.GameObjects.Entities.Moveable.People;

import CustomMisc.Tree;
import Engine.GameObjects.Entities.Moveable.CanOpen;
import Engine.GameObjects.Entities.Moveable.Moveable;
import Engine.GameObjects.Tiles.Tile;
import Engine.Logic.MainData;
import Engine.Logic.PeopleName;
import Engine.Logic.Ticks.IUpdateable;
import Engine.Logic.Ticks.Tick;

import java.awt.*;
import java.util.ArrayList;

public class Person extends Moveable implements IUpdateable, CanOpen {

    private static final String XML_NODE_NAME = "Person";

    private String firstName;
    private String lastName;
    private String gender;
    private Integer age;
    private String birthday;
    private Double money;
    private boolean occupied;

    private static final char ICON = '☺';
    private static final double SPEED = 5;

    public static ArrayList<Person> people = new ArrayList<>();

    public Person(Tile location, String gender, String firstName, String lastName, Integer age, Double money) {
        super(location, null);

        if (gender == null) {
            int genderInt = MainData.mainData.getRandom().nextInt(2);
            switch (genderInt) {
                case 0:
                    this.gender = "male";
                    break;
                case 1:
                    this.gender = "female";
                    break;
            }
        } else {
            this.gender = gender;
        }

        if (firstName == null) {
            this.firstName = generateName(0);
        } else {
            this.firstName = firstName;
        }

        if (lastName == null) {
            this.lastName = generateName(1);
        } else {
            this.lastName = lastName;
        }

        if (age == null) {
            this.age = MainData.mainData.getRandom().nextInt(30) + 30;
        } else {
            this.age = age;
        }

        if (money == null) {
            this.money = (double) MainData.mainData.getRandom().nextInt(100) + 30;
        } else {
            this.money = money;
        }

        Tick.addToUpdateList(this);
    }

    private String generateName(int position){
        ArrayList<String> possibleNames = PeopleName.possibleNames(null, position);
        return possibleNames.get(MainData.mainData.getRandom().nextInt(possibleNames.size()));
    }

    //<editor-fold desc="Can Open">
    private boolean canOpen;

    @Override
    public boolean getCanOpen() {
        return canOpen;
    }

    @Override
    public void setCanOpen(boolean canOpen) {
        this.canOpen = canOpen;
    }
    //</editor-fold>

    public Person(Tile tile, Image icon) {
        super(tile, icon);
        people.add(this);
        setCanOpen(true);
    }

    //<editor-fold desc="Get Methods">
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getBirthday() {
        return birthday;
    }

    public Double getMoney() {
        return money;
    }

    public ArrayList<String> getNameCheckers(){
       ArrayList<String> nameCheckers = new ArrayList<>();
       nameCheckers.add(gender);
       return nameCheckers;
    }

    public boolean isOccupied() {
        return occupied;
    }
    //</editor-fold>

    //<editor-fold desc="Set Methods">
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void setOccupied(boolean next) {
        occupied = next;
    }
    //</editor-fold>

    public void update() {
        super.update();
    }

    public Person(Tree<StorableForm> storageTree) {
        super(storageTree);
        fromStorage(storageTree);
    }

    @Override
    public void fillStorageTree(Tree<StorableForm> storageTree) {
        super.fillStorageTree(storageTree);
        StorableForm head = storageTree.getHead();
        head.put("nodeName", XML_NODE_NAME);

        head.put("firstName", String.valueOf(firstName));
        head.put("lastName", String.valueOf(lastName));
        head.put("gender", String.valueOf(gender));
        head.put("age", String.valueOf(age));
        head.put("birthday", String.valueOf(birthday));
        head.put("money", String.valueOf(money));
    }

    @Override
    public void fromStorage(Tree<StorableForm> storageTree) {
        StorableForm head = storageTree.getHead();
        firstName = head.get("firstName");
        lastName = head.get("lastName");
        gender = head.get("gender");
        age = Integer.parseInt(head.get("age"));
        birthday = head.get("birthday");
        money = Double.parseDouble(head.get("money"));
    }

    @Override
    public void assignReferences(Tree<StorableForm> storageTree) {
        super.assignReferences(storageTree);
    }
}
