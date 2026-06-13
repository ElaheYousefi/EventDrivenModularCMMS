package ir.dadeandish.api;

public interface AssignTaskAPI {

    public void proceedSchedule(int taskId);
    public AssignTaskDTO getById(int taskId);
}
