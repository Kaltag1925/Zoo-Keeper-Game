package XML;

import java.io.File;

public class CreateSaveFolder {
    public static void createSave(String name) {
        File file = new File(FileLocations.saveLocation + File.separator + name);
        try{
            if(file.mkdirs()) {
                FileLocations.createFiles(file);

                FileLocations.setCurrentSave(file);
                FileLocations.setFiles(file);

//                 new MainData();
//                AnimalFoodType.loadFoodTypes();
//                AnimalFoodCompany.createAnimalFoodCompanies(10);
//                AnimalBodyPartTemplate.makeVisible();
//                AnimalSpecies.loadAnimalSpecies();
//                AnimalName.makeVisible();
//                AnimalCompany.createAnimalCompanies(10);
//                AIZoo.createAnimalZoos(3, 1, 2, 5);
//                PeopleName.makeVisible();
//                Job.makeVisible();
//                LookingForJob.create(10);
//                FileLocations.setFiles(file);
                save();
            } else {
                System.out.println("Directory not created");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteSave(String name){
        File file = new File(FileLocations.saveLocation + File.separator + name);
        if (file.exists()){
            if (file.delete()){
                System.out.println(file.getName() + " has been deleted");
            } else {
                System.out.println(file.getName() + " could not be deleted");
            }
        }
    }

    public static boolean loadSave(String name) throws CloneNotSupportedException {
        File file = new File(FileLocations.saveLocation + File.separator + name);
        if (file.exists()){
            FileLocations.setCurrentSave(file);
            FileLocations.setFiles(file);
//            BasicSaveData.makeVisible();
//            AnimalFoodType.loadFoodTypes();
//            AnimalFoodCompany.loadAnimalFoodCompanies();
//            AnimalFoodReal.loadFoodOwned();
//            AnimalName.makeVisible();
//            AnimalBodyPartTemplate.makeVisible();
//            AnimalSpecies.loadAnimalSpecies();
//            AnimalReal.loadAnimals();
//            AnimalCompany.loadAnimalCompanies();
//            PeopleName.makeVisible();
//            Visitor.loadVisitors();
//            AIZoo.loadAnimalZoos();
//            Job.makeVisible();
//            LookingForJob.makeVisible();
            System.out.println(file.getName() + " loaded");
            save();
            return true;
        } else {
            System.out.println(file.getName() + " not found");
            return false;
        }
    }

    public static File[] getSaves(){
        File[] fileArray = FileLocations.saveLocation.listFiles();
        if (fileArray == null || fileArray.length == 0){
            return null;
        } else {
            return fileArray;
        }
    }

    public static void save(){
//        AnimalReal.saveAnimals();
//        AnimalCompany.saveAnimalCompanies();
//        BasicSaveData.save();
//        Visitor.saveVisitors();
//        AnimalFoodReal.saveFoodOwned();
//        AnimalFoodCompany.saveAnimalFoodCompanies();
//        AIZoo.saveAnimalZoos();
//        LookingForJob.save();
    }
}