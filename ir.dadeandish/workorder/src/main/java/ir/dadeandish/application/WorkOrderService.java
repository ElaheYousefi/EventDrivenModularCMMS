package ir.dadeandish.application;

import ir.dadeandish.api.*;
import ir.dadeandish.domain.WorkOrderModel;
import ir.dadeandish.domain.WorkOrderRepository;
import ir.dadeandish.domain.WorkOrderStatus;
import ir.dadeandish.event.ReadyAssignTasksEvent;
import ir.dadeandish.event.WorkOrderAssignedEvent;
import ir.dadeandish.event.WorkOrderCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class WorkOrderService implements WorkorderAPI {

    private WorkOrderRepository workOrderRepository;
    private static final Logger logger= LoggerFactory.getLogger(WorkOrderService.class);
//    private MaintenanceAPI maintenanceAPI;
//    private EmployeeAPI employeeAPI;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AssignTaskAPI assignTaskAPI;
    private final Mapper mapper;

    @Autowired
    public WorkOrderService(WorkOrderRepository workOrderRepository, AssignTaskAPI assignTaskAPI, ApplicationEventPublisher applicationEventPublisher, Mapper mapper) {
        this.workOrderRepository= workOrderRepository;
        this.applicationEventPublisher= applicationEventPublisher;
        this.assignTaskAPI= assignTaskAPI;
//        this.employeeAPI= employeeAPI;
        this.mapper= mapper;
    }

    @EventListener
    public void handle(ReadyAssignTasksEvent event){
        for(AssignTaskDTO task: event.getAssignTaskDTOList()){
            WorkOrderModel workOrderModel= addWorkOrder(task.getId(), task.getEquipId(), task.getEquipName());
            if(workOrderModel!= null)
                assignTaskAPI.proceedSchedule(task.getId());
        }
    }
//    public void generateWorkOrders(AssignTaskDTO task) {
//        List<AssignTaskDTO> tasks = assignTaskAPI.getReadyTasks();
//        System.out.println("tasks num="+ tasks.size());
//        for (AssignTaskDTO task : tasks) {
//            System.out.println("assigned task id="+ task.getId());
        //    WorkOrderModel workOrderModel= addWorkOrder(task.getId());
//            applicationEventPublisher.publishEvent(new WorkOrderAssignedEvent(workOrderModel.getId(), task.getId()));
            //this is the part which needs to calculate in event handler which needs definedTaskId. how we can get it???????????
            //DefineTaskDto defineTaskDto= maintenanceAPI.getDefineTaskById(definedTaskId);
            //LocalDate nextExecutionDate= LocalDate.now()
            //.plusDays(defineTaskDto.getPeriodDay()+ 1);
            //assignedTaskRepository.updateAssignTaskNextExecutionDate(task.getId(), nextExecutionDate);
    //    }
  //  }

    public WorkOrderModel addWorkOrder(int taskId, int equipId, String equipName){
        WorkOrderModel wo= new WorkOrderModel();
        logger.info("create new work order through scheduled task");
        wo.setAssignTaskId(taskId);
        wo.setEquipmentId(equipId);
        wo.setEquipmentName(equipName);
        wo.setDueDate(LocalDate.now().plusDays(1));
        wo.setWorkOrderStatus(WorkOrderStatus.New);

        return workOrderRepository.save(wo);
    }


    @Transactional
    public void assignWorkOrderToEmployee(int workOrderId, int employeeId) {
        //EmployeeDto employeeDto= employeeAPI.getEmployee(employeeId);
        EmployeeDto employeeDto= new EmployeeDto();
        WorkOrderModel workOrderModel= workOrderRepository.findById(workOrderId)
                        .orElseThrow(() -> new RuntimeException("work order with this id doesn't exist"));
        workOrderModel.setEmployeeId(employeeId);
//        AssignTaskDTO assignTaskDTO= assignTaskAPI.getById(workOrderModel.getAssignTaskId());
//        EquipmentDTO equipmentDTO= equipmentAPI.getEquipByID(assignTaskDTO.getEquipId());
//        workOrderModel.setEquipmentId(equipmentDTO.getId());
//        workOrderModel.setEquipmentName(equipmentDTO.getName());
        workOrderRepository.save(workOrderModel);
        applicationEventPublisher.publishEvent(
                new WorkOrderAssignedEvent(workOrderModel.getPriority(),
                        workOrderModel.getId(), workOrderModel.getDueDate(),
                        employeeDto.getName(), employeeDto.getEmail(), employeeDto.getMobile(),
                        workOrderModel.getEmployeeName()));
    }

    @Transactional
    public void addObservation(
            int workOrderId, String observResult, Enum equipmentStatus, WorkOrderStatus workOrderStatus, String employeeName) {

        WorkOrderModel workOrder =
                workOrderRepository.findById(workOrderId)
                        .orElseThrow(() ->
                                new RuntimeException("Work order not found"));

        // business validation
        if (workOrder.getWorkOrderStatus() == WorkOrderStatus.Done) {
            throw new RuntimeException(
                    "Observation already completed");
        }
        workOrder.setWorkOrderStatus(WorkOrderStatus.Done);
        workOrder.setDoneDate(LocalDate.now());
        workOrder.setObservResult(observResult);
        workOrder.setEquipmentStatus(equipmentStatus);
        workOrder.setEmployeeName(employeeName);
        workOrderRepository.save(workOrder);
        applicationEventPublisher.publishEvent(new WorkOrderCompletedEvent(
                workOrder.getEquipmentId(),
                equipmentStatus
        ));
    }

//    public List<WorkOrderResponse> getAllWorkOrders() {
//        return workOrderRepository.findAll()
//                .stream()
//                .map(workOrder -> {
//                    WorkOrderResponse response = new WorkOrderResponse();
//                    response.setWorkOrderId(workOrder.getId());
//                    response.setWorkOrderStatus(workOrder.getWorkOrderStatus());
//                    response.setDueDate(workOrder.getDueDate());
//                    response.setObservResult(workOrder.getObservResult());
//                    response.setEquipmentName(workOrder.getAssignTask().getEquipModel().getName());
//                    response.setEquipStatus(workOrder.getEquipStatus());
//                    return response;
//                })
//                .toList();
//    }

    public WorkorderDTO getWorkOrderByID(int workOrderId){
        WorkOrderModel workOrderModel= workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new RuntimeException("this workorder doesn't exist"));
        return mapper.convertWorkOrderToDTO(workOrderModel);
    }
}
