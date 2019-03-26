package XML;

import java.io.File;
import java.io.IOException;

public class FileLocations {

    public static File saveLocation;
    public static File currentSave;

    public static File taskFolder = new File(".." + File.separator + "Tasks");

    public static File animalDefs = new File("Animal_Definitions.xml");
    public static File animalNames = new File("Animal_Names.xml");
    public static File animalBodyPartTemplates = new File("Animal_Body_Part_Templates.xml");
    public static File peopleNames = new File("People_Names.xml");
    public static File foodTypes = new File("Food_Types.xml");
    public static File jobs = new File("Jobs.xml");

    public static File animals;
    public static File animalCompanies;
    public static File basicSaveData;
    public static File visitors;
    public static File food;
    public static File animalFoodCompanies;
    public static File animalZoos;
    public static File lookingForJob;

    public static File temporary;

    public static void setSaveLocation(){
        String path = FileLocations.animalDefs.getAbsolutePath();
        int i = path.lastIndexOf(File.separator);
        String path2 = path.substring(0, i);
        i = path2.lastIndexOf(File.separator);
        String pathString = path2.substring(0, i + 1);
        File file = new File(pathString + File.separator + "Saves");
        if (file.exists()){
            saveLocation = file;
        } else {
            try{
                if(file.mkdirs()) {
                    System.out.println("Directory Created");
                    saveLocation = file;
                } else {
                    System.out.println("Directory is not created");
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void setCurrentSave(File location){
        currentSave = location;
    }

    public static void setFiles(File location){
        animals = new File(location + File.separator + "Animals.xml");
        animalCompanies = new File(location + File.separator + "Animal_Companies.xml");
        basicSaveData = new File(location + File.separator + "Basic_Save_Data.xml");
        visitors = new File(location + File.separator + "Visitors.xml");
        food = new File(location + File.separator + "Food.xml");
        animalFoodCompanies = new File(location + File.separator + "Animal_Food_Companies.xml");
        animalZoos = new File(location + File.separator + "Animal_Zoos.xml");
        lookingForJob = new File(location + File.separator + "Looking_For_Job.xml");
        temporary = new File(location + File.separator + "Animal_Definitions.xml");
    }

    public static void createFiles(File location) {
        createSpecificFile(location, "Animals.xml");
        createSpecificFile(location, "Animal_Companies.xml");
        createSpecificFile(location, "Basic_Save_Data.xml");
        createSpecificFile(location, "Visitors.xml");
        createSpecificFile(location, "Food.xml");
        createSpecificFile(location, "Animal_Food_Companies.xml");
        createSpecificFile(location, "Animal_Zoos.xml");
        createSpecificFile(location, "Looking_For_Job.xml");
        createSpecificFile(location, "Animal_Definitions.xml");
    }

    private static void createSpecificFile(File location, String name) {
        File newFile = new File(location + File.separator + name);
        try {
            if(newFile.createNewFile()){
                System.out.println(newFile.getName() + " created");
            } else {
                System.out.println(newFile.getName() + " not created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
