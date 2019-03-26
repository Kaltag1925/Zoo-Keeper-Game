//package Engine.GameObjects.Entities.Moveable.People;
//
//import CustomMisc.SaveBuilder;
//import CustomMisc.Tree;
//import Engine.Logic.Job;
//import Engine.Logic.MainData;
//import XML.FileLocations;
//import XML.StorageClasses.StorageXML;
//import javafx.util.Pair;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Vector;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class LookingForJob extends Person {
//
//    private static final String XML_NODE_NAME = "LookingForJob";
//
//    private HashMap<Integer, Double> experienceMap;
//    private HashMap<Integer, Pair<Double, Double>> salaryInfoMap;
//
//    public static HashMap<Integer, LookingForJob> allLookingForJob = new HashMap<>();
//
//    //<editor-fold desc="Initialization">
////    public LookingForJob(String gender, String firstName, String lastName, Integer age, Double money, HashMap<Integer, Double> experienceMap, HashMap<Integer, Pair<Double, Double>> salaryInfoMap) {
////        super(gender, firstName, lastName, age, money);
////
////        if (experienceMap == null) {
////            this.experienceMap = createExperience();
////        } else {
////            this.experienceMap = experienceMap;
////        }
////
////        if (salaryInfoMap == null) {
////            this.salaryInfoMap = createSalaryInfo(this.experienceMap);
////        } else {
////            this.salaryInfoMap = salaryInfoMap;
////        }
////
////        allLookingForJob.put(getID(), this);
////    }
//
////    public LookingForJob() {
////        super(null, null, null, null, null);
////
////        experienceMap = createExperience();
////        salaryInfoMap = createSalaryInfo(experienceMap);
////
////        allLookingForJob.put(getID(), this);
////    }
//
//    //<editor-fold desc="Creation">
//    private static HashMap<Integer, Double> createExperience() {
//        HashMap<Integer, Double> experienceMap = new HashMap<>();
//
//        for (Job job : Job.jobList.values()) {
//            Double experience = Math.abs(MainData.mainData.getRandom().nextGaussian() + 0.20 / 6);
//            experienceMap.put(job.getID(), experience);
//        }
//
//        return experienceMap;
//    }
//
//    private static HashMap<Integer, Pair<Double, Double>> createSalaryInfo(HashMap<Integer, Double> experienceMap) {
//        HashMap<Integer, Pair<Double, Double>> salaryInfoMap = new HashMap<>();
//
//        Iterator iterator = experienceMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry entry = (Map.Entry)iterator.next();
//            Job job = Job.jobList.get(entry.getKey());
//            Double experience = (Double) entry.getValue();
//
//            double difference = Math.abs(MainData.mainData.getRandom().nextGaussian() / 20) * job.getMeanSalary();
//            double modifier = ((experience + 0.75) * job.getSkillMultiplier());
//
//            double lower = job.getMeanSalary() * modifier - difference;
//            double upper = job.getMeanSalary() * modifier + difference;
//            Pair<Double, Double> range = new Pair<>(lower, upper);
//
//            salaryInfoMap.put(job.getID(), range);
//        }
//
//        return salaryInfoMap;
//    }
//    //</editor-fold>
//    //</editor-fold>
//
//    //<editor-fold desc="Get Methods">
//    private HashMap<Integer, Double> getExperienceMap() {
//        return experienceMap;
//    }
//
//    private HashMap<Integer, Pair<Double, Double>> getSalaryInfoMap() {
//        return salaryInfoMap;
//    }
//    //</editor-fold>
//
//    @Override
//    public void update() {
//        super.update();
//    }
//
//    public LookingForJob(Tree<StorableForm> storageTree) {
//        super(storageTree);
//        fromStorage(storageTree);
//
//        Tree<StorableForm> expeienceTree = storageTree.findFirstTreeFromMethod("Experience", StorableForm::getNodeName);
//        experienceMap = SaveBuilder.read(expeienceTree);
//
//        Tree<StorableForm> salaryInfoTree = storageTree.findFirstTreeFromMethod("SalaryInfo", StorableForm::getNodeName);
//        salaryInfoMap = SaveBuilder.read(salaryInfoTree);
//
//        allLookingForJob.put(getID(), this);
//    }
//
//    @Override
//    public void fromStorage(Tree<StorableForm> storedDataTree) {
//        super.fromStorage(storedDataTree);
//    }
//
//    @Override
//    public Tree<StorableForm> createStorageObject() {
//        Tree<StorableForm> returnTree = super.createStorageObject();
//        StorableForm storableForm = returnTree.getHead();
//        storableForm.put("nodeName", XML_NODE_NAME);
//
//        Tree<StorableForm> experience = SaveBuilder.build(experienceMap, "Experience");
//        returnTree.addTreeChild(experience, true);
//
//        Tree<StorableForm> salaryInfo = SaveBuilder.build(salaryInfoMap, "SalaryInfo");
//        returnTree.addTreeChild(salaryInfo, true);
//
//        return returnTree;
//    }
//
//    public static void create(int num) {
//        for (int i = 0; i < num; i++) {
//            new LookingForJob();
//        }
//    }
//
//    public static void save() {
//        new LookingForJobStorage().save();
//    }
//
//    public static void makeVisible() {
//        new LookingForJobStorage().makeVisible();
//    }
//
//    private static class LookingForJobStorage extends StorageXML {
//        LookingForJobStorage() {
//
//        }
//
//        public void makeVisible() {
//            CopyOnWriteArrayList<Tree<StorableForm>> data = super.makeVisible(FileLocations.lookingForJob);
//            for (Tree<StorableForm> dataTree : data) {
//                for (Tree<StorableForm> lfjTree : dataTree.getChildren().values()) {
//                    if (lfjTree.getHead().getNodeName().equals(XML_NODE_NAME)) {
//                        new LookingForJob(lfjTree);
//                    }
//                }
//            }
//        }
//
//        public void save() {
//            Vector<Tree<StorableForm>> data = new Vector<>();
//            for (LookingForJob lfj : allLookingForJob.values()) {
//                data.add(lfj.createStorageObject());
//            }
//            super.save(FileLocations.lookingForJob, data);
//        }
//    }
//}
