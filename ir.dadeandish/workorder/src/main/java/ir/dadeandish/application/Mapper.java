package ir.dadeandish.application;

import ir.dadeandish.api.WorkorderDTO;
import ir.dadeandish.domain.WorkOrderModel;

public class Mapper {

    public WorkorderDTO convertWorkOrderToDTO(WorkOrderModel workOrderModel){

        WorkorderDTO workorderDTO= new WorkorderDTO();
        workorderDTO.setId(workOrderModel.getId());
        workorderDTO.setTaskId(workOrderModel.getAssignTaskId());
        workorderDTO.setDueDate(workOrderModel.getDueDate());
        return workorderDTO;
    }
}
