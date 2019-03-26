//package Engine.Logic;
//
//import CustomMisc.Tree;
//import XML.StorageClasses.AStorableObject;
//import XML.FileLocations;
//import XML.StorageClasses.StorageXML;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.HashMap;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class Job extends AStorableObject {
//
//    private Integer id;
//    private String name;
//    private double meanSalary;
//    private double skillMultiplier;
//
//    public static HashMap<Integer, Job> jobList = new HashMap<>();
//
//    Job(Integer id) {
//        this.id = id;
//        jobList.put(this.id, this);
//    }
//
//    public Integer getID() {
//        return id;
//
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public double getMeanSalary() {
//        return meanSalary;
//    }
//
//    public double getSkillMultiplier() {
//        return skillMultiplier;
//    }
//
//    private static void fillJobInfo(Tree<StorableForm> storageTree) {
//        Job job = jobList.get(storageTree.getHead().get("id"));
//
//        try {
//            Job.class.getDeclaredMethod("fromStorage", Tree.class).invoke(job, storageTree);
//        } catch (NullPointerException e) {
//            throw new NullPointerException("Could not find job for " + storageTree.getHead().get("id") + ", " + storageTree.getHead().get("name"));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void fillStorageTree(Tree<StorableForm> storageTree) {
//
//    }
//
//    public Tree<StorableForm> createStorageObject() {
//        return null;
//    }
//
//    public void fromStorage(Tree<StorableForm> storedDataTree) {
//        StorableForm storedData = storedDataTree.getHead();
//        name = storedData.get("name");
//        meanSalary = Double.parseDouble(storedData.get("meanSalary"));
//        skillMultiplier = Double.parseDouble(storedData.get("skillMultiplier"));
//    }
//
//    public static void load() {
//        new StorageJobs().load();
//    }
//
//    private static class StorageJobs extends StorageXML {
//
//        public StorageJobs() {
//
//        }
//
//        public void load() {
//            CopyOnWriteArrayList<Tree<StorableForm>> data = super.load(FileLocations.jobs);
//            for (Tree<StorableForm> dataTree : data) {
//                for (Tree<StorableForm> jobTree : dataTree.getChildren().values()) {
//                    if (jobTree.getHead().getNodeName().equals("Job") && Boolean.parseBoolean(jobTree.getHead().get("implemented"))) {
//                        fillJobInfo(jobTree);
//                    }
//                }
//            }
//        }
//    }
//}
