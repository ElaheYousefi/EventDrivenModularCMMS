package ir.dadeandish.event;

import ir.dadeandish.api.AssignTaskDTO;

import java.util.List;

public class ReadyAssignTasksEvent {

    private final List<AssignTaskDTO> assignTaskDTOList;

    public ReadyAssignTasksEvent(List<AssignTaskDTO> assignTaskDTOList) {
        this.assignTaskDTOList = assignTaskDTOList;
    }

    public List<AssignTaskDTO> getAssignTaskDTOList() {
        return assignTaskDTOList;
    }
}
