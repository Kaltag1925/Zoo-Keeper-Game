package Engine.Logic.Assignments;

public interface Assignable {

//    int EMPLOYEE_TYPE = 0;
//    int VISITOR_TYPE = 1;
//    int ANIMAL_TYPE = 2;
//
//    int getAssignableType();

    Assignment getCurrentAssignment();

    void setCurrentAssignment(Assignment assignment);

    Task getCurrentTask();

    void setCurrentTask(Task task);
}
